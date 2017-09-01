package com.archives.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.MapDetail;
import com.archives.pojo.MapTotal;
import com.archives.service.ListConfigService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/listConfig")
public class ListConfigController {
	
	@Resource
	private ListConfigService listConfigService;
	
	@RequestMapping("/initMapTotalTree.do")
	@ResponseBody
	public List<MapTotal> initMapTotalTree(){
		List<MapTotal> mapTotalList = listConfigService.findAllListItemForTree();
		return mapTotalList;
	}
	
	@RequestMapping("/addListConfigInit.do")
	public ModelAndView addListConfigInit(String guid,String content){
		ModelAndView mv = new ModelAndView("alert/addListConfig");
		return mv;
	}
	@RequestMapping("/updListConfigInit.do")
	public ModelAndView updListConfigInit(String guid){
		ModelAndView mv = new ModelAndView("alert/updListConfig");
		MapTotal Item = new MapTotal();
		Item = listConfigService.selectByPrimaryKey(guid);
		mv.addObject("selItem", Item);
		return mv;
	}
	
	@RequestMapping("/saveListConfig.do")
	@ResponseBody
	public Result saveListConfig(MapTotal mapTotal){
		Result res = listConfigService.insertSelective(mapTotal);
		return res;
	}
	@RequestMapping("/updListConfig.do")
	@ResponseBody
	public Result updDeptInfo(MapTotal Item){
		Result res = listConfigService.updateByPrimaryKeySelective(Item);
		return res;
	}
	@RequestMapping("/removeListConfig.do")
	@ResponseBody
	public Result removeListConfig(String guid){
		Result res = listConfigService.deleteByPrimaryKey(guid);
		return res;
	}
	
	@RequestMapping("/findSubItem.do")
	@ResponseBody
	public Pager findSubItem(MapDetail mapDetail, String priorIds, int rows, int page){
		Pager resultPage= null; 
		List<String> idList = new ArrayList<String>();
		if(null != priorIds && !"".equals(priorIds)){
			for(String priorId : priorIds.split(",")){
				if(StringUtils.isEmpty(priorId)){
					continue;
				}
				idList.add(priorId);
			}
		}
		int start = (page -1) * rows;
		if(0 < idList.size()){
			resultPage = listConfigService.findSubItemByPriorIdsForPage(mapDetail, idList, start, rows);
		}else{
			resultPage = listConfigService.findSubItemByPriorIdsForPage(mapDetail, null, start, rows);
		}
		
		
		return resultPage;
	}
	
	@RequestMapping("/addSubItemInit.do")
	public ModelAndView addSubItemInit(String priorId){
		ModelAndView mv = new ModelAndView("alert/addSubItem");
		MapTotal mapTotal = listConfigService.selectByPrimaryKey(priorId);
		mv.addObject("ItemList", mapTotal);
		return mv;
	}
		
	@RequestMapping("/saveSubItem.do")
	@ResponseBody
	public Result saveSubItem(MapDetail mapDetail){
		Result res = listConfigService.insertSubItem(mapDetail);
		return res;
	}
	
	@RequestMapping("/deleteSubItem.do")
	@ResponseBody
	public Result deleteSubItem(String subIds){
		List<String> idList = new ArrayList<String>();
		if(null != subIds && !"".equals(subIds)){
			for(String subId : subIds.split(",")){
				if(StringUtils.isEmpty(subId)){
					continue;
				}
				idList.add(subId);
			}
		}
		return listConfigService.deleteSubItemByIds(idList);
	}
	@RequestMapping("/updSubItemInit.do")
	public ModelAndView updSubItemInit(String subId){
		ModelAndView mv = new ModelAndView("alert/updSubItem");
		MapDetail mapDetail = listConfigService.selectSubItemByPrimaryKey(subId);
		mv.addObject("subItem", mapDetail);
		return mv;
	}
	
	@RequestMapping("/updateSubItem.do")
	@ResponseBody
	public Result updateSubItem(MapDetail mapDetail){
		Result res = listConfigService.updateSubItemSelective(mapDetail);
		return res;
	}
}
