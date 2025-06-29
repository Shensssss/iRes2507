package web.patient.service;

import web.patient.entity.Patient;

import java.util.Map;

public interface PatientService {
	
	Patient register(Patient patient);
	
	Patient login(Patient patient);
	
	Patient findById(int patientId); 

	void updatePatient(Patient patient);
	
	Patient edit(Patient reqPatient);

	Map<String, Object> getReservedPatientsWithKeyword(Integer clinicId, String keyword, int page, int pageSize);

	Patient findByPhone(String phone);

}
