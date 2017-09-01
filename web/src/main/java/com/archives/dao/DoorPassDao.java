package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.DoorPass;

public interface DoorPassDao{
    int deleteByPrimaryKey(Integer guid);

    int insert(DoorPass record);

    int insertSelective(DoorPass record);

    DoorPass selectByPrimaryKey(Integer guid);

    int updateByPrimaryKeySelective(DoorPass record);

    int updateByPrimaryKey(DoorPass record);
    
    public List<DoorPass> findDoorPassForPage(@Param("paraMap") Map paraMap);
	public int findCountDoorPassForPage(@Param("paraMap") Map paraMap);
}