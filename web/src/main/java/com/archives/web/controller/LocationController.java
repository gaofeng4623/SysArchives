package com.archives.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.LocationConfig;
import com.archives.pojo.LocationControl;
import com.archives.pojo.LocationInfo;
import com.archives.pojo.LocationType;
import com.archives.service.LocationConfigService;
import com.archives.service.LocationInfoService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/basic")
public class LocationController {
	@Resource
	private LocationConfigService locationConfigService;
	
	@Resource
	private LocationInfoService locationInfoService;
	
	@RequestMapping("/getConfigTreeNodes.do")
	@ResponseBody
	public List<LocationConfig> getConfigTreeNodes(){
		
		List<LocationConfig> configs = locationConfigService.getConfigTreeNodes();
		return configs;
	}
	
	@RequestMapping("/getLocationTreeNodes.do")
	@ResponseBody
	public List<LocationInfo> getLocationTreeNodes(Integer pid,String type){
		Integer parentId = -1;
		if(null != pid && -1 < pid){
			parentId = pid;
		}
		List<LocationInfo> configs = locationInfoService.NodesByParentId(parentId,type);
		return configs;
	}
	
	@RequestMapping("/queryPageLocationList.do")
	@ResponseBody
	public Pager queryPageLocationList(Integer pid, int rows, int page){
		Pager resultPage= null; 
		if(null == pid || 0 > pid){
			pid = 0;
		}
		int start = (page -1) * rows;
		resultPage = locationInfoService.queryPageLocationList(pid, start, rows);
		return resultPage;
	}
	/**
	 * 查询所有信息
	 * @param pid
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping("/queryPageLocationAll.do")
	@ResponseBody
	public List<LocationInfo> queryPageLocationAll(){
		List<LocationInfo> locationInfo = locationInfoService.queryPageLocationAll();
		return locationInfo;
	}
	/**
	 * 根据guid获取位置信息
	* @Title: getLocationInfoByGuid 
	* @Description: TODO  
	* @param @param request
	* @param @return     
	* @return ModelAndView   
	* @throws
	 */
	@RequestMapping("/getLocationInfoByGuid.do")
	@ResponseBody
	public Result getLocationInfoByGuid(Integer guid){
		Result result = new Result();
		try {
			LocationInfo locationInfo = locationInfoService.getLocationInfoByGuid(guid);
			result.setStatus(0);
			result.setMessage("获取位置信息成功");
			result.setData(locationInfo);
		} catch (Exception e) {
			result.setStatus(1);
			result.setMessage("获取信息失败");
		}
		return result;
	}
	
	/**
	 * 根据父节点查询位置类型列表
	 * @Title: toAddLocation  
	 * @Description: TODO 
	 * @param       
	 * @return ModelAndView     
	 * @throws
	 */
	@RequestMapping("/getTypeList.do")
	@ResponseBody
	public List<LocationType> getTypeList(Integer parentId){
		int parentcode = -1;
		List<LocationType> typelist = new ArrayList<LocationType>();
		if(parentId != null && 0 < parentId){
			LocationInfo locationInfo = locationInfoService.getLocationInfoByGuid(parentId);
			parentcode = locationInfo.getLocationTypeGuid();
		}
		typelist = locationInfoService.getTypeList(parentcode);
		return typelist;
	}
	
	@RequestMapping("/queryPageConfigList.do")
	@ResponseBody
	public Pager queryPageConfigList(Integer pid, int rows, int page){
		Pager resultPage= null; 
		if(null == pid || 0 > pid){
			pid = 0;
		}
		int start = (page -1) * rows;
		resultPage = locationConfigService.queryPageConfigList(pid, start, rows);
		return resultPage;
	}
	
	@RequestMapping("/toAddConfig.do")
	public ModelAndView toAddConfig(Integer parentid){
		ModelAndView mv = new ModelAndView("alert/addConfig");
		LocationConfig config = null;
		if(null == parentid || 0 >= parentid){
			config = new LocationConfig();
			config.setGuid(0);
			config.setLocationName("无");
		}else{
			config = locationConfigService.selectLocationConfigByPrimaryKey(parentid);
		}
		List<LocationType> typelist = locationConfigService.getTypeList();
		mv.addObject("superConfig", config);
		mv.addObject("typeList", typelist);
		return mv;
	}
	
