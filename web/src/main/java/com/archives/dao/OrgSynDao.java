package com.archives.dao;

import org.apache.ibatis.annotations.Param;

import com.system.base.pojo.Department;
import com.system.base.pojo.Employee;

public interface OrgSynDao {
	public int createDepartment(Department department); //创建部门
	public int createDepartmentRef(@Param("remoteId")String remoteId, @Param("departmentId")String departmentId); //创建部门映射关系
	public int deleteDepartment(@Param("deptNo")String deptNo); //根据远程部门ID删除本地部门
	public int updateDepartment(@Param("deptNo")String deptNo, @Param("parentGuid")String parentGUID, @Param("departmentName")String departmentName, @Param("departmentTabIndex")String departmentTabIndex); //根据远程部门ID修改本地部门
	public int createEmployee(Employee employee); 
	public int createEmployeeRef(@Param("remoteId")String remoteId, @Param("employeeId")String employeeId);  //创建人员映射关系
	public int deleteEmployee(String empNo); //根据远程人员ID删除本地人员
	public int updateEmployee(Employee employee);  //更新本地的人员信息
	public int removeEmployee(@Param("newDeptNo")String newDeptNo,@Param("empNo")String empNo); //根据远程的人员ID和部门ID，移动本地人员到指定部门
	public int changePassword(@Param("empNo")String empNo,@Param("password")String password);  //根据远程的人员ID修改本地的人员密码
	public String selecByRemoteId(@Param("remoteId")String remoteId);//得到对应本地的父节点id
	public String selecByremoteIdEmploy(@Param("remoteId")String remoteId);
	public int deleteDepartmentRef(@Param("remoteId")String remoteId); //根据远程部门ID删除映射信息
	public int deleteEmployeeRef(@Param("empNo")String empNo); //根据远程人员ID删除映射信息
}
