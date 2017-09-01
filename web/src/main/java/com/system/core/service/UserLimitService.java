package com.system.core.service;

import java.util.List;

import com.system.base.pojo.Department;
import com.system.base.pojo.OrgNode;
import com.system.base.pojo.Resouce;
import com.system.base.pojo.Roledefine;

public interface UserLimitService {
	
	public List<Department> findAllDepartments();
	
	public List<OrgNode> createOrgNodeTreeRole() throws Exception;
	
	public List<OrgNode> createOrgNodeTreeByRoleName(String roleId) throws Exception;
	
	public List<Roledefine> findRoleNameById(String empId);
	
	public List<Resouce> findAllResouce(String roleId);

}
