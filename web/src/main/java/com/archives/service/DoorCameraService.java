package com.archives.service;

import java.util.List;

import com.archives.pojo.DoorCamera;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface DoorCameraService {
	    int deleteByPrimaryKey(Integer guid) throws Exception;
	    int insert(DoorCamera record)throws Exception;

	    int insertSelective(DoorCamera record)throws Exception;

	    DoorCamera selectByPrimaryKey(Integer guid)throws Exception;

	    int updateByPrimaryKeySelective(DoorCamera record)throws Exception;
		public Pager findDoorCameraPage(DoorCamera emp,int start, int totalSize )throws Exception;
		public Result insertDoorSelective(DoorCamera door)throws Exception;
		public Result delDoorSelective(int guid)throws Exception;
		public Result updateDoorSelective(DoorCamera door)throws Exception;
		public Result delDoor(List<String> idList)throws Exception; 
		public List<DoorCamera> querydoorCameraAll()throws Exception;
		

    }

