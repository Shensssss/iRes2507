package web.major.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.clinic.entity.Clinic;
import web.major.dao.ClinicMajorDao;
import web.major.entity.Major;
import web.major.service.ClinicMajorService;

@Service
public class ClinicMajorServiceImpl implements ClinicMajorService {

	@Autowired
	private ClinicMajorDao dao;

	@Override
	public List<Clinic> getClinicsByMajorId(Integer majorId) {
		return dao.findClinicsByMajorIdOrAll(majorId);
	}

	@Override
	public List<Major> getMajorByClinicId(Integer clinicId) {
		return dao.findMajorByClinicsId(clinicId);
	}

}
