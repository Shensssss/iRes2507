package web.appointment.dao;

import java.util.List;

import web.appointment.entity.Notification;

public interface clinicNotificationDAO {

	List<Notification> findByClinicId(int clinic_id);

	List<Object[]> selectNotificationMsgByClinicId(int clinic_id);

	int updateReadStatus(String appointment_id);

}
