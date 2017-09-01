package com.system.core.service;

import java.util.List;

import com.system.base.pojo.Employee;
import com.system.base.pojo.Resouce;
import com.system.base.pojo.Result;
import com.system.base.pojo.Rolemembers;
import com.system.base.pojo.WorkflowRoleDefine;
import com.system.base.pojo.WorkflowRoleMember;

public interface WorkflowRoleService {

	List<WorkflowRoleDefine> findAllRoles();

	WorkflowRoleDefine selectByPrimaryKey(String guid);

	Result saveRoleInfo(WorkflowRoleDefine workflowRoleDefine);

	Result deleteByPrimaryKey(String guid);

	List<Resouce> findDepEmp(String roleId);

	List<Rolemembers> selectRoleId(String roleId);

	List<Employee> findEmployeeId(List<String> userIdList);

	List<Employee> queryEmpByDepId(String deptIds);

	void saveWorkflowRoleMember(WorkflowRoleMember workflowRoleMember);

	void deleteEmpByPrimaryKey(String employeeId);

}
