package web.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import web.appointment.entity.Appointment;
import web.appointment.service.AppointmentService;

// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.*;

// import com.google.gson.Gson;

// import core.util.CommonUtil;

// import java.io.IOException;
// import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    
    @Autowired
    private AppointmentService service;

    @GetMapping("/apiToday")
    @ResponseBody
    public List<Appointment> getTodayAppointments(@RequestParam(value = "period", required = false) String period) {
        Date today = normalizeDate(new Date());

        int timePeriod = 1;
        if ("afternoon".equalsIgnoreCase(period)) {
            timePeriod = 2;
        } else if ("evening".equalsIgnoreCase(period)) {
            timePeriod = 3;
        }

        return service.getAppointmentsByDateAndPeriod(today, timePeriod);
    }

    private Date normalizeDate(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(sdf.format(date));
        } catch (Exception e) {
            return date;
        }
    }
}

/* 
@WebServlet("/appointment")
public class AppointmentManagement extends HttpServlet {
	private AppointmentService service;
	
	@Override
    public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), AppointmentService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

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

    private Date normalizeDate(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(sdf.format(date));
        } catch (Exception e) {
            return date;
        }
    }
}
*/
