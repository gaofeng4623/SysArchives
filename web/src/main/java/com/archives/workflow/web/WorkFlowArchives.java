package com.archives.workflow.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.Borrow;
import com.archives.pojo.WorkflowInstance;
import com.archives.service.BorrowComments;
import com.archives.service.BorrowService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Ajax;
import com.system.workflow.activiti.commons.WFComment;
import com.system.workflow.activiti.service.WorkflowProcessCoreService;
import com.system.workflow.activiti.util.UserUtil;

@Controller
@RequestMapping(value = "/workflow")
public class WorkFlowArchives {
	
	@Autowired
	WorkflowProcessCoreService workflowProcessCoreService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	IdentityService identityService;
	@Autowired
    TaskService taskService;
	@Autowired
	BorrowComments borrowComments;
	@Resource
	private BorrowService borrowService;
	
	@RequestMapping(value = "/submit")
	@ResponseBody
    public Ajax submit(String pass, String comment, String taskId, String processInstanceId, HttpSession session) {
		try {
			User user = UserUtil.getUserFromSession(session);
			 Task task = workflowProcessCoreService.findTaskById(taskId);
			 if (task.getAssignee() == null) {
				 taskService.claim(taskId, user.getId()); 
			 }
			Map<String, Object> variables = new HashMap<String, Object>();
			if ("1".equals(pass)) {
				variables.put("pass", 1);
				//Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
				//Object o = runtimeService.getVariable(task.getProcessInstanceId(), "back");
				//ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				//Object o = runtimeService.getVariable(task.getExecutionId(), "back");
				//Map<String, Object> v = task.getProcessVariables();
			} else {
				variables.put("pass", 0);
			}
			workflowProcessCoreService.commitProcess(taskId, user.getId(), comment, variables);
			return new Ajax(true, "办理完成");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "提交失败");
		}
	}
	
	
	/**
	 * 重新提交
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/reSubmit.do")
	@ResponseBody
	public Result reSubmit(String borrowId, HttpSession session) throws Exception {
		Borrow borrow = borrowService.findBorrowById(borrowId);
		Map variables = new HashMap();
		String userId =UserUtil.getUserFromSession(session).getId(); 
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(borrowId).singleResult();
		String taskId = workflowProcessCoreService.findCurrentTask(processInstance.getProcessInstanceId()).get(0).getId();
		if ("1".equals(borrow.getWorkflow())) { //同庭档案审批
			variables.put("pass", 1);
			workflowProcessCoreService.commitProcess(taskId, userId, borrow.getCaseDetail(), variables);
		} else if ("2".equals(borrow.getWorkflow())){ //跨庭档案审批
			variables.put("pass", 2);
			workflowProcessCoreService.commitProcess(taskId, userId, borrow.getCaseDetail(), variables);
		} else {
			return new Result(0, "请选择审批流程");
		}
		borrowService.takedLock(borrowId, "1");
		return new Result(0, "提交成功");
	}
	
	
	@RequestMapping(value = "/mypage")
    public ModelAndView test(String taskId, String processInstanceId) {
        ModelAndView mav = new ModelAndView("worktest/mypage");
        mav.addObject("taskId", taskId);
        return mav;
    }
	
	
	@RequestMapping(value = "/back")
    public Ajax back(String processInstanceId, HttpSession session) throws Exception {
		Ajax ajax = new Ajax(true, "成功");
		User user = UserUtil.getUserFromSession(session);
		workflowProcessCoreService.callBackProcess(processInstanceId, "edit", null, "admin",
				"用户取消借阅,从第五步撤回到第二步!");
        return ajax;
    }
	
	
	@RequestMapping(value = "/delete")
    public Ajax delete(String processInstanceId, HttpSession session) throws Exception {
		try {
			User user = UserUtil.getUserFromSession(session);
			workflowProcessCoreService.deleteProcessInstance(processInstanceId);
			return new Ajax(true, "流程实例已删除");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "删除失败");
		}
    }
	
	@RequestMapping(value = "/historyCount")
	@ResponseBody
	public Result getHistoryCount(HttpSession session) {
		User user = UserUtil.getUserFromSession(session);
		int count = historyService.createHistoricTaskInstanceQuery().finished().taskAssignee(user.getId()).list().size();
		return new Result(1, String.valueOf(count));
	}
	
	@RequestMapping(value = "/todoCount")
	@ResponseBody
	public Result getTodoListCount(HttpSession session) {
		User user = UserUtil.getUserFromSession(session);
		int count = taskService.createTaskQuery().taskAssignee(user.getId()).active().list().size();
		count += taskService.createTaskQuery().taskCandidateUser(user.getId()).active().list().size();
		return new Result(1, String.valueOf(count));
	}
	
	@RequestMapping(value = "/trace")
	@ResponseBody
	public WorkflowInstance trace(String borrowId) {
		WorkflowInstance workflowInstance = new WorkflowInstance();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(borrowId).singleResult();
		 if (null == processInstance) {
			 HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
					 .processInstanceBusinessKey(borrowId).singleResult();
			 workflowInstance.setProcessInstanceId(historicProcessInstance.getId());
			 workflowInstance.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
		 } else {
			 workflowInstance.setProcessInstanceId(processInstance.getProcessInstanceId());
			 workflowInstance.setProcessDefinitionId(processInstance.getProcessDefinitionId());
		 }
        
		return workflowInstance;
	}
	
	@RequestMapping("/destroy.do")
	@ResponseBody
	public Result destroy(String borrowId)
			throws Exception {
		try {
			borrowService.delete(borrowId);
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(borrowId).singleResult();
			if (processInstance != null) {
				workflowProcessCoreService.deleteProcessInstance(processInstance.getProcessInstanceId());
			}
			return new Result(1, "删除完成");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(0, "删除失败");
		}
	}
	
	 
    @RequestMapping(value = "/form-comments/{processInstanceId}")
    public ModelAndView processComment(@PathVariable("processInstanceId") String processInstanceId) {
    	ModelAndView mv = new ModelAndView("/workflow/process-comments");
    	List<WFComment> comments = borrowComments.findProcessCommentsByInstanceId(processInstanceId);
    	mv.addObject("comments", comments);
    	return mv;
    }
    
    @RequestMapping("/form-comments.do")
    public ModelAndView formComment(String borrowId) {
    	ModelAndView mv = new ModelAndView("/workflow/process-comments");
    	List<WFComment> comments = borrowComments.findProcessCommentsByBorrowId(borrowId);
    	mv.addObject("comments", comments);
    	return mv;
    }
    
    @RequestMapping("/getTaskName.do")
	@ResponseBody
	public Result getTaskName(String borrowId) throws Exception {
		try {
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceBusinessKey(borrowId).singleResult();
			if (processInstance != null) {
				Task task = taskService.createTaskQuery().active().processInstanceId(processInstance.getId()).singleResult();
				return new Result(1, task.getName());
			} else {
				return new Result(0, "审批通过");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(0, "获取任务失败");
		}
	}
	
}
