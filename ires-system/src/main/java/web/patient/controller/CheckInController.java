package web.patient.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.patient.entity.Patient;
import web.patient.service.PatientService;

@RestController
@RequestMapping("checkIn")
public class CheckInController {

	@Autowired
	private PatientService patientService;

	@PostMapping
	public Patient checkIn(@SessionAttribute Patient patient, @RequestBody Map<String, String> payload) {
		String code = payload.get("appointmentId");
		return patientService.checkIn(patient, code);
	}
}
