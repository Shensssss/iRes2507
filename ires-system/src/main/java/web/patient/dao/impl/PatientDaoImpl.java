package web.patient.dao.impl;

import java.util.List;


import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import web.patient.dao.PatientDao;
import web.patient.entity.Patient;

@Repository
public class PatientDaoImpl implements PatientDao {
	@PersistenceContext
	private Session session;

	@Override
	public int insert(Patient patient) {
		saveOrUpdate(patient);
		return 1;
	}

	@Override
	public Patient selectById(Integer id) {
		return session.get(Patient.class, id);
	}

	@Override
	public int deleteById(Integer id) {
		Patient patient = session.load(Patient.class, id);
		session.remove(patient);
		return 1;
	}

	@Override
	public int update(Patient patient) {
		final StringBuilder hql = new StringBuilder().append("UPDATE Patient SET ");
		final String password = patient.getPassword();
		if (password != null && !password.isEmpty()) {
			hql.append("password = :password,");
		}
		hql.append("name = :name,")
			.append("gender = :gender,")
			.append("birthday = :birthday,")
			.append("phone = :phone,")
			.append("address = :address,")
			.append("emergency_content = :emergency_content,")
			.append("emergency_name = :emergency_name,")
			.append("relation = :relation,")
			.append("blood_type = :blood_type,")
			.append("notes = :notes,")
			.append("profile_picture = :profile_picture,")
			.append("update_time = NOW() ")
			.append("WHERE email = :email");

		Query<?> query = session.createQuery(hql.toString());
		if (password != null && !password.isEmpty()) {
			query.setParameter("password", password);
		}
		return query.setParameter("name", patient.getName())
					.setParameter("gender", patient.getGender())
					.setParameter("birthday", patient.getBirthday())
					.setParameter("phone", patient.getPhone())
					.setParameter("address", patient.getAddress())
					.setParameter("emergency_content", patient.getEmergencyContent())
					.setParameter("emergency_name", patient.getEmergencyName())
					.setParameter("relation", patient.getRelation())
					.setParameter("blood_type", patient.getBloodType())
					.setParameter("notes", patient.getNotes())
					.setParameter("profile_picture", patient.getProfilePicture())
					.executeUpdate();
	}

	@Override
	public List<Patient> selectAll() {
		final String hql = "from Patient order by patientId";
		return session.createQuery(hql, Patient.class)
				.getResultList();
	}

	public void saveOrUpdate(Patient patient) {
	    session.saveOrUpdate(patient);
	}

	@Override
	public Patient selectByEmail(String email) {
		CriteriaBuilder cBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Patient> cQuery = cBuilder.createQuery(Patient.class);
		Root<Patient> root = cQuery.from(Patient.class);
		cQuery.where(cBuilder.equal(root.get("email"), email));

		return session.createQuery(cQuery).uniqueResult();
	}

	@Override
	public Patient selectForLogin(String email, String password) {
		String sql = "select * from patient where email = :email and password = :password";
		System.out.println(email);
		System.out.println(password);
		return session
				.createNativeQuery(sql, Patient.class)
				.setParameter("email", email)
				.setParameter("password", password)
				.uniqueResult();
	}

	@Override
	public Patient findById(int patientId) {
	    return selectById(patientId);
	}

	@Override
	public List<Patient> findReservedPatientsByKeyword(String keyword, int offset, int pageSize, int clinicId) {
		String hql = "SELECT DISTINCT a.patient FROM Appointment a " +
				"WHERE a.clinic.id = :clinicId " +
				"AND (a.patient.name LIKE :keyword OR a.patient.phone LIKE :keyword) " +
				"ORDER BY a.patient.name ASC";

		return session.createQuery(hql, Patient.class)
				.setParameter("clinicId", clinicId)
				.setParameter("keyword", "%" + keyword + "%")
				.setFirstResult(offset)
				.setMaxResults(pageSize)
				.getResultList();
	}

	@Override
	public long countReservedPatientsByKeyword(String keyword, int clinicId) {
		String hql = "SELECT COUNT(DISTINCT a.patient.id) FROM Appointment a " +
				"WHERE a.clinic.id = :clinicId " +
				"AND (a.patient.name LIKE :keyword OR a.patient.phone LIKE :keyword) ";

		return session.createQuery(hql, Long.class)
				.setParameter("clinicId", clinicId)
				.setParameter("keyword", "%" + keyword + "%")
				.uniqueResult();
	}
}
