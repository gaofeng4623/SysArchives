package com.archives.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.InventoryPlan;
import com.archives.pojo.LocationInfo;
import com.archives.service.ArchiveInventoryService;
import com.archives.service.InventoryService;
import com.archives.service.InventroyService;
import com.archives.service.LocationInfoService;
import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.util.common.Consts;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/libInventory")
public class ArchiveInventoryController {
	
	@Resource
	private ArchiveInventoryService archiveInventoryService;
	@Resource
	private InventoryService inventoryService;
	@Resource
	private LocationInfoService locationInfoService;
	@Resource
	private InventroyService inventroyService;
	/**
	 * 根据条件查询盘点记录
	 * @Title: doSearchRecordData  
	 * @Description: TODO 
	 * @param
	 * @return Object     
	 * @throws
	 */
	@RequestMapping("/doSearchRecordData.do")
	@ResponseBody
	public Pager doSearchRecordData(Integer page, Integer rows, String inventoryPlanName,
			String createrName, String stime, String etime,String sign){
		if(page==null || rows==null){
			page = 1;
			rows = 20;
		}
		return archiveInventoryService.loadRecordData(page, rows, inventoryPlanName, createrName, stime, etime,sign);
	}
	
	@RequestMapping("/doSearchRemoveData.do")
	@ResponseBody
	public Pager doSearchRemoveData(Integer page, Integer rows, String inventoryPlanName,
			String createrName, String stime, String etime,String sign){
		if(page==null || rows==null){
			page = 1;
			rows = 20;
		}
		return archiveInventoryService.doSearchRemoveData(page, rows, inventoryPlanName, createrName, stime, etime,sign);
	}
	
	
	//上架盘点弹窗
	@RequestMapping("/addShelvesPlanInit.do")
	@ResponseBody
	public ModelAndView addShelvesPlanInit(){
		ModelAndView mv = new ModelAndView("alert/shelvesPlan");
		return mv;
	}
	
	//移架盘点页面
	@RequestMapping("/addRemovePlanInit.do")
	@ResponseBody
	public ModelAndView addRemovePlanInit(String type){
		ModelAndView mv = new ModelAndView("alert/removePlan");
		mv.addObject("type", type);
		return mv;
	}
	
	@RequestMapping("/addDocShelvesPlanInit.do")
	@ResponseBody
	public ModelAndView addDocShelvesPlanInit(){
		ModelAndView mv = new ModelAndView("alert/docShelvesPlan");
		return mv;
	}
	
	@RequestMapping("/addInventoryPlanInit.do")
	@ResponseBody
	public ModelAndView addInventoryPlanInit(){
		ModelAndView mv = new ModelAndView("alert/inventoryPlan");
		return mv;
	}
	@RequestMapping("/addInventoryDocPlanInit.do")
	@ResponseBody
	public ModelAndView addInventoryDocPlanInit(){
		ModelAndView mv = new ModelAndView("alert/inventoryDocPlan");
		return mv;
	}
	
	/**
	 * 
	 * @Title: saveInventoryPlan  
	 * @Description: TODO 
	 * @param inventoryPlanName 盘点计划名称   location 盘点位置编码  locationname 盘点位置名称
	 * @return Object     
	 * @throws
	 */
	@RequestMapping("/saveInventoryPlan.do")
	@ResponseBody
	public Result saveInventoryPlan(InventoryPlan inventory, HttpSession session){
		Result result = null;
		inventory.setInventoryLocationGuid(inventory.getLocation());
		inventory.setCreateDate(new Date());
		if(null == inventory.getIsCircle()){
			inventory.setCicleType(null);
			inventory.setCircleSize(null);
		}
		// 自定义盘点
		//inventory.setInventoryType("1");
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		inventory.setCreater(emp.getEmployeeId());
		
		if(null !=inventory.getIsCircle() &&  1 == inventory.getIsCircle()){
			// 周期盘点
			archiveInventoryService.saveInventoryPlan(inventory);
			result = new Result();
			result.setStatus(0);
			result.setMessage(inventory.getLocationname()+"的周期盘点计划制定成功！");
			return result;
		}else{
			// 非周期盘点 
			if(archiveInventoryService.isExistPlan(inventory.getLocation(), null)){
				archiveInventoryService.saveInventoryPlan(inventory);
				result = new Result();
				result.setStatus(0);
				result.setMessage("盘点"+inventory.getLocationname()+"的计划制定成功！");
				return result;
			} else {
				result = new Result();
				result.setStatus(1);
				result.setMessage("盘点位置"+inventory.getLocationname()+"已制定盘点计划，盘点未结束，请重新选择盘点位置！");
				return result;
			}
		}
	}
	
	
	
