package web.major.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.major.entity.Major;
import web.major.service.MajorService;

@Controller
@RequestMapping(value = "major", produces = "application/json;charset=UTF-8")
public class MajorController {
	@Autowired
	private MajorService service;
	
	@GetMapping("list")
	@ResponseBody
	public List<Major> major() {
		return  service.findAllMajor();
	}
}
