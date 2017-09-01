package com.archives.service;

import javax.jws.WebService;

/**
 * @info 同步组织机构数据
 * @author GF
 * 
 */
@WebService
public interface OrgSynService {
	
	public int initDepartments(String nodesXml);

	public int initEmployees(String empXml);
	
	public String updateDepartment(String departXml);
	
	public String deleteDepartment(String deptNo);
	
	public String addDepartment(String departXml);
	
	public String addEmployee(String empXml);
	
	public String deleteEmployee(String empNo);
	
	public String updateEmployee(String empXml);
	
	public String removeEmployee(String empXml);
	

}
