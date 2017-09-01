package com.archives.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.MapDetail;
import com.archives.pojo.MapTotal;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface ListConfigService {
	
	public List<MapTotal> findAllListItemForTree();
	
	public MapTotal selectByPrimaryKey(String guid);
	
	public Result insertSelective(MapTotal record);
	
	public Result updateByPrimaryKeySelective(MapTotal record);
	
	public Result deleteByPrimaryKey(String departmentid);
	
	public Pager findSubItemByPriorIdsForPage(MapDetail mapDetail, List<String> idList, int start, int totalSize );

	public Result insertSubItem(MapDetail record);
	
	public MapDetail selectSubItemByPrimaryKey(String subId);
	
	public Result updateSubItemSelective(MapDetail record);
	
	public Result deleteSubItemByIds(@Param("idList") List<String> idList);
}
