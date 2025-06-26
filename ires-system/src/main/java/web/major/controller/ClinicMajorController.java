package web.major.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.clinic.entity.Clinic;
import web.major.entity.Major;
import web.major.service.ClinicMajorService;

@Controller
@RequestMapping(value = "clinicMajor")
public class ClinicMajorController {
	@Autowired
	private ClinicMajorService service;
	
	@GetMapping("list")
	@ResponseBody
	public List<Clinic> findClinicsByMajorIdOrAll(@RequestParam(required = false) Integer majorId){
        return service.getClinicsByMajorId(majorId);
	}
	
	@GetMapping("major")
	@ResponseBody
	public List<Major> findMajorByClinicId(@RequestParam(required = false) Integer clinicId){
		return service.getMajorByClinicId(clinicId);
	}	
}
