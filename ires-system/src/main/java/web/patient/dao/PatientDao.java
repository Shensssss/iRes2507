package web.patient.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import core.dao.CoreDao;
import web.patient.entity.Patient;

import java.util.List;

public interface PatientDao extends CoreDao<Patient, Integer> {
	Patient selectByEmail(String email);

	Patient selectForLogin(String email, String password);
	
	Patient findById(int patientId);
<<<<<<< HEAD
	
	List<Patient> searchedByNameAndBirthday(String name, String birthday);
	List<Patient> searchedByNameAndPhone(String name, String phone);
	List<Patient> searchedByNameAndBirthdayAndPhone(String name, String birthday, String phone);
=======

	List<Patient> findReservedPatientsByKeyword(String keyword, int offset, int pageSize, int clinicId);

	long countReservedPatientsByKeyword(String keyword, int clinicId);

	Patient findByPhone(String phone);

>>>>>>> main
}
