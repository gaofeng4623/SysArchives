package com.system.web.controller;

import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.base.pojo.SysLog;
import com.system.core.service.SysLogService;
import com.system.workflow.activiti.commons.Pager;

/**
 * @info 系统相关日志模块 
 * @author 高峰 2016-09-08
 */
@Controller
@RequestMapping("/sysLogManage")
public class SysLogManageController {
	@Resource
	private SysLogService sysLogService;

	// 查询所有数据
	@RequestMapping("/sysLogTable.do")
	@ResponseBody
	public Pager sysLogTable(SysLog sysLog, int rows, int page)
			throws ParseException {
		Pager resultPage = null;
		int start = (page - 1) * rows;
		resultPage = sysLogService.findSysLogPage(sysLog, start, rows);
		return resultPage;

	}
}
