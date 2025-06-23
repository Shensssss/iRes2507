package web.clinic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import core.pojo.Core;
import web.clinic.entity.Clinic;
import web.clinic.service.RegisterService;




@Controller
@RequestMapping("clinic")
public class ForgetPasswordController{ 
	@Autowired
	private RegisterService registerService;
	
	@PostMapping("forgetPassword")
	@ResponseBody
	public Core findPassword(HttpServletRequest request, @RequestBody(required = false) Clinic clinic) {
		Core core = new Core();
		
		
		if(clinic != null) {
			String errMsg = registerService.findPassword(clinic);
			core.setSuccessful(errMsg == null);
			if (errMsg != null) {
				core.setMessage(errMsg);
			}
		} else {
			core.setSuccessful(false);
			core.setMessage("無會員資料");
		}
		return core;
	}
}