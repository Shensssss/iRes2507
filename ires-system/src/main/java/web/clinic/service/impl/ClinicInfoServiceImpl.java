package web.clinic.service.impl;

import java.util.HashMap;
import java.util.Map;

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
public abstract class ClinicInfoServiceImpl implements ClinicInfoService {
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
        if (editedClinic.getClinicName() != null) existing.setClinicName(editedClinic.getClinicName());
        if (editedClinic.getPhone() != null) existing.setPhone(editedClinic.getPhone());
        if (editedClinic.getAddressCity() != null) existing.setAddressCity(editedClinic.getAddressCity());
        if (editedClinic.getAddressTown() != null) existing.setAddressTown(editedClinic.getAddressTown());
        if (editedClinic.getAddressRoad() != null) existing.setAddressRoad(editedClinic.getAddressRoad());
        if (editedClinic.getWeb() != null) existing.setWeb(editedClinic.getWeb());
        if (editedClinic.getRegistrationFee() != null) existing.setRegistrationFee(editedClinic.getRegistrationFee());
        if (editedClinic.getMemo() != null) existing.setMemo(editedClinic.getMemo());
        if (editedClinic.getProfilePicture() != null) existing.setProfilePicture(editedClinic.getProfilePicture());

        return clinicInfoDao.update(existing);
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

	@Override
	public Map<String, String> getOpenPeriod(Integer clinicId) {
		Clinic clinic = clinicInfoDao.selectById(clinicId);
        if (clinic == null) {
            return null;
        }

        Map<String, String> periods = new HashMap<>();
        periods.put("weekMorning", clinic.getWeekMorning());
        periods.put("weekAfternoon", clinic.getWeekAfternoon());
        periods.put("weekNight", clinic.getWeekNight());
        return periods;
	}

}
