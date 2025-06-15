package web.doctor.controller;

import static core.util.CommonUtil.json2Pojo;
import static core.util.CommonUtil.writePojo2Json;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.pojo.Core;
import core.util.CommonUtil;
import web.clinic.entity.Clinic;
import web.doctor.entity.Doctor;
import web.doctor.service.DoctorService;

@WebServlet("/doctor/add")
public class AddServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private DoctorService doctorService;
	
//	還沒使用spring MVC,手動初始化
	@Override
	public void init() throws ServletException {
		doctorService = CommonUtil.getBean(getServletContext(), DoctorService.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		將前端傳來的doctor物件(Json格式)反序列化, 訂為final避免誤改
	    final Doctor doctor = json2Pojo(req, Doctor.class);
	    final Core core = new Core();
	    
//	    後端getAttribute，找出clinicId
//	    (登入成功時要寫setAttribute("loggedInClinic", clinic); 此處才可以使用!)
		Clinic loggedInClinic = (Clinic) req.getSession().getAttribute("loggedInClinic");
		if (loggedInClinic == null) {
			core.setSuccessful(false);
			core.setMessage("診所尚未登入");
		}else {
			// 手動將clinicId填入，而非接受前端傳來的參數
			Clinic clinic = new Clinic();
		    clinic.setClinicId(loggedInClinic.getClinicId());
		    doctor.setClinic(clinic);

		    int result = doctorService.addDoctor(doctor);
		    if (result == 1) {
		        core.setSuccessful(true);
		        core.setMessage("新增成功");
		        core.setData(doctorService.showAllDoctors(loggedInClinic.getClinicId()));
		    } else {
		        core.setSuccessful(false);
		        core.setMessage("新增失敗");
		    }
		}
		writePojo2Json(resp, core);
	}	
}
