package web.patient.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.appointment.entity.Appointment;
import web.appointment.service.AppointmentService;
import web.patient.entity.Patient;

@RestController
@RequestMapping("reservation")
public class AppointmentRecord {

	@Autowired
	private AppointmentService appointmentService;
	@GetMapping
	public List<Appointment> getAppointments(HttpSession session) {
	    Patient patient = (Patient) session.getAttribute("patient");
	    if (patient == null) return new ArrayList<>();

	    Integer id = patient.getPatientId();
	    if (id == null) return new ArrayList<>();

	    return appointmentService.findByPatientId(id);
	}
}