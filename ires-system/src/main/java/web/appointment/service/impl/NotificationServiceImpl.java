package web.appointment.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.appointment.dao.NotificationDAO;
import web.appointment.entity.Notification;
import web.appointment.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDAO notificationDao;

    @Override
    public void createNotification(Notification notification) {
        notificationDao.save(notification);
    }

    @Override
    public Notification findById(String id) {
        return notificationDao.findById(id);
    }

    @Override
    public List<Notification> findByPatientId(int patientId) {
        return notificationDao.findByPatientId(patientId);
    }

    @Override
    public void markAsRead(String notificationId) {
        Notification n = notificationDao.findById(notificationId);
        if (n != null && !Boolean.TRUE.equals(n.getReadStatus())) {
            n.setReadStatus(true);
            n.setReadDatetime(new Timestamp(System.currentTimeMillis()));
            notificationDao.update(n);
        }
    }
}


