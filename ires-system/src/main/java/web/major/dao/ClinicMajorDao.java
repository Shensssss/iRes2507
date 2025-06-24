package web.major.dao;

import java.util.List;

import web.clinic.entity.Clinic;
import web.major.entity.Major;

public interface ClinicMajorDao {
	List<Clinic> findClinicsByMajorIdOrAll(Integer majorId);
	List<Major> findMajorByClinicsId(Integer clinicId);
}

