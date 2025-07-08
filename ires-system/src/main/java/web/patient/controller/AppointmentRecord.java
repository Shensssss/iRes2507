package web.patient.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.appointment.entity.Appointment;
import web.appointment.service.AppointmentService;
import web.patient.entity.Patient;
import web.patient.service.FavoriteService;

@RestController
@RequestMapping("reservation")
public class AppointmentRecord {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private FavoriteService favoriteService; // ← 這行你需要加上

	@GetMapping
	public List<Appointment> getAppointments(HttpSession session) {
		Patient patient = (Patient) session.getAttribute("patient");
		if (patient == null)
			return new ArrayList<>();

		Integer id = patient.getPatientId();
		if (id == null)
			return new ArrayList<>();

		return appointmentService.findByPatientId(id);
	}

	@GetMapping("/favorites/check")
	public ResponseEntity<Map<String, Boolean>> checkFavorite(@RequestParam Integer clinicId, HttpSession session) {
		Patient patient = (Patient) session.getAttribute("patient");
		if (patient == null || patient.getPatientId() == null) {
			return ResponseEntity.badRequest().body(Map.of("exists", false));
		}

		boolean exists = favoriteService.hasFavoriteClinic(patient.getPatientId(), clinicId);
		return ResponseEntity.ok(Map.of("exists", exists));
	}
}