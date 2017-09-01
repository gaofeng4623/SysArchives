package com.system.core.service;

import java.util.List;

import com.system.base.pojo.Department;
import com.system.base.pojo.Employee;
import com.system.base.pojo.OrgNode;
import com.system.base.pojo.Result;

public interface OrganizationService {
	public List<Department> findAllDepartmentsforTree();

	public List<OrgNode> createOrgNodeTree() throws Exception;

	public Department selectByPrimaryKey(String departmentid);

	public Result insertSelective(Department record) throws Exception;

	public Result updateByPrimaryKeySelective(Department dept) throws Exception;

	public Result deleteByPrimaryKey(String departmentid) throws Exception;

	public Result insertEmpSelective(Employee emp, String[] roleId) throws Exception;

	public Result deleteEmployeeByIds(List<String> idList) throws Exception;

	public Employee findEmployeeById(String empId);

	public Result updateEmpSelective(Employee emp, String[] roleId) throws Exception;
	List<Department> selectBySuperId(String departmentid);
	
}
