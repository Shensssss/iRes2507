package web.patient.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.pojo.Core;
import web.patient.entity.Patient;
import web.patient.service.PatientService;

@RestController
@RequestMapping("clinic")
public class ClinicPatientProfileController {
	@Autowired
	private PatientService patientService;
	
	@GetMapping("searchPatient")
	public ResponseEntity<Core> searchedPatient(
			@RequestParam("name") String name,
			@RequestParam(value = "birthday", required = false)String birthday,
			@RequestParam(value = "phone", required = false) String phone
			, HttpSession session){
		
		Core core = new Core();
		Boolean loggedin = (Boolean) session.getAttribute("loggedin");
		
		if (loggedin == null || !loggedin) {
	        core.setStatusCode(401);
	        core.setMessage("診所尚未登入");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
	    }else {
	    	if(name == null || name.isEmpty() || 
				((phone == null || phone.isEmpty()) && (birthday == null || birthday.isEmpty()))) {
				core.setStatusCode(400);
				core.setMessage("查詢條件不足");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(core);
			}else {
				List<Patient> patient = patientService.clinicSearch(name, birthday, phone);
				if(patient == null || patient.isEmpty()) {
		        	core.setStatusCode(404);
		        	core.setMessage("查無此病人");
		        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(core);
				}else {
					core.setStatusCode(200);
					core.setMessage("載入病人資料成功");
					core.setData(patient);
					return ResponseEntity.ok(core);
				}
			}
	    }
	}
}
