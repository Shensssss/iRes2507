package web.clinic.dao.impl;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import web.clinic.entity.Clinic;


@Repository
public class ClinicDaoImpl {
	
	@PersistenceContext
	private Session session;
	
	public int updateObj(Clinic clinic) {
		session.update(clinic);
		return 1;
	}
	
	public int updatePsd(Clinic clinic) {
		final StringBuilder hql = new StringBuilder().append("UPDATE clinic SET ");

		final String password = clinic.getPassword();
		if (password != null && !password.isEmpty()) {
			hql.append("password = :password,");

		}
		hql.append("nickname = :nickname,").append("pass = :pass,").append("roleId = :roleId,")
				.append("updater = :updater,").append("lastUpdatedAt = NOW() ").append("WHERE username = :username");

		Query<?> query = session.createQuery(hql.toString());
		if (password != null && !password.isEmpty()) {
			query.setParameter("password", password);

		}
//		return query.setParameter("nickname", member.getNickname()).setParameter("pass", member.getPass())
//				.setParameter("roleId", member.getRoleId()).setParameter("updater", member.getUpdater())
//				.setParameter("username", member.getUsername()).executeUpdate();
		return 1;

	}

}
