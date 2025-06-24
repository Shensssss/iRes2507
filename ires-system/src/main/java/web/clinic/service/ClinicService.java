package web.clinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import web.clinic.entity.Clinic;

public interface ClinicService {
	void editPsd(Clinic clinic);
	List<Clinic> filterClinics(Integer majorId, LocalDate date, LocalTime startTime, LocalTime endTime);
	Clinic findById(Integer clinicId); 
}
