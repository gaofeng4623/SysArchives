package com.archives.workflow.web;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;

import com.archives.service.BorrowService;
import com.system.util.common.SpringContextUtil;

public class PassListener implements org.activiti.engine.delegate.ExecutionListener {

	@Override
	public void notify(DelegateExecution excution) throws Exception {
		 RuntimeService runtimeService = SpringContextUtil.getBean("runtimeService");
		 ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(excution.getProcessInstanceId()).singleResult();
		 BorrowService borrowService = SpringContextUtil.getBean(BorrowService.class);
		 borrowService.updateBorrowStatus(processInstance.getBusinessKey()); 
		 if (borrowService.isReborrow(processInstance.getBusinessKey())) {
			 borrowService.unReborrowLock(processInstance.getBusinessKey());
			 borrowService.updateReborrowStatus(processInstance.getBusinessKey());
		 }
		 
	}
	

}
