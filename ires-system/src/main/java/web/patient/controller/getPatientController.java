package web.patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.clinic.entity.Clinic;
import web.patient.entity.Patient;
import web.patient.service.PatientService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("patient")
public class getPatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/patientList")
    @ResponseBody
    public Map<String, Object> getPatients(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String keyword,
            HttpSession session
    ) {
        Clinic clinic = (Clinic) session.getAttribute("clinic");
        if (clinic == null) {
            throw new RuntimeException("未登入診所");
        }

        int pageSize = 10;
        return patientService.getReservedPatientsWithKeyword(
                clinic.getClinicId(), keyword, page, pageSize
        );
    }

    @GetMapping("/findByPhone")
    @ResponseBody
    public ResponseEntity<?> findByPhone(@RequestParam String phone) {
        Patient patient = patientService.findByPhone(phone);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到病患");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("patientId", patient.getPatientId());
        result.put("name", patient.getName());
        return ResponseEntity.ok(result);
    }
}
