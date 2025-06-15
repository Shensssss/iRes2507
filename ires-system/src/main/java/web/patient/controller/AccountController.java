package web.patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.patient.entity.Patient;
import web.patient.service.PatientService;

@RestController //回傳JSON格式資料
@RequestMapping("account")
public class AccountController {

    @Autowired//自動注入patientService
    private PatientService patientService;

 // 取得病患的帳戶資訊
    @GetMapping
    public Patient getInfo(@SessionAttribute(required = false) Patient patient) {
    	if (patient == null) {
            patient = new Patient();//沒有物件建立一個 
            patient.setMessage("無病患資訊");//設定病患資訊提醒前端沒資料
            patient.setSuccessful(false);//未登入所以沒有成功
        }
        return patient;//有資料回傳病患資料
    }

 // 確認密碼是否匹配
    @GetMapping("/{password}")
    public Boolean checkPassword(@PathVariable String password, @SessionAttribute(required = false) Patient patient) {
        return patient != null && password.equals(patient.getPassword());
        //資料存在比對密碼,符合回傳true,不符合或不存在回傳false
    }

 // 更新病患資料
    @PutMapping("/update")
    public Patient edit(@SessionAttribute Patient patient, @RequestBody Patient reqPatient) {
        reqPatient.setPatientId(patient.getPatientId()); // 保持病患ID不變
        return patientService.edit(reqPatient); // 直接返回更新後的資料
    }
}


