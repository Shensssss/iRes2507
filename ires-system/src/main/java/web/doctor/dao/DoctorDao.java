package web.doctor.dao;

import java.util.List;

import core.dao.CoreDao;
import web.doctor.entity.Doctor;

public interface DoctorDao extends CoreDao<Doctor, Integer>{
	
	//按診所別搜尋所有醫師
	List<Doctor> selectAllByClinicId(Integer clinicId);
	
	//按診所別依姓名搜尋醫師，避免醫師姓名重複時搜尋到其他診所醫師
	List<Doctor> selectByClinicIdAndDoctorName(Integer clinicId, String doctorName);
}
