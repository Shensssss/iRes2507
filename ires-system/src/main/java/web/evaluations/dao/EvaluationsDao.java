package web.evaluations.dao;

import java.util.List;

import core.dao.CoreDao;
import web.evaluations.entity.Evaluations;

public interface EvaluationsDao extends CoreDao<Evaluations, Integer>{
	List<Evaluations> findEvaluationsByClinicId(Integer clinicId);
}
