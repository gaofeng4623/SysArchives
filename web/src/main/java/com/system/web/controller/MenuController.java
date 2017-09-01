package com.system.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.system.base.pojo.Menu;
import com.system.base.pojo.Result;
import com.system.core.service.MenuService;
import com.system.util.common.GUID;
import com.system.workflow.activiti.commons.Pager;

/**
 * @info 系统菜单相关功能
 * @author 高峰 2016-09-08
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
	@Resource
	private MenuService menuServer;

	/**
	 * 初始化功能菜单树
	 */
	@RequestMapping("/initMenuTree.do")
	@ResponseBody
	public List<Menu> initMenuTree() {
		return menuServer.findAllMenuforTree();
	}

	/**
	 * 获得角色功能菜单树
	 */
	@RequestMapping("/initRoleTree.do")
	@ResponseBody
	public List<Menu> initRoleTree(String roleid) {
		return menuServer.selectLimitedMenusByRoleId(roleid);
	}

	/**
	 * 查找功能列表
	 */
	@RequestMapping("/findMenu.do")
	@ResponseBody
	public Pager findMenuItem(int page, int rows, String menuIds) {
		List<String> idList = new ArrayList<String>();
		if (null != menuIds && !"".equals(menuIds)) {
			for (String menuId : menuIds.split(",")) {
				if (StringUtils.isEmpty(menuId)) {
					continue;
				}
				idList.add(menuId);
			}
		}
		List<Menu> menuList = new ArrayList<Menu>();
		// 开始查询位置
		int start = (page - 1) * rows;
		// 总数量
		int totalSize = 0;
		if (0 < idList.size()) {
			totalSize = menuServer.countMenuByMenuIds(idList);
			menuList = menuServer.selectMenuByMenuIds(idList, start, rows);
		} else {
			totalSize = menuServer.countMenuByMenuIds(null);
			menuList = menuServer.selectMenuByMenuIds(null, start, rows);
		}
		return new Pager(totalSize, menuList);
	}

	/**
	 * 添加菜单
	 */
	@RequestMapping("/addMenuInit.do")
	public ModelAndView addMenuInit(String menuId, String menuName) {
		ModelAndView mv = new ModelAndView("system/alert/addMenu");
		Menu menu = new Menu();
		menu.setGuid(menuId);
		menu.setMenuName(menuName);
		mv.addObject("superMenu", menu);
		return mv;
	}

	/**
	 * 添加菜单保存
	 */
	@RequestMapping("/saveMenuInfo.do")
	@ResponseBody
	public Result saveMenuInfo(Menu menu) throws Exception {
		menu.setGuid(new GUID().toString());
		Result res = menuServer.insertSelective(menu);
		return res;
	}

	/**
	 * 修改菜单
	 */
	@RequestMapping("/updateMenuInit.do")
	public ModelAndView updateMenuInit(String menuId) throws Exception {
		ModelAndView mv = new ModelAndView("system/alert/updMenu");
		Menu menu = new Menu();
		menu = menuServer.selectByPrimaryKey(menuId);
		mv.addObject("selMenu", menu);
		return mv;
	}

	/**
	 * 修改菜单保存
	 */
	@RequestMapping("/updMenuInfo.do")
	@ResponseBody
	public Result updMenuInfo(Menu menu) throws Exception {
		return menuServer.updateByPrimaryKeySelective(menu);
	}

	/**
	 * 删除菜单
	 */
	@RequestMapping("/delMenu.do")
	@ResponseBody
	public Result delMenu(String menuId) throws Exception {
		return menuServer.deleteByPrimaryKey(menuId);
	}

}
