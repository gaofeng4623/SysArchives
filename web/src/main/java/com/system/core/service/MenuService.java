package com.system.core.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.system.base.pojo.Menu;
import com.system.base.pojo.Result;

public interface MenuService {
	
	public String createMenuStyler();
	
	public String createMenuScripts(HttpServletRequest request);
	
	public List<Menu> findAllMenuforTree();
	
	public void setBasePath(String basePath);
	
	public Result insertSelective(Menu menu) throws Exception;
	
	public Result updateByPrimaryKeySelective(Menu menu) throws Exception;
	
	public Result deleteByPrimaryKey(String menuId) throws Exception;
	
	public Menu selectByPrimaryKey(String menuId);
	
	public List<Menu> selectMenuByMenuIds(List<String> idList,int startIndex,int perPageNum);
	
	public Integer countMenuByMenuIds(List<String> idList);
	
	public List<Menu> selectLimitedMenusByRoleId(String roleid);
	 
	public String createLimitedMenuList(String groupid, String userid, HttpServletRequest request, boolean isAdministrator);  
	
}
