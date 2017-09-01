package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.Box;
import com.archives.pojo.LocationControl;

public interface BoxDao {
	int insert(Box record);

	int insertSelective(Box record);

	public List<Box> findBoxForPage(@Param("paraMap") Map paraMap);

	public int findCountBoxForPage(@Param("paraMap") Map paraMap);

	Box selectByPrimaryKey(Integer guid);
	
	Box selectByParams(Box box);

	int updateByPrimaryKeySelective(Box record);

	int updateByPrimaryKey(LocationControl record);

	int delBoxByIds(@Param("idList") List<Integer> idList);
	public List<Box> findBoxForShelves(@Param("paraMap")Map<String, Object> m);
	public int findCountBoxForShelves(@Param("paraMap")Map<String, Object> m);
}