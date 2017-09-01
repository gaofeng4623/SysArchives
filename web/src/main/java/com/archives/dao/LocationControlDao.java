package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.LocationControl;

public interface LocationControlDao {
    int deleteByPrimaryKey(Integer id);

    int insert(LocationControl record);

    int insertSelective(LocationControl record);

    LocationControl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LocationControl record);

    int updateByPrimaryKey(LocationControl record);
    
    List<LocationControl> queryLocationControlListForCombobox(@Param("condation")String condation);

	List<LocationControl> queryPageControlList(@Param("m")Map m);

	int queryPageControlListCount(@Param("m")Map m);

	LocationControl queryControlByCondation(LocationControl locationControl);

	int delLocationControlByIds(@Param("idList")List<Integer> idList);

}