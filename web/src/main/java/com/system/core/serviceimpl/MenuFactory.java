package com.system.core.serviceimpl;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.system.base.pojo.Employee;
import com.system.core.service.MenuFactoryService;
import com.system.core.service.MenuService;
import com.system.util.common.Consts;

/**
 * @info 系统目录集成控制
 * @author 高峰 2016-09-06
 */
@Service
public class MenuFactory implements MenuFactoryService{
	
	@Resource
	private MenuService menuGroupServer;
	@Resource
	private MenuService menuListServer;


	public String createMenuString(ServletRequest request, String optype) {
		HttpServletRequest req = (HttpServletRequest) request;
		String basePath = request.getServletContext().getContextPath();
		Employee emp = (Employee) req.getSession().getAttribute(Consts.userkey);
		String userid = emp.getEmployeeId();
		String loginName = emp.getEmployeeLoginName();
		if (optype.equalsIgnoreCase(Consts.MENUGROUP)) {
			menuGroupServer.setBasePath(basePath);
			return menuGroupServer.createLimitedMenuList(null, userid, null, loginName.equals(Consts.Administrator));
		} else if (optype.equalsIgnoreCase(Consts.MENULIST)) {
			String groupid = request.getParameter(Consts.groupId);
			return menuListServer.createLimitedMenuList(groupid, userid, req, loginName.equals(Consts.Administrator));
		}
		
		return null;
	}
	
	

}
