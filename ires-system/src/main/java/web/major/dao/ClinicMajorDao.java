package web.major.dao;

import java.util.List;

import web.clinic.entity.Clinic;

public interface ClinicMajorDao {
	List<Clinic> findClinicsByMajorIdOrAll(Integer majorId);
}

