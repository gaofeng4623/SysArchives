package com.system.dao.database;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.system.base.pojo.Employee;
import com.system.base.pojo.Rolemembers;
import com.system.base.pojo.WorkflowRoleDefine;
import com.system.base.pojo.WorkflowRoleMember;

public interface WorkflowRoleDao {

	public List<WorkflowRoleDefine> findAllRoles();

	public WorkflowRoleDefine selectByPrimaryKey(String guid);

	public WorkflowRoleDefine selectByName(String roleName);

	public int saveRole(WorkflowRoleDefine workflowRoleDefine);

	public void deleteByPrimaryKey(String guid);
	
	public void deleteWorkflowRoleMembers(String guid);

	public List<Employee> findRoleId(String roleId);

	public List<Rolemembers> selectRoleId(String roleId);

	public List<Employee> findEmployeeId(List<String> userIdList);

	public List<Employee> queryEmpByDepId(String deptIds);

	public void saveWorkflowRoleMember(WorkflowRoleMember workflowRoleMember);

	public void deleteEmpByPrimaryKey(String employeeId);

	public void deleteRoleByEmpIdList(@Param("idList")List<String> idList);

}
