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

import com.archives.pojo.DoorCamera;
import com.archives.service.DoorCameraService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/doorCamera")
public class DoorCameraController {
	@Resource
	private DoorCameraService doorCameraService;
	// 查询所有数据
		@RequestMapping("/doorCameraAll.do")
		@ResponseBody
		public Pager doorMangageAll(DoorCamera doorCamera, int rows, int page) throws Exception{
			try {
				if(doorCamera.getCameraname()!=null){
					doorCamera.setCameraname(URLDecoder.decode(doorCamera.getCameraname(), "utf-8"));
				}
				if(doorCamera.getLoginname()!=null){
					doorCamera.setLoginname(URLDecoder.decode(doorCamera.getLoginname(), "utf-8"));
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Pager resultPage= null; 
			
			int start = (page -1) * rows;
			resultPage = doorCameraService.findDoorCameraPage(doorCamera, start, rows);
			return resultPage;
			
		}
		
		@RequestMapping("/addDoorCamera.do")
		@ResponseBody
		public Result addMangage(DoorCamera door)throws Exception{
			Result res = doorCameraService.insertDoorSelective(door);
			return res;
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
			return doorCameraService.delDoor(idList);
		}
		@RequestMapping("/updDoorCamera.do")
		@ResponseBody
		public DoorCamera updMangage(int guid)throws Exception{
			DoorCamera doorCamera=doorCameraService.selectByPrimaryKey(guid);
			return doorCamera;
		}
		
		
		
		
		@RequestMapping("/updMangageByGuid.do")
		
		@ResponseBody
		public Result updMangageByGuid(DoorCamera door)throws Exception{
			return doorCameraService.updateDoorSelective(door);
		
		}
		/**
		 * 查询所有信息
		 * @param pid
		 * @param rows
		 * @param page
		 * @return
		 */
		@RequestMapping("/querydoorCameraAll.do")
		@ResponseBody
		public List<DoorCamera> querydoorCameraAll()throws Exception{
			List<DoorCamera> locationInfo = doorCameraService.querydoorCameraAll();
			return locationInfo;
		}
}
