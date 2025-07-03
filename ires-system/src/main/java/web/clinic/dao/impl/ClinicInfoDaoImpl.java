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

	@Override
	public int insert(Clinic pojo) {
		throw new RuntimeException("NO-OP");
	}
	
	@Override
	public int deleteById(String id) {
		throw new RuntimeException("NO-OP");
	}

	@Override
	public int update(Clinic pojo) {
		throw new RuntimeException("NO-OP");
	}

	@Override
	public Clinic selectById(String id) {
		throw new RuntimeException("NO-OP");
	}

	@Override
	public List<Clinic> selectAll() {
		throw new RuntimeException("NO-OP");
	}

	@Override
	public int updateInfo(Clinic clinic) {
		String hql = "UPDATE Clinic SET clinicName = :clinicName ,agencyId = :agencyId ,phone = :phone ,addressCity = :addressCity , addressTown = :addressTown ,addressRoad = :addressRoad ,web = :web ,registrationFee = :registrationFee ,memo = :memo ,morning = :morning , afternoon = :afternoon ,night = :night ,weekMorning = :weekMorning ,weekAfternoon = :weekAfternoon ,weekNight = :weekNight WHERE account = :account";;
		return session.createQuery(hql)
				.setParameter("clinicName", clinic.getClinicName())
	            .setParameter("agencyId", clinic.getAgencyId())
	            .setParameter("phone", clinic.getPhone())
	            .setParameter("addressCity", clinic.getAddressCity())
	            .setParameter("addressTown", clinic.getAddressTown())
	            .setParameter("addressRoad", clinic.getAddressRoad())
	            .setParameter("web", clinic.getWeb())
	            .setParameter("registrationFee", clinic.getRegistrationFee())
	            .setParameter("memo", clinic.getMemo())
	            .setParameter("morning", clinic.getMorning())
	            .setParameter("afternoon", clinic.getAfternoon())
	            .setParameter("night", clinic.getNight())
	            .setParameter("weekMorning", clinic.getWeekMorning())
	            .setParameter("weekAfternoon", clinic.getWeekAfternoon())
	            .setParameter("weekNight", clinic.getWeekNight())
	            .setParameter("account", clinic.getAccount())
	            .executeUpdate();
	}

}
