////package web.clinic.controller;
////
////import java.io.IOException;
////
////import javax.servlet.ServletException;
////import javax.servlet.annotation.WebServlet;
////import javax.servlet.http.HttpServlet;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
////
////import com.google.gson.Gson;
////import com.google.gson.JsonObject;
////
////import core.util.CommonUtil;
////import web.clinic.entity.Clinic;
////import web.clinic.service.RegisterService;
////import web.clinic.service.impl.RegisterServiceImpl;
////
////@WebServlet("/clinic/login")
////public class LoginServlet extends HttpServlet{
////	private static final long serialVersionUID = 1L;
////	private RegisterService registerService;
////	
////	@Override
////	public void init() throws ServletException {
////		registerService = CommonUtil.getBean(getServletContext(), RegisterService.class);
////		//		try {
//////			registerService = new RegisterServiceImpl();
//////		} catch (Exception e) {
//////			e.printStackTrace();
//////		}
////	}
////	@Override
////	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////		Gson gson = new Gson();
////		Clinic clinic = gson.fromJson(req.getReader(), Clinic.class);
////		
////		JsonObject respBody = new JsonObject();
////		if(clinic != null) {
////			String errMsg = registerService.login(clinic);
////			respBody.addProperty("success", errMsg == null);
////			if (errMsg != null) {
////				respBody.addProperty("errMsg", errMsg);
////			}
////			
////		}else {
////			respBody.addProperty("success", false);
////			respBody.addProperty("errMsg", "無會員資料");
////		}
////		resp.setContentType("application/json");
////	    resp.setCharacterEncoding("UTF-8");
////		resp.getWriter().write(respBody.toString());
////		}
////	}
////
//
//
//package web.clinic.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import core.pojo.Core;
//import web.clinic.entity.Clinic;
//import web.clinic.service.RegisterService;
//
//@Controller
//@RequestMapping("clinic")
//public class LoginController {
//	@Autowired
//	private RegisterService registerService;
//
//	@PostMapping("login")
//	@ResponseBody
//	public Core login(@RequestBody(required = false) Clinic clinic) {
//		Core core = new Core();
//		if (clinic != null) {
//			String errMsg = registerService.login(clinic);
//			core.setSuccessful(errMsg == null);
//			if (errMsg != null) {
//				core.setMessage(errMsg);
//			}
//		} else {
//			core.setSuccessful(false);
//			core.setMessage("無會員資料");
//		}
//		return core;
//	}
//}
//
