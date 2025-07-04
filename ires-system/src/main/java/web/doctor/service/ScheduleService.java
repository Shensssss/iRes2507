package web.doctor.service;

import java.util.List;

import web.doctor.entity.Schedule;

public interface ScheduleService {

	int addSchedule(Schedule schedule);
	
	int editSchedule(Schedule schedule);

	List<Schedule> showSchedule(Integer doctorId);

}
