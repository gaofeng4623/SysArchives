package com.system.core.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.system.base.pojo.Employee;
import com.system.base.pojo.Person;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;
public interface UserService {
	
	public int save(Person p);
	public List findAll();
	public int update(Employee p);
	public int delete(Person p);
	//根据用户名和密码查询用户
	public Result login(String loginName, String passWord, HttpServletRequest request);
	public Pager findEmpByDeptIdsForPage(Employee emp, List<String> idList, int start, int totalSize);
	public List<Employee> findEmpByCondition(Employee emp);
	public List<Employee> findEmpByDeptId(List<Long> idList);
	 //根据id查询用户信息
	public List<Employee> findEmployeeId(Map m);
	//根据角色id查询用心信息
	public List<Employee> findRoleId(String roleId);
	public List<Employee> findEmpNotRoleid(String deptIds, String roleid);
	public Result findUser(String name);
	//List<Employee> findEmpNotRoleid(String roleid);
	public Employee findEmp(String employeeLoginName);
}
