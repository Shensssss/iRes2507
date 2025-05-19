package web.appointment.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import core.util.HibernateUtil;
import web.appointment.dao.AppointmentDAO;
import web.appointment.entity.Appointment;

import java.util.Date;
import java.util.List;

public class AppointmentDAOImpl implements AppointmentDAO {

    @Override
    public int insert(Appointment appointment) {
        saveOrUpdate(appointment); 
        return 1;
    }

    @Override
    public int update(Appointment appointment) {
        saveOrUpdate(appointment); 
        return 1;
    }

    @Override
    public Appointment selectById(String id) {
        return findById(id);
    }

    @Override
    public List<Appointment> selectAll() {
        return findAll(); 
    }

    public void saveOrUpdate(Appointment appointment) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(appointment);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Appointment findById(String appointmentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Appointment.class, appointmentId);
        }
    }

    public List<Appointment> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Appointment", Appointment.class).list();
        }
    }

    @Override
    public int deleteById(String id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Appointment appointment = session.get(Appointment.class, id);
            if (appointment != null) {
                session.delete(appointment);
            }
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return 0;
        }
    }    

    @Override
    public List<Appointment> findByDate(Date date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Appointment WHERE appointmentDate = :today", Appointment.class)
                    .setParameter("today", date)
                    .getResultList();
        }
    }

    @Override
    public List<Appointment> findByDateAndPeriod(Date date, int timePeriod) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT a FROM Appointment a\r\n"
	            	   + " JOIN FETCH a.doctor d\r\n"
	            	   + " JOIN FETCH a.clinic c\r\n"
	            	   + " JOIN FETCH a.patient p\r\n"
	            	   + " WHERE a.appointmentDate = :date\r\n"
	            	   + "  AND a.timePeriod = :period\r\n"
	            	   + " ORDER BY a.reserveNo ASC";
//        	String hql = "FROM Appointment a WHERE a.appointmentDate = :date AND a.timePeriod = :period ORDER BY a.reserveNo ASC";
            List<Appointment> list = session.createQuery(hql, Appointment.class)
                .setParameter("date", date)
                .setParameter("period", timePeriod)
                .getResultList();

            return list;
        }
    }
}