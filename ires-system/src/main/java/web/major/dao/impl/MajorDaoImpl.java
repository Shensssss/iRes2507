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
	    String sql = "select major_id, major_name, create_id, create_time, update_id, update_time FROM major";
	    return session
	            .createNativeQuery(sql, Major.class)
	            .getResultList();
	}
}
