package web.doctor.service;

import java.util.List;

import web.doctor.entity.Doctor;

public interface DoctorService{
	
	int addDoctor(Doctor doctor);

    int deleteDoctor(Integer doctorId, Integer clinicId);

    int editDoctor(Doctor doctor);

    List<Doctor> showAllDoctors(Integer clinicId);

    List<Doctor> showSearchedByName(Integer clinicId, String doctorName);

}
