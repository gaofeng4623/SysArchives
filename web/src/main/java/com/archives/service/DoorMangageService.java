package com.archives.service;

import java.util.List;

import com.archives.pojo.DoorMangage;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface DoorMangageService {
	  int deleteByPrimaryKey(Integer guid)throws Exception;

	    int insert(DoorMangage record)throws Exception;

	    int insertSelective(DoorMangage record)throws Exception;

	    DoorMangage selectByPrimaryKey(Integer guid)throws Exception;

	    int updateByPrimaryKeySelective(DoorMangage record)throws Exception;
		public Pager findDoorMangagePage(DoorMangage emp,int start, int totalSize )throws Exception;
		public Result insertDoorSelective(DoorMangage door)throws Exception;
		public Result delDoorSelective(int guid)throws Exception;
		public Result updateDoorSelective(DoorMangage door)throws Exception;
		public Result delDoor(List<String> idList)throws Exception;
		

    }

