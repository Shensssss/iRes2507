package web.clinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import web.clinic.entity.CallNumber;
import web.clinic.entity.Clinic;
import web.clinic.service.ClinicService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/callNumber")
public class CallNumberController {

    @Autowired
    private ClinicService clinicService;

    @GetMapping("/init")
    public ResponseEntity<?> initCallNumber(
            @RequestParam Integer doctorId,
            @RequestParam Integer timePeriod,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Integer number,
            HttpSession session) {

        try {
            Clinic clinic = (Clinic) session.getAttribute("clinic");
            if (clinic == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("尚未登入或 session 遺失");
            }

            Integer clinicId = clinic.getClinicId();
            CallNumber result = clinicService.findOrCreateCallNumber(clinicId, doctorId, timePeriod, date);

            if (number != null) {
                result.setNumber(number);
                result.setUpdateId("admin");
                result.setUpdateTime(LocalDateTime.now());
                clinicService.saveCallNumber(result);
            }

//            Map<String, Object> response = new HashMap<>();
//            response.put("callNumberId", result.getCallNumberId());
//            response.put("number", result.getNumber());
//            response.put("appointmentDate", result.getAppointmentDate().toString());
//            response.put("timePeriod", result.getTimePeriod());

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("後端處理錯誤：" + e.getMessage());
        }
    }
}
