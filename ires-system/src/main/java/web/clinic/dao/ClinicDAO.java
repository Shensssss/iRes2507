package web.clinic.dao;

import java.util.List;
import web.clinic.entity.Clinic;
import core.dao.CoreDao;

public interface ClinicDAO extends CoreDao<Clinic, String> {
    int updatePsd(Clinic clinic);
}