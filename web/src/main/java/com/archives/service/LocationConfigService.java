package com.archives.service;

import java.util.List;

import com.archives.pojo.LocationConfig;
import com.archives.pojo.LocationType;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface LocationConfigService {

	public List<LocationConfig> getConfigTreeNodes();

	public Pager queryPageConfigList(Integer pid, int start, int rows);

	public LocationConfig selectLocationConfigByPrimaryKey(Integer parentid);

	public List<LocationType> getTypeList();

	public Result saveConfigInfo(LocationConfig config);

	public Result delConfigInfo(List<String> idList);

}
