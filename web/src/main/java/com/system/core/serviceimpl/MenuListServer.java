package com.system.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.system.base.pojo.Menu;
import com.system.base.pojo.MenuItem;
import com.system.base.pojo.MenuModel;
import com.system.base.pojo.SubMenuModel;
import com.system.core.service.MenuListAbsServer;
import com.system.dao.database.MenuDao;
import com.system.util.common.Consts;
/**
 * @info 控制系统目录列表的生成
 * @author 高峰 2016-09-06
 */
@Service
public class MenuListServer extends MenuListAbsServer{
	
	@Resource
	private MenuDao menuDao;
	
	@Override
	public String createMenuScripts(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		String contextPath = request.getServletContext().getContextPath();
		String homePage = request.getParameter(Consts.homePage);
		sb.append("<script type=\"text/javascript\">\r\n");
		sb.append("$(function() {\r\n");
		sb.append("$('#nav > ul > li > ul > li ').hover(function(){\r\n");
		sb.append("$('#nav > ul > li > ul > li > ul').css(\"top\",$(this).index()*42);\r\n");	
		sb.append("});\r\n");		
		sb.append("$(\"#content\").attr('src', \"handleView.do?src=").append(homePage).append("\"); \r\n"); //加载默认页
		sb.append("});");	
		sb.append("function panelRefresh(name, url) {\r\n");
		sb.append("	if(url != \"\" && url != \"undefined\" && url != null){\r\n");
		sb.append("	$(\"#center-panel\").panel(\"setTitle\", name);\r\n");
		sb.append("	$(\".pageTitle\").html(name);\r\n");
		sb.append("	$(\"#content\").attr(\"src\",\"").append(contextPath).append("\" + url);\r\n");
		sb.append("	}\r\n");
		sb.append("}\r\n");
		sb.append("</script>\r\n");
		return sb.toString();
	}
	
	@Override
	public String createMenuStyler() {
		StringBuffer sb = new StringBuffer();
		sb.append("<style type=\"text/css\">\r\n");
		sb.append("ul, li { list-style:none;}\r\n");
		sb.append("a { text-decoration:none;}\r\n");
		sb.append(".nav { border:0px solid #ccc; border-right:none; overflow:hidden; float:right;}\r\n");
		sb.append(".nav ul li { float:left;}\r\n");
		sb.append(".nav ul li a {margin-top:10px;width:120px; height:30px; text-align:center; line-height:30px; display:block; border-right:2px #ccc;color:#666;}\r\n");
		sb.append(".nav ul li a:hover { color:#24659D; }\r\n");
		sb.append(".nav ul li ul { position:absolute;display:none;}\r\n");
		sb.append(".nav ul li ul li { float:none;}\r\n");
		sb.append(".nav ul li ul li a {display:block;margin-top:0px;width:90px; height: 20px;line-height: 20px; padding: 10px 16px; border:1px  dotted #ccc; background: #f5f5f5;}\r\n");
		sb.append(".nav ul li ul li a:hover { color:#fff; background-color:#4A8BC3;}\r\n");
		sb.append(".nav ul li:hover ul li:hover ul li a:hover { color:#fff; background-color:#4A8BC3;}\r\n");
		sb.append(".nav ul li:hover ul { display:block;}\r\n");
		sb.append(".nav ul li:hover ul li ul {display: none;}\r\n");
		sb.append(".nav ul li:hover ul li:hover ul {display:block;left:123px;}\r\n");
		sb.append(".pageTitle { margin-left: 1%;margin-top:10px;color:#24659D;font-size: 14px; font-weight: bold;}\r\n");
		sb.append("body,html{font-size: 13px !important;font-family: \"Microsoft YaHei UI\", \"微软雅黑\", Georgia, \"Times New Roman\", STXihei, \"华文细黑\", serif !important;}\r\n");
		sb.append("html,body,h1,h2,h3,h4,h5,h6,ul,li,dd,dt{margin:0;padding:0;}\r\n");
		sb.append("</style>\r\n");
		return sb.toString();
	}

	/**
	 * 处理登录后树形菜单的加载
	 */
	@Override
	public String createLimitedMenuList(String groupid, String userid, 
			HttpServletRequest request, boolean isAdministrator) {
		StringBuffer sb = new StringBuffer();
		sb.append(createMenuStyler());
		sb.append(createMenuScripts(request));
		List<SubMenuModel> result = new ArrayList<SubMenuModel>();
		List<Menu> menuList = menuDao.selectMenusByGroupId(groupid);
		List<MenuItem> limitedMenus = isAdministrator ? menuDao
		.selectAllMenuItems() : menuDao.selectLimitedMenusByUserid(userid);
		for (Menu menu : menuList) {
			SubMenuModel sm = new SubMenuModel(menu);
			filterFirstMenu(menu, sm, limitedMenus);
			List<Menu> childMenus = menuDao.selectMenusByGroupId(menu.getGuid());
			for (Menu mu : childMenus) {
				MenuModel mm = new MenuModel(mu);
				filterSecondMenu(sm, mm, mu, limitedMenus);
			}
			if (sm.isLimited())
				result.add(sm);
		}
		
		for (SubMenuModel sm : result) {
			sb.append(sm);
		}
		return sb.toString();
	}

	private void filterFirstMenu(Menu menu, SubMenuModel sm,
			List<MenuItem> limitedMenus) {
		for (MenuItem mi : limitedMenus) {
			if (mi.getSuporId().equals(menu.getGuid())) {
				sm.getChildren().add(mi);
				sm.setLimited(true);
			}
		}
	}

	private void filterSecondMenu(SubMenuModel sm, MenuModel mm, Menu mu,
			List<MenuItem> limitedMenus) {
		for (MenuItem mi : limitedMenus) {
			if (mi.getSuporId().equals(mu.getGuid())) {
				mm.getChildren().add(mi);
				sm.setLimited(true);
			}
		}
		if (!isEmpty(mm)) {
			sm.getChildren().add(mm);
		}
	}

	private boolean isEmpty(MenuModel mm) {
		return mm.getChildren() == null || mm.getChildren().size() == 0;
	}

}
