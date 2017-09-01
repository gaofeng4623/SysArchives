package com.archives.exchange.dao;

import java.util.List;

import com.system.base.pojo.Department;
import com.system.base.pojo.Employee;

public interface OrgHandlerDao {
	
	public List<Department> queryDepartments();

	public List<Employee> queryEmployees();
}
