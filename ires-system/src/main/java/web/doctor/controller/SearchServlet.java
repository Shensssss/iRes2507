package web.doctor.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import core.util.CommonUtil;
import web.doctor.entity.Doctor;
import web.doctor.service.DoctorService;

@WebServlet("/doctorInfo")
public class SearchServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private DoctorService doctorService;
	
	@Override
	public void init() throws ServletException {
		doctorService = CommonUtil.getBean(getServletContext(), DoctorService.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO 待登入成功完成後，回頭修改(登入成功若有setAttribute則可以取到物件getClinicId)
		Integer clinicId = 1;
		doctorService.selectAllByClinicId(clinicId);
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		List<Doctor> doctors = doctorService.selectAllByClinicId(clinicId);
	    PrintWriter writer = resp.getWriter();
		String json = new com.google.gson.Gson().toJson(doctors);
		writer.print(json);
		
	}

}
