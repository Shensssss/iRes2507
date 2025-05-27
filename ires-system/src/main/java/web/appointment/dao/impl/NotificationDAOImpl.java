package web.appointment.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import web.appointment.dao.NotificationDAO;
import web.appointment.entity.Notification;

@Repository
public class NotificationDAOImpl implements NotificationDAO {

    @PersistenceContext
    private Session session;

    @Override
    public void save(Notification notification) {
        session.persist(notification);
    }

    @Override
    public Notification findById(String id) {
        return session.find(Notification.class, id);
    }

    @Override
    public List<Notification> findByPatientId(int patientId) {
        String hql = "SELECT n FROM Notification n WHERE n.patient.patientId = :pid ORDER BY n.sentDatetime DESC";
        return session.createQuery(hql, Notification.class)
                .setParameter("pid", patientId)
                .getResultList();
    }

    @Override
    public void update(Notification notification) {
        session.merge(notification);
    }

    @Override
    public boolean existsByTypeAndAppointment(String type, String appointmentId) {
        String hql = "SELECT COUNT(n) FROM Notification n " +
                      "WHERE n.notificationType = :type AND n.appointment.appointmentId = :appointmentId";
        Long count = session.createQuery(hql, Long.class)
                .setParameter("type", type)
                .setParameter("appointmentId", appointmentId)
                .getSingleResult();
        return count > 0;
    }
}

