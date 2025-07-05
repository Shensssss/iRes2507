package web.clinic.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import web.clinic.dao.ClinicInfoDao;
import web.clinic.entity.Clinic;

@Repository
public class ClinicInfoDaoImpl implements ClinicInfoDao {
	@PersistenceContext
	private Session session;

	//註冊時即建立，用不到此方法
	@Override
	public int insert(Clinic clinic) {
		session.persist(clinic);
		return 1;
	}
	
	//診所無權刪除診所，用不到此方法
	@Override
	public int deleteById(Integer clinicId) {
		Clinic clinic = session.get(Clinic.class, clinicId);
        if (clinic == null) {
            return 0;
        }
        session.remove(clinic);
        return 1;
	}

	@Override
	public int update(Clinic editedClinic) {
		session.update(editedClinic);
	    return 1;
	}

	@Override
	public Clinic selectById(Integer clinicId) {
		return session.get(Clinic.class, clinicId);
	}

	@Override
	public List<Clinic> selectAll() {
		return session.createQuery("FROM Clinic", Clinic.class).list();
	}

}
