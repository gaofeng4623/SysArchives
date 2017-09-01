package com.system.core.service;

import java.text.ParseException;

import com.system.base.pojo.Result;
import com.system.base.pojo.SysLog;
import com.system.workflow.activiti.commons.Pager;

public interface SysLogService {
	public Pager findSysLogPage(SysLog sysLog,int start, int totalSize ) throws ParseException;
	
	public Pager findSynLogPage(SysLog sysLog,int start, int totalSize ) throws ParseException; //同步日志
	
	public Result insertSelective(int operationType,String event);

}

