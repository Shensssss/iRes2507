package web.evaluations.service;

import java.util.List;

import web.evaluations.entity.Evaluations;

public interface EvaluationsService {
	List<Evaluations> getEvaluationsByClinicId(Integer clinicId);
	Integer addComment(Evaluations evaluations);
}
