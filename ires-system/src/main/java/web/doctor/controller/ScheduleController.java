package web.doctor.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.pojo.Core;
import web.clinic.entity.Clinic;
import web.doctor.entity.Schedule;
import web.doctor.service.ScheduleService;

@RestController
@RequestMapping("doctor")
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping("addSchedule")
	public ResponseEntity<Core> addSchedule(@RequestBody Schedule schedule, HttpSession session) {
	    
		Core core = new Core();
        Clinic loggedInClinic = (Clinic) session.getAttribute("clinic");
        
        if (loggedInClinic == null) {
            core.setStatusCode(401);
            core.setMessage("診所尚未登入");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
        }
        
        schedule.setClinic(loggedInClinic);
	    int result = scheduleService.addSchedule(schedule);
	    if(result == 1){
	        core.setMessage("休假資料儲存成功");
	        core.setStatusCode(200);
	        core.setData(scheduleService.showSchedule(schedule.getDoctor().getDoctorId()));
	        return ResponseEntity.ok(core);

	    } else {
	        core.setMessage("休假資料新增失敗");
	        core.setStatusCode(400);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(core);
	    }
	}
	
	@PutMapping("editSchedule")
	public ResponseEntity<Core> editSchedule(@RequestBody Schedule schedule, HttpSession session) {
		Core core = new Core();
		Clinic loggedInClinic = (Clinic) session.getAttribute("clinic");

		if (loggedInClinic == null) {
			core.setStatusCode(401);
			core.setMessage("診所尚未登入");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
		}

		int result = scheduleService.editSchedule(schedule);
		if (result == 1) {
			core.setStatusCode(200);
			core.setMessage("休假資料修改成功");
			core.setData(scheduleService.showSchedule(schedule.getDoctor().getDoctorId()));
			return ResponseEntity.ok(core);
		} else {
			core.setStatusCode(400);
			core.setMessage("修改失敗");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(core);
		}
	}
	
	@GetMapping("getSchedule/{doctorId}")
	public ResponseEntity<Core> getSchedule(@PathVariable Integer doctorId, HttpSession session) {
		Core core = new Core();
		Clinic loggedInClinic = (Clinic) session.getAttribute("clinic");

		if (loggedInClinic == null) {
			core.setStatusCode(401);
			core.setMessage("診所尚未登入");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
		}

        List<Schedule> schedules = scheduleService.showSchedule(doctorId);
        core.setStatusCode(200);
        core.setMessage("查詢成功");
        core.setData(schedules);
        return ResponseEntity.ok(core);
    }

}
