package com.archives.service;

import com.archives.pojo.DocHistory;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface DocHistoryService {
	
	//public Pager findArcHistoryPage(DocHistory emp,int start, int totalSize);
	
	public Result insertArcHistory(int DocGuid,String event, String personName)throws Exception;

}

