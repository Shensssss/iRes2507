package web.patient.service;

import web.patient.entity.Patient;

public interface PatientService {
	
	Patient register(Patient patient);
	
	Patient login(Patient patient);
	
	Patient findById(int patientId); 

	void updatePatient(Patient patient);
}
