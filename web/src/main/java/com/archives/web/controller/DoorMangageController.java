package com.archives.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.DoorMangage;
import com.archives.service.DoorMangageService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/doorMangage")
public class DoorMangageController {
	@Resource
	private DoorMangageService doorMangageService;
	// 查询所有数据
		@RequestMapping("/doorMangageAll.do")
		@ResponseBody
		public Pager doorMangageAll(DoorMangage doorMangage, int rows, int page) throws Exception{
			try {
				if(doorMangage.getDoorid()!=null){
					doorMangage.setDoorid(URLDecoder.decode(doorMangage.getDoorid(), "utf-8"));
				}
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Pager resultPage= null; 
			
			int start = (page -1) * rows;
			resultPage = doorMangageService.findDoorMangagePage(doorMangage, start, rows);
			return resultPage;
			
		}
		@RequestMapping("/saveOrUpdate.do")
		public ModelAndView saveOrUpdate(int guid)throws Exception{
			ModelAndView mv = new ModelAndView("alert/addDoorManage");
			
			return mv;
		}
		@RequestMapping("/addMangage.do")
		@ResponseBody
		public Result addMangage(DoorMangage door)throws Exception{
			Result res = doorMangageService.insertDoorSelective(door);
			return res;
		}
		/*@RequestMapping("/delMangage.do")
		@ResponseBody
		public Result delMangage(int guid){
			Result res = doorMangageService.delDoorSelective(guid);
			return res;
		}*/
		
		/**
		 * 删除位置信息
		* @Title: delConfigInfo 
		* @Description: TODO  
		* @param @param request
		* @param @return     
		* @return ModelAndView   
		* @throws
		 */
		@RequestMapping("/delMangage.do")
		@ResponseBody
		public Result delMangage(String guids)throws Exception{
			List<String> idList = new ArrayList<String>();
			if(null != guids && !"".equals(guids)){
				for(String empId : guids.split("~")){
					if(StringUtils.isEmpty(empId)){
						continue;
					}
					idList.add(empId);
				}
			}
			return doorMangageService.delDoor(idList);
		}
		@RequestMapping("/updMangage.do")
		@ResponseBody
		public DoorMangage updMangage(int guid)throws Exception{
			DoorMangage doorMangage=doorMangageService.selectByPrimaryKey(guid);
			return doorMangage;
		}
		
		
		
		
		@RequestMapping("/updMangageByGuid.do")
		
		@ResponseBody
		public Result updMangageByGuid(DoorMangage door)throws Exception{
			//Result res = doorMangageService.updateDoorSelective(door);
			return doorMangageService.updateDoorSelective(door);
		
		}
}
