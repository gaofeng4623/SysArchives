package com.archives.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.archives.dao.InventoryPlanCellDao;
import com.archives.dao.InventoryPlanDao;
import com.archives.dao.InventoryResultDao;
import com.archives.dao.LocationInfoDao;
import com.archives.pojo.InventoryPlan;
import com.archives.pojo.InventoryPlanCell;
import com.archives.pojo.InventoryResult;
import com.archives.pojo.LocationInfo;
import com.archives.service.ArchiveInventoryService;
import com.system.base.pojo.Result;
import com.system.dao.database.EmployeeDao;
import com.system.workflow.activiti.commons.Pager;

@Service
public class ArchiveInventoryServiceImpl implements ArchiveInventoryService {
	
	@Autowired
	private InventoryPlanDao inventoryPlanDao;
	
	@Autowired
	private InventoryResultDao inventoryResultDao;
	
	@Autowired
	private LocationInfoDao locationInfoDao;
	
	@Autowired
	private InventoryPlanCellDao inventoryPlanCellDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	
	@Override
	public Pager loadRecordData(Integer page, Integer rows,
			String inventoryPlanName, String createrName, String stime,
			String etime,String sign) {
		int start = (page-1)*rows;
		Map m = new HashMap();
		m.put("start", start);
		m.put("rows", rows);
		m.put("inventoryPlanName", inventoryPlanName);
		m.put("createrName", createrName);
		m.put("stime", stime);
		m.put("etime", etime);
		m.put("sign", sign);
		List<InventoryPlan> inventoryPlan = inventoryPlanDao.loadRecordData(m);
		int total = inventoryPlanDao.findInventoryRecordCount(m);
		for(InventoryPlan tempPlan: inventoryPlan){
			// 获取盘点数量
			int inventoryCount = inventoryResultDao.findInventoryCount(tempPlan.getGuid());
			String inventoryLocationGuid = tempPlan.getInventoryLocationGuid();
			List<Integer> locationIds = new ArrayList<Integer>();
			if(null != inventoryLocationGuid){
				String[] locationIdStr = inventoryLocationGuid.split(",");
				for(String locationId : locationIdStr){
					locationIds.add(Integer.parseInt(locationId));
				}
			}
			
			String inventoryLocationPath = locationInfoDao.getLocationPathByIds(locationIds);
			tempPlan.setInventoryLocationPath(inventoryLocationPath);
			//获取差异数量
			int locationDiff = inventoryResultDao.findInventoryDiffCount(tempPlan.getGuid());
			if(("2").equals(tempPlan.getIsEnd()) || ("4").equals(tempPlan.getIsEnd())){
				tempPlan.setStatus("盘点结束");
				tempPlan.setInventoryCount(inventoryCount);
				tempPlan.setDiffCount(String.valueOf(locationDiff));
				//获取盘点结果，状态为盘点结束的根据盘点id查询差异表中是否有差异的rfid数据，结果“一致”“不一致”
				if(locationDiff==0){
					tempPlan.setInventoryResult("一致");
				}else{
					tempPlan.setInventoryResult("不一致");
				}
			}else if(("1").equals(tempPlan.getIsEnd())){
				tempPlan.setStatus("正在进行中");
				tempPlan.setInventoryCount(inventoryCount);
				tempPlan.setDiffCount(String.valueOf(locationDiff));
				//获取盘点结果，状态为盘点结束的根据盘点id查询差异表中是否有差异的rfid数据，结果“一致”“不一致”
				if(locationDiff==0){
					tempPlan.setInventoryResult("一致");
				}else{
					tempPlan.setInventoryResult("不一致");
				}
			}else if(("3").equals(tempPlan.getIsEnd())){
				tempPlan.setStatus("盘点中止");
				tempPlan.setInventoryCount(inventoryCount);
				tempPlan.setDiffCount(String.valueOf(locationDiff));
				//获取盘点结果，状态为盘点结束的根据盘点id查询差异表中是否有差异的rfid数据，结果“一致”“不一致”
				if(locationDiff==0){
					tempPlan.setInventoryResult("一致");
				}else{
					tempPlan.setInventoryResult("不一致");
				}
			}else{
				tempPlan.setInventoryResult("");
				tempPlan.setDiffCount("");
				tempPlan.setStatus("盘点未开始");
			}
		}
		
		return new Pager(total,inventoryPlan);
	}
	
