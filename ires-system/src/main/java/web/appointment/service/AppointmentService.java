package web.appointment.service;

import web.appointment.entity.Appointment;
import web.clinic.entity.Clinic;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface AppointmentService {

    void saveOrUpdate(Appointment appointment);

    Appointment findById(String appointmentId);

    List<Appointment> findAll();

    void deleteById(String appointmentId);

    List<Appointment> getAppointmentsByDate(Date date);

    List<Appointment> getAppointmentsByDateAndPeriod(Date date, int timePeriod);

    List<Appointment> getAppointmentsByClinicDateAndPeriod(Integer clinicId, Date date, int timePeriod);

    List<Appointment> getHistoryByPatientId(int patientId, Integer clinicId);

    void save(Appointment appointment);

    Appointment updateAppointment(Appointment a);

    boolean deleteAppointment(String id);

    void reserveAppointments(Integer clinicId, List<Appointment> appointments);
    
    List<Appointment> findByPatientId(Integer patientId);

    int resolveTimePeriod(Clinic clinic, LocalTime now);
}