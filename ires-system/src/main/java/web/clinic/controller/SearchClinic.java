package web.clinic.controller;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.clinic.entity.Clinic;
import web.clinic.service.ClinicService;
import web.patient.entity.Patient;

@Controller
@RequestMapping("/clinic")
public class SearchClinic {

    @Autowired
    private ClinicService service;

    @GetMapping("/filter")
    @ResponseBody
    public List<Clinic> filterClinics(
            @RequestParam(required = false) Integer majorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date,
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime endTime
    ) {
        return service.filterClinics(majorId, date, startTime, endTime);
    }
    
    @GetMapping("/id/{clinicId}")
    @ResponseBody
    public Clinic findClinic(@PathVariable Integer clinicId) {
        return service.findById(clinicId);
    }
}