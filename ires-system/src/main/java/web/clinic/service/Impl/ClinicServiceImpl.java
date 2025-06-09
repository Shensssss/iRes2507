package web.clinic.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.clinic.dao.ClinicDao;
import web.clinic.entity.Clinic;
import web.clinic.service.ClinicService;

@Service
@Transactional
public class ClinicServiceImpl implements ClinicService {
	@Autowired
	private ClinicDao clinicDao;

//	@Override
//	public void editPsd(Clinic clinic) {
//		//helen debug
//		//clinicDao.update(clinic);
//		
//		
//	}

	@Autowired
	@Override
	public Clinic editPsd(Clinic clinic) {
		final Clinic oClinic = clinicDao.selectbyClinic(clinic.getClinicId().toString());
		clinic.setPassword(oClinic.getPassword());
		final int resultCount = clinicDao.update(clinic);
//		clinic.setSuccessful(resultCount > 0);
//		clinic.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
		 return clinic;
	}

}
