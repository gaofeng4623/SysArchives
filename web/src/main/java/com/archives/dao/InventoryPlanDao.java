package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.InventoryPlan;

public interface InventoryPlanDao {
    int deleteByPrimaryKey(Integer guid);

    int insert(InventoryPlan record);

    int insertSelective(InventoryPlan record);

    InventoryPlan selectByPrimaryKey(Integer guid);

    int updateByPrimaryKeySelective(InventoryPlan record);

    int updateByPrimaryKey(InventoryPlan record);

	List<InventoryPlan> loadRecordData(@Param("m") Map m);
	
	List<InventoryPlan> loadRemoveData(@Param("m") Map m);

	int findInventoryRecordCount(@Param("m") Map m);
	
	int findInventoryRemoveCount(@Param("m") Map m);

	InventoryPlan findLocationByLocationGuid(Integer guid);

	List<InventoryPlan> getAllInventoryPlanList(@Param("m")Map m); //获得全部盘点计划
	
	List<InventoryPlan> getInventoryPlanListForPage(@Param("m")Map m); //分页显示盘点计划

	void deleteInventoryPlanByIds(@Param("idList")List<String> idList);

	List<InventoryPlan> upShelvesPlanList();
	
	List<InventoryPlan> toListHistory(@Param("m") Map m);
	
	int toListHistoryCount(InventoryPlan inventoryPlan);
	
	int getTotalPlanList(@Param("m") Map m);

}