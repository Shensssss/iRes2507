package web.clinic.dao.impl;

import org.springframework.stereotype.Repository;
import web.appointment.entity.Appointment;
import web.clinic.dao.ClinicDAO;
import web.clinic.entity.Clinic;

import java.util.List;

@Repository
public class ClinicDaoImpl implements ClinicDAO {

	@Override
	public int insert(Clinic clinic) {
		return 1;
	}

	@Override
	public int update(Clinic clinic) {
		return 1;
	}

	@Override
	public int deleteById(Integer id) {
		return 0;
	}

	public List<Clinic> selectAll() {
		return null;
	}

	@Override
	public Clinic selectById(Integer id) {
		return null;
	}

	@Override
	public int updatePsd(Clinic clinic) {
		return 0;
	}
	 
}
