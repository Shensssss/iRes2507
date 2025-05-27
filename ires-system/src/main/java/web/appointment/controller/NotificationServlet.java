package web.appointment.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import core.util.CommonUtil;
import web.appointment.entity.Appointment;
import web.appointment.entity.Notification;
import web.appointment.service.AppointmentService;
import web.appointment.service.NotificationService;
import web.appointment.dao.AppointmentDAO;
import web.patient.entity.Patient;
import web.patient.service.PatientService;

@WebServlet("/notification")
public class NotificationServlet extends HttpServlet {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public void init() throws ServletException {
        notificationService = CommonUtil.getBean(getServletContext(), NotificationService.class);
        patientService      = CommonUtil.getBean(getServletContext(), PatientService.class);
        appointmentService  = CommonUtil.getBean(getServletContext(), AppointmentService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        BufferedReader reader = req.getReader();
        Gson gson = new Gson();

        // 建立一個帶有型別 List<Map<String, Object>> 的 TypeToken 匿名子類別實例，在執行時可以保留這個完整泛型型別的資訊。
        List<Map<String, Object>> inputList = gson.fromJson(reader, new TypeToken<List<Map<String, Object>>>() {}.getType());

        List<String> resultMessages = new ArrayList<>();

        for (Map<String, Object> item : inputList) {
            try {
                int patientId = ((Number) item.get("patientId")).intValue();
                String appointmentId = (String) item.get("appointmentId");
                String message = (String) item.get("message");
                String notificationType = (String) item.get("notificationType");

                Patient patient = patientService.findById(patientId);
                Appointment appointment = appointmentService.findById(appointmentId);

                Notification n = new Notification();
                n.setPatient(patient);
                n.setAppointment(appointment);
                n.setMessage(message);
                n.setNotificationType(notificationType);

                String result = notificationService.createNotification(n);
                resultMessages.add(result);

            } catch (Exception e) {
                resultMessages.add("發生錯誤：" + e.getMessage());
                e.printStackTrace();
            }
        }

        resp.setContentType("application/json;charset=UTF-8");
        gson.toJson(resultMessages, resp.getWriter());
    }
}
