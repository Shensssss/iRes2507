package web.patient.dao;

import core.dao.CoreDao;
import web.patient.entity.Patient;

import java.util.List;

public interface PatientDao extends CoreDao<Patient, Integer> {
	Patient selectByEmail(String email);

	Patient selectForLogin(String email, String password);
	
	Patient findById(int patientId);

	List<Patient> findReservedPatientsByKeyword(String keyword, int offset, int pageSize, int clinicId);

	long countReservedPatientsByKeyword(String keyword, int clinicId);

	Patient findByPhone(String phone);

}
