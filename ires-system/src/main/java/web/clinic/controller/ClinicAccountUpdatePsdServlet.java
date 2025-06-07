package web.clinic.controller;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import core.util.CommonUtil;
import web.clinic.entity.Clinic;
import web.clinic.service.ClinicService;

@WebServlet("/clinic/clinicaccountupdatepsd")
public class ClinicAccountUpdatePsdServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClinicService service;
	
	@Override
    public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), ClinicService.class);
    }
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession();
//		final String username = ((Clinic) session.getAttribute("clinic_id")).getUsername();
//		Clinic clinic = json2Pojo(request, Member.class);
//		member.setUsername(username);
//		writePojo2Json(response, service.edit(member));
	}
}
