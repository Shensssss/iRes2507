package web.clinic.service.Impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.clinic.dao.ClinicDao;
import web.clinic.entity.Clinic;
import web.clinic.service.ClinicService;

@Service
@Transactional
public class ClinicServiceImpl implements ClinicService{
	@Autowired
	private ClinicDao clinicDao;
	
	@Override
	public void editPsd(Clinic clinic) {
		clinicDao.update(clinic);
		
	}

}
