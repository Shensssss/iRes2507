package web.patient.service;

import web.patient.entity.Patient;

public interface PatientService {
	
	Patient register(Patient patient);
	
	Patient login(Patient patient);
	
	Patient findById(int patientId); 
	
	Patient edit(Patient reqPatient);

	void updatePatient(Patient patient);

}
