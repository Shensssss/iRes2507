package web.major.dao;

import java.util.List;

import web.major.entity.Major;

public interface MajorDao {
	List<Major> selectAll();
}
