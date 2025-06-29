package web.appointment.service.impl;

import core.util.CommonUtil;
import web.appointment.dao.AppointmentDAO;
import web.appointment.entity.Appointment;
import web.appointment.service.AppointmentService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
    private AppointmentDAO appointmentDAO;
    @Autowired
    private CommonUtil commonUtil;

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

    public List<Appointment> getHistoryByPatientId(int patientId) {
        return appointmentDAO.findByPatientId(patientId);
    }

    @Override
    public void save(Appointment appointment) {
        appointmentDAO.insert(appointment);
    }

    public Appointment updateAppointment(Appointment a) {
        Appointment origin = appointmentDAO.selectById(a.getAppointmentId());
        if (origin == null) return null;

        boolean dateChanged = !origin.getAppointmentDate().equals(a.getAppointmentDate());
        boolean periodChanged = !origin.getTimePeriod().equals(a.getTimePeriod());
        boolean doctorChanged = !origin.getDoctorId().equals(a.getDoctorId());

        origin.setAppointmentDate(a.getAppointmentDate());
        origin.setTimePeriod(a.getTimePeriod());
        origin.setDoctorId(a.getDoctorId());

        if (dateChanged || periodChanged || doctorChanged) {
            int newReserveNo = commonUtil.getNextReserveNo(
                    origin.getClinicId(),
                    origin.getDoctorId(),
                    origin.getAppointmentDate(),
                    origin.getTimePeriod()
            );
            origin.setReserveNo(newReserveNo);
        }

        appointmentDAO.update(origin);
        return origin;
    }

    public boolean deleteAppointment(String id) {
        Appointment a = appointmentDAO.selectById(id);
        if (a == null) return false;
        appointmentDAO.deleteById(a.getAppointmentId());
        return true;
    }


}