	/**
	 * 
	 * @Title: 制定上架盘点并执行
	 * @Description: TODO 
	 * @param inventoryPlanName 盘点计划名称   location 盘点位置编码  locationname 盘点位置名称
	 * @return Object     
	 * @throws
	 */
	
	@RequestMapping("/saveAndExecuteInventoryPlan.do")
	@ResponseBody
	public Result saveAndExecuteInventoryPlan(InventoryPlan inventory, HttpSession session){
		Result result = new Result();
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		inventory.setCreater(emp.getEmployeeId());
		inventory.setInventoryLocationGuid(inventory.getLocation());
		inventory.setCreateDate(new Date());
		inventory.setCicleType(null);
		inventory.setCircleSize(null);
		if(archiveInventoryService.isExistPlan(inventory.getLocation(), null)){
			try {
				Integer guid = archiveInventoryService.saveInventoryPlan(inventory);
				Object[] flag = inventroyService.invokeInventroy("Inventroy", new Object[]{guid, 5});
				if ("0".equals(flag[0])){
					result.setStatus(0);
					result.setMessage("上架盘点制定成功！");
				} else {
					result.setStatus(1);
					result.setMessage(flag[0].toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(1);
				result.setMessage("调用盘点服务失败,请联系管理员!");
			}
		} else {
			result.setStatus(1);
			result.setMessage("盘点位置"+inventory.getLocationname()+"已制定盘点计划，盘点未结束，请重新选择盘点位置！");
		}
		return result;
	}
	
	
	/**
	 * 
	 * @Title: 制定重新上架盘点计划并执行
	 * @Description: TODO 
	 * @param inventoryPlanName 盘点计划名称   location 盘点位置编码  locationname 盘点位置名称
	 * @return Object     
	 * @throws
	 */
	
	@RequestMapping("/saveAndExecuteRemovePlan.do")
	@ResponseBody
	public Result saveAndExecuteRemovePlan(InventoryPlan inventory, HttpSession session){
		Result result = new Result();
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		inventory.setCreater(emp.getEmployeeId());
		inventory.setInventoryLocationGuid(inventory.getLocation());
		inventory.setCreateDate(new Date());
		inventory.setCicleType(null);
		inventory.setCircleSize(null);
		if(archiveInventoryService.isExistPlan(inventory.getLocation(), null)){
			try {
				Integer guid = archiveInventoryService.saveInventoryPlan(inventory);
				Object[] flag = inventroyService.invokeInventroy("Inventroy", new Object[]{guid, 6});
				if ("0".equals(flag[0])){
					result.setStatus(0);
					result.setMessage("重新上架盘点制定成功！");
				} else {
					result.setStatus(1);
					result.setMessage(flag[0].toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(1);
				result.setMessage("调用盘点服务失败,请联系管理员!");
			}
		} else {
			result.setStatus(1);
			result.setMessage("盘点位置"+inventory.getLocationname()+"已制定盘点计划，盘点未结束，请重新选择盘点位置！");
		}
		return result;
	}
	
	
	/**
	 * 
	 * @Title: 保存盘点计划  
	 * @Description: TODO 
	 * @param inventoryPlanName 盘点计划名称   location 盘点位置编码  locationname 盘点位置名称
	 * @return Object     
	 * @throws
	 */
	@RequestMapping("/updInventoryPlan.do")
	@ResponseBody
	public Result updInventoryPlan(InventoryPlan inventory, HttpSession session)throws Exception{
		Result result = null;	
		inventory.setInventoryLocationGuid(inventory.getLocation());
		inventory.setCreateDate(new Date());
		if(null == inventory.getIsCircle()){
			inventory.setIsCircle(0);
			inventory.setCicleType(null);
			inventory.setCircleSize(null);
		}
		// 自定义盘点
		inventory.setInventoryType("4");
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		inventory.setCreater(emp.getEmployeeId());
		if(archiveInventoryService.isExistPlan(inventory.getLocation(), inventory.getGuid())){
			archiveInventoryService.updInventoryPlan(inventory);
			result = new Result();
			result.setStatus(0);
			result.setMessage("盘点"+inventory.getLocationname()+"的计划修改成功！");
			return result;
		}else{
			result = new Result();
			result.setStatus(1);
			result.setMessage("盘点位置"+inventory.getLocationname()+"已制定盘点计划，盘点未结束，请重新选择盘点位置！");
			return result;
		}
		
	}
	
	/**
	 * 显示盘点结果
	 * @Title: toShowResultDetailPage  
	 * @Description: TODO 
	 * @param  guid 盘点计划guid     
	 * @return Object     
	 * @throws
	 */
	@RequestMapping("/toShowResultDetailPage.do")
	public ModelAndView toShowResultDetailPage(String guid){
		ModelAndView mv = new ModelAndView("/inventoryResultDetail");
		mv.addObject("pid", guid);
		return mv;
	}
	
	/**
	 * 根据盘点计划的guid查询出盘点计划结果数据
	 * @Title: toShowResultDetailData  
	 * @Description: TODO 
	 * @param    inventoryPlanId 盘点计划guid   
	 * @return Object     
	 * @throws
	 */
	@RequestMapping("/toShowResultDetail.do")
	@ResponseBody
	public Pager toShowResultDetailData(int page,int rows,String inventoryPlanId){
		if(StringUtils.isNotEmpty(inventoryPlanId)){
			Pager inventoryResult = archiveInventoryService.loadResultDetailData(page, rows, Integer.parseInt(inventoryPlanId));
			return inventoryResult;
		}else{
			return null ;
		}
		
	}
	
	/**
	 * 显示盘点对比差异结果
	 * @Title: toShowDiffDetailPage  
	 * @Description: TODO 
	 * @param  guid 盘点计划guid     
	 * @return Object     
	 * @throws
	 */
	@RequestMapping("/toShowDiffDetailPage.do")
	public ModelAndView toShowDiffDetailPage(String guid,Model model){
		ModelAndView mv = new ModelAndView("/inventoryDiffDetail");
		mv.addObject("pid", guid);
		return mv;
	}

	@RequestMapping("/toShowDiffDetail.do")
	@ResponseBody
	public Pager toShowDiffDetailData(int page,int rows,String inventoryPlanId){
		if(StringUtils.isNotEmpty(inventoryPlanId)){
			Pager inventoryResult = archiveInventoryService.loadDiffDetailData(page, rows, Integer.parseInt(inventoryPlanId));
			return inventoryResult;
		}else{
			return null ;
		}
		
	}
	
	@RequestMapping("/doSearchInstantInventoryCell.do")
	@ResponseBody
	public Pager doSearchInstantInventoryCell(Integer page, Integer rows, 
			String inventoryMan,String status, String stime, String etime, String inventoryPlanId,String sign){
		if(page==null || rows==null){
			page = 1;
			rows = 20;
		}
		return archiveInventoryService.loadInstantInventoryCell(page, rows, inventoryMan,status, stime, etime, inventoryPlanId,sign);
	}
	
	/**
	 * 根据格子位置id查询盘点结果
	 * @Title: toShowInventoryCellDetail  
	 * @Description: TODO 
	 * @param       
	 * @return Object     
	 * @throws
	 */
	@RequestMapping("/toShowInventoryCellDetail.do")
	public ModelAndView toShowInventoryCellDetail(String planId,String locationGuid,String inventoryDate,String stime,String etime,String sign,String inventoryPlanId){
		ModelAndView mv = new ModelAndView("/instantInventoryHistory");
		mv.addObject("planId", planId);
		mv.addObject("locationGuid",locationGuid);
		mv.addObject("inventoryDate", inventoryDate);
		mv.addObject("inventoryPlanId",inventoryPlanId);
		mv.addObject("stime", stime);
		mv.addObject("etime", etime);
		mv.addObject("sign", sign);
		return mv;
	}
	/**
	 * @throws Exception 
	 * 根据格子查看即时盘点详细记录
	 * @Title: doSearchInstantInventoryData  
	 * @Description: TODO 
	 * @param       
	 * @return Object     
	 * @throws
	 */
	@RequestMapping("/doSearchInstantinventoryData.do")
	@ResponseBody
	public Pager doSearchInstantInventoryData(Integer page, Integer rows, String planId,
			String locationGuid,String inventoryDate,String stime,String etime) throws Exception{
		if(page==null || rows==null){
			page = 1;
			rows = 20;
		}
		return archiveInventoryService.loadInstantInventoryData(page, rows, planId, locationGuid,inventoryDate,stime,etime);
	}
	
	@RequestMapping("/delInventoryPlan.do")
	@ResponseBody
	public Result delInventoryPlan(String guids) throws Exception{
		List<String> idList = new ArrayList<String>();
		if(null != guids && !"".equals(guids)){
			for(String empId : guids.split(",")){
				if(StringUtils.isEmpty(empId)){
					continue;
				}
				idList.add(empId);
			}
		}
		return archiveInventoryService.delInventoryPlan(idList);
	}
	
	@RequestMapping("updInventoryPlanInit.do")
	@ResponseBody
	public ModelAndView updInventoryPlanInit(Integer guid) throws Exception{
		ModelAndView mv = new ModelAndView("alert/updInventoryPlan");
		InventoryPlan plan = inventoryService.getInventoryPlanByGuid(guid);
		if(null != plan.getBeginTime()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
			plan.setBeginTimeStr(sdf.format(plan.getBeginTime()));
		}
		if(null != plan.getInventoryLocationGuid()){
			String locationIds = plan.getInventoryLocationGuid();
			String[] locationIdArry = locationIds.split(",");
			String locationPaths = "";
			for(String locationId : locationIdArry){
				LocationInfo location = locationInfoService.getLocationInfoByGuid(Integer.parseInt(locationId));
				if(null != location){
					locationPaths += location.getLocationName()+ ",";
				}
			}
			plan.setInventoryLocationName(locationPaths);
			
		}
		mv.addObject("plan",plan);
		return mv;
	}
	@RequestMapping("updDocInventoryPlanInit.do")
	@ResponseBody
	public ModelAndView updDocInventoryPlanInit(Integer guid) throws Exception{
		ModelAndView mv = new ModelAndView("alert/updDocInventoryPlan");
		InventoryPlan plan = inventoryService.getInventoryPlanByGuid(guid);
		if(null != plan.getBeginTime()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
			plan.setBeginTimeStr(sdf.format(plan.getBeginTime()));
		}
		if(null != plan.getInventoryLocationGuid()){
			String locationIds = plan.getInventoryLocationGuid();
			String[] locationIdArry = locationIds.split(",");
			String locationPaths = "";
			for(String locationId : locationIdArry){
				LocationInfo location = locationInfoService.getLocationInfoByGuid(Integer.parseInt(locationId));
				if(null != location){
					locationPaths += location.getLocationName()+ ",";
				}
			}
			plan.setInventoryLocationName(locationPaths);
			
		}
		mv.addObject("plan",plan);
		return mv;
	}
}
