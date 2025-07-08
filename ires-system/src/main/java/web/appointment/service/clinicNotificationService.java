package web.appointment.service;

import java.util.List;

import web.appointment.entity.Notification;

public interface clinicNotificationService {
	List<Notification> findByClinicId(int clinic_id);

	List<Object[]> selectNotificationMsgByClinicId(int clinic_id);

}
