package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.InventoryResult;
import com.archives.pojo.SpeakResult;

public interface InventoryResultDao {
    int deleteByPrimaryKey(Integer guid);

    int insert(InventoryResult record);

    int insertSelective(InventoryResult record);

    InventoryResult selectByPrimaryKey(Integer guid);

    int updateByPrimaryKeySelective(InventoryResult record);

    int updateByPrimaryKey(InventoryResult record);

	int findInventoryCount(Integer guid);

	int findInventoryDiffCount(Integer guid);

	List<InventoryResult> loadResultDetailData(@Param("m")Map m);

	int findInventoryResultCount(@Param("m")Map m);

	List<InventoryResult> loadDiffDetailData(@Param("m")Map m);

	int findInventoryDiffCountPage(@Param("m")Map m);

	int isExistInInventoryPlan(@Param("m")Map m);

	List<InventoryResult> loadInstantInventoryData(@Param("m")Map m);

	int findInstantInventoryCount(@Param("m")Map m);

	int isExistInInstantInventory(@Param("m")Map m);

	int countLostInventory(@Param("guid")Integer guid, @Param("locationPath")String locationPath);
	
	int countSurplusInventory(@Param("guid")Integer guid, @Param("locationPath")String locationPath);
	
	int countBoxSurplusInventory(@Param("guid")Integer guid, @Param("locationPath")String locationPath);

	int countRightInventory(@Param("guid")Integer guid, @Param("locationPath")String locationPath);
	
	List<InventoryResult> selectArchivesByLocation(@Param("locationGuid")Integer locationGuid);
	
	List<InventoryResult> selectInventoryResult(@Param("inventoryPlanId")Integer inventoryPlanId);
	
	int readInventoryResult(@Param("inventoryPlanId")Integer inventoryPlanId,
			@Param("inventoryLoactionGuid")Integer inventoryLoactionGuid);

	List<InventoryResult> loadDiffDetailDataGrid(@Param("inventoryPlanId")Integer inventoryPlanId,
		@Param("inventoryLoactionGuid")Integer inventoryLoactionGuid, @Param("start") int start, @Param("rows")int rows);
	
	List<InventoryResult> loadBoxDiffDetailDataGrid(@Param("inventoryPlanId")Integer inventoryPlanId,
			@Param("inventoryLoactionGuid")Integer inventoryLoactionGuid, @Param("start") int start, @Param("rows")int rows);
	
	int loadCountDiffDetailDataGrid(@Param("inventoryPlanId")Integer inventoryPlanId,@Param("inventoryLoactionGuid")Integer inventoryLoactionGuid);
	
	int loadBoxCountDiffDetailDataGrid(@Param("inventoryPlanId")Integer inventoryPlanId,@Param("inventoryLoactionGuid")Integer inventoryLoactionGuid);
	
	int getTotalCellByLocationGuid(@Param("guid") Integer guid);
	
	int getPassCellByLocationGuid(@Param("planId") Integer planId, @Param("guid") Integer guid);
	
	int testWarningCount(@Param("planId") Integer planId, @Param("guid") Integer guid);  //获得诉讼某架子下报警的档案数量
	
	int testBoxWarningCount(@Param("planId") Integer planId, @Param("guid") Integer guid);  //获得文书某架子下报警的档案数量
	
	List<SpeakResult> getMissResult(@Param("planId") Integer planId);
	
	List<SpeakResult> getWrongResult(@Param("planId") Integer planId);
	
	int getWarningCount(@Param("planId") Integer planId);
	
	int testPassCess(@Param("planId") Integer planId, @Param("guid") Integer guid);
	
	void deleteInventoryPlanResultByIds(@Param("idList")List<String> idList);
	
	void insertSpeakResult(SpeakResult speakResults);
	
	void clear(@Param("planId")Integer planId);
	
	int getUnReadCount(@Param("planId") Integer planId, @Param("guid") Integer guid);

}