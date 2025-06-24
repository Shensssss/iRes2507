package web.evaluations.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import web.evaluations.entity.Evaluations;
import web.evaluations.service.EvaluationsService;

@Controller
@RequestMapping(value = "evaluations")
public class EvaluationsController {

	@Autowired EvaluationsService service;
	
	@GetMapping("list")
	@ResponseBody
	public List<Evaluations> findEvaluationsByClinicId(@RequestParam Integer clinicId){
		return service.getEvaluationsByClinicId(clinicId);
	}
	
	@PostMapping("add")
	@ResponseBody
	public int addComment(@RequestBody Evaluations evaluations) throws JsonProcessingException{
		System.out.println(new ObjectMapper().writeValueAsString(evaluations));
		return service.addComment(evaluations);
	}
}
