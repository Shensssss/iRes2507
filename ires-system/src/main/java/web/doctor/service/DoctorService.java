package web.doctor.service;

import java.util.List;

import web.doctor.entity.Doctor;

public interface DoctorService{
	
	int insert(Doctor doctor);

    int deleteById(Integer doctorId);

    int update(Doctor doctor);

    List<Doctor> selectAllByClinicId(Integer clinicId);

    List<Doctor> selectByClinicIdAndDoctorName(Integer clinicId, String doctorName);

}
