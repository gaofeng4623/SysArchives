package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.DoorMangage;

public interface DoorMangageDao {
	int deleteByPrimaryKey(Integer guid);

	int insert(DoorMangage record);

	int insertSelective(DoorMangage record);

	DoorMangage selectByPrimaryKey(Integer guid);

	int updateByPrimaryKeySelective(DoorMangage record);

	int updateByPrimaryKey(DoorMangage record);

	public List<DoorMangage> findDoorMangageForPage(
			@Param("paraMap") Map paraMap);

	public int findCountDoorMangageForPage(@Param("paraMap") Map paraMap);

	public int deleteDoorMangageByIds(@Param("idList") List<Integer> idList);

	int deleteDoorByIds(@Param("idList") List<String> idList);
}