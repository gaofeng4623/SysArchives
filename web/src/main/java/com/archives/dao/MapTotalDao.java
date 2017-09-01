package com.archives.dao;

import java.util.List;

import com.archives.pojo.MapTotal;

public interface MapTotalDao {
    int deleteByPrimaryKey(String guid);

    int insert(MapTotal record);

    int insertSelective(MapTotal record);

    MapTotal selectByPrimaryKey(String guid);

    int updateByPrimaryKeySelective(MapTotal record);

    int updateByPrimaryKey(MapTotal record);

    List<MapTotal> selectAllItems();
    
    MapTotal selectByName(String content);
}