package core.util;

import static core.util.Constants.GSON;
import static core.util.Constants.JSON_MIME_TYPE;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Date;

import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component
public class CommonUtil {
	@PersistenceContext
	private Session session;

	private static final Logger logger = LogManager.getLogger(CommonUtil.class);

	public static <T> T getBean(ServletContext sc, Class<T> clazz) {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
		return context.getBean(clazz);
	}
	
	public static <P> P json2Pojo(HttpServletRequest request, Class<P> classOfPojo) {
		try (BufferedReader br = request.getReader()) {
			return GSON.fromJson(br, classOfPojo);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	public static <P> void writePojo2Json(HttpServletResponse response, P pojo) {
		response.setContentType(JSON_MIME_TYPE);
		try (PrintWriter pw = response.getWriter()) {
			pw.print(GSON.toJson(pojo));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	//取得看診號碼
	public int getNextReserveNo(int clinicId, Date date, int timePeriod) {
		String hql = "SELECT MAX(a.reserveNo) FROM Appointment a " +
				"WHERE a.clinicId = :clinicId AND a.appointmentDate = :date AND a.timePeriod = :period";

		Integer max = session.createQuery(hql, Integer.class)
				.setParameter("clinicId", clinicId)
				.setParameter("date", date)
				.setParameter("period", timePeriod)
				.uniqueResult();

		int next = (max != null ? max : 0) + 1;

		if (next % 2 == 0) {
			next += 1;
		}

		return next;
	}

	//判斷是否有預約過firstVisit
	public int getFirstVisit(int patientId, int clinicId) {
		String hql = "SELECT COUNT(*) FROM Appointment a WHERE a.patientId = :pid AND a.clinicId = :cid";
		Long count = session.createQuery(hql, Long.class)
				.setParameter("pid", patientId)
				.setParameter("cid", clinicId)
				.uniqueResult();
		return (count != null && count > 0) ? 1 : 0;
	}
}
