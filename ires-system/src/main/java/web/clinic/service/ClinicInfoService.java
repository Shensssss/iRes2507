package web.clinic.service;

import web.clinic.entity.Clinic;

public interface ClinicInfoService {
	
    int editClinic(Clinic editedClinic);
    
    int editBusinessHours(Clinic editedClinic);
    
    Clinic getClinicById(Integer clinicId);

}