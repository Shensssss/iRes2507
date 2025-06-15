package web.major.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import web.clinic.entity.Clinic;
import web.major.dao.ClinicMajorDao;

@Repository
public class ClinicMajorDaoImpl implements ClinicMajorDao{
	@PersistenceContext
	private Session session;
	  @Override
	  public List<Clinic> findClinicsByMajorIdOrAll(Integer majorId) {
		  System.out.println(majorId);
		    if (majorId != null) {
		        String hql = "select cm.clinic from ClinicMajor cm where cm.major.majorId = :majorId";
		        return session.createQuery(hql, Clinic.class)
		                      .setParameter("majorId", majorId)
		                      .getResultList();
		    } else {
		        String sql = "SELECT * FROM clinic";
		        return session.createNativeQuery(sql, Clinic.class)
		                      .getResultList();
		    }
		}
}
