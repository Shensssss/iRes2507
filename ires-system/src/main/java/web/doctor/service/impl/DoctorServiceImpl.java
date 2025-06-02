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
	private DoctorDao doctorDAO;

	@Override
	public int insert(Doctor doctor) {
		return doctorDAO.insert(doctor);
	}

	@Override
	public int deleteById(Integer doctorId) {
		return doctorDAO.deleteById(doctorId);
	}

	@Override
	public int update(Doctor doctor) {
		return doctorDAO.update(doctor);
	}

	@Override
	public List<Doctor> selectAllByClinicId(Integer clinicId) {
		return doctorDAO.selectAllByClinicId(clinicId);
	}

	@Override
	public List<Doctor> selectByClinicIdAndDoctorName(Integer clinicId, String doctorName) {
		return doctorDAO.selectByClinicIdAndDoctorName(clinicId, doctorName);
	}

}
