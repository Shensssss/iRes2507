package web.clinic.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import web.clinic.dao.ClinicDAO;
import web.clinic.entity.Clinic;

@Repository
public class ClinicDaoImpl implements ClinicDAO {
	@PersistenceContext
	private Session session;

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
		return session.get(Clinic.class, id);
	}

	@Override
	public int updatePsd(Clinic clinic) {
		return 0;
	}

	@Override
	public Integer findClinicIdByAgencyId(String agencyId) {
		return session.createQuery("SELECT c.clinicId FROM Clinic c WHERE c.agencyId = :id", Integer.class)
				.setParameter("id", agencyId).getSingleResult();
	}
}
