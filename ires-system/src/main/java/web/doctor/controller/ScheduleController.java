package web.doctor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.pojo.Core;
import web.doctor.entity.Schedule;
import web.doctor.service.ScheduleService;

@RestController
@RequestMapping("doctor")
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping("addSchedule")
	public ResponseEntity<Core> addSchedule(@RequestBody Schedule schedule) {
	    Core core = new Core();
	    int result = scheduleService.addSchedule(schedule);
	    if(result == 1){
	        core.setMessage("休假資料儲存成功");
	        core.setStatusCode(200);
	        core.setData(scheduleService.showSchedule(schedule.getDoctor().getDoctorId()));
	        return ResponseEntity.ok(core);

	    } else {
	        core.setMessage("休假資料新增失敗");
	        core.setStatusCode(500);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(core);
	    }
	}

}
