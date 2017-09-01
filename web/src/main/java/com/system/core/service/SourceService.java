package com.system.core.service;

import java.util.List;

import javax.servlet.ServletRequest;

import com.system.base.pojo.Result;
import com.system.base.pojo.Source;


/*系统的资源服务*/
public interface SourceService {

	public List<Source> findAllSource();
	
	public List<Source> selectLimitedSourceByRoleId(String roleId);
	
	public List<Source> selectSourceBySourceIds(List<String> idList, int startIndex, int perPageNum);
	
	public int countSourceBySourceIds(List<String> idList);
	
	public int insertRoleSource(String roleId, String[] idlist);
	
	public int removeRoleSource(String roleId);

	public Source selectByPrimaryKey(String guid);
	
	public List<Source> selectByTypeName(String type, ServletRequest request); 

	public Result insertSource(Source record) throws Exception;

	public Result deleteByPrimaryKey(String guid) throws Exception;

	public Result updateBySelective(Source record) throws Exception;
}
