package web.patient.service;

import java.util.List;

import web.patient.entity.Patient;

public interface PatientService {
	
	Patient register(Patient patient);
	
	Patient login(Patient patient);
	
	Patient findById(int patientId); 

	void updatePatient(Patient patient);
	
	Patient edit(Patient reqPatient);
	
	List<Patient> clinicSearch(String name, String birthday, String phone);

}
