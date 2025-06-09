package web.clinic.dao;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import core.dao.CoreDao;
import web.clinic.entity.Clinic;

@Repository
public interface ClinicDao extends CoreDao<Clinic, String>  {

	Clinic selectbyClinic(String clinic_id);
	
	Clinic updatePsd(Clinic clinic);	
	
//	@PersistenceContext
//	private Session sessions;
//	
//	@Override
//	public Clinic selectById(Integer clinic_id) {
//
//		return session.get(Clinic.class, clinic_id);
//	}

}
