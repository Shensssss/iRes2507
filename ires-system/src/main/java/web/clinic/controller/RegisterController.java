package web.clinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import web.clinic.entity.Clinic;
import web.clinic.service.RegisterService;

@Controller
@RequestMapping("clinic")
public class RegisterController {
	@Autowired
	private RegisterService registerservice;

	@PostMapping("register")
	public String register(@RequestBody(required = false) Clinic clinic) {
		if (clinic == null) {
			clinic = new Clinic();
			clinic.setMessage("無會員資訊");
			clinic.setSuccessful(false);
			return "註冊失敗";
		}
		return registerservice.register(clinic);
	}
}