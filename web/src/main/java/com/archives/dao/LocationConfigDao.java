package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.LocationConfig;
import com.archives.pojo.LocationType;

public interface LocationConfigDao {
    int deleteByPrimaryKey(Integer guid);

    int insert(LocationConfig record);

    int insertSelective(LocationConfig record);

    LocationConfig selectByPrimaryKey(Integer guid);

    int updateByPrimaryKeySelective(LocationConfig record);

    int updateByPrimaryKey(LocationConfig record);

	List<LocationConfig> selectAllLocationConfigs();

	List<LocationConfig> queryPageConfigList(@Param("m")Map m);

	int queryPageConfigCount(@Param("m")Map m);

	List<LocationType> getTypeList();

	int deleteConfigInfoByIds(@Param("idList")List<String> idList);

}