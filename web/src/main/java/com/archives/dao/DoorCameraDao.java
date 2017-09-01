package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.DoorCamera;

public interface DoorCameraDao {
    int deleteByPrimaryKey(Integer guid);

    int insert(DoorCamera record);

    int insertSelective(DoorCamera record);

    DoorCamera selectByPrimaryKey(Integer guid);

    int updateByPrimaryKeySelective(DoorCamera record);

    int updateByPrimaryKey(DoorCamera record);
    public List<DoorCamera> findDoorCameraForPage(
			@Param("paraMap") Map paraMap);

	public int findCountDoorCameraForPage(@Param("paraMap") Map paraMap);
	int deleteDoorByIds(@Param("idList") List<String> idList);
	public List<DoorCamera> querydoorCameraAll();
}