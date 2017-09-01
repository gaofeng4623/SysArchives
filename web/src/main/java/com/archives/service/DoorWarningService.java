package com.archives.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.DoorWarning;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface DoorWarningService {
	
	 
    public List<DoorWarning> findDoorWarningForPage(@Param("paraMap") Map paraMap)throws Exception;
	public int findCountDoorWarningForPage(@Param("paraMap") Map paraMap)throws Exception;
	
	Pager findEmpByDoorWarningForPage(DoorWarning emp, int start, int totalSize)throws Exception;

	public Result updateDoorSelective(DoorWarning door)throws Exception;
	public List<DoorWarning> findAllByIds(DoorWarning emp);

}
