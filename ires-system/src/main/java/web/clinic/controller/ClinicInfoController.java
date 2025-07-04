package web.clinic.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.pojo.Core;
import web.clinic.entity.Clinic;
import web.clinic.service.ClinicInfoService;

@RestController
@RequestMapping("clinic/clinicInfo")
public class ClinicInfoController{ 
	@Autowired
	private ClinicInfoService clinicInfoService;
	
	@PutMapping("editBasicInfo")
	public ResponseEntity<Core> editBasicInfo(@RequestBody Clinic clinic, HttpSession session) {
        Core core = new Core();

        Clinic loggedInClinic = (Clinic) session.getAttribute("clinic");
        if (loggedInClinic == null) {
            core.setStatusCode(401);
            core.setMessage("診所尚未登入");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
        }

        clinic.setClinicId(loggedInClinic.getClinicId());
        int result = clinicInfoService.editClinic(clinic);

        if (result == 1) {
            core.setStatusCode(200);
            core.setMessage("基本資料更新成功");
            return ResponseEntity.ok(core);
        } else {
            core.setStatusCode(400);
            core.setMessage("基本資料更新失敗");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(core);
        }
    }
	
	@PutMapping("editBusinessHours")
	public ResponseEntity<Core> editBusinessHours(@RequestBody Clinic clinic, HttpSession session) {
        Core core = new Core();

        Clinic loggedInClinic = (Clinic) session.getAttribute("clinic");
        if (loggedInClinic == null) {
            core.setStatusCode(401);
            core.setMessage("診所尚未登入");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
        }

        clinic.setClinicId(loggedInClinic.getClinicId());
        int result = clinicInfoService.editBusinessHours(clinic);

        if (result == 1) {
            core.setStatusCode(200);
            core.setMessage("營業時間更新成功");
            return ResponseEntity.ok(core);
        } else {
            core.setStatusCode(400);
            core.setMessage("營業時間更新失敗");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(core);
        }
    }
	
	@GetMapping("showInfo/{clinicId}")
    public ResponseEntity<Core> showInfo(@PathVariable Integer clinicId, HttpSession session) {
        Core core = new Core();

        Clinic loggedInClinic = (Clinic) session.getAttribute("clinic");
        if (loggedInClinic == null) {
            core.setStatusCode(401);
            core.setMessage("診所尚未登入");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
        }
        
        if (!loggedInClinic.getClinicId().equals(clinicId)) {
            core.setStatusCode(403);
            core.setMessage("無權限查詢其他診所資料");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(core);
        }

        Clinic clinic = clinicInfoService.getClinicById(clinicId);
        if (clinic == null) {
            core.setStatusCode(404);
            core.setMessage("查無診所資料");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(core);
        }

        core.setStatusCode(200);
        core.setMessage("查詢成功");
        core.setData(clinic);
        return ResponseEntity.ok(core);
    }
}