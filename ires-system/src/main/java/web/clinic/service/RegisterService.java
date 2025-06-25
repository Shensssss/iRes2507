package web.clinic.service;

import web.clinic.entity.Clinic;

public interface RegisterService {
	
	String register(Clinic clinic);
	
	Clinic login(Clinic clinic);
	
	Clinic findPassword(Clinic clinic);
	
	String resetPassword(Clinic clinic);

}