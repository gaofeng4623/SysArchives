package com.archives.service;

import java.util.List;

import com.archives.pojo.LocationControl;
import com.archives.pojo.LocationInfo;
import com.archives.pojo.LocationType;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface LocationInfoService {

	List<LocationInfo> getNodesByParentId(Integer pid);
	List<LocationInfo> NodesByParentId(Integer pid,String type);

	Pager queryPageLocationList(Integer pid, int start, int rows);

	LocationInfo getLocationInfoByGuid(Integer guid);

	List<LocationType> getTypeList(int parentcode);

	Result saveLocationInfo(LocationInfo locationInfo);

	Result updateLocationInfo(LocationInfo locationInfo);

	Result delLocationInfo(List<String> idList);

	List<LocationInfo> getOrderList(Integer guid);

	Result saveOrderInfo(String orderStr);
	public List<LocationInfo> queryPageLocationAll();
	Pager queryPageControlList(String controlAddress, String branchAddress, int start, int rows);

	Result saveLocationControl(LocationControl locationControl);

	LocationControl queryLocationControlById(Integer ctrlId);

	Result updLocationControl(LocationControl locationControl);

	Result delLocationControlInfo(List<Integer> idList);

	List<LocationControl> queryLocationControlListForCombobox(String condation);
}
