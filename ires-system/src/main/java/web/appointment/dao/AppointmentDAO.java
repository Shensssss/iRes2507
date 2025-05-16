package web.appointment.dao;

import web.appointment.entity.Appointment;
import java.util.Date;
import java.util.List;

import core.dao.CoreDao;

public interface AppointmentDAO extends CoreDao<Appointment, String> {
    List<Appointment> findByDate(Date date);
    List<Appointment> findByDateAndPeriod(Date date, int timePeriod);
}