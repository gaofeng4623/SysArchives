package com.archives.service;

import java.io.OutputStream;
import java.util.List;

import com.archives.pojo.ArchivesInfo;
import com.system.base.pojo.Department;
import com.system.workflow.activiti.commons.Pager;

public interface ArchivesCollectionService {

	Pager findCollection(String middleTime, String undertakeDep, int start, int rows);

	List<ArchivesInfo> findCollectionNopage(String middleTime,String undertakeDep);

	List<Department> findAllDepartmentsforTree();
	List<ArchivesInfo> listInfo(String middleTime,String undertakeDep);
	public void exportExcel(String title,String[] headers,List mapList,OutputStream out,String pattern);
	List<ArchivesInfo> filingReate();
	List<ArchivesInfo> filingReateCount();
}
