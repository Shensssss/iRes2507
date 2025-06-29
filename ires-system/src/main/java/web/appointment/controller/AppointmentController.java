package web.appointment.controller;

import core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import web.appointment.dao.AppointmentDAO;
import web.appointment.entity.Appointment;
import web.appointment.entity.Notification;
import web.appointment.service.AppointmentService;

// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.*;

// import com.google.gson.Gson;

// import core.util.CommonUtil;

// import java.io.IOException;
// import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import web.appointment.service.NotificationService;
import web.clinic.entity.Clinic;
import web.doctor.dao.DoctorDao;
import web.doctor.entity.Doctor;
import web.doctor.service.DoctorService;
import web.patient.entity.Patient;
import web.patient.service.PatientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService service;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private AppointmentDAO appointmentDAO;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorDao doctorDao;

    @GetMapping("/apiToday")
    @ResponseBody
    public List<Appointment> getTodayAppointments(@RequestParam(value = "period", required = false) String period) {
//        Date today = normalizeDate(new Date());
        java.sql.Date today = new java.sql.Date(normalizeDate(new Date()).getTime());

        int timePeriod = 1;
        if ("afternoon".equalsIgnoreCase(period)) {
            timePeriod = 2;
        } else if ("evening".equalsIgnoreCase(period)) {
            timePeriod = 3;
        }

        return service.getAppointmentsByDateAndPeriod(today, timePeriod);
    }

    @GetMapping("/history")
    @ResponseBody
    public ResponseEntity<?> getAppointmentHistory(@RequestParam int patientId) {
        List<Appointment> list = service.getHistoryByPatientId(patientId);
        List<Map<String, Object>> result = list.stream().map(a -> {
            Map<String, Object> map = new HashMap<>();
            map.put("appointmentId", a.getAppointmentId());
            map.put("appointmentDate", a.getAppointmentDate().toString()); // yyyy-MM-dd
            map.put("timePeriod", a.getTimePeriod());
            map.put("doctorId", a.getDoctorId());
            map.put("doctorName", a.getDoctor().getDoctorName());
            map.put("reserveNo", a.getReserveNo());
            map.put("status", a.getStatus());
            map.put("createTime", a.getCreateTime());
            map.put("updateTime", a.getUpdateTime());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/reserve")
    @ResponseBody
    public ResponseEntity<?> reserve(HttpServletRequest request, @RequestBody List<Appointment> appointments) {
        HttpSession session = request.getSession(false);
        Clinic clinic = (session != null) ? (Clinic) session.getAttribute("member") : null;

        if (clinic == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                    .body("未登入或 session 已過期");
        }

        Integer clinicId = clinic.getClinicId();

        for (Appointment a : appointments) {
            try {
                a.setClinicId(clinicId);

                if (a.getDoctorId() == null) {
                    return ResponseEntity.badRequest().body("缺少必要欄位：doctorId");
                }
                if (a.getPatientId() == null) {
                    return ResponseEntity.badRequest().body("缺少必要欄位：patientId");
                }
                if (a.getAppointmentDate() == null) {
                    return ResponseEntity.badRequest().body("缺少必要欄位：appointmentDate");
                }

                a.setAppointmentId(UUID.randomUUID().toString());

                a.setReserveNo(commonUtil.getNextReserveNo(
                        a.getClinicId(),
                        a.getDoctorId(),
                        a.getAppointmentDate(),
                        a.getTimePeriod()
                ));

                a.setFirstVisit(commonUtil.getFirstVisit(
                        a.getPatientId(),
                        a.getClinicId()
                ));

                a.setStatus(0); // 未報到
                a.setNotes(null);

                service.save(a);

                Patient patient = patientService.findById(a.getPatientId());
                Appointment appointment = service.findById(a.getAppointmentId());
                Doctor doctor = doctorDao.selectById(a.getDoctorId());

                Notification notification = new Notification();
                notification.setNotificationId(UUID.randomUUID().toString());
                notification.setAppointment(appointment);
                notification.setPatient(patient);
                notification.setMessage("您已成功預約，看診日期：" + a.getAppointmentDate() + "、時段：" + getTimePeriod(a.getTimePeriod()) + " 醫師：" + doctor.getDoctorName());
                notification.setSentDatetime(new Timestamp(System.currentTimeMillis()));
                notification.setReadStatus(false);
                notification.setNotificationType("appointment");

                try {
                    notificationService.createNotification(notification);
                } catch (Exception e) {
                    System.err.println("通知寫入失敗: " + e.getMessage());
                }

            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                        .body("預約時發生錯誤: " + e.getMessage());
            }
        }
        return ResponseEntity.ok("預約成功");
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody Appointment appointment) {
        if (appointment.getAppointmentId() == null) {
            return ResponseEntity.badRequest().body("缺少 appointmentId");
        }

        Appointment updated = service.updateAppointment(appointment);
        if (updated == null) {
            return ResponseEntity.status(500).body("更新失敗");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "更新成功");

        Appointment newAppointment = appointmentDAO.selectById(updated.getAppointmentId());
        response.put("updateTime", newAppointment.getUpdateTime());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") String appointmentId) {
        boolean success = service.deleteAppointment(appointmentId);
        return success ? ResponseEntity.ok("刪除成功") : ResponseEntity.status(500).body("刪除失敗");
    }

    private Date normalizeDate(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(sdf.format(date));
        } catch (Exception e) {
            return date;
        }
    }

    public String getTimePeriod(Integer timePeriod) {
        if (timePeriod == null) return "";
        switch (timePeriod) {
            case 1:
                return "早上";
            case 2:
                return "下午";
            case 3:
                return "晚上";
            default:
                return timePeriod.toString();
        }
    }

}

/* 
@WebServlet("/appointment")
public class AppointmentManagement extends HttpServlet {
	private AppointmentService service;
	
	@Override
    public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), AppointmentService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("apiToday".equals(action)) {
            Date today = normalizeDate(new Date());
            String period = req.getParameter("period");

            int timePeriod = 1;
            if ("afternoon".equals(period)) {
                timePeriod = 2;
            } else if ("evening".equals(period)) {
                timePeriod = 3;
            }

            List<Appointment> list = service.getAppointmentsByDateAndPeriod(today, timePeriod);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            PrintWriter out = resp.getWriter();
            out.print(new Gson().toJson(list));
            // out.flush();
        }
    }

    private Date normalizeDate(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(sdf.format(date));
        } catch (Exception e) {
            return date;
        }
    }
}
*/
