package web.patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import web.patient.entity.Patient;
import web.patient.service.PatientService;
@Service //讓Spring管理
@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired//自動注入accountService
    private PatientService patientService;

    // 顯示帳戶編輯頁面
    @GetMapping("edit")//將資料映射到account物件
    public String editAccountForm(@RequestParam Integer patientId, Model model) { // 接收patient_id作為參數
        Patient patient= patientService.findById(patientId); // 依照patient_id 查詢帳戶資料
        model.addAttribute("patient", patient); // 將查詢到的帳戶資料放入model供前端操作
        return "Patient"; 
    }

    // 更新帳戶資料存到後端
    @PostMapping("update")
    public String updateAccount(@ModelAttribute Patient patient) {//接收前端傳送過來的account物件
    	//將資料映射到account物件
    	patientService.updatePatient(patient);//呼叫AccountService的updateAccount方法來處理
        return "Patient"; 
    }
}