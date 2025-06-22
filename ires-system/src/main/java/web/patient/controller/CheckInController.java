package web.patient.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.appointment.dao.AppointmentDAO;
import web.appointment.entity.Appointment;
//import web.clinic.dao.ClinicDAO;
import web.clinic.entity.Clinic;
import web.patient.entity.Patient;

@RestController
@RequestMapping("checkin")
public class CheckInController {

	@Autowired
	private AppointmentDAO appointmentDAO;

//	@Autowired
//	private ClinicDAO clinicDAO;

	@PostMapping // 處理POST請求
	// 從當前使用者session拿到patient物件;讀取資料並轉換成Map
	public Patient checkIn(@SessionAttribute Patient patient, @RequestBody Map<String, String> request) {

		String code = request.get("code"); // 從Map中取得19碼數字存入code中
        if (code == null || code.length() != 19 || !code.matches("\\d{19}")) {//要符合19碼數字
            throw new IllegalArgumentException("QRcode格式錯誤：必須為19碼數字");//拋出錯誤資訊
        }

     // 解析 QRcode
        String appointmentdate = code.substring(0, 8);// 取字串前8碼表示西元年月日;索引0~8(不含8)
        String clinicId = code.substring(8, 18);// 取9~18碼(10碼)表示診所id;不含索引18
        String period = code.substring(18, 19);// 取最後一碼表示預約時段
        int timePeriod = Integer.parseInt(period);//將字串轉為整數類別
        
     // 驗證時段合理性(三個時段)
        if (timePeriod < 1 || timePeriod > 3) {
            throw new IllegalArgumentException("時段格式錯誤(應為1~3)");//拋出錯誤資訊
        }

     // 確認診所資訊
//        Clinic clinic = clinicDAO.selectById(clinicId);//從資料庫查詢診所並回傳clinic資料
//        if (clinic == null) {//若查沒資料拋出錯誤訊息
//            throw new IllegalArgumentException("無對應診所id：" + clinicId);
//        }

     // 查預約日期、診所ID、病患ID
//        LocalDate appointmentDate = LocalDate.parse(appointmentdate, DateTimeFormatter.ofPattern("yyyyMMdd"));
//        Appointment appointment = appointmentDAO.findByClinicPatientDate(
//        	    clinic.getClinicId(),
//        	    patient.getPatientId(),
//        	    appointmentDate);

//        if (appointment == null) {//查無預約紀錄拋出錯誤資訊
//            throw new IllegalArgumentException("找不到對應預約紀錄");
//        }
//
//        if (appointment.getStatus() == 1) {//已報到不再報到一次
//            throw new IllegalStateException("此預約已完成報到");
//        }

     // 設為已報到
//        appointment.setStatus(1);//將status欄位改為1(已報到)
//        appointmentDAO.update(appointment);//修改後的物件寫入DB

        return patient;// 回傳報到成功的病患資訊
	}
}