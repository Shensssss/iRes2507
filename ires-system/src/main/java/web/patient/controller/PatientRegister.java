package web.patient.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.patient.entity.Patient;
import web.patient.service.PatientService;
import web.patient.service.impl.PatientServiceImpl;

@WebServlet("/patient/register")
public class PatientRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PatientService patientService;

	@Override
	public void init() {
		try {
			patientService = new PatientServiceImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		Patient patient = gson.fromJson(req.getReader(), Patient.class);

		JsonObject respBody = new JsonObject();
		if (patient != null) {
			String errorMsg = patientService.register(patient);
			respBody.addProperty("success", errorMsg == null);
			if (errorMsg != null) {
				respBody.addProperty("errorMessage", errorMsg);
			}
		} else {
			respBody.addProperty("success", false);
			respBody.addProperty("errorMessage", "無會員資料");
		}

		resp.setContentType("application/json");
		resp.getWriter().write(respBody.toString());
	}
}
