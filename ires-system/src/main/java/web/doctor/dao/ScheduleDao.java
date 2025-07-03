package web.doctor.dao;

import java.util.List;

import core.dao.CoreDao;
import web.doctor.entity.Schedule;

public interface ScheduleDao extends CoreDao<Schedule, Integer>{
	
	public List<Schedule> selectByClinicIdAndDoctorId(Integer clinicId, Integer doctorId);

}
