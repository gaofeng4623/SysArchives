package com.system.core.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.system.base.pojo.Menu;
import com.system.base.pojo.Result;

public abstract class MenuAbsServer implements MenuService{

	@Override
	public abstract List<Menu> findAllMenuforTree();

	@Override
	public abstract Result insertSelective(Menu Menu) throws Exception;

	@Override
	public abstract Result updateByPrimaryKeySelective(Menu Menu) throws Exception;

	@Override
	public abstract Result deleteByPrimaryKey(String functionid) throws Exception;

	@Override
	public abstract Menu selectByPrimaryKey(String functionId);

	@Override
	public abstract List<Menu> selectMenuByMenuIds(List<String> idList, int startIndex, int perPageNum);

	@Override
	public abstract Integer countMenuByMenuIds(List<String> idList);

	@Override
	public abstract List<Menu> selectLimitedMenusByRoleId(String roleid);

	@Override
	public String createLimitedMenuList(String groupid, String userid,
			HttpServletRequest request, boolean isAdministrator) {
		return null;
	}

	@Override
	public String createMenuStyler() {
		return null;
	}

	@Override
	public String createMenuScripts(HttpServletRequest request) {
		return null;
	}
	
	@Override
	public void setBasePath(String basePath) {}

}
