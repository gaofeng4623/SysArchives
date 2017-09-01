package com.archives.workflow.web;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.archives.dao.BorrowDao;
import com.system.util.common.SpringContextUtil;

public class ArcStatusListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String eventName = execution.getEventName();
		String borrowId = execution.getProcessBusinessKey();
		if ("start".equals(eventName)) {
			BorrowDao borrowDao = SpringContextUtil.getBean(BorrowDao.class);
			int count = borrowDao.createBorrowStatus(borrowId, "0");
		} 
	}

}
