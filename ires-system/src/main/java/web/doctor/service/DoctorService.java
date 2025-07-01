package web.doctor.service;

import java.util.List;
import java.util.Map;

import web.doctor.entity.Doctor;

public interface DoctorService{
	
	int addDoctor(Doctor doctor);

    int deleteDoctor(Integer doctorId, Integer clinicId);

    int editDoctor(Doctor doctor);

    List<Doctor> showAllDoctors(Integer clinicId);

    List<Doctor> showSearchedByName(Integer clinicId, String doctorName);

    public Map<String, Object> getDoctorsByKeyword(Integer clinicId, String keyword, int page, int pageSize);

}
