package com.archives.workflow.web;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;

import com.archives.service.BorrowService;
import com.system.util.common.SpringContextUtil;

public class ChangeListener implements org.activiti.engine.delegate.ExecutionListener {

	@Override
	public void notify(DelegateExecution excution) throws Exception {
		 RuntimeService runtimeService = SpringContextUtil.getBean("runtimeService");
		 ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(excution.getProcessInstanceId()).singleResult();
		 BorrowService borrowService = SpringContextUtil.getBean(BorrowService.class);
		 borrowService.takedLock(processInstance.getBusinessKey(), "2");
	}
	

}
