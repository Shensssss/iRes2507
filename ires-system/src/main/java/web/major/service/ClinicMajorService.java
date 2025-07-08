package web.major.service;

import java.util.List;
import web.clinic.entity.Clinic;
import web.major.entity.ClinicMajor;
import web.major.entity.Major;

public interface ClinicMajorService {
	List<Clinic> getClinicsByMajorId(Integer majorId);
	List<Major> getMajorByClinicId(Integer clinicId);
	int addClinicMajor(ClinicMajor clinicMajor);
	int editClinicMajor(Integer loggedinClinicId, List<Integer> selectedMajorIds);
}
