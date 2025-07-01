package web.appointment.service;

import web.appointment.entity.Appointment;

import java.util.Date;
import java.util.List;

public interface AppointmentService {

    void saveOrUpdate(Appointment appointment);

    Appointment findById(String appointmentId);

    List<Appointment> findAll();

    void deleteById(String appointmentId);

    List<Appointment> getAppointmentsByDate(Date date);

    List<Appointment> getAppointmentsByDateAndPeriod(Date date, int timePeriod);

    List<Appointment> getHistoryByPatientId(int patientId);

    void save(Appointment appointment);

    Appointment updateAppointment(Appointment a);

    boolean deleteAppointment(String id);

    void reserveAppointments(Integer clinicId, List<Appointment> appointments);
}