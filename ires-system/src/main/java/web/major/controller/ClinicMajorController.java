package web.major.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.clinic.entity.Clinic;
import web.major.service.ClinicMajorService;

@Controller
@RequestMapping(value = "clinicMajor", produces = "application/json;charset=UTF-8")
public class ClinicMajorController {
	@Autowired
	private ClinicMajorService service;
	
	@GetMapping("list")
	@ResponseBody
	public List<Clinic> findClinicsByMajorIdOrAll(@RequestParam(required = false) Integer majorId){
		System.out.println(majorId);
        return service.getClinicsByMajorId(majorId);
	}	
}
