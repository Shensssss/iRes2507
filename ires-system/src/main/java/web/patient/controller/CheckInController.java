package web.patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.patient.entity.Patient;
import web.patient.service.PatientService;

@RestController
@RequestMapping("checkIn")
public class CheckInController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public Patient checkIn(@SessionAttribute Patient patient, @RequestBody String code) {
        return patientService.checkIn(patient, code);
    }
}
