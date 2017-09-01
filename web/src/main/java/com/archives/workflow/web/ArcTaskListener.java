package com.archives.workflow.web;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;

import com.archives.dao.BorrowDao;
import com.archives.pojo.Borrow;
import com.archives.service.ArcHistoryService;
import com.system.util.common.SpringContextUtil;
public class ArcTaskListener implements ActivitiEventListener , ExecutionListener{

	@Override
	public boolean isFailOnException() {
		return false;
	}

	@Override
	public void onEvent(ActivitiEvent event) {
		 TaskService taskService = SpringContextUtil.getBean("taskService");
		 Task task = taskService.createTaskQuery().active().processInstanceId(event.getProcessInstanceId()).singleResult();
		 if (task == null) return;
		 RuntimeService runtimeService = SpringContextUtil.getBean("runtimeService");
		 ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(event.getProcessInstanceId()).singleResult();
		 BorrowDao borrowDao = SpringContextUtil.getBean(BorrowDao.class);
		 borrowDao.updateWorkflowStatus(processInstance.getBusinessKey(), task.getName());
	}

	@Override
	public void notify(DelegateExecution excution) throws Exception {
		TaskService taskService = SpringContextUtil.getBean("taskService");
		 Task task = taskService.createTaskQuery().active().processInstanceId(excution.getProcessInstanceId()).singleResult();
		 if (task == null) return;
		 RuntimeService runtimeService = SpringContextUtil.getBean("runtimeService");
		 ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(excution.getProcessInstanceId()).singleResult();
		 BorrowDao borrowDao = SpringContextUtil.getBean(BorrowDao.class);
		 borrowDao.updateWorkflowStatus(processInstance.getBusinessKey(), task.getName());
		 ArcHistoryService archis =  SpringContextUtil.getBean(ArcHistoryService.class);
		 Borrow borrow = borrowDao.findBorrowById(processInstance.getBusinessKey());
		 String itemGuids = borrow.getItemGuids();
		 if (itemGuids.contains(",")) 
		 {
			String[] arr = itemGuids.split(",");
				for (String s : arr)
				{
				if (!StringUtils.isEmpty(s))
						archis.insertArcHistory(Integer.parseInt(s), task.getName(), null);
				}
			} else {
				if (!StringUtils.isEmpty(itemGuids))
					archis.insertArcHistory(Integer.parseInt(itemGuids), task.getName(), null);
			}
		 
	}
	

}
