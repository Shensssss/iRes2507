package web.patient.service.impl;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.patient.dao.PatientDAO;
import web.patient.dao.impl.PatientDAOImpl;
import web.patient.entity.Patient;
import web.patient.service.PatientService;
@Service
public class PatientServiceImpl implements PatientService {
	@Autowired
	private PatientDAO patientDAO;
	
	public PatientServiceImpl() throws NamingException {
		patientDAO = new PatientDAOImpl();
	}

	@Override
	public String register(Patient patient) {
		if(patient.getEmail() == null || patient.getEmail().isEmpty()) {
			return "使用者Email不得為空";
		}
		if(patient.getPassword()==null || patient.getPassword().length()<6){
			return "密碼長度必須大於6"	;
		}
		if(patient.getName()==null|| patient.getName().isEmpty()){
			return "使用者名稱不得為空"	;
		}
		if(patient.getGender()==0){
			return "使用者性別不得為空"	;
		}
		if(patient.getBirthday()==null|| patient.getBirthday().isEmpty()){
			return "使用者生日不得為空"	;
		}
		int count = patientDAO.insert(patient);
		return count < 1 ? "系統錯誤" : null;
	}



	@Override
	public Patient login(Patient patient) {
		if(patient.getEmail() == null || patient.getEmail().isEmpty()) {
			return null;
		}
		if(patient.getPassword() == null || patient.getPassword().isEmpty()) {
			return null;
		}
		return null;
	}

	@Override
	public Patient findById(int patientId) {
		return  patientDAO.findById(patientId);
	}

}
