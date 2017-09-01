package com.archives.service;

import java.util.List;

import com.archives.pojo.InventoryPlan;
import com.archives.pojo.InventoryResult;
import com.archives.pojo.LocationInfo;
import com.archives.pojo.SpeakResult;
import com.archives.pojo.Status;
import com.system.workflow.activiti.commons.Pager;

public interface InventoryService {

	List<InventoryPlan> getAllInventoryPlanList(String inventoryPlanName,
			String createrName, String stime, String etime, String type) throws Exception;

	List<InventoryPlan> getInventoryPlanListForPage(int start, int rows, String type, String show, String planId) throws Exception;
	
	int getTotalPlanList(String type, String status) throws Exception;
	
	List<Status> prosLibrary(int start, int rows, String type, String status, String planId)throws Exception;
	
	Pager toListHistory(InventoryPlan inventoryPlan ,int start, int totalSize ) throws Exception;

	List<LocationInfo> loadLocationFrame(int locationId)throws Exception;

	List<Status> prosFrame(String locationId, int inventoryPlanId)throws Exception;

	List<LocationInfo> loadLocationGrid(int locationId)throws Exception;
	
	List<LocationInfo> loadLocationLineGrid(int locationId, int serialNo)throws Exception;

	List<Status> prosGrid(int locationId, InventoryPlan plan, int width)throws Exception;
	
	int getUnReadCount(int locationId, int inventoryPlanId)throws Exception;
	
	List<Status> prosCellGrid(int locationId, InventoryPlan plan)throws Exception;
	
	Status createStatus(LocationInfo lc, List<InventoryResult> cellArchives, List<InventoryResult> cellResults, InventoryPlan inventoryPlan, int maxlength, int width, boolean isCell);
	
	List<Status> prosBoxGrid(int locationId, int inventoryPlanId)throws Exception;
	
	List<Status> prosShelvesBoxGrid(int locationId, int inventoryPlanId)throws Exception;

	Pager loadDiffDetailDataGrid(Integer inventoryPlanId,
			Integer inventoryLoactionGuid, int page, int rows)throws Exception;
	
	Pager loadBoxDiffDetailDataGrid(Integer inventoryPlanId,
			Integer inventoryLoactionGuid, int page, int rows)throws Exception;

	void okInventoryPlan(String inventoryPlanId)throws Exception;

	InventoryPlan getInventoryPlanByGuid(Integer guid)throws Exception;

	List<InventoryPlan> upShelvesPlanList()throws Exception;
	
	List<SpeakResult> getMissResult(Integer planId);
	
	List<SpeakResult> getWrongResult(Integer planId);
	
	int getWarningCount(Integer planId);
	
	void insertSpeakResult(SpeakResult speakResults);
	
	void clear(Integer planId);
}
