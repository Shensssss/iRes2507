package web.patient.dao;

import core.dao.CoreDao;
import web.patient.entity.Patient;

public interface PatientDao extends CoreDao<Patient, Integer> {
	Patient selectByEmail(String email);

	Patient selectForLogin(String email, String password);
	
	Patient findById(int patientId);
}
