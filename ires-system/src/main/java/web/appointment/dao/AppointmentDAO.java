package web.appointment.dao;

import web.appointment.entity.Appointment;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import core.dao.CoreDao;

public interface AppointmentDAO extends CoreDao<Appointment, String> {
    List<Appointment> findByDate(Date date);

    List<Appointment> findByDateAndPeriod(Date date, int timePeriod);

    boolean existsByPatientIdAndClinicId(Integer patientId, Integer clinicId);

    boolean existsDuplicateAppointment(int patientId, Date date);

    List<Appointment> findByPatientId(int patientId);

    Long countAppointmentsByGroup(int clinicId, int doctorId, Date date, int timePeriod);
}