package web.clinic.service.impl;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.clinic.dao.ClinicInfoDao;
import web.clinic.entity.Clinic;
import web.clinic.service.ClinicInfoService;

@Service
@Transactional
public class ClinicInfoServiceImpl implements ClinicInfoService {
	@Autowired
	private ClinicInfoDao clinicInfoDao;
	
	@PersistenceContext
	private Session session;

	@Override
	public int editClinic(Clinic editedClinic) {
		Clinic existing = clinicInfoDao.selectById(editedClinic.getClinicId());
        if (existing == null) {
            return 0;
        }
        return clinicInfoDao.update(editedClinic);
	}

	@Override
	public int editBusinessHours(Clinic editedClinic) {
		Clinic existing = clinicInfoDao.selectById(editedClinic.getClinicId());
        if (existing == null) {
            return 0;
        }
        return clinicInfoDao.updateBusinessHours(editedClinic);
	}

	@Override
	public Clinic getClinicById(Integer clinicId) {
		return clinicInfoDao.selectById(clinicId);
	}

}
