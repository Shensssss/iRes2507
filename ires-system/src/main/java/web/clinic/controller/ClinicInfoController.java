package web.clinic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.pojo.Core;
import web.clinic.entity.Clinic;
import web.clinic.service.ClinicInfoService;

@RestController
@RequestMapping("clinic/clinicInfo")
public class ClinicInfoController{ 
	@Autowired
	private ClinicInfoService clinicInfoService;
	
	@PutMapping
	public Core updateInfo(HttpServletRequest request, @RequestBody(required = false) Clinic reqClinic) {
	    Core core = new Core();

	    Clinic sessionClinic = (Clinic) request.getSession().getAttribute("clinic");
	    if (sessionClinic == null) {
	        core.setSuccessful(false);
	        core.setMessage("Session 過期或未驗證");
	        return core;
	    }

	    // 強制以 session 的帳號為準
	    reqClinic.setClinicName(sessionClinic.getClinicName());
	    reqClinic.setAgencyId(sessionClinic.getAgencyId());
	    reqClinic.setPhone(sessionClinic.getPhone());
	    reqClinic.setAddressCity(sessionClinic.getAddressCity());
	    reqClinic.setAddressTown(sessionClinic.getAddressTown());
	    reqClinic.setAddressRoad(sessionClinic.getAddressRoad());
	    reqClinic.setLatitude(sessionClinic.getLatitude());
	    reqClinic.setLongitude(sessionClinic.getLongitude());
	    reqClinic.setProfilePicture(sessionClinic.getProfilePicture());
	    reqClinic.setAccount(sessionClinic.getAccount());

	    String errMsg = clinicInfoService.updateInfo(reqClinic);
	    core.setSuccessful(errMsg == null);
	    core.setMessage(errMsg == null ? "更新成功" : errMsg);
	    return core;
	}
}