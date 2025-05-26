package web.appointment.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import core.util.CommonUtil;
import web.appointment.entity.Appointment;
import web.appointment.entity.Notification;
import web.appointment.service.AppointmentService;
import web.appointment.service.NotificationService;
import web.appointment.dao.AppointmentDAO;
import web.patient.entity.Patient;

@WebServlet("/notification")
public class NotificationServlet extends HttpServlet {

    private NotificationService notificationService;
    private PatientDao patientDao;
    private AppointmentDAO appointmentDao;

    @Override
    public void init() throws ServletException {
        notificationService = CommonUtil.getBean(getServletContext(), NotificationService.class);
        patientDao          = CommonUtil.getBean(getServletContext(), PatientDao.class);
        appointmentDao      = CommonUtil.getBean(getServletContext(), AppointmentDAO.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String patientIdStr = req.getParameter("patient_id");
        String appointmentId = req.getParameter("appointment_id");
        String message = req.getParameter("message");
        String type = req.getParameter("notification_type");

        if (patientIdStr == null || appointmentId == null || message == null || type == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少必要參數");
            return;
        }

        int patientId = Integer.parseInt(patientIdStr);
        Patient patient = patientDao.findById(patientId);
        Appointment appointment = appointmentDao.selectById(appointmentId);

        if (patient == null || appointment == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "找不到對應的病人或預約");
            return;
        }

        Notification n = new Notification();
        n.setNotificationId(UUID.randomUUID().toString());
        n.setPatient(patient);
        n.setAppointment(appointment);
        n.setMessage(message);
        n.setNotificationType(type);
        n.setSentDatetime(new Timestamp(System.currentTimeMillis()));
        n.setReadStatus(false);
        n.setReadDatetime(null);

        notificationService.createNotification(n);

        resp.setContentType("text/plain");
        resp.getWriter().write("通知已建立");
    }
}
