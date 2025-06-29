package web.appointment.controller;

import core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import web.appointment.entity.Appointment;
import web.appointment.service.AppointmentService;

// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.*;

// import com.google.gson.Gson;

// import core.util.CommonUtil;

// import java.io.IOException;
// import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import web.clinic.entity.Clinic;
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
            map.put("appointmentDate", a.getAppointmentDate().toString()); // yyyy-MM-dd
            map.put("timePeriod", a.getTimePeriod());
            map.put("doctorName", a.getDoctor().getDoctorName());
            map.put("reserveNo", a.getReserveNo());
            map.put("status", a.getStatus());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/reserve")
    @ResponseBody
    public ResponseEntity<?> reserve(HttpServletRequest request, @RequestBody List<Appointment> appointments) {
        // 從 session 中取出登入診所資訊
        HttpSession session = request.getSession(false);
        Clinic clinic = (session != null) ? (Clinic) session.getAttribute("member") : null;

        if (clinic == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                    .body("未登入或 session 已過期");
        }

        Integer clinicId = clinic.getClinicId();  // 從 session 取出 clinicId

        for (Appointment a : appointments) {
            a.setClinicId(clinicId);

            if (a.getDoctorId() == null || a.getPatientId() == null || a.getAppointmentDate() == null) {
                return ResponseEntity.badRequest().body("缺少必要欄位（doctorId / patientId / appointmentDate）");
            }

            // 產生預約 ID（UUID）
            a.setAppointmentId(UUID.randomUUID().toString());

            a.setReserveNo(commonUtil.getNextReserveNo(
                    a.getClinicId(),
                    a.getAppointmentDate(),
                    a.getTimePeriod()
            ));

            a.setFirstVisit(commonUtil.getFirstVisit(
                    a.getPatientId(),
                    a.getClinicId()
            ));
            a.setStatus(0); // 0 = 未報到、1 = 已報到、2 = 已取消
            a.setNotes(null);

            service.save(a);
        }
        return ResponseEntity.ok("預約成功");
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
