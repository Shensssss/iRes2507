package web.doctor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.doctor.dao.DoctorDao;
import web.doctor.entity.Doctor;
import web.doctor.service.DoctorService;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService{

	@Autowired
	private DoctorDao doctorDao;

	@Override
	public int addDoctor(Doctor doctor) {
		// 1. name不可以為空
		if (doctor.getDoctorName() == null || doctor.getDoctorName().isEmpty()) {
			throw new IllegalArgumentException("醫師姓名為空");
		}
		
		// 2. name不可以重複
		Integer clinicId = doctor.getClinic().getClinicId();
		List<Doctor> existed = doctorDao.selectByClinicIdAndDoctorName(clinicId, doctor.getDoctorName());
		if (!existed.isEmpty()) {
			throw new IllegalArgumentException("醫師姓名重複");
		}

	    //clinicId若是前端傳來的doctor物件參數之一需要驗證避免被攻擊?或是可以直接從servlet加上這個屬性?
		
		// 3. 執行insert
		return doctorDao.insert(doctor);
	}

	@Override
	public int deleteDoctor(Integer doctorId) {
		if(doctorId == null) {
			throw new IllegalArgumentException("查無此醫師ID");
		}
		Doctor doctor = doctorDao.selectById(doctorId);
		if (doctor == null) {
			throw new IllegalArgumentException("醫師資料異常");
		}
		return doctorDao.deleteById(doctorId);
	}

	@Override
	public int editDoctor(Doctor doctor) {
		// 1. name不可以為空
		if (doctor.getDoctorName() == null || doctor.getDoctorName().isEmpty()) {
			throw new IllegalArgumentException("醫師姓名為空");
		}

		// 2. name不可以重複，但要排除自己(當同名的id和自己id不同就是跟別人姓名重複)
		Integer clinicId = doctor.getClinic().getClinicId();
		List<Doctor> existed = doctorDao.selectAllByClinicId(clinicId);

		boolean isDuplicate = false;
		for (int i = 0; i < existed.size(); i++) {
			Doctor d = existed.get(i);
			if (d.getDoctorId()!= doctor.getDoctorId()) {
				isDuplicate = true;
			    break;
			}
		}

		if (isDuplicate) {
		    throw new IllegalArgumentException("醫師姓名重複");
		}else {
		// 3. 執行update
		return doctorDao.update(doctor);
		}
	}

	@Override
	public List<Doctor> showAllDoctors(Integer clinicId) {
		return doctorDao.selectAllByClinicId(clinicId);
	}

	@Override
	public List<Doctor> showSearchedByName(Integer clinicId, String doctorName) {
		return doctorDao.selectByClinicIdAndDoctorName(clinicId, doctorName);
	}

}
