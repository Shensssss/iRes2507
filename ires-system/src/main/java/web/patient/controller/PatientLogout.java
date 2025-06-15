package web.patient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("patient")
public class PatientLogout {

    @PostMapping("logout")
    public void logout(SessionStatus sessionStatus) {
    	sessionStatus.setComplete();;  
    }
}