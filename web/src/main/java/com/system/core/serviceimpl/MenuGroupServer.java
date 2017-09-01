package com.system.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.system.base.pojo.Menu;
import com.system.base.pojo.MenuItem;
import com.system.core.service.MenuListAbsServer;
import com.system.dao.database.MenuDao;


/**
 * @info 控制分组目录的显示
 * @author 高峰 2016-09-06
 */
@Service
public class MenuGroupServer extends MenuListAbsServer{

	@Resource
	private MenuDao menuDao;

	@Override
	public String createMenuScripts(HttpServletRequest request) {
		  StringBuffer scripts = new StringBuffer();
		  scripts.append("<script type=\"text/javascript\">\r\n");
		  scripts.append("$(document).ready(function(){\r\n");
		  scripts.append("  $(\"ul li\").each(function(){\r\n");
		  scripts.append("    $(this).bind(\"click\",function(){ \r\n");
		  scripts.append("  	  if(this.id != \"\"){ \r\n");
		  scripts.append("        $('#iframe').attr('src',$(this).attr('context')); \r\n");
		  scripts.append(" 		$(\"ul li\").removeClass('select-color');\r\n");
		  scripts.append(" 		$(this).addClass('select-color');\r\n");
		  scripts.append("	   } \r\n");
		  scripts.append("	}); \r\n");
		  scripts.append("  });\r\n");  
		  scripts.append("  $(\"ul li:first-child\").trigger(\"click\");\r\n");
		  scripts.append("})\r\n");
		  scripts.append("</script>\r\n");
		  return scripts.toString();
	}

	/***菜单样式可根据实际需要扩展***/
	@Override
	public String createMenuStyler() {
		StringBuffer styler = new StringBuffer();
		styler.append("<style type=\"text/css\">\r\n");
		styler.append(".select-color {background: #fff;color: #000  !important;}\r\n");
		styler.append(".header_type{background-color:#4a8bc3;}\r\n");
		styler.append(".header_type li{\r\n");
		styler.append("height: 50px;\r\n");
		styler.append("width: 170px;\r\n");
		styler.append("float: left ;\r\n");
		styler.append("border-right: 1px solid #24659d;\r\n");
		styler.append("text-align: center;\r\n");
		styler.append("display: inline-block;\r\n");
		styler.append("line-height: 40px;\r\n");
		styler.append("font-size: 15px;\r\n");
		styler.append("font-family:\"微软雅黑\";\r\n");
		styler.append("font-weight: bold;\r\n");
		styler.append("color: #a5defb;\r\n");
		styler.append("cursor: pointer;\r\n");
		styler.append("}\r\n");
		styler.append("</style>\r\n");
		return styler.toString();
	}
	
	@Override
	public String createLimitedMenuList(String groupid, String userid, 
			HttpServletRequest request, boolean isAdministrator) {
		List<Menu> result = new ArrayList();
		List<Menu> groupMenus = menuDao.selectGroupMenus();
		if (isAdministrator) return createMenuList(groupMenus);
		List<MenuItem> limitedMenus = menuDao
				.selectLimitedMenusByUserid(userid);
		if (groupMenus != null) {
			for (Menu mu : groupMenus) {
				menuFilter(mu, limitedMenus, mu);
				if (mu.isChecked()) {
					result.add(mu);
				}
			}
		}
		return createMenuList(result);
	}

	public void menuFilter(Menu menu, List<MenuItem> limitedMenus, Menu group) {
		if (menu.getIsMenu() == 1) {
			List<Menu> child = menuDao.selectBySuperId(menu.getGuid());
			for (Menu mu : child) {
				menuFilter(mu, limitedMenus, group);
			}
		} else {
			for (MenuItem item : limitedMenus) {
				if (item.getGuid().equals(menu.getGuid())) {
					group.setChecked(true);
					break;
				}
			}
		}
	}
	
	public String createMenuList(List<Menu> groupMenus) {
		StringBuffer menus = new StringBuffer();
		for (Menu mu : groupMenus) {
			  mu.setBasePath(getBasePath());
			  menus.append(mu);
		  }
		menus.append(createMenuScripts(null));
		menus.append(createMenuStyler());
		return menus.toString();
	}
	
	
}
