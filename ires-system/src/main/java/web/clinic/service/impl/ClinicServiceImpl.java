package web.clinic.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.clinic.dao.ClinicDAO;
import web.clinic.entity.CallNumber;
import web.clinic.entity.Clinic;
import web.clinic.service.ClinicService;
import web.major.dao.ClinicMajorDao;
import web.major.service.ClinicMajorService;

@Service
@Transactional
public class ClinicServiceImpl implements ClinicService {
	@Autowired
	private ClinicDAO clinicDAO;

	@Autowired
	private ClinicMajorDao clinicMajorDao;

	@Override
	public String editPsd(Clinic clinic) {
		final Clinic oclinic = clinicDAO.selectById(clinic.getClinicId());

		clinic.setClinicName(oclinic.getClinicName());
		clinic.setAgencyId(oclinic.getAgencyId());
		clinic.setAccount(oclinic.getAccount());
		clinic.setPhone(oclinic.getPhone());
		final int resultCount = clinicDAO.updatePsd(clinic);
		String strReturn = (resultCount > 0 ? "密碼修改成功" : "密碼修改失敗");

		return strReturn;
	}

	@Override
	public List<Clinic> filterClinics(Integer majorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
	    int weekday = date.getDayOfWeek().getValue();
	    List<Clinic> clinics = clinicMajorDao.findClinicsByMajorIdOrAll(majorId);
	    List<Clinic> result = new ArrayList<>();

	    for (int i = 0; i < clinics.size(); i++)  {
	        Clinic clinic = clinics.get(i);
	        System.out.println(clinic.getClinicName());
	        boolean matched = false;

	        if (clinic.getWeekMorning() != null && clinic.getMorning() != null && clinic.getMorning().contains("-")) {
	            List<String> days = Arrays.asList(clinic.getWeekMorning().split(","));
	            if (days.contains(String.valueOf(weekday))) {
	                String[] times = clinic.getMorning().split("-");
	                LocalTime clinicStart = LocalTime.parse(times[0].trim());
	                LocalTime clinicEnd = LocalTime.parse(times[1].trim());
	                if (!(clinicEnd.isBefore(startTime) || clinicStart.isAfter(endTime))) {
	                    matched = true;
	                }
	            }
	        }

	        if (!matched && clinic.getWeekAfternoon() != null && clinic.getAfternoon() != null && clinic.getAfternoon().contains("-")) {
	            List<String> days = Arrays.asList(clinic.getWeekAfternoon().split(","));
	            if (days.contains(String.valueOf(weekday))) {
	                String[] times = clinic.getAfternoon().split("-");
	                LocalTime clinicStart = LocalTime.parse(times[0].trim());
	                LocalTime clinicEnd = LocalTime.parse(times[1].trim());
	                if (!(clinicEnd.isBefore(startTime) || clinicStart.isAfter(endTime))) {
	                    matched = true;
	                }
	            }
	        }

	        if (!matched && clinic.getWeekNight() != null && clinic.getNight() != null && clinic.getNight().contains("-")) {
	            List<String> days = Arrays.asList(clinic.getWeekNight().split(","));
	            if (days.contains(String.valueOf(weekday))) {
	                String[] times = clinic.getNight().split("-");
	                LocalTime clinicStart = LocalTime.parse(times[0].trim());
	                LocalTime clinicEnd = LocalTime.parse(times[1].trim());
	                if (!(clinicEnd.isBefore(startTime) || clinicStart.isAfter(endTime))) {
	                    matched = true;
	                }
	            }
	        }

	        if (matched) {
	            result.add(clinic);
	        }
	    }

	    return result;
	}

	@Override
	public Clinic selectById(int clinic_id) {
		return clinicDAO.selectById(clinic_id);
	}
	
	@Override
	public List<Clinic> getClinicByAccount(String clinic_account) {
		return clinicDAO.getClinicByAccount(clinic_account);
	}
	
	@Override
	public Clinic findById(Integer clinicId) {
		return clinicDAO.selectById(clinicId);
	}

	@Override
	public CallNumber findOrCreateCallNumber(Integer clinicId, Integer doctorId, Integer timePeriod, LocalDate date) {
		try {
			if (clinicId == null || doctorId == null || timePeriod == null || date == null) {
				throw new IllegalArgumentException("參數不能為 null");
			}

			return clinicDAO.findCallNumber(clinicId, doctorId, timePeriod, date)
					.orElseGet(() -> {
						CallNumber newEntry = CallNumber.builder()
								.clinicId(clinicId)
								.doctorId(doctorId)
								.timePeriod(timePeriod)
								.appointmentDate(date)
								.number(0)
								.consultationStatus(0)
								.createId("admin")
								.updateId("admin")
								.createTime(LocalDateTime.now())
								.updateTime(LocalDateTime.now())
								.build();
						return clinicDAO.save(newEntry);
					});

		} catch (Exception e) {
			e.printStackTrace(); // 或使用 logger.error(...)
			throw new RuntimeException("建立或查詢叫號資料時發生錯誤：" + e.getMessage(), e);
		}
	}

	@Override
	public CallNumber saveCallNumber(CallNumber callNumber) {
		return clinicDAO.save(callNumber);
	}

}