	/**
	 * 保存位置类型配置信息
	 * @Title: saveLocationInfo  
	 * @Description: TODO 
	 * @param       
	 * @return ModelAndView     
	 * @throws
	 */
	@RequestMapping("/saveConfigInfo.do")
	@ResponseBody
	public Result saveLocationInfo(LocationConfig config){
		Result result = locationConfigService.saveConfigInfo(config);
		return result;
	}
	
	
	/**
	 * 删除位置信息
	* @Title: delConfigInfo 
	* @Description: TODO  
	* @param @param request
	* @param @return     
	* @return ModelAndView   
	* @throws
	 */
	@RequestMapping("/delConfigInfo.do")
	@ResponseBody
	public Result delConfigInfo(String ids){
		List<String> idList = new ArrayList<String>();
		if(null != ids && !"".equals(ids)){
			for(String empId : ids.split(",")){
				if(StringUtils.isEmpty(empId)){
					continue;
				}
				idList.add(empId);
			}
		}
		return locationConfigService.delConfigInfo(idList);
	}
	
	@RequestMapping("/saveLocationInfo.do")
	@ResponseBody
	public Result saveLocationInfo(LocationInfo locationInfo){
		Result result = locationInfoService.saveLocationInfo(locationInfo);
		return result;
	}
	
	@RequestMapping("/updateLocationInfo.do")
	@ResponseBody
	public Result updateLocationInfo(LocationInfo locationInfo){
		Result result = locationInfoService.updateLocationInfo(locationInfo);
		return result;
	}
	@RequestMapping("/delLocationInfo.do")
	@ResponseBody
	public Result delLocationInfo(String guids){
		List<String> idList = new ArrayList<String>();
		if(null != guids && !"".equals(guids)){
			for(String empId : guids.split(",")){
				if(StringUtils.isEmpty(empId)){
					continue;
				}
				idList.add(empId);
			}
		}
		return locationInfoService.delLocationInfo(idList);
	}
	
	
	/**
	 * 获取排序列表
	 * @return
	 */
	@RequestMapping("/getOrderList.do")
	@ResponseBody
	public ModelAndView getOrderList(Integer guid){
		ModelAndView mv = new ModelAndView("alert/order");
		List<LocationInfo> dataList = locationInfoService.getOrderList(guid);
		mv.addObject("dataList", dataList);
		return mv;
	}
	
	@RequestMapping("/saveOrderInfo.do")
	@ResponseBody
	public Result saveOrderInfo(String orderStr){
		return locationInfoService.saveOrderInfo(orderStr);
	}
	
	@RequestMapping("/locationControlList.do")
	@ResponseBody
	public Pager locationControlList(String controlAddress, String branchAddress , int rows, int page){
		Pager resultPage= null; 
		int start = (page -1) * rows;
		resultPage = locationInfoService.queryPageControlList(controlAddress, branchAddress, start, rows);
		return resultPage;
	}
	
	@RequestMapping("/addControlInit.do")
	@ResponseBody
	public ModelAndView addControlInit(){
		ModelAndView mv = new ModelAndView("alert/addControl");
		return mv;
	}
	@RequestMapping("/saveLocationControl.do")
	@ResponseBody
	public Result saveLocationControl(LocationControl locationControl){
		return locationInfoService.saveLocationControl(locationControl);
	}
	
	@RequestMapping("/editControlInit.do")
	@ResponseBody
	public ModelAndView editControlInit(Integer id){
		ModelAndView mv = new ModelAndView("alert/updControl");
		LocationControl ctrl = locationInfoService.queryLocationControlById(id);
		mv.addObject("ctrl", ctrl);
		return mv;
	}
	
	@RequestMapping("/updLocationControl.do")
	@ResponseBody
	public Result updLocationControl(LocationControl locationControl){
		return locationInfoService.updLocationControl(locationControl);
	}
	@RequestMapping("/delLocationControl.do")
	@ResponseBody
	public Result delLocationControl(String ids){
		List<Integer> idList = new ArrayList<Integer>();
		if(null != ids && !"".equals(ids)){
			for(String ctrlId : ids.split(",")){
				if(StringUtils.isEmpty(ctrlId)){
					continue;
				}
				idList.add(Integer.parseInt(ctrlId));
			}
		}
		return locationInfoService.delLocationControlInfo(idList);
	}
	@RequestMapping("/getLocationControlList.do")
	@ResponseBody
	public List<LocationControl> queryLocationControlListForCombobox(String condation){
		return locationInfoService.queryLocationControlListForCombobox(condation);
	}
}
