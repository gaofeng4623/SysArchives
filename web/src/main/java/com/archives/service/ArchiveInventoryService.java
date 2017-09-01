package com.archives.service;

import java.util.List;

import com.archives.pojo.InventoryPlan;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface ArchiveInventoryService {

	Pager loadRecordData(Integer page, Integer rows, String inventoryPlanName,
			String createrName, String stime, String etime,String sign);
	
	Pager doSearchRemoveData(Integer page, Integer rows, String inventoryPlanName,
			String createrName, String stime, String etime,String sign); 

	boolean isExistPlan(String location, Integer guid);

	Integer saveInventoryPlan(InventoryPlan inventory);

	Pager loadResultDetailData(int page, int rows, int inventoryPlanId);

	Pager loadDiffDetailData(int page, int rows, int inventoryPlanId);

	Pager loadInstantInventoryCell(Integer page, Integer rows,
			String inventoryMan, String status, String stime, String etime, String inventoryPlanId,String sign);

	Pager loadInstantInventoryData(Integer page, Integer rows, String planId,
			String locationGuid, String inventoryDate, String stime,
			String etime) throws Exception;

	Result delInventoryPlan(List<String> idList) throws Exception;

	void updInventoryPlan(InventoryPlan inventory)throws Exception;

}
