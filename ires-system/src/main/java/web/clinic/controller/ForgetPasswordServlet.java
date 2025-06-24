//package web.clinic.controller;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//
//import core.util.CommonUtil;
//import web.clinic.entity.Clinic;
//import web.clinic.service.RegisterService;
//
//@WebServlet("/clinic/forgetPassword")
//public class ForgetPasswordServlet extends HttpServlet{
//	private static final long serialVersionUID = 1L;
//	private RegisterService registerService;
//	
//	@Override
//	public void init() throws ServletException {
//		registerService = CommonUtil.getBean(getServletContext(), RegisterService.class);
//		//		try {
////			registerService = new RegisterServiceImpl();
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//	}
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Gson gson = new Gson();
//		Clinic clinic = gson.fromJson(req.getReader(), Clinic.class);
//		
//		JsonObject respBody = new JsonObject();
//		if(clinic != null) {
//			String errMsg = registerService.findPassword(clinic);
//			respBody.addProperty("success", errMsg == null);
//			if (errMsg != null) {
//				respBody.addProperty("errMsg", errMsg);
//			}
//			
//		}else {
//			respBody.addProperty("success", false);
//			respBody.addProperty("errMsg", "無會員資料");
//		}
//		resp.setContentType("application/json");
//	    resp.setCharacterEncoding("UTF-8");
//		resp.getWriter().write(respBody.toString());
//		}
//	}
//
