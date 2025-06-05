package web.clinic.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import core.util.HibernateUtil;
import web.clinic.dao.RegisterDao;
import web.clinic.entity.Clinic;


@Repository
public class RegisterDaoImpl implements RegisterDao{
	public Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

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

	@Override
	public int insert(Clinic clinic) {
		getSession().persist(clinic);
		return 1;
	}
//		Session session = sessionFactory.openSession();
//		try {
//			Transaction tx = session.beginTransaction();
//			session.persist(clinic);
//			tx.commit();
//			return clinic.getClinicId();
//		} catch (Exception e) {
//			session.getTransaction().rollback();
//			e.printStackTrace();
//			return 0;
//		}
//	}

	@Override
	public Clinic selectbyAccount(String account) {
		String hql = "FROM Clinic WHERE account = :account";
		return getSession()
				.createQuery(hql, Clinic.class)
				.setParameter("account", account)
				.uniqueResult();
			//	.get(Clinic.class, account);
			
		
//		Session session = getSession();
//		CriteriaBuilder cBuilder = session.getCriteriaBuilder();
//		CriteriaQuery<Clinic> cQuery = cBuilder.createQuery(Clinic.class);
//		Root<Clinic> root = cQuery.from(Clinic.class);
//		cQuery.where(cBuilder.equal(root.get("account"), account));
//		return session.createQuery(cQuery).uniqueResult;
	}



}
