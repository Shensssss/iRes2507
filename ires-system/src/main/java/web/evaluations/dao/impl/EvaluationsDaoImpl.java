package web.evaluations.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import web.evaluations.dao.EvaluationsDao;
import web.evaluations.entity.Evaluations;
import web.patient.entity.Patient;

@Repository
public class EvaluationsDaoImpl implements EvaluationsDao {
	@PersistenceContext
	private Session session;

	@Override
	public List<Evaluations> findEvaluationsByClinicId(Integer clinicId) {

		String hql = "from Evaluations ev where ev.clinic.clinicId = :clinicId";
		return session.createQuery(hql, Evaluations.class)
				.setParameter("clinicId", clinicId)
				.getResultList();

	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Evaluations pojo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Evaluations selectById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Evaluations> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Evaluations evaluations) {
		saveOrUpdate(evaluations);
		return 1;
	}
	
	public void saveOrUpdate(Evaluations evaluations) {
	    session.saveOrUpdate(evaluations);
	}
}