	@Override
	public Pager doSearchRemoveData(Integer page, Integer rows,
			String inventoryPlanName, String createrName, String stime,
			String etime,String sign) {
		int start = (page-1)*rows;
		Map m = new HashMap();
		m.put("start", start);
		m.put("rows", rows);
		m.put("inventoryPlanName", inventoryPlanName);
		m.put("createrName", createrName);
		m.put("stime", stime);
		m.put("etime", etime);
		m.put("sign", sign);
		List<InventoryPlan> inventoryPlan = inventoryPlanDao.loadRemoveData(m);
		int total = inventoryPlanDao.findInventoryRemoveCount(m);
		for(InventoryPlan tempPlan: inventoryPlan){
			// 获取盘点数量
			int inventoryCount = inventoryResultDao.findInventoryCount(tempPlan.getGuid());
			String inventoryLocationGuid = tempPlan.getInventoryLocationGuid();
			List<Integer> locationIds = new ArrayList<Integer>();
			if(null != inventoryLocationGuid){
				String[] locationIdStr = inventoryLocationGuid.split(",");
				for(String locationId : locationIdStr){
					locationIds.add(Integer.parseInt(locationId));
				}
			}
			
			String inventoryLocationPath = locationInfoDao.getLocationPathByIds(locationIds);
			tempPlan.setInventoryLocationPath(inventoryLocationPath);
			tempPlan.setInventoryCount(inventoryCount);
		}
		
		return new Pager(total,inventoryPlan);
	}
	
	
	@Override
	public boolean isExistPlan(String location, Integer guid) {
		boolean result = true;
		int LOCATION_TYPE = 8; // 5 ：位置类型为架   8:位置类型为格子
		List<Integer> locationGuids = new ArrayList<Integer>();
		for(String locationId : location.split(",")){
			if(StringUtils.isEmpty(locationId)){
				continue;
			}
			LocationInfo tempLocation =  locationInfoDao.selectByPrimaryKey(Integer.parseInt(locationId));
			if(null != tempLocation){
				Map m = new HashMap();
				m.put("locationPath", tempLocation.getLocationPath());
				m.put("locationType", LOCATION_TYPE);
				List<LocationInfo> locationList = locationInfoDao.getCellLocationByLocationName(m);
				if(null != locationList){
					for(LocationInfo  locationInfo: locationList){
						locationGuids.add(locationInfo.getGuid());
					}
				}
				
			}
		}
		if(0 < locationGuids.size()){
			Integer count = inventoryPlanCellDao.selectByLocationGuids(locationGuids, guid);
			if(null!=count && 0 < count){
				result = false;
			}
		}else{
			result = false;
		}
		
		return result;
	}
	@Override
	public Integer saveInventoryPlan(InventoryPlan inventory) {
		// 保存盘点计划并返回计划的guid 
		inventory.setIsEnd("0");
		inventoryPlanDao.insertSelective(inventory);
		String locations = inventory.getInventoryLocationGuid();
		System.out.println("locations : " + locations);
		// 获取所有要盘点的位置位置名称 
		List<String> locationPaths = new ArrayList<String>();
		if(null != inventory && !"".equals(inventory)){
			for(String locationId : locations.split(",")){
				if(StringUtils.isEmpty(locationId)){
					continue;
				}
				System.out.println("locationId = " + locationId);
				LocationInfo tempLocation =  locationInfoDao.selectByPrimaryKey(Integer.parseInt(locationId));
				if(null != tempLocation){
					locationPaths.add(tempLocation.getLocationPath());
				}
			}
		}
		// 根据位置名称获取所有要盘点的格子
		List<InventoryPlanCell> list = new ArrayList<InventoryPlanCell>();
		int LOCATION_TYPE = 8; // 5 ：位置类型为格子
		for(String locationPath : locationPaths){
			Map m = new HashMap();
			m.put("locationPath", locationPath);
			m.put("locationType", LOCATION_TYPE);
			List<LocationInfo> locationList = locationInfoDao.getCellLocationByLocationName(m);
			// 处理盘点计划对应的格子信息
			for(LocationInfo tempLocation :locationList){
				InventoryPlanCell planCess = new InventoryPlanCell();
				planCess.setInventoryPlanId(inventory.getGuid());
				planCess.setLocationGuid(tempLocation.getGuid());
				list.add(planCess);
			}
		}
		inventoryPlanCellDao.batchInsertPlanCell(list);
		
		return inventory.getGuid();
		
	}
	@Override
	public Pager loadResultDetailData(int page, int rows, int inventoryPlanId) {
		Pager resultPage = new Pager();
		int start = (page-1)*rows;
		Map m = new HashMap();
		m.put("start", start);
		m.put("rows", rows);
		m.put("inventoryPlanId", inventoryPlanId);
		List<InventoryResult> inventoryResult = inventoryResultDao.loadResultDetailData(m);
		int total = inventoryResultDao.findInventoryResultCount(m);
		resultPage.setRows(inventoryResult);
		resultPage.setTotal(total);
		return resultPage;
	}
	@Override
	public Pager loadDiffDetailData(int page, int rows, int inventoryPlanId) {
		Pager resultPage = new Pager();
		int start = (page-1)*rows;
		Map m = new HashMap();
		m.put("start", start);
		m.put("rows", rows);
		m.put("inventoryPlanId", inventoryPlanId);
		List<InventoryResult> inventoryDiff = inventoryResultDao.loadDiffDetailData(m);
		System.out.println("单元格统计：" + inventoryDiff.size());
		for(InventoryResult deffInv : inventoryDiff){
			if(null != deffInv.getSysLocationGuid()){
				LocationInfo locationInfo = locationInfoDao.selectByPrimaryKey(deffInv.getSysLocationGuid());
				if(null != locationInfo){
					deffInv.setSysLocationPath(locationInfo.getLocationPath());
				}
			}else{
				deffInv.setSysLocationPath("未上架");
			}
			if("-1".equals(deffInv.getStatus())){
				int count = isExistInInventoryPlan(inventoryPlanId, deffInv.getRfid());
				if(count>0){
					deffInv.setStatusName("错放档案");
				}else{
					deffInv.setStatusName("缺失档案");
				}
				
			}else if("0".equals(deffInv.getStatus())){
				deffInv.setStatusName("错放档案");
			}else if("2".equals(deffInv.getStatus())){
				deffInv.setStatusName("补录档案");
			}else{
				deffInv.setStatusName("正常档案");
			}
			
		}
		int total = inventoryResultDao.findInventoryDiffCountPage(m);
		resultPage.setRows(inventoryDiff);
		resultPage.setTotal(total);
		return resultPage;
	}
	// 判断其他位置是否有此档案
	private int isExistInInventoryPlan(int inventoryPlanId, String rfid) {
		Map m = new HashMap();
		m.put("inventoryPlanId", inventoryPlanId);
		m.put("rfid", rfid);
		return inventoryResultDao.isExistInInventoryPlan(m);
	}
	@Override
	public Pager loadInstantInventoryCell(Integer page, Integer rows,
			String inventoryMan, String status, String stime, String etime, String inventoryPlanId,String sign) {
		Pager resultPage = new Pager();
		List<InventoryPlanCell> cellList;
		int total;
		int start = (page-1)*rows;
		Map m = new HashMap();
		m.put("start", start);
		m.put("rows", rows);
		m.put("inventoryMan", inventoryMan);
		m.put("status", status);
		m.put("stime", stime);
		m.put("etime", etime);
		m.put("inventoryPlanId", inventoryPlanId);
		if(sign.equals("0")){
			 cellList = inventoryPlanCellDao.loadInstantInventoryCell(m);
			 total = inventoryPlanCellDao.loadInstantInventoryCellCount(m);
		}else{
			 cellList = inventoryPlanCellDao.boxLoadInstantInventoryCell(m);
			 total = inventoryPlanCellDao.boxLoadInstantInventoryCellCount(m);
		}
		
		resultPage.setRows(cellList);
		resultPage.setTotal(total);
		return resultPage;
	}
	@Override
	public Pager loadInstantInventoryData(Integer page, Integer rows, String planId,
			String locationGuid, String inventoryDate, String stime,
			String etime) throws Exception {
		Pager resultPage = new Pager();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date inventDate = new Date();
		inventDate.setTime(Long.parseLong(inventoryDate));
		inventoryDate = sdf.format(inventDate);
		int start = (page-1)*rows;
		Map m = new HashMap();
		m.put("start", start);
		m.put("rows", rows);
		m.put("planId", planId);
		m.put("locationGuid", locationGuid);
		m.put("inventoryDate", inventoryDate);
		m.put("stime", stime);
		m.put("etime", etime);
		List<InventoryResult> inventoryResult = inventoryResultDao.loadInstantInventoryData(m);
		for(InventoryResult tempResult : inventoryResult){
			if((tempResult.getSysLocationGuid()==0) || (null==tempResult.getSysLocationGuid())){
				tempResult.setSysLocationPath("未上架");
			}else{
				LocationInfo location = locationInfoDao.selectByPrimaryKey(tempResult.getSysLocationGuid());
				if(null != location){
					tempResult.setSysLocationPath(location.getLocationPath());
				}else{
					tempResult.setSysLocationPath("未上架");
				}
			}
			
			/*if("-1".equals(tempResult.getStatus())){
				tempResult.setStatusName("缺失档案");
				Employee emp = employeeDao.findEmployeeById(tempResult.getCreater());
				String inventoryman = null;
				if(null != emp){
					inventoryman = emp.getEmployeeName();
				}
				int count = isExistInInstantInventory(tempResult.getRfid(),inventoryman,stime, etime);
				if(count<2){
					tempResult.setResultName("缺失档案");
				}else{
					tempResult.setResultName("错放档案");
				}
				
			}else if("0".equals(tempResult.getStatus())){
				tempResult.setStatusName("错放档案");
				tempResult.setResultName("错放档案");
			}else if("2".equals(tempResult.getStatus())){
				tempResult.setStatusName("补录档案");
				tempResult.setResultName("补录档案");
			}else{
				tempResult.setStatusName("正常档案");
				tempResult.setResultName("正常档案");
			}*/
		}
		
		int total = inventoryResultDao.findInstantInventoryCount(m);
		resultPage.setRows(inventoryResult);
		resultPage.setTotal(total);
		return resultPage;
	}
	private int isExistInInstantInventory(String rfid, String creater,
			String stime, String etime) {
		Map m = new HashMap();
		m.put("rfid", rfid);
		m.put("creater", creater);
		m.put("stime", stime);
		m.put("etime", etime);
		return inventoryResultDao.isExistInInstantInventory(m);
	}
	@Override
	public Result delInventoryPlan(List<String> idList) throws Exception {
		inventoryPlanCellDao.deletePlanCellByInvenId(idList);
		inventoryPlanDao.deleteInventoryPlanByIds(idList);
		inventoryResultDao.deleteInventoryPlanResultByIds(idList);
		Result result = new Result();
		result.setStatus(0);
		result.setMessage("删除成功！");
		return result;
	}
	@Override
	public void updInventoryPlan(InventoryPlan inventory) throws Exception {
		// 保存盘点计划并返回计划的guid 
		inventory.setIsEnd("0");
		// 修改盘点计划
		inventoryPlanDao.updateByPrimaryKey(inventory);
		List<String> idList = new ArrayList<String>();
		idList.add(""+inventory.getGuid());
		// 删除原计划的格子
		inventoryPlanCellDao.deletePlanCellByInvenId(idList);
		
		String locations = inventory.getInventoryLocationGuid();
		// 获取所有要盘点的位置位置名称 
		List<String> locationPaths = new ArrayList<String>();
		if(null != inventory && !"".equals(inventory)){
			for(String locationId : locations.split(",")){
				if(StringUtils.isEmpty(locationId)){
					continue;
				}
				LocationInfo tempLocation =  locationInfoDao.selectByPrimaryKey(Integer.parseInt(locationId));
				if(null != tempLocation){
					locationPaths.add(tempLocation.getLocationPath());
				}
			}
		}
		// 根据位置名称获取所有要盘点的格子
		List<InventoryPlanCell> list = new ArrayList<InventoryPlanCell>();
		int LOCATION_TYPE = 5; // 5 ：位置类型为架
		for(String locationPath : locationPaths){
			Map m = new HashMap();
			m.put("locationPath", locationPath);
			m.put("locationType", LOCATION_TYPE);
			List<LocationInfo> locationList = locationInfoDao.getCellLocationByLocationName(m);
			// 处理盘点计划对应的格子信息
			for(LocationInfo tempLocation :locationList){
				InventoryPlanCell planCess = new InventoryPlanCell();
				planCess.setInventoryPlanId(inventory.getGuid());
				planCess.setLocationGuid(tempLocation.getGuid());
				list.add(planCess);
			}
		}
		inventoryPlanCellDao.batchInsertPlanCell(list);
	}

}
