package web.clinic.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.clinic.dao.ClinicDAO;
import web.clinic.entity.Clinic;
import web.clinic.service.ClinicService;

@Service
@Transactional
public class ClinicServiceImpl implements ClinicService{
	@Autowired
	private ClinicDAO clinicDAO;
	
	@Override
	public void editPsd(Clinic clinic) {
		clinicDAO.update(clinic);
		
	}

}
