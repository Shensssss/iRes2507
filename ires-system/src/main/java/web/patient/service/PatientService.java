package web.patient.service;

import web.patient.entity.Patient;

public interface PatientService {
	
	String register(Patient patient);
	
	Patient login(Patient patient);
	
	Patient findById(int patientId); 
}
