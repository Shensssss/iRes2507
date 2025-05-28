package web.clinic.service.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import core.util.HibernateUtil;
import web.clinic.dao.RegisterDao;
import web.clinic.dao.impl.RegisterDaoImpl;
import web.clinic.entity.Clinic;
import web.clinic.service.RegisterService;

public class RegisterServiceImpl implements RegisterService{
	
	public RegisterDao dao = new RegisterDaoImpl();

	
	@Override
	public String register(Clinic clinic) {
		
		
		String clinicName = clinic.getClinicName();
		if (clinicName == null || clinicName.length() < 1 || clinicName.length() > 20) {
			return "診所名字長度需介1~20";
		}
	
		String agencyId = clinic.getAgencyId();
		if (agencyId == null || agencyId.length() != 10) {
			return "機構代碼長度需為10";
		}
		
		String phone = clinic.getPhone();
		if (phone == null || phone.length() < 10 || phone.length() > 10) {
			return "電話長度需為10";
		}
		
		String addressCity = clinic.getAddressCity();
		if (addressCity == null || addressCity.length() < 3 || addressCity.length() > 3) {
			return "城市長度為3";
		}
		
		String addressTown = clinic.getAddressTown();
		if (addressTown == null || addressTown.length() < 2 || addressTown.length() > 4) {
			return "鄉鎮長度介於2~4";
		}
		
		String addressRoad = clinic.getAddressRoad();
		if (addressRoad == null || addressRoad.length() < 1 || addressRoad.length() > 50) {
			return "道路長度介於1~50";
		}
		
		String account = clinic.getAccount();
		if (account == null || account.length() < 1 || account.length() > 50) {
			return "email長度介於1~50";
		}
		
		String password = clinic.getPassword();
		if (password == null || password.length() < 1 || password.length() > 50) {
			return "password長度介於1~50";
		}
					
		
		
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			Transaction tx = session.beginTransaction();
			if(dao.selectbyAccount(account) != null) {
				return "此email已被註冊";	
			}
				
			int count = dao.insert(clinic);
			if (count < 1) {
				tx.rollback();
				return "系統錯誤，請聯絡管理員";
			} 
			//session.persist(clinic);
			tx.commit();
			return null;
		} catch (Exception e){
				session.getTransaction().rollback();
				e.printStackTrace();
				return null;
		}
	
		

	}

}
