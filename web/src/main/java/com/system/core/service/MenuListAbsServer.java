package com.system.core.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.system.base.pojo.Menu;
import com.system.base.pojo.Result;

public abstract class MenuListAbsServer implements MenuService{
	
	private String basePath;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public abstract String createMenuStyler();
	

	@Override
	public abstract String createMenuScripts(HttpServletRequest request);
	
	@Override
	public abstract String createLimitedMenuList(String groupid, String userid,
			HttpServletRequest request, boolean isAdministrator);

	@Override
	public List<Menu> findAllMenuforTree() {
		return null;
	}

	@Override
	public Result insertSelective(Menu menu) throws Exception {
		return null;
	}

	@Override
	public Result updateByPrimaryKeySelective(Menu menu) throws Exception {
		return null;
	}

	@Override
	public Result deleteByPrimaryKey(String menuId) throws Exception {
		return null;
	}

	@Override
	public Menu selectByPrimaryKey(String menuId) {
		return null;
	}

	@Override
	public List<Menu> selectMenuByMenuIds(List<String> idList, int startIndex,
			int perPageNum) {
		return null;
	}

	@Override
	public Integer countMenuByMenuIds(List<String> idList) {
		return null;
	}

	@Override
	public List<Menu> selectLimitedMenusByRoleId(String roleid) {
		return null;
	}

}
