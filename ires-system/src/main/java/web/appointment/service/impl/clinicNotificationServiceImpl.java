package web.appointment.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.appointment.dao.clinicNotificationDAO;
import web.appointment.entity.Notification;
import web.appointment.service.clinicNotificationService;

@Service
@Transactional
public class clinicNotificationServiceImpl implements clinicNotificationService {

	@Autowired
	private clinicNotificationDAO clinicNotificationDAO;

	@Override
	public List<Notification> findByClinicId(int clinic_id) {
		return clinicNotificationDAO.findByClinicId(clinic_id);
	}

	@Override
	public List<Object[]> selectNotificationMsgByClinicId(int clinic_id) {
		return clinicNotificationDAO.selectNotificationMsgByClinicId(clinic_id);
	}
}
