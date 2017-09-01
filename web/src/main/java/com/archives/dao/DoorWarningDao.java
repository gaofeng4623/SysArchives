package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.DoorWarning;
public interface DoorWarningDao {
    int deleteByPrimaryKey(Integer guid);

    int insert(DoorWarning record);

    int insertSelective(DoorWarning record);

    DoorWarning selectByPrimaryKey(Integer guid);

    int updateByPrimaryKeySelective(DoorWarning record);

    int updateByPrimaryKey(DoorWarning record);
    
    public List<DoorWarning> findDoorWarningForPage(@Param("paraMap") Map paraMap);
	public int findCountDoorWarningForPage(@Param("paraMap") Map paraMap);
	public List<DoorWarning> findAllByIds(@Param("paraMap") Map paraMap);

}