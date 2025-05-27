package web.clinic.dao;

import java.util.List;
import core.dao.CoreDao;
import web.clinic.entity.Clinic;

public interface ClinicDao extends CoreDao<Clinic, String> {

	List<Clinic> UpdatePsd(int clinicId);

}
