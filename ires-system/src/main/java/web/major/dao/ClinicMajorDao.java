package web.major.dao;

import java.util.List;

import web.clinic.entity.Clinic;
import web.major.entity.ClinicMajor;
import web.major.entity.Major;

public interface ClinicMajorDao {
	List<Clinic> findClinicsByMajorIdOrAll(Integer majorId);
	List<Major> findMajorByClinicsId(Integer clinicId);
	int insert(ClinicMajor clinicMajor);
	int update(ClinicMajor clinicMajor);
	int deleteByClinicId(Integer clinicId);
}

