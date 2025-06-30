package web.patient.service.impl;



import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.patient.dao.PatientDao;
import web.patient.entity.Patient;
import web.patient.service.PatientService;
@Service
@Transactional
public class PatientServiceImpl implements PatientService {
	@Autowired
	private PatientDao dao;

	@Override
	public Patient register(Patient patient) {
		if(patient.getEmail() == null) {
			patient.setMessage("使用者Email不得為空");
			patient.setSuccessful(false);
			return patient;
		}
		if(patient.getPassword()==null || patient.getPassword().length()<6){
			patient.setMessage("密碼長度必須大於6");
			patient.setSuccessful(false);
			return patient;
		}
		if(patient.getName()==null){
			patient.setMessage("使用者名稱不得為空");
			patient.setSuccessful(false);
			return patient;
		}
		if(patient.getGender()==0){
			patient.setMessage("使用者性別不得為空");
			patient.setSuccessful(false);
			return patient;
		}
		if(patient.getBirthday()==null){
			patient.setMessage("使用者生日不得為空");
			patient.setSuccessful(false);
			return patient;
		}
		dao.insert(patient);
		patient.setMessage("成功註冊");
		patient.setSuccessful(true);
		return patient;
	}

    @Override
    public Patient login(Patient patient) {
    	String email = patient.getEmail();
    	String password =patient.getPassword();
    	if(email==null && password==null) {
    	   patient.setMessage("使用者信箱或密碼不得為空");
    	   patient.setSuccessful(false);
    	   return patient;
       }
    	patient = dao.selectForLogin(email, password);
    	if(patient==null) {
    		Patient errorPatient = new Patient();
	    	errorPatient.setMessage("信箱或密碼錯誤");
	    	errorPatient.setSuccessful(false);
	    	return errorPatient;
    	}
    	patient.setMessage("成功登入");
    	patient.setSuccessful(true);
    	return patient;
    }

	@Override
	public Patient findById(int patientId) {
		return  dao.findById(patientId);
	}

	@Override
    public void updatePatient(Patient patient) {
        dao.update(patient); 
    }

	@Override
	public Patient edit(Patient patient) {
		updatePatient(patient);
		return findById(patient.getPatientId());
	}

	@Override
	public List<Patient> clinicSearch(String name, String birthday, String phone) {
	    
	    if (birthday != null && (phone == null || phone.isEmpty())) {
	        return dao.searchedByNameAndBirthday(name, birthday);
	    } else if ((birthday == null || birthday.isEmpty()) && phone != null) {
	        return dao.searchedByNameAndPhone(name, phone);
	    } else if (birthday != null && phone != null) {
	        return dao.searchedByNameAndBirthdayAndPhone(name, birthday, phone); // 生日和電話條件
	    } else {
	        throw new IllegalArgumentException("查詢條件不足");
	    }
	}
	
}
