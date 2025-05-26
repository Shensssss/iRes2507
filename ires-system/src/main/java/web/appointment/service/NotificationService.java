package web.appointment.service;

import java.util.List;

import web.appointment.entity.Notification;

public interface NotificationService {
    void createNotification(Notification notification);
    Notification findById(String id);
    List<Notification> findByPatientId(int patientId);
    void markAsRead(String notificationId);
}
