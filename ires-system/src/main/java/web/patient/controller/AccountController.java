package web.patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.patient.entity.Patient;
import web.patient.service.PatientService;

@RestController
@RequestMapping("account")
public class AccountController {

	@Autowired
	private PatientService patientService;

	// 取得病患的帳戶資訊
	@GetMapping("patient")
	public Patient getInfo(@SessionAttribute("patient") Patient patient) {
		return patient;
	}

	// 回傳更新後的病患資料
	@PutMapping("patient")
	public Patient edit(@SessionAttribute("patient") Patient patient, @RequestBody Patient reqPatient) {
		final Integer patientId = patient.getPatientId();
		reqPatient.setPatientId(patientId);
		return patientService.edit(reqPatient);
	}
}