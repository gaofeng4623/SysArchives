package com.archives.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.DoorPass;
import com.system.workflow.activiti.commons.Pager;

public interface DoorPassService {
	
	 
    public List<DoorPass> findDoorPassForPage(@Param("paraMap") Map paraMap)throws Exception;
	public int findCountDoorPassForPage(@Param("paraMap") Map paraMap)throws Exception;
	
	Pager findEmpByDoorPassForPage(DoorPass emp, int start, int totalSize)throws Exception;


}
