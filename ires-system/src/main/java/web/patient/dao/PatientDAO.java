package web.patient.dao;

import core.dao.CoreDao;
import web.patient.entity.Patient;

public interface PatientDAO extends CoreDao<Patient, Integer> {
	Patient selectByEmailPassword(String email);

	Patient selectForLogin(String email, String password);
	
	Patient findById(int patientId);
}
