package web.appointment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import web.appointment.dao.AppointmentDAO;
import web.appointment.entity.Appointment;
import web.appointment.service.AppointmentService;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;
import web.clinic.entity.Clinic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService service;

    @Autowired
    private AppointmentDAO appointmentDAO;

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

//    @GetMapping("/history")
//    @ResponseBody
//    public ResponseEntity<?> getAppointmentHistory(@RequestParam int patientId) {
//        List<Appointment> list = service.getHistoryByPatientId(patientId);
//        List<Map<String, Object>> result = list.stream().map(a -> {
//            Map<String, Object> map = new HashMap<>();
//            map.put("appointmentId", a.getAppointmentId());
//            map.put("appointmentDate", a.getAppointmentDate().toString()); // yyyy-MM-dd
//            map.put("timePeriod", a.getTimePeriod());
//            map.put("doctorId", a.getDoctorId());
//            map.put("doctorName", a.getDoctor().getDoctorName());
//            map.put("reserveNo", a.getReserveNo());
//            map.put("status", a.getStatus());
//            map.put("createTime", a.getCreateTime());
//            map.put("updateTime", a.getUpdateTime());
//            return map;
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.ok(result);
//    }

    @GetMapping("/history")
    @ResponseBody
    public ResponseEntity<?> getAppointmentHistory(@RequestParam int patientId) {
        List<Appointment> list = service.getHistoryByPatientId(patientId);
        return ResponseEntity.ok(list);
    }


    @PostMapping("/reserve")
    @ResponseBody
    public ResponseEntity<String> reserve(HttpServletRequest request, @RequestBody List<Appointment> appointments) {
        HttpSession session = request.getSession(false);
        Clinic clinic = (session != null) ? (Clinic) session.getAttribute("clinic") : null;

        if (clinic == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                    .body("未登入或 session 已過期");
        }

        try {
            service.reserveAppointments(clinic.getClinicId(), appointments);
            return ResponseEntity
                    .ok() // 200
                    .contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                    .body("預約成功");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST) // 400
                    .contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                    .body("錯誤：" + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                    .contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                    .body("預約時發生錯誤：" + e.getMessage());
        }
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
}
