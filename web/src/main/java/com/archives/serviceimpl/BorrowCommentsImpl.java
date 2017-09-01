package com.archives.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archives.service.BorrowComments;
import com.system.dao.database.EmployeeDao;
import com.system.util.common.SpringContextUtil;
import com.system.workflow.activiti.commons.SortWFCommentByTime;
import com.system.workflow.activiti.commons.WFComment;
@Service
public class BorrowCommentsImpl implements BorrowComments{
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
    TaskService taskService;
	
	@Override
	public List<WFComment> findProcessCommentsByBorrowId(String borrowId) {
		String processInstanceId = "";
    	ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(borrowId).singleResult();
    	if (processInstance != null) {
    		processInstanceId = processInstance.getProcessInstanceId();
    	} else {
    		 HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
					 .processInstanceBusinessKey(borrowId).singleResult();
    		 processInstanceId = historicProcessInstance.getId();
    	}
 		return findProcessCommentsByInstanceId(processInstanceId);
	}

	
	public List<WFComment> findProcessCommentsByInstanceId(String processInstanceId) {
		List<Comment> historyCommnets = new ArrayList();
 		List<HistoricActivityInstance> hais = historyService
 				.createHistoricActivityInstanceQuery()
 				.processInstanceId(processInstanceId).activityType("userTask")
 				.orderByHistoricActivityInstanceEndTime().desc().list();
 		EmployeeDao employeeDao = SpringContextUtil.getBean(EmployeeDao.class);
 		for (HistoricActivityInstance hai : hais) {
 			String historytaskId = hai.getTaskId();
 			List<Comment> comments = taskService.getTaskComments(historytaskId);
 			if (comments != null && comments.size() > 0) {
 				historyCommnets.addAll(comments);
 			}
 		}
 		List<WFComment> list = new ArrayList<WFComment>();
 		for (Comment c : historyCommnets) {
 			WFComment comment = new WFComment();
 			comment.setId(c.getId());
 			comment.setTaskId(c.getTaskId());
 			comment.setProcessInstanceId(c.getProcessInstanceId());
 			comment.setTime(c.getTime());
 			comment.setType(c.getType());
 			comment.setUserId(c.getUserId());
 			comment.setFullMessage(c.getFullMessage());
 			HistoricTaskInstance t = historyService
 					.createHistoricTaskInstanceQuery().taskId(c.getTaskId())
 					.singleResult();
 			comment.setTaskDefinitionKey(t.getTaskDefinitionKey());
 			comment.setTaskName(t.getName());
 			comment.setUserName(employeeDao.findUser(c.getUserId()).getEmployeeName()); //用户名称

 			list.add(comment);
 		}
 		Collections.sort(list, new SortWFCommentByTime());
 		return list;
	}
}
