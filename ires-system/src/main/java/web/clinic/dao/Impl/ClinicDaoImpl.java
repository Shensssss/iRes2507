package web.clinic.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.clinic.dao.ClinicDao;
import web.clinic.entity.Clinic;


@Repository
@Service
@Transactional
public class ClinicDaoImpl implements ClinicDao {

	// session 與 dao 的方式未測試
	@PersistenceContext
	private Session session;
	
	@Autowired
	private ClinicDao dao;
	
	@Override
	public Clinic selectbyClinic(String clinic_id) {
		return session.get(Clinic.class, clinic_id);
		
//		String hql = "FROM Clinic WHERE account = :account";
//		return getSession()
//				.createQuery(hql, Clinic.class)
//				.setParameter("account", account)
//				.uniqueResult();
//			//	.get(Clinic.class, account);
	}
	
	@Override
	public Clinic updatePsd(Clinic clinic) {
		final Clinic oClinic = dao.selectById(clinic.getClinicId().toString());
		clinic.setPassword(oClinic.getPassword());
		
		clinic.setUpdateId(clinic.getClinicId().toString());
		final int resultCount = dao.update(clinic);
//		clinic.setSuccessful(resultCount > 0);
//		clinic.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
		return clinic;
	}


	@Override
	public int insert(Clinic pojo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Clinic pojo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Clinic selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clinic> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
