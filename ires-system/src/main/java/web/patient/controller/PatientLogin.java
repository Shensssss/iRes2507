package web.patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import web.patient.entity.Patient;
import web.patient.service.PatientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/patient")
public class PatientLogin {
    @Autowired
    private PatientService service;

    @PostMapping("/login")
    @ResponseBody
    public Patient login(@RequestBody Patient patient, HttpServletRequest request) {
    	System.out.println(patient.getEmail());
    	System.out.println(patient.getPassword());
    	patient = service.login(patient);
    	if(patient.isSuccessful()) {
    		if (request.getSession(false) != null) {
    			request.changeSessionId();
    		}
    		HttpSession session = request.getSession();
    		session.setAttribute("loggedin", true);
    		session.setAttribute("patient", patient);
    	}
    	return patient;
    }
}