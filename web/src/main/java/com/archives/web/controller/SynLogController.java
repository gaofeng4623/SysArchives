package com.archives.web.controller;
import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.base.pojo.SysLog;
import com.system.core.service.SysLogService;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/sysLogController")
public class SynLogController {
	@Resource
	private SysLogService sysLogService;
	
	// 查询所有数据
	@RequestMapping("/synLog.do")
	@ResponseBody
	public Pager synLog(SysLog sysLog, int rows, int page) throws ParseException {
		Pager resultPage= null;
		int start = (page -1) * rows;
		resultPage = sysLogService.findSynLogPage(sysLog, start, rows);
		return resultPage;
		
	}

}
