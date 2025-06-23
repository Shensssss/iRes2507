package web.clinic.dao;

import core.dao.CoreDao;
import web.clinic.entity.Clinic;

public interface RegisterDao extends CoreDao<Clinic, String> {
	int insert(Clinic clinic);

	Clinic selectbyAccount(String account);

	Clinic selectForLogin(String account, String password);

	Clinic selectForPassword(String agencyId, String account);

	int updatePassword(String account, String password);
}
