package web.appointment.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import web.appointment.dao.clinicNotificationDAO;
import web.appointment.entity.Notification;

@Repository
public class clinicNotificationDAOImpl implements clinicNotificationDAO {
	@PersistenceContext
	private Session session;

	// 目前用patientId先代替clinic_id，待clinic有通知的資料後，再 hql 裡的內容
	@Override
	public List<Notification> findByClinicId(int clinic_id) {
		String hql = "SELECT n FROM Notification n WHERE n.patient.patientId = :pid ORDER BY n.sentDatetime DESC";
		return session.createQuery(hql, Notification.class).setParameter("pid", clinic_id).getResultList();
	}

	@Override
	public List<Object[]> selectNotificationMsgByClinicId(int clinic_id) {
		String hql = " SELECT a.appointmentId, p.name, n.message, n.notificationType, n.readStatus "
				+ ", DATE_FORMAT(n.sentDatetime, '%Y/%m/%d')" + "    FROM Notification n "
				+ "    LEFT JOIN n.patient p " + "    LEFT JOIN n.appointment a "
				+ "    WHERE n.notificationType NOT IN ('預約報到通知', 'appointment') " + "    	AND n.readStatus = 0 "
				+ "    	AND a.clinic.clinicId = :clinic_id " + "    ORDER BY n.sentDatetime DESC";

		System.out.println("查看hql結果:" + hql);

		return session.createQuery(hql, Object[].class).setParameter("clinic_id", clinic_id).getResultList();
	}

}
