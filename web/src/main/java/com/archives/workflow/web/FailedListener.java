package com.archives.workflow.web;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;

import com.archives.dao.BorrowDao;
import com.system.util.common.SpringContextUtil;

public class FailedListener implements org.activiti.engine.delegate.ExecutionListener {

	@Override
	public void notify(DelegateExecution excution) throws Exception {
		 RuntimeService runtimeService = SpringContextUtil.getBean("runtimeService");
		 ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(excution.getProcessInstanceId()).singleResult();
		 BorrowDao borrowDao = SpringContextUtil.getBean(BorrowDao.class);
		 borrowDao.deleteBorrow(processInstance.getBusinessKey());
		 borrowDao.clearBorrowDetail(processInstance.getBusinessKey());
	}
	

}
