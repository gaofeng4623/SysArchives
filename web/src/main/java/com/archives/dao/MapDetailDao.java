package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.MapDetail;

public interface MapDetailDao {
    int deleteByPrimaryKey(String priorid);

    int insert(MapDetail record);

    int insertSubItem(MapDetail record);

    MapDetail selectByPrimaryKey(String priorid);

    int updateSubItemSelective(MapDetail record);

    int updateByPrimaryKey(MapDetail record);

	public List<MapDetail> findSubItemByPriorIdsForPage(@Param("paraMap") Map<String, Object> paraMap);

	public int findCountSubItemByPriorIdsForPage(@Param("paraMap") Map<String, Object> paraMap);
	
	public int deleteSubItemByIds(@Param("idList") List<String> idList);
}