package web.clinic.dao;

import web.clinic.entity.Clinic;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import core.dao.CoreDao;

public interface ClinicDAO extends CoreDao<Clinic, Integer> {
//public interface ClinicDAO extends CoreDao<Clinic, String> {	// 衝突 
    int updatePsd(Clinic clinic);
    
    Integer findClinicIdByAgencyId(String agencyId);
    
	Clinic selectById(int clinic_id);

	List<Clinic> getClinicByAccount(String clinic_account);

}