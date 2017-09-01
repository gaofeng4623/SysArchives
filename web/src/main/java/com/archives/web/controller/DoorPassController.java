package com.archives.web.controller;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.archives.pojo.DoorPass;
import com.archives.service.DoorPassService;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/doorPassaa")
public class DoorPassController {

	@Resource
	private DoorPassService doorPassService;


	// 查询所有数据
	@RequestMapping("/findDoorPass.do")
	@ResponseBody
	public Pager findDoorWarning(DoorPass doorPass, int rows, int page) throws Exception{
		Pager resultPage= null; 
		
		int start = (page -1) * rows;
		resultPage = doorPassService.findEmpByDoorPassForPage(doorPass, start, rows);
		
		return resultPage;
		
		
		
	}

}
