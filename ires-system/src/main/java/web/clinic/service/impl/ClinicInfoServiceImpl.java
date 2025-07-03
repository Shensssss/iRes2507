package web.clinic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.clinic.dao.ClinicInfoDao;
import web.clinic.entity.Clinic;
import web.clinic.service.ClinicInfoService;

@Service
@Transactional
public class ClinicInfoServiceImpl implements ClinicInfoService {
	@Autowired
	private ClinicInfoDao clinicInfoDao;

@Override
	public String updateInfo(Clinic clinic) {
	    if (clinic.getAccount() == null || clinic.getAccount().isBlank()) return "帳號錯誤";
	    if (clinic.getClinicName() == null || clinic.getClinicName().isBlank()) return "診所名字未輸入";
	    if (clinic.getAgencyId() == null || clinic.getAgencyId().isBlank()) return "代碼未輸入";
	    if (clinic.getPhone() == null || clinic.getPhone().isBlank()) return "電話未輸入";
	    if (clinic.getAddressCity() == null || clinic.getAddressCity().isBlank()) return "縣市未輸入";
	    if (clinic.getAddressTown() == null || clinic.getAddressTown().isBlank()) return "鄉鎮未輸入";
	    if (clinic.getAddressRoad() == null || clinic.getAddressRoad().isBlank()) return "道路未輸入";
	    //if (clinic.getWeb() == null) return "官網錯誤";
	    if (clinic.getRegistrationFee() == null) return "掛號費錯誤";
	    //if (clinic.getMemo() == null) return "備註錯誤";
	    if (clinic.getMorning() == null || clinic.getAfternoon() == null || clinic.getNight() == null ||
	        clinic.getWeekMorning() == null || clinic.getWeekAfternoon() == null || clinic.getWeekNight() == null) {
	        return "營業時間錯誤";
	    }

	    int result = clinicInfoDao.updateInfo(clinic);
	    return result == 1 ? null : "更新失敗";
	}

}
