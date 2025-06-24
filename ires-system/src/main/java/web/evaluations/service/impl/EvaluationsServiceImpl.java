package web.evaluations.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.evaluations.dao.EvaluationsDao;
import web.evaluations.entity.Evaluations;
import web.evaluations.service.EvaluationsService;

@Service
@Transactional
public class EvaluationsServiceImpl implements EvaluationsService {

	@Autowired 
	private EvaluationsDao dao;
	
	@Override
	public List<Evaluations> getEvaluationsByClinicId(Integer clinicId) {
		return dao.findEvaluationsByClinicId(clinicId);
	}

	@Override
	public Integer addComment(Evaluations evaluations) {
		return dao.insert(evaluations);
	}

}
