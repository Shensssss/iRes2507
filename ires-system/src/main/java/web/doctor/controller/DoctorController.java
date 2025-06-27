package web.doctor.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.pojo.Core;
import web.clinic.entity.Clinic;
import web.doctor.entity.Doctor;
import web.doctor.service.DoctorService;

@RestController
@RequestMapping("doctor")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	// 新增醫師
    @PostMapping("add")
    public ResponseEntity<Core> addDoctor(@RequestBody Doctor doctor, HttpSession session) {
        Core core = new Core();
        Clinic loggedInClinic = (Clinic) session.getAttribute("loggedInClinic");
        if (loggedInClinic == null) {
        	//模擬登入
        	loggedInClinic = new Clinic();
            loggedInClinic.setClinicId(1);
//            core.setStatusCode(401);
//            core.setMessage("診所尚未登入");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
        }

        doctor.setClinic(loggedInClinic);
        int result = doctorService.addDoctor(doctor);
        if (result == 1) {
            core.setStatusCode(201);
            core.setMessage("新增成功");
            core.setData(doctorService.showAllDoctors(loggedInClinic.getClinicId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(core);
        } else {
            core.setStatusCode(400);
            core.setMessage("新增失敗");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(core);
        }
    }
    
    // 編輯醫師
    @PostMapping("edit")
    public ResponseEntity<Core> editDoctor(@RequestBody Doctor doctor, HttpSession session) {
        Core core = new Core();
        Clinic loggedInClinic = (Clinic) session.getAttribute("loggedInClinic");
        if (loggedInClinic == null) {
        	//模擬登入
        	loggedInClinic = new Clinic();
            loggedInClinic.setClinicId(1);
//            core.setStatusCode(401);
//            core.setMessage("診所尚未登入");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
        }
        
        doctor.setClinic(loggedInClinic);
        int result = doctorService.editDoctor(doctor);
        if (result == 1) {
            core.setStatusCode(200);
            core.setMessage("存檔成功");
            core.setData(doctorService.showAllDoctors(loggedInClinic.getClinicId()));
            return ResponseEntity.ok(core);
        } else {
            core.setStatusCode(400);
            core.setMessage("存檔失敗");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(core);
        }
    }

    // 刪除醫師
    @DeleteMapping("delete")
    public ResponseEntity<Core> deleteDoctor(@RequestBody Map<String, Integer> payload, HttpSession session) {
        Core core = new Core();

        Clinic loggedInClinic = (Clinic) session.getAttribute("loggedInClinic");
        if (loggedInClinic == null) {
        	//模擬登入
        	loggedInClinic = new Clinic();
            loggedInClinic.setClinicId(1);
//            core.setStatusCode(401);
//            core.setMessage("診所尚未登入");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
        }

        Integer doctorId = payload.get("doctorId");
        if (doctorId == null) {
            core.setStatusCode(400);
            core.setMessage("刪除失敗");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(core);
        }

        int result = doctorService.deleteDoctor(doctorId, loggedInClinic.getClinicId());

        if (result == 1) {
            core.setStatusCode(200);
            core.setMessage("刪除成功");
            return ResponseEntity.ok(core);
        } else {
            core.setStatusCode(404);
            core.setMessage("找不到欲刪除的醫施");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(core);
        }
    }

    // 顯示全部醫師
    @GetMapping("showAll")
    public ResponseEntity<Core> showAllDoctors(@RequestParam(required = false)Integer clinicId, HttpSession session) {
        Core core = new Core();
//        病人查詢時從前端傳入clinicId
        if(clinicId != null) {
        	List<Doctor> doctors = doctorService.showAllDoctors(clinicId);
        	core.setStatusCode(200);
            core.setMessage("載入成功");
            core.setData(doctors);
            return ResponseEntity.ok(core);
        }
        
//        診所使用時前端不傳入clinicId,需要從後端取得
        Clinic loggedInClinic = (Clinic) session.getAttribute("loggedInClinic");
        if(loggedInClinic == null) {
   	     //模擬登入
        	loggedInClinic = new Clinic();
            loggedInClinic.setClinicId(1);
//        	core.setStatusCode(401);
//	        core.setMessage("診所尚未登入");
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
	    }
        
	    clinicId = loggedInClinic.getClinicId();
	    
	
	    List<Doctor> doctors = doctorService.showAllDoctors(clinicId);
	    core.setStatusCode(200);
	    core.setMessage("載入成功");
	    core.setData(doctors);
	    return ResponseEntity.ok(core);
    }

    // 根據醫師姓名搜尋
    @PostMapping("showSearchedByName")
    public ResponseEntity<Core> showSearchedByName(@RequestBody Doctor doctor, HttpSession session) {
        Core core = new Core();
        Clinic loggedInClinic = (Clinic) session.getAttribute("loggedInClinic");
        if (loggedInClinic == null) {
        	//模擬登入
        	loggedInClinic = new Clinic();
            loggedInClinic.setClinicId(1);
//            core.setStatusCode(401);
//            core.setMessage("診所尚未登入");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
        }

        List<Doctor> doctors = doctorService.showSearchedByName(loggedInClinic.getClinicId(), doctor.getDoctorName());
        core.setStatusCode(200);
        core.setMessage("載入成功");
        core.setData(doctors);
        return ResponseEntity.ok(core);
    }
}
