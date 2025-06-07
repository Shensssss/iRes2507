package web.appointment.controller;

import web.appointment.entity.Appointment;
import web.appointment.service.AppointmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;

import core.util.CommonUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/appointment")
public class AppointmentManagement extends HttpServlet {

//    private final AppointmentService appointmentService = new AppointmentServiceImpl();
	private AppointmentService service;
	
	@Override
    public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), AppointmentService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        // if ("edit".equals(action)) {
        //     String id = req.getParameter("id");
        //     Appointment appointment = appointmentService.findById(id);
        //     req.setAttribute("appointment", appointment);
        //     req.getRequestDispatcher("/").forward(req, resp);

        // } else if ("delete".equals(action)) {
        //     String id = req.getParameter("id");
        //     appointmentService.deleteById(id);
        //     resp.sendRedirect("appointment?action=list");

        // } else if ("apiToday".equals(action)) {
        //     Date today = normalizeDate(new Date());
        //     String period = req.getParameter("period");

        //     int timePeriod = 1;
        //     if ("afternoon".equals(period)) {
        //         timePeriod = 2;
        //     } else if ("evening".equals(period)) {
        //         timePeriod = 3;
        //     }

        //     List<Appointment> list = appointmentService.getAppointmentsByDateAndPeriod(today, timePeriod);

        //     resp.setContentType("application/json");
        //     resp.setCharacterEncoding("UTF-8");

        //     PrintWriter out = resp.getWriter();
        //     out.print(new Gson().toJson(list));
        //     out.flush();
        // } else {
        //     List<Appointment> appointments = appointmentService.findAll();
        //     req.setAttribute("appointments", appointments);
        //     req.getRequestDispatcher("/").forward(req, resp);
        // }


        if ("apiToday".equals(action)) {
            Date today = normalizeDate(new Date());
            String period = req.getParameter("period");

            int timePeriod = 1;
            if ("afternoon".equals(period)) {
                timePeriod = 2;
            } else if ("evening".equals(period)) {
                timePeriod = 3;
            }

            List<Appointment> list = service.getAppointmentsByDateAndPeriod(today, timePeriod);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            PrintWriter out = resp.getWriter();
            out.print(new Gson().toJson(list));
            // out.flush();
        }
    }

    // @Override
    // protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //     req.setCharacterEncoding("UTF-8");

    //     String id = req.getParameter("appointmentId");
    //     String patientId = req.getParameter("patientId");
    //     String clinicId = req.getParameter("clinicId");
    //     String doctorId = req.getParameter("doctorId");
    //     String reserveNo = req.getParameter("reserveNo");
    //     String dateStr = req.getParameter("appointmentDate");
    //     String timePeriod = req.getParameter("timePeriod");
    //     String firstVisit = req.getParameter("firstVisit");
    //     String status = req.getParameter("status");
    //     String notes = req.getParameter("notes");

    //     Appointment appointment = new Appointment();

    //     if (id == null || id.isEmpty()) {
    //         appointment.setAppointmentId(UUID.randomUUID().toString());
    //     } else {
    //         appointment.setAppointmentId(id);
    //     }

    //     Patient patient = new Patient();
    //     patient.setPatientId(Integer.parseInt(patientId));
    //     appointment.setPatient(patient);

    //     Clinic clinic = new Clinic();
    //     clinic.setClinicId(Integer.parseInt(clinicId));
    //     appointment.setClinic(clinic);

    //     appointment.setDoctorId(Integer.parseInt(doctorId));
    //     appointment.setReserveNo(Integer.parseInt(reserveNo));
    //     appointment.setTimePeriod(Integer.parseInt(timePeriod));
    //     appointment.setFirstVisit("1".equals(firstVisit) || "true".equalsIgnoreCase(firstVisit));
    //     appointment.setStatus(Integer.parseInt(status));
    //     appointment.setNotes(notes);

    //     try {
    //         Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
    //         appointment.setAppointmentDate(date);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }

    //     appointmentService.saveOrUpdate(appointment);
    //     resp.sendRedirect("appointment?action=list");
    // }

    private Date normalizeDate(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(sdf.format(date));
        } catch (Exception e) {
            return date;
        }
    }
}
