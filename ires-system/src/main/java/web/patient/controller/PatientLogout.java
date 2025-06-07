package web.patient.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;

@RequestMapping("patient")
public class PatientLogout {

    @PostMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();  
        return "已登出";
    }
}