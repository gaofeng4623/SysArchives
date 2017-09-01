package com.system.dao.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.system.base.pojo.Employee;

public interface EmployeeDao {
	// 根据用户名和密码查询用户
	public Employee findUser(String name);

	public int updateByPrimaryKeySelective(Employee emp);

	public List<Employee> findEmpByDeptIds(@Param("paraMap") Map paraMap);

	public List<Employee> findEmpByDeptIdsForPage(@Param("paraMap") Map paraMap);

	public int findCountEmpByDeptIdsForPage(@Param("paraMap") Map paraMap);

	public List<Employee> findEmpByDeptId(String deptId);

	public int insertSelective(Employee emp);

	public Employee findEmpByLoginName(String employeeLoginName);

	public int deleteEmployeeByIds(@Param("idList") List<String> idList);

	public Employee findEmployeeById(String empId);

	public List<Employee> findEmpByCondition(@Param("emp") Employee emp);

	// 根据id查询用户信息
	public List findEmployeeId(@Param("paraMap") Map paraMap);

	public List<Employee> findAll();

	// 根据角色id查询用户信息
	public List<Employee> findRoleId(String roleId);
	public List<Employee> findEmpNotRoleid(@Param("idList") String deptIds,@Param("roleId")String roleid) ;
	public Employee findEmp(String employeeLoginName);


}
