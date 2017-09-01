package com.system.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.system.base.pojo.Employee;
import com.system.base.pojo.Resouce;
import com.system.base.pojo.Result;
import com.system.base.pojo.Rolemembers;
import com.system.base.pojo.WorkflowRoleDefine;
import com.system.base.pojo.WorkflowRoleMember;
import com.system.core.service.WorkflowRoleService;
import com.system.util.common.Consts;
/**
 * @info   工作流角色设置
 * @author 高峰 2016-09-08
 */
@Controller
@RequestMapping("/workflowRole")
public class WorkflowRoleController {
	@Resource
	private WorkflowRoleService workflowRoleService;

	/**
	 * 显示所有角色
	 */
	@RequestMapping("/allRoles.do")
	@ResponseBody
	public List<WorkflowRoleDefine> getAllRoles() {
		return workflowRoleService.findAllRoles();
	}

	/**
	 * 新增角色弹出
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/addRole.do")
	public ModelAndView addRole(String guid) {
		ModelAndView mv = new ModelAndView("system/alert/addWorkflowRole");
		WorkflowRoleDefine role = workflowRoleService.selectByPrimaryKey(guid);
		if (role == null) {
			role = new WorkflowRoleDefine();
			role.setGuid("0");
			role.setRoleName(Consts.ROOTWORKFLOW);
		}
		mv.addObject("superRole", role);
		return mv;
	}

	/**
	 * 保存新增角色
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping("/saveRoleInfo.do")
	@ResponseBody
	public Result saveRoleInfo(WorkflowRoleDefine workflowRoleDefine) {
		Result res = workflowRoleService.saveRoleInfo(workflowRoleDefine);
		return res;
	}

	/**
	 * 删除角色
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/removeRole.do")
	@ResponseBody
	public Result removeRole(String guid) {
		Result res = workflowRoleService.deleteByPrimaryKey(guid);
		return res;
	}

	/**
	 * 选择角色时加载部门树
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/initDeptAndEmpTree.do")
	@ResponseBody
	public List<Resouce> initDeptAndEmpTree(String roleId) {
		List<Resouce> depts = workflowRoleService.findDepEmp(roleId);
		return depts;
	}

	/**
	 * 查询与角色相关的用户信息
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/findRolemembers.do")
	@ResponseBody
	public List<Employee> findRolemembers(String roleId) {
		List<Rolemembers> rolemembersList = workflowRoleService
				.selectRoleId(roleId);
		List<String> userIdList = new ArrayList<String>();
		List<Employee> rolList = null;
		if (rolemembersList.size() > 0) {
			for (int i = 0; i < rolemembersList.size(); i++) {
				userIdList.add(rolemembersList.get(i).getUserid());
			}
		}
		if (0 < userIdList.size()) {
			rolList = workflowRoleService.findEmployeeId(userIdList);
			return rolList;
		} else {
			return rolList;
		}
	}

	/**
	 * 保存角色人员关联信息
	 * 
	 * @param roleid
	 * @param deptIds
	 * @param typehh
	 * @return
	 */
	@RequestMapping("/saveRolemembers.do")
	@ResponseBody
	public Result saveRolemembers(String roleid, String deptIds, String typehh) {
		Result res = new Result();
		if (typehh.equals("0")) {// 部门
			List<Employee> list = workflowRoleService.queryEmpByDepId(deptIds);
			if (list != null && list.size() > 0) {
				for (Employee employee : list) {
					WorkflowRoleMember workflowRoleMember = new WorkflowRoleMember();
					workflowRoleMember.setRoleId(roleid);
					workflowRoleMember.setUserId(employee.getEmployeeId());
					workflowRoleService
							.saveWorkflowRoleMember(workflowRoleMember);
					res.setStatus(0);
				}
			} else {
				res.setMessage("你选择的部门下面没有人员");
				res.setStatus(1);
			}
		} else {
			WorkflowRoleMember workflowRoleMember = new WorkflowRoleMember();
			workflowRoleMember.setRoleId(roleid);
			workflowRoleMember.setUserId(deptIds);
			workflowRoleService.saveWorkflowRoleMember(workflowRoleMember);
			res.setStatus(0);
		}
		return res;
	}

	/**
	 * 删除角色下人员
	 * 
	 * @param employeeId
	 * @return
	 */
	@RequestMapping("/deleteByPrimaryKey.do")
	@ResponseBody
	public Result deleteByPrimaryKey(String employeeId) {
		Result res = new Result();
		workflowRoleService.deleteEmpByPrimaryKey(employeeId);
		res.setStatus(0);
		return res;
	}
}
