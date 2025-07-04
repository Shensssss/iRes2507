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
	//更新基本資料
	public int update(Clinic updatedClinic) {
		Clinic clinic = session.get(Clinic.class, updatedClinic.getClinicId());

	    clinic.setClinicName(updatedClinic.getClinicName());
	    clinic.setPhone(updatedClinic.getPhone());
	    clinic.setAddressCity(updatedClinic.getAddressCity());
	    clinic.setAddressTown(updatedClinic.getAddressTown());
	    clinic.setAddressRoad(updatedClinic.getAddressRoad());
	    clinic.setWeb(updatedClinic.getWeb());
	    clinic.setRegistrationFee(updatedClinic.getRegistrationFee());
	    clinic.setMemo(updatedClinic.getMemo());
	    clinic.setProfilePicture(updatedClinic.getProfilePicture());
	    return 1;
	}
	
	@Override
	//更新營業時間
	public int updateBusinessHours(Clinic updatedClinic) {
	    Clinic clinic = session.get(Clinic.class, updatedClinic.getClinicId());
	    
	    clinic.setMorning(updatedClinic.getMorning());
	    clinic.setAfternoon(updatedClinic.getAfternoon());
	    clinic.setNight(updatedClinic.getNight());
	    clinic.setWeekMorning(updatedClinic.getWeekMorning());
	    clinic.setWeekAfternoon(updatedClinic.getWeekAfternoon());
	    clinic.setWeekNight(updatedClinic.getWeekNight());
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
