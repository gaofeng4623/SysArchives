package com.archives.service;

import com.archives.pojo.ArcHistory;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface ArcHistoryService {
	
	public Pager findArcHistoryPage(ArcHistory emp,int start, int totalSize);
	
	public Result insertArcHistory(int arcGuid,String event, String personName);

}

