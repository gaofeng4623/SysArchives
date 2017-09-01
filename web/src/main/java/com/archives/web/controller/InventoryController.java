package com.archives.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.InventoryPlan;
import com.archives.pojo.LocationInfo;
import com.archives.pojo.SpeakResult;
import com.archives.pojo.Status;
import com.archives.service.InventoryService;
import com.archives.service.InventroyService;
import com.archives.service.LocationInfoService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
	
	@Resource
	private InventoryService inventoryService;
	
	@Resource
	private LocationInfoService locationInfoService;
	
	@Resource
	private InventroyService inventroyService;
	
	
	
	//盘点历史
	@RequestMapping("/toInventoryHistory.do")
	public ModelAndView toInventoryHistory(String inventoryPlanName,
			String createrName, String stime, String etime,String sign) throws Exception {
		ModelAndView mv = new ModelAndView("/inventoryHistory");
		//List<InventoryPlan> planList = inventoryService.getInventoryPlanList(inventoryPlanName, createrName,  stime,  etime);
		//mv.addObject("planList", planList);
		mv.addObject("sign",sign);
		return mv;
	}
	
	//盘点历史查看结果
	@RequestMapping("/toResultHistory.do")
	public ModelAndView toResultHistory(String inventoryPlanId,String sign) throws Exception {
		ModelAndView mv = new ModelAndView("/instantInventoryManage");
		mv.addObject("inventoryPlanId", inventoryPlanId);
		mv.addObject("sign",sign);
		return mv;
	}
	
	//盘点历史计划列表
	@RequestMapping("/toListHistory.do")
	@ResponseBody
	public Pager toListHistory(InventoryPlan inventoryPlan, int rows, int page) throws Exception {
		int start = (page -1) * rows;
		Pager resultPage = inventoryService.toListHistory(inventoryPlan, start, rows);
		return resultPage;
	}
	
	
	//架页面
	@RequestMapping("/toInventoryFrame.do")
	public ModelAndView toInventoryFrame(int locationId, int inventoryPlanId, String inventoryType, String type) throws Exception {
		ModelAndView mv = new ModelAndView("/inventoryFrame");
		InventoryPlan plan = inventoryService.getInventoryPlanByGuid(inventoryPlanId);
		List<LocationInfo> location = inventoryService.loadLocationFrame(locationId);
		LocationInfo frameLocation = locationInfoService.getLocationInfoByGuid(locationId);
		mv.addObject("locationId", locationId);
		mv.addObject("location", location);
		mv.addObject("inventoryPlanId", inventoryPlanId);
		mv.addObject("inventoryType", inventoryType);
		mv.addObject("locationName", frameLocation.getLocationPath());
		mv.addObject("type", type);
		mv.addObject("isEnd", plan.getIsEnd());
		
		return mv;
	}
	
	@RequestMapping("/prosFrame.do")
	@ResponseBody
	public Result prosFrame(String locationId, int inventoryPlanId, HttpSession session) {
		Result result = new Result();
		try {
			List<Status> status =  inventoryService.prosFrame(locationId, inventoryPlanId);
			result.setStatus(0);
			result.setMessage("请求成功！");
			result.setData(status);
		} catch (Exception e) {
			result.setStatus(1);
			result.setMessage("出现异常！");
			e.printStackTrace();
		}
		return result;
	}
	
	
	// 盘点进度页面分页显示
	@RequestMapping("/toInventoryLibrary.do")
	public ModelAndView toInventoryLibrary(Integer page, Integer rows, String type, String show) throws Exception {
		int total = inventoryService.getTotalPlanList(type, show);
		int pagecount = total % 15 == 0 ? total / 15 : (total / 15) +1;
		page = page == null || page <= 0 ? 1 : page > pagecount ? pagecount : page;
		rows = rows == null ? 15 : rows;
		int start = (page - 1) * rows;
		ModelAndView mv = new ModelAndView("/inventoryLibrary");
		List<InventoryPlan> planList = inventoryService.getInventoryPlanListForPage(start, rows, type, show, null);
		mv.addObject("planList", planList);
		mv.addObject("type", type);
		mv.addObject("show", show);
		mv.addObject("page" , page);
		mv.addObject("rows", rows);
		mv.addObject("total", total);
		mv.addObject("pagecount", pagecount);
		return mv;
	}
	
	
	//分页显示盘点进度
	@RequestMapping("/prosLibrary.do")
	@ResponseBody
	public Result prosLibrary(Integer page, Integer rows, String type, String show, String planId) {
		Result result = new Result();
		try {
			page = page == null ? 1 : page;
			rows = rows == null ? 15 : rows;
			int start = (page -1) * rows;
			List<Status> data =  inventoryService.prosLibrary(start, rows, type, show, planId);
			result.setStatus(0);
			result.setMessage("请求成功！");
			result.setData(data);
		} catch (Exception e) {
			result.setStatus(1);
			result.setMessage("出现异常！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 诉讼的架子界面
	 * @param locationId
	 * @param inventoryPlanId
	 * @param inventoryType 5代表上架，1代表盘点
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toInventoryGrid.do")
	@ResponseBody
	public ModelAndView toInventoryGrid(int locationId, int inventoryPlanId) throws Exception {
		LocationInfo frameLocationInfo = locationInfoService.getLocationInfoByGuid(locationId);
		int locationType = frameLocationInfo.getLocationTypeGuid();
		int serialNo = frameLocationInfo.getSerialNo();
		List<LocationInfo> location = locationType == 7 ? 
		inventoryService.loadLocationLineGrid(locationId, serialNo) //显示行下的格子
	  : inventoryService.loadLocationGrid(locationId); //显示架下的格子
		InventoryPlan plan = inventoryService.getInventoryPlanByGuid(inventoryPlanId);
		//ModelAndView mv = new ModelAndView("alert/inventoryGrid");
		ModelAndView mv = new ModelAndView("alert/shelves");
		mv.addObject("locationId", locationId);
		mv.addObject("locationType", locationType);
		mv.addObject("location", location);
		mv.addObject("locationName", frameLocationInfo.getLocationPath());
		mv.addObject("inventoryPlanId", inventoryPlanId);
		mv.addObject("isEnd", plan.getIsEnd());
		return mv;
	}
	
	
	//文书架子页面
	@RequestMapping("/toInventoryBoxGrid.do")
	@ResponseBody
	public ModelAndView toInventoryBoxGrid(int locationId, int inventoryPlanId, String inventoryType) throws Exception {
		LocationInfo frameLocationInfo = locationInfoService.getLocationInfoByGuid(locationId);
		int locationType = frameLocationInfo.getLocationTypeGuid();
		int serialNo = frameLocationInfo.getSerialNo();
		List<LocationInfo> location = locationType == 7 ? 
		inventoryService.loadLocationLineGrid(locationId, serialNo) //显示行下的格子
	  : inventoryService.loadLocationGrid(locationId); //显示架下的格子
		ModelAndView mv = new ModelAndView("alert/inventoryBoxGrid");
		mv.addObject("locationId", locationId);
		mv.addObject("location", location);
		mv.addObject("locationName", frameLocationInfo.getLocationPath());
		mv.addObject("inventoryPlanId", inventoryPlanId);
		mv.addObject("inventoryType", inventoryType);
		return mv;
	}
	
	//诉讼格子盘点进度条
	@RequestMapping("/prosGrid.do")
	@ResponseBody
	public Result prosGrid(int locationId, int inventoryPlanId, int width) {
		Result result = new Result();
		try {
			InventoryPlan plan = inventoryService.getInventoryPlanByGuid(inventoryPlanId);
			List<Status> status = inventoryService.prosGrid(locationId, plan, width);
			int count = inventoryService.getUnReadCount(locationId, inventoryPlanId);
			int state = Integer.parseInt(plan.getIsEnd());
			state = (state == 2 || state == 4) && count == 0 ? 2 : 3; //当且仅当盘点结束并且结果读取完毕后，才算盘点结束
			result.setStatus(state);
			result.setMessage("请求成功！");
			result.setData(status);
		} catch (Exception e) {
			result.setStatus(9);
			result.setMessage("出现异常！");
			e.printStackTrace();
		}
		return result;
	}
	
	//诉讼点开格子详细页面进度
	@RequestMapping("/prosCellGrid.do")
	@ResponseBody
	public Result prosCellGrid(int locationId, int inventoryPlanId, HttpSession session) {
		Result result = new Result();
		try {
			InventoryPlan plan = inventoryService.getInventoryPlanByGuid(inventoryPlanId);
			List<Status> status = inventoryService.prosCellGrid(locationId, plan); 
			int count = inventoryService.getUnReadCount(locationId, inventoryPlanId);
			int state = Integer.parseInt(plan.getIsEnd());
			state = (state == 2 || state == 4) && count == 0 ? 2 : 3;
			result.setStatus(state);
			result.setMessage("请求成功！");
			result.setData(status);
		} catch (Exception e) {
			result.setStatus(9);
			result.setMessage("出现异常！");
			e.printStackTrace();
		}
		return result;
	}
	
	//文书格子盘点进度条
	@RequestMapping("/prosBoxGrid.do")
	@ResponseBody
	public Result prosBoxGrid(int locationId, int inventoryPlanId, HttpSession session) {
		Result result = new Result();
		try {
			List<Status> status =  inventoryService.prosBoxGrid(locationId, inventoryPlanId);
			result.setStatus(0);
			result.setMessage("请求成功！");
			result.setData(status);
		} catch (Exception e) {
			result.setStatus(9);
			result.setMessage("出现异常！");
			e.printStackTrace();
		}
		return result;
	}
	
	//文书上架进度条
	@RequestMapping("/prosShelvesBoxGrid.do")
	@ResponseBody
	public Result prosShelvesBoxGrid(int locationId, int inventoryPlanId, HttpSession session) {
		Result result = new Result();
		try {
			List<Status> status =  inventoryService.prosShelvesBoxGrid(locationId, inventoryPlanId);
			result.setStatus(0);
			result.setMessage("请求成功！");
			result.setData(status);
		} catch (Exception e) {
			result.setStatus(9);
			result.setMessage("出现异常！");
			e.printStackTrace();
		}
		return result;
	}
	
	// 点格子弹出诉讼统计结果
	@RequestMapping("/toShowGridResult.do")
	public ModelAndView toShowGridResult(String inventoryPlanId, String inventoryLoactionGuid) throws Exception {
		ModelAndView mv = new ModelAndView("alert/grid");
		mv.addObject("inventoryPlanId", inventoryPlanId);
		mv.addObject("inventoryLoactionGuid", inventoryLoactionGuid);
		return mv;
	}
	
	// 点格子弹出文书统计结果
	@RequestMapping("/toShowBoxGridResult.do")
	public ModelAndView toShowBoxGridResult(String inventoryPlanId, String inventoryLoactionGuid) throws Exception {
		ModelAndView mv = new ModelAndView("alert/inventoryBoxGridResult");
		mv.addObject("inventoryPlanId", inventoryPlanId);
		mv.addObject("inventoryLoactionGuid", inventoryLoactionGuid);
		return mv;
	}
	
	//诉讼格子结果详细
	@RequestMapping("/toShowDiffDetail.do")
	@ResponseBody
	public Pager toShowDiffDetailData(Integer inventoryPlanId, Integer inventoryLoactionGuid, int page, int rows) throws Exception{
		return inventoryService.loadDiffDetailDataGrid(inventoryPlanId, inventoryLoactionGuid, page, rows);
	}
	
	//文书格子结果详细
	@RequestMapping("/toShowBoxDiffDetail.do")
	@ResponseBody
	public Pager toShowBoxDiffDetail(Integer inventoryPlanId, Integer inventoryLoactionGuid, int page, int rows) throws Exception{
		return inventoryService.loadBoxDiffDetailDataGrid(inventoryPlanId, inventoryLoactionGuid, page, rows);
	}
		
	@RequestMapping("/okInventory.do")
	@ResponseBody
	public Result okInventory(String inventoryPlanId) {
		
		Result result = new Result();
		try {
			inventoryService.okInventoryPlan(inventoryPlanId);;
			result.setStatus(0);
			result.setMessage("请求成功！");
		} catch (Exception e) {
			result.setStatus(1);
			result.setMessage("出现异常！");
			e.printStackTrace();
		}
		return result;
	}
	
	//开始执行自定义盘点
	@RequestMapping("/startInventory.do")
	@ResponseBody
	public Result startInventory(Integer planId) {
		Result result = new Result();
		try {
			inventroyService.refreshPlanCellStatus(planId); //重置格子进度 高峰 2016-08-17
			inventroyService.clearResultByPlanId(planId); //清空盘点结果
			Object[] flag = inventroyService.invokeInventroy("Inventroy", new Object[]{planId,1});
			if("0".equals(flag[0])){
				result.setStatus(0);
				result.setMessage("请求成功！");
				System.out.println("请求成功!");
				inventroyService.updateInventroyStatus(planId, "1");
			}else{
				result.setStatus(1);
				result.setMessage("出现异常！");
				System.out.println("出现异常！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(1);
			result.setMessage("调用盘点服务失败,请联系管理员!");
			System.out.println("调用盘点服务失败,请联系管理员!");
		}
		return result;
	}
	
	
	//开始执行上架盘点
	@RequestMapping("/startShelves.do")
	@ResponseBody
	public Result startShelves(Integer planId) {
		Result result = new Result();
		try {
			inventroyService.refreshPlanCellStatus(planId); //重置格子进度 高峰 2016-08-17
			inventroyService.clearResultByPlanId(planId); //清空盘点结果
			Object[] flag = inventroyService.invokeInventroy("Inventroy", new Object[]{planId,5});
			if("0".equals(flag[0])){
				result.setStatus(0);
				result.setMessage("请求成功！");
				System.out.println("请求成功!");
			}else{
				result.setStatus(1);
				result.setMessage("出现异常！");
				System.out.println("出现异常！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(1);
			result.setMessage("调用盘点服务失败,请联系管理员!");
			System.out.println("调用盘点服务失败,请联系管理员!");
		}
		return result;
	}
	
	
	//执行重新上架
	@RequestMapping("/startReShelves.do")
	@ResponseBody
	public Result startReShelves(Integer planId){
		Result result = new Result();
		try {
			inventroyService.refreshPlanCellStatus(planId); //重置格子进度 高峰 2016-08-17
			inventroyService.clearResultByPlanId(planId); //清空盘点结果
			Object[] flag = inventroyService.invokeInventroy("Inventroy", new Object[]{planId, 6});
			if ("0".equals(flag[0])){
				result.setStatus(0);
				result.setMessage("重新上架！");
			} else {
				result.setStatus(1);
				result.setMessage(flag[0].toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(1);
			result.setMessage("调用盘点服务失败,请联系管理员!");
		}
		return result;
	}
	
	
	//根据位置查询盘点结果
	@RequestMapping("/toInventoryHistoryResult.do")
	public ModelAndView toInventoryHistoryResult(int locationId, int inventoryPlanId) throws Exception {
		ModelAndView mv = new ModelAndView("/inventoryFrame");
		List<LocationInfo> location = inventoryService.loadLocationFrame(locationId);
		LocationInfo frameLocation = locationInfoService.getLocationInfoByGuid(locationId);
		mv.addObject("locationId", locationId);
		mv.addObject("location", location);
		mv.addObject("inventoryPlanId", inventoryPlanId);
		mv.addObject("locationName", frameLocation.getLocationPath());
		return mv;
	}
	
	//语音提示-查询缺失和错放的档案
	@RequestMapping("/getMessage.do")
	@ResponseBody
	public Result getMessage(Integer planId){
		Result result = new Result();
		try {
			StringBuffer sb = new StringBuffer();
			InventoryPlan plan = inventoryService.getInventoryPlanByGuid(planId);
			List<SpeakResult> missResult = inventoryService.getMissResult(planId);
			List<SpeakResult> wrongResult = inventoryService.getWrongResult(planId);
			int warningCount = inventoryService.getWarningCount(planId);
			if ("2".equals(plan.getIsEnd()) && warningCount == 0) {
				result.setStatus(1);
			}
			if (warningCount > 0) sb.append(" 请注意  ");
			for (SpeakResult res : missResult) {
				String locationPath = res.getLocationPath();
				locationPath = locationPath.substring(locationPath.indexOf(">"));
				locationPath = locationPath.replaceAll(">", "第");
				sb.append(locationPath).append("缺失档案").append(res.getCount()).append("本 ");
				inventoryService.insertSpeakResult(res);
			}
			for (SpeakResult res : wrongResult) {
				String locationPath = res.getLocationPath();
				locationPath = locationPath.substring(locationPath.indexOf(">"));
				locationPath = locationPath.replaceAll(">", "第");
				sb.append(locationPath).append("错放档案").append(res.getCount()).append("本 ");
				inventoryService.insertSpeakResult(res);
			}
			result.setMessage(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(1);
			result.setMessage("查询服务失败 请咨询管理员");
		}
		return result;
	}

	//清除语音提示缓存
	@RequestMapping("/clear.do")
	@ResponseBody
	public void clear(Integer planId){
		inventoryService.clear(planId);
	}
}
