package web.major.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import web.major.dao.MajorDao;
import web.major.entity.Major;

@Repository
public class MajorDaoImpl implements MajorDao{
	@PersistenceContext
	private Session session;

	@Override
	public List<Major> selectAll() {
		String hql = "from Major";
		return session
		        .createQuery(hql, Major.class)
		        .getResultList();
	}
	
	@Override
	public Major selectMajorById(Integer majorId) {
	    String hql = "FROM Major m WHERE m.majorId = :majorId";
	    return session.createQuery(hql, Major.class)
	                  .setParameter("majorId", majorId)
	                  .uniqueResult();
	}
	
}
