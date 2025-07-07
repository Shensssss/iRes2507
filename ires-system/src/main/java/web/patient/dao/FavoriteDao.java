package web.patient.dao;

import java.util.List;
import java.util.Map;

import web.patient.entity.Favorite;

public interface FavoriteDao {

	boolean existsByPatientIdClinicId(Integer patientId, Integer clinicId);

	void save(Favorite favorite);

	List<Map<String, Object>> findFavoritesByPatientId(Integer patientId);

	boolean removeFavorite(Integer patientId, Integer clinicId);

	List<Favorite> findByPatientId(Integer patientId);
}
