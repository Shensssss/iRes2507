package web.clinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.clinic.entity.Clinic;
import web.clinic.service.RegisterService;

@RestController
@RequestMapping("clinic/register")
public class RegisterController {
	@Autowired
	private RegisterService registerservice;

	@PostMapping
//	@ResponseBody
	public String register(@RequestBody(required = false) Clinic clinic) {
//	Member member = json2Pojo(request, Member.class);
		if (clinic == null) {
			clinic = new Clinic();
			clinic.setMessage("無會員資訊");
			clinic.setSuccessful(false);
//			writePojo2Json(response, member);
			return "註冊失敗";
		}
		
		return registerservice.register(clinic);
//		writePojo2Json(response, member);
	}
}