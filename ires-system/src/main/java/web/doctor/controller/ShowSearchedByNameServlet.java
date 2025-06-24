package web.doctor.controller;

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

@WebServlet("/doctor/showSearchedByName")
public class ShowSearchedByNameServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private DoctorService doctorService;
	
	@Override
	public void init() throws ServletException {
		doctorService = CommonUtil.getBean(getServletContext(), DoctorService.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final Core core = new Core();
		final Doctor doctor = new Doctor();
		Clinic loggedInClinic = (Clinic) req.getSession().getAttribute("loggedInClinic");
		if (loggedInClinic == null) {
			core.setSuccessful(false);
			core.setMessage("診所尚未登入");
		}else {
			core.setSuccessful(true);
			core.setMessage("載入成功");
			core.setData(doctorService.showSearchedByName(loggedInClinic.getClinicId(), doctor.getDoctorName()));
		}
		writePojo2Json(resp, core);
	}
}
