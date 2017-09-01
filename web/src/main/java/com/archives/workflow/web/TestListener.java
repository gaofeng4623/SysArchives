package com.archives.workflow.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.system.workflow.activiti.service.WorkflowProcessCoreService;
import com.system.workflow.activiti.util.UserUtil;

@Controller
@RequestMapping("/worktest")
public class TestListener{
	@Autowired
	WorkflowProcessCoreService workflowProcessCoreService;
	@Autowired
    TaskService taskService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	protected RuntimeService runtimeService;
	
	/**
	 * 发起借阅申请
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/test.do")
	public void borrow(HttpSession session) throws Exception {
		String userId =UserUtil.getUserFromSession(session).getId(); 
		Map src = new HashMap();
		src.put("userId", userId);
		src.put("reborrow", "0");
		src.put("begin", userId);
		src.put("changer", userId);
		String instanceId = workflowProcessCoreService.startProcess("borrow", "11", userId, src, null); 
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(instanceId).list();
		Task task = tasks.get(0);
		//然后根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
		System.out.println("流程定义ID:" + task.getProcessDefinitionId());
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)
				repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());

		List<ActivityImpl> activitiList = def.getActivities();  //rs是指RepositoryService的实例

		//根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：

		String excId = task.getExecutionId();
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
		String activitiId = execution.getActivityId();

		//然后循环activitiList 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：

		for(ActivityImpl activityImpl : activitiList){
			String id = activityImpl.getId();
			if(activitiId.equals(id)){
				TaskDefinition taskDefinition = getNextTaskDefinition("borrow", id, "${pass==1}", false);
				if (taskDefinition != null) {
					System.out.println("下一步任务名称---" + taskDefinition.getKey() + ":" 
							+ taskDefinition.getNameExpression());
				}
			}
			
		}
	}
	
	/**
	* 根据与流程定义key获取当前节点的下一个任务节点
	* @param key  流程定义Key
	* @param elementId当前节点Id
	* @param elString当前节点流向下一个节点的匹配字符串   
	* 如下      ${deptLeaderPass}--------------------------XML已定义的字符串
	*  ${!deptLeaderPass}
	* 获取领导同意的userTask，则传入 ${deptLeaderPass}
	* @return
	*/
	public TaskDefinition getNextTaskDefinition(String key,String activityId,String elString,boolean bFlag){
		ProcessDefinition processDefinition = getNewProcessDefinition(key);//根据流程定义key获得最新的流程定义信息
		if (processDefinition != null) {
			ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) 
			((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinition.getId());  
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);//当前节点
		if ("userTask".equals(activityImpl.getProperty("type")) && !bFlag) {
			TaskDefinition taskDefinition = ((UserTaskActivityBehavior)activityImpl.getActivityBehavior()).getTaskDefinition();
			return taskDefinition;
		} else {
			List<PvmTransition> outpvmTransitions = null;
			List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions(); //获得从当前节点出去的钱路(路由)
			for(PvmTransition pvmTransition : pvmTransitions){
				PvmActivity pa = pvmTransition.getDestination(); //获取所有的终点节点,终点只有两种可能,一种是任务节点,一种是网关节点
				if ("exclusiveGateway".equals(pa.getProperty("type"))) {
					outpvmTransitions = pa.getOutgoingTransitions();
					if (outpvmTransitions.size() == 1) {
						return getNextTaskDefinition(key, pa.getId(), elString, false); //递归取子节点
					} else if (outpvmTransitions.size() > 1) {
						for (PvmTransition outPvmTransition : outpvmTransitions) {
							Object object = outPvmTransition.getProperty("conditionText");
							String routeName = String.valueOf(outPvmTransition.getProperty("name")); //动作名称
							if (elString.equals(object.toString().trim())) {
								PvmActivity pvmActivity = outPvmTransition.getDestination();
								return getNextTaskDefinition(key, pvmActivity.getId(), elString, false);
							}
						}
					}
				} else if ("userTask".equals(pa.getProperty("type"))) {
					return ((UserTaskActivityBehavior)((ActivityImpl)pa).getActivityBehavior()).getTaskDefinition();
				}
			}
		   }
		}
		return null;
	}
	
	/**
	* 根据key获得一个最新的流程定义
	* @param key
	* @return
	*/
	public  ProcessDefinition getNewProcessDefinition(String key) {
	//根据key查询已经激活的流程定义，并且按照版本进行降序。那么第一个就是将要得到的最新流程定义对象
	List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
			.processDefinitionKey(key).orderByProcessDefinitionVersion().desc().list();
	if (processDefinitionList.size() > 0) {
		return processDefinitionList.get(0);
	}
	return null;
   }

}
