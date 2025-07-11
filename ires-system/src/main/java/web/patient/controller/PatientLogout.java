package web.patient.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("patient")
public class PatientLogout {

    @PostMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 清除所有 session
        return "redirect:/Patient/login.html"; // 或 return null 由前端跳轉
    }
}