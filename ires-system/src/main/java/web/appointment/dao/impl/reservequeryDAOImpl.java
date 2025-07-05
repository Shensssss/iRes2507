package web.appointment.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import web.appointment.dao.reservequeryDAO;
import web.appointment.entity.Appointment;

// 此檔案可以合在 AppointmentDAOImpl
@Repository
public class reservequeryDAOImpl implements reservequeryDAO {
	@PersistenceContext
	private Session session;

	@Override
	public int insert(Appointment appointment) {
		session.persist(appointment);
		return 1;
	}

	@Override
	public int deleteById(String id) {
		Appointment appointment = session.load(Appointment.class, id);
		session.remove(appointment);
		return 1;
	}

	@Override
	public int update(Appointment pojo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Appointment selectById(String id) {
		return session.get(Appointment.class, id);
	}

	@Override
	public List<Appointment> selectAll() {

		final String hql = "FROM Appointment ORDER BY reserveNo";
		return session.createQuery(hql, Appointment.class).getResultList();
	}

	@Override // 為什麼用不到？ helen bug 待處理
	public List<Object[]> findByclinicid_doctorid_DateAndPeriod(int clinic_id, int doctor_id, Date date,
			int timePeriod) {

//		String hql = "SELECT a.reserveNo, p.name, d.doctorName, a.status "
		String hql = "SELECT a.reserveNo, p.name, d.doctorName,case a.status when 1 then '已報到' else '未報到' end "
				+ "FROM Appointment a "
				+ "LEFT JOIN a.patient p " 
				+ "LEFT JOIN a.doctor d " 
				+ "WHERE a.clinic.clinicId = :clinic_id "
				+ "AND a.doctor.doctorId = :doctor_id " 
				+ "AND a.appointmentDate = :date "
				+ "AND a.timePeriod = :period " 
				+ "ORDER BY a.reserveNo";
		return session.createQuery(hql, Object[].class).setParameter("clinic_id", clinic_id)
				.setParameter("doctor_id", doctor_id).setParameter("date", date).setParameter("period", timePeriod)
				.getResultList();
	}

}
