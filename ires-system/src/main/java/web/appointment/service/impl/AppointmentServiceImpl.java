package web.appointment.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.util.CommonUtil;
import web.appointment.dao.AppointmentDAO;
import web.appointment.entity.Appointment;
import web.appointment.entity.Notification;
import web.appointment.service.AppointmentService;
import web.appointment.service.NotificationService;
import web.clinic.dao.ClinicDAO;
import web.clinic.entity.Clinic;
import web.doctor.dao.DoctorDao;
import web.doctor.entity.Doctor;
import web.patient.entity.Patient;
import web.patient.service.PatientService;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentDAO appointmentDAO;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ClinicDAO clinicDAO;

    @Override
    public void saveOrUpdate(Appointment appointment) {
        if (appointment.getAppointmentId() == null || appointment.getAppointmentId().isEmpty()) {
            appointmentDAO.insert(appointment);
        } else {
            appointmentDAO.update(appointment);
        }
    }

    @Override
    public Appointment findById(String appointmentId) {
        return appointmentDAO.selectById(appointmentId);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentDAO.selectAll();
    }

    @Override
    public void deleteById(String appointmentId) {
        appointmentDAO.deleteById(appointmentId);
    }

    @Override
    public List<Appointment> getAppointmentsByDate(Date date) {
        return appointmentDAO.findByDate(date);
    }

    @Override
    public List<Appointment> getAppointmentsByDateAndPeriod(Date date, int timePeriod) {
        return appointmentDAO.findByDateAndPeriod(date, timePeriod);
    }

    public List<Appointment> getHistoryByPatientId(int patientId) {
        return appointmentDAO.findByPatientId(patientId);
    }

    @Override
    public void save(Appointment appointment) {
        appointmentDAO.insert(appointment);
    }

    public Appointment updateAppointment(Appointment a) {
        Appointment origin = appointmentDAO.selectById(a.getAppointmentId());
        if (origin == null) {
            return null;
        }

        boolean dateChanged = !origin.getAppointmentDate().equals(a.getAppointmentDate());
        boolean periodChanged = !origin.getTimePeriod().equals(a.getTimePeriod());
        boolean doctorChanged = !origin.getDoctorId().equals(a.getDoctorId());

        origin.setAppointmentDate(a.getAppointmentDate());
        origin.setTimePeriod(a.getTimePeriod());
        origin.setDoctorId(a.getDoctorId());

        if (dateChanged || periodChanged || doctorChanged) {
            int newReserveNo = commonUtil.getNextReserveNo(
                    origin.getClinicId(),
                    origin.getDoctorId(),
                    origin.getAppointmentDate(),
                    origin.getTimePeriod()
            );
            origin.setReserveNo(newReserveNo);
        }

        appointmentDAO.update(origin);
        return origin;
    }

    public boolean deleteAppointment(String id) {
        Appointment a = appointmentDAO.selectById(id);
        if (a == null) {
            return false;
        }
        appointmentDAO.deleteById(a.getAppointmentId());
        return true;
    }

    public void reserveAppointments(Integer clinicId, List<Appointment> appointments) {
        for (Appointment a : appointments) {

            if (a.getDoctorId() == null) {
                throw new IllegalArgumentException("缺少必要欄位：doctorId");
            }

            if (a.getPatientId() == null) {
                throw new IllegalArgumentException("缺少必要欄位：patientId");
            }

            if (a.getAppointmentDate() == null) {
                throw new IllegalArgumentException("缺少必要欄位：appointmentDate");
            }

            //重複預約判斷
            if (appointmentDAO.existsDuplicateAppointment(a.getPatientId(), a.getAppointmentDate())) {
                throw new IllegalArgumentException("重複預約");
            }

            //超出預約人數判斷
            Long existingCount = appointmentDAO.countAppointmentsByGroup(
                    clinicId, a.getDoctorId(), a.getAppointmentDate(), a.getTimePeriod()
            );

            Clinic clinic = clinicDAO.selectById(clinicId);
            int quota = clinic.getQuota();

            if (existingCount >= quota) {
                throw new IllegalArgumentException("該時段已達預約上限，無法新增預約");
            }

            a.setClinicId(clinicId);
            a.setAppointmentId(UUID.randomUUID().toString());

            a.setReserveNo(commonUtil.getNextReserveNo(
                    clinicId,
                    a.getDoctorId(),
                    a.getAppointmentDate(),
                    a.getTimePeriod()
            ));

            a.setStatus(0);

            appointmentDAO.insert(a);

            //預約成功後寫入一筆通知
            Patient patient = patientService.findById(a.getPatientId());
            Appointment appointment = appointmentDAO.selectById(a.getAppointmentId());
            Doctor doctor = doctorDao.selectById(a.getDoctorId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Notification notification = new Notification();
            notification.setNotificationId(UUID.randomUUID().toString());
            notification.setAppointment(appointment);
            notification.setPatient(patient);
<<<<<<< HEAD
            notification.setMessage("您已成功預約，看診日期：" + a.getAppointmentDate()
                    + "、時段：" + getTimePeriod(a.getTimePeriod())
                    + " 醫師：" + doctor.getDoctorName());
=======
            notification.setMessage("您已成功預約，看診日期：" + sdf.format(a.getAppointmentDate()) +
                    "、時段：" + getTimePeriod(a.getTimePeriod()) +
                    "、醫師：" + doctor.getDoctorName());
>>>>>>> 9f921c2ef6f4a147b5a7625996f47b0f7854eaf6
            notification.setSentDatetime(new Timestamp(System.currentTimeMillis()));
            notification.setReadStatus(false);
            notification.setNotificationType("預約成功通知");

            notificationService.createNotification(notification);
        }
    }

    private String getTimePeriod(Integer code) {
        switch (code) {
            case 1:
                return "早上";
            case 2:
                return "下午";
            case 3:
                return "晚上";
            default:
                return "未知";
        }
    }
<<<<<<< HEAD
}
=======
    @Override
    public List<Appointment> findByPatientId(Integer patientId) {
        return appointmentDAO.findByPatientId(patientId);
    }
}
>>>>>>> 9f921c2ef6f4a147b5a7625996f47b0f7854eaf6
