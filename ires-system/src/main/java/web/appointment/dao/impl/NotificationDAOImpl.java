package web.appointment.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import web.appointment.dao.NotificationDAO;
import web.appointment.entity.Notification;

@Repository
public class NotificationDAOImpl implements NotificationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Notification notification) {
        entityManager.persist(notification);
    }

    @Override
    public Notification findById(String id) {
        return entityManager.find(Notification.class, id);
    }

    @Override
    public List<Notification> findByPatientId(int patientId) {
        String jpql = "SELECT n FROM Notification n WHERE n.patient.patientId = :pid ORDER BY n.sentDatetime DESC";
        return entityManager.createQuery(jpql, Notification.class)
                .setParameter("pid", patientId)
                .getResultList();
    }

    @Override
    public void update(Notification notification) {
        entityManager.merge(notification);
    }
}

