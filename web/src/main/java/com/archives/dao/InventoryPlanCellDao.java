package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.InventoryPlanCell;

public interface InventoryPlanCellDao {
    int deleteByPrimaryKey(Integer guid);

    int insert(InventoryPlanCell record);

    int insertSelective(InventoryPlanCell record);

    InventoryPlanCell selectByPrimaryKey(Integer guid);

    int updateByPrimaryKeySelective(InventoryPlanCell record);

    int updateByPrimaryKey(InventoryPlanCell record);

	void batchInsertPlanCell(@Param("list")List<InventoryPlanCell> list);

	Integer selectByLocationGuids(@Param("list")List<Integer> locationGuids, @Param("planId")Integer planId);

	List<InventoryPlanCell> loadInstantInventoryCell(@Param("m")Map m);

	int boxLoadInstantInventoryCellCount(@Param("m")Map m);
	List<InventoryPlanCell> boxLoadInstantInventoryCell(@Param("m")Map m);

	int loadInstantInventoryCellCount(@Param("m")Map m);

	void deletePlanCellByInvenId(@Param("idList")List<String> idList);
	
	int refreshPlanCellStatus(@Param("planId") int planId);
	
	int deleteResultByPlanId(@Param("planId") int planId);
}