package web.appointment.service.impl;

import web.appointment.dao.AppointmentDAO;
import web.appointment.dao.impl.AppointmentDAOImpl;
import web.appointment.entity.Appointment;
import web.appointment.service.AppointmentService;

import java.util.Date;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    @Override
    public void saveOrUpdate(Appointment appointment) {
        if (appointment.getAppointmentId() == null || appointment.getAppointmentId().isEmpty()) {
            appointmentDAO.insert(appointment);
        } else {
            appointmentDAO.update(appointment);
        }
    }

    @Override
    public Appointment findById(String appointmentId) {
        return appointmentDAO.selectById(appointmentId);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentDAO.selectAll();
    }

    @Override
    public void deleteById(String appointmentId) {
        appointmentDAO.deleteById(appointmentId);
    }

    @Override
    public List<Appointment> getAppointmentsByDate(Date date) {
        return appointmentDAO.findByDate(date);
    }

    @Override
    public List<Appointment> getAppointmentsByDateAndPeriod(Date date, int timePeriod) {
        return appointmentDAO.findByDateAndPeriod(date, timePeriod);
    }
}