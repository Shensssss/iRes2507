package web.clinic.dao;

import core.dao.CoreDao;
import web.clinic.entity.Clinic;

public interface ClinicInfoDao extends CoreDao<Clinic, String> {

	int updateInfo(Clinic clinic);
}
