package web.major.service;

import java.util.List;
import web.clinic.entity.Clinic;

public interface ClinicMajorService {
	List<Clinic> getClinicsByMajorId(Integer majorId);
}
