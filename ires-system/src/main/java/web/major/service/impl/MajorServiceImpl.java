package web.major.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.major.dao.MajorDao;
import web.major.entity.Major;
import web.major.service.MajorService;

@Service
public class MajorServiceImpl implements MajorService{
	@Autowired
	private MajorDao dao; 

	@Override
	public List<Major> findAllMajor() {
		return dao.selectAll();
	}

}
