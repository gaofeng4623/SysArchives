package com.system.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.system.base.pojo.Menu;
import com.system.base.pojo.Result;
import com.system.core.service.MenuAbsServer;
import com.system.dao.database.MenuDao;
import com.system.util.common.Consts;

/**
 * @info 目录的权限控制服务
 * @author 高峰 2016-09-06
 */
@Service
public class MenuServer extends MenuAbsServer {
	@Resource
	private MenuDao menuDao;

	public List<Menu> findAllMenuforTree() {
		List<Menu> menuList = menuDao.selectAllMenu();
		return createMenuTree(menuList);
	}

	private List createMenuTree(List<Menu> menuList) {
		List<Menu> nodeList = new ArrayList<Menu>();
		Menu root = new Menu();
		root.setGuid("00000");
		root.setSuporId("-1");
		root.setMenuName(Consts.ROOTMENU);
		root.setIsGroup(0);
		root.setIsMenu(1);
		menuList.add(root);

		for (Menu node1 : menuList) {
			boolean mark = false;
			for (Menu node2 : menuList) {
				if (null != node1.getSuporId()
						&& node1.getSuporId().equals(node2.getGuid())) {
					mark = true;
					node2.getChildren().add(node1);
					break;
				}
			}
			if (!mark) {
				nodeList.add(node1);
			}
		}
		return nodeList;
	}

	public Menu selectByPrimaryKey(String menuId) {
		return menuDao.selectByPrimaryKey(menuId);
	}

	public List<Menu> selectMenuByMenuIds(List<String> idList, int startIndex,
			int perPageNum) {
		return menuDao.selectMenuByMenuIds(idList, startIndex, perPageNum);
	}

	public Integer countMenuByMenuIds(List<String> idList) {
		return menuDao.countMenuByMenuIds(idList);
	}

	public Result insertSelective(Menu menu) throws Exception {
		Result res = new Result();
		int flag = menuDao.insertSelective(menu);
		if (-1 < flag) {
			res.setStatus(0);
			res.setMessage("保存成功!");
		} else {
			res.setStatus(1);
			res.setMessage("保存失败,请联系管理员!");
		}
		return res;
	}

	public Result updateByPrimaryKeySelective(Menu menu) throws Exception {
		Result res = new Result();
		int flag = menuDao.updateByPrimaryKeySelective(menu);
		if (-1 < flag) {
			res.setStatus(0);
			res.setMessage("修改成功!");
		} else {
			res.setStatus(1);
			res.setMessage("修改失败,请联系管理员!");
		}
		return res;
	}

	public Result deleteByPrimaryKey(String menuId) throws Exception {
		Result res = new Result();
		Menu temp = menuDao.selectByPrimaryKey(menuId);
		if (null == temp) {
			res.setStatus(1);
			res.setMessage("菜单不存在,请确认!");
			return res;
		}
		List<Menu> childDept = menuDao.selectBySuperId(menuId);
		if (null != childDept && 0 < childDept.size()) {
			res.setStatus(1);
			res.setMessage("当前菜单存在子菜单,无法删除!");
			return res;
		}
		menuDao.deleteByPrimaryKey(menuId);
		menuDao.deleteLimitByMenuId(menuId);
		res.setStatus(0);
		res.setMessage("删除成功!");
		return res;
	}

	/**
	 * 根据角色生成权限菜单树
	 */
	public List<Menu> selectLimitedMenusByRoleId(String roleid) {
		List<Menu> allMenus = menuDao.selectAllMenu();
		List<Menu> limitedMenus = null;
		try {
			limitedMenus = menuDao.selectLimitedMenusByRoleId(roleid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Menu me : allMenus) {
			for (Menu mu : limitedMenus) {
				if (me.getGuid().equals(mu.getGuid())) {
					me.setChecked(true);
				}
			}
		}
		return createMenuTree(allMenus);
	}

}
