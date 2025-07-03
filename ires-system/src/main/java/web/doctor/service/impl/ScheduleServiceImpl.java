package web.doctor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.doctor.dao.ScheduleDao;
import web.doctor.entity.Schedule;
import web.doctor.service.ScheduleService;


@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService{
	
	@Autowired
	private ScheduleDao scheduleDao;

	@Override
	public int addSchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Schedule> showSchedule(Integer doctorId) {
		// TODO Auto-generated method stub
		return null;
	}

}
