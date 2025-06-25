package web.clinic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.clinic.entity.Clinic;
import web.clinic.service.RegisterService;

@Controller
@RequestMapping("clinic")
public class LoginController {
	@Autowired
	private RegisterService registerService;

	@PostMapping("login")
	@ResponseBody
	public Clinic login(HttpServletRequest request, @RequestBody(required = false) Clinic clinic) {
		if(clinic == null) {
			clinic = new Clinic();
			clinic.setSuccessful(false);
			clinic.setMessage("無會員資料");
			return clinic;
		}
		clinic = registerService.login(clinic);
		if (clinic != null && clinic.isSuccessful()) {
			if(request.getSession(false) != null) {
				request.changeSessionId();
			}
			 final HttpSession session = request.getSession();
			 session.setAttribute("loggedin", true);
			 session.setAttribute("clinic", clinic);
		}
		return clinic;
	}
}	