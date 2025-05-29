package web.appointment.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.appointment.dao.NotificationDAO;
import web.appointment.entity.Notification;
import web.appointment.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDAO notificationDAO;

    @Override
    @Transactional
    public String createNotification(Notification notification) {

        if (notification.getNotificationId() == null) {
            notification.setNotificationId(UUID.randomUUID().toString());
        }
        if (notification.getSentDatetime() == null) {
            notification.setSentDatetime(new Timestamp(System.currentTimeMillis()));
        }
        if (notification.getReadStatus() == null) {
            notification.setReadStatus(false);
        }
        
        String type = notification.getNotificationType();
        String appointmentId = notification.getAppointment().getAppointmentId();

        boolean exists = notificationDAO.existsByTypeAndAppointment(type, appointmentId);

        if (exists) {
            return "Appointment " + appointmentId + " already reminded for " + type;
        }

        notificationDAO.save(notification);
        return "Appointment " + appointmentId + " reminder created.";
    }

    @Override
    public Notification findById(String id) {
        return notificationDAO.findById(id);
    }

    @Override
    public List<Notification> findByPatientId(int patientId) {
        return notificationDAO.findByPatientId(patientId);
    }

    @Override
    public void markAsRead(String notificationId) {
        Notification n = notificationDAO.findById(notificationId);
        if (n != null && !Boolean.TRUE.equals(n.getReadStatus())) {
            n.setReadStatus(true);
            n.setReadDatetime(new Timestamp(System.currentTimeMillis()));
            notificationDAO.update(n);
        }
    }
}


