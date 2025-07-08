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
    public List<Clinic> filterClinics(
            Integer majorId,
            String towns,
            Double minRating,
            Double maxDistanceKm,
            Double userLat,
            Double userLng,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    ) {
        int weekday = date.getDayOfWeek().getValue();
        List<Clinic> clinics = clinicMajorDao.findClinicsByMajorIdOrAll(majorId);
        List<Clinic> result = new ArrayList<>();

        for (Clinic clinic : clinics) {
            if (towns != null && !towns.isBlank()) {
                List<String> selectedTowns = Arrays.asList(towns.split(","));
                if (clinic.getAddressTown() == null || !selectedTowns.contains(clinic.getAddressTown())) {
                    continue;
                }
            }

            if (minRating != null && clinic.getRating() != null && clinic.getRating() < minRating) {
                continue;
            }

            if (userLat != null && userLng != null && maxDistanceKm != null
                    && clinic.getLatitude() != null && clinic.getLongitude() != null) {
                double distance = haversine(userLat, userLng, clinic.getLatitude(), clinic.getLongitude());
                if (distance > maxDistanceKm) {
                    continue;
                }
            }

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

    // 距離公式
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
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
