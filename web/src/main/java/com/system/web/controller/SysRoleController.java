package com.system.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.base.pojo.Roledefine;
import com.system.base.pojo.Rolemembers;
import com.system.base.pojo.SysRole;
import com.system.core.service.RoledefineService;
import com.system.core.service.RolemembersService;
import com.system.core.service.SysRoleService;
import com.system.core.service.UserService;
import com.system.util.common.Consts;

/**
 * @info 实现系统角色相关服务 
 * @author 高峰 2016-09-08
 */
@Controller
@RequestMapping("/sysRole")
public class SysRoleController {
	@Resource
	private UserService userService;  //用户服务
	@Resource
	private RoledefineService roledefineService; //角色定义
	@Resource
	private RolemembersService rolemembersService; //角色映射
	@Resource
	private SysRoleService sysRoleService; //角色菜单服务

	/*初始化角色定义树*/
	@RequestMapping("/initRoledefinTree.do")
	@ResponseBody
	public List<Roledefine> initRoledefinTree() {
		return roledefineService.findAllRoledefine();
	}
	
	/*添加角色*/
	@RequestMapping("/addRole.do")
	public ModelAndView addRole(String guid) {
		ModelAndView mv = new ModelAndView("system/alert/addRoleDefine");
		Roledefine role = roledefineService.selectByPrimaryKey(guid);
		if (role == null) {
			role = new Roledefine();
			role.setGuid("0");
			role.setRolename(Consts.ROOTROLE);
		}
		mv.addObject("superDept", role);
		return mv;
	}
	
	
	/*保存角色*/
	@RequestMapping("/saveRoleInfo.do")
	@ResponseBody
	public Result insertSelective(Roledefine record) throws Exception {
		return roledefineService.insertRoledefine(record);
	}

	/*编辑角色页面*/
	@RequestMapping("/editRole.do")
	public ModelAndView editRole(String guid) {
		ModelAndView mv = new ModelAndView("system/alert/editRoleDefine");
		Roledefine role = roledefineService.selectByPrimaryKey(guid);
		Roledefine group = roledefineService.selectByPrimaryKey(role.getGroupid());
		if (role == null) {
			role = new Roledefine();
			role.setGuid("0");
			role.setRolename(Consts.ROOTROLE);
		}
		String groupName = group == null ? Consts.ROOTROLE
				: group.getRolename();
		role.setGroupName(groupName);
		mv.addObject("target", role);
		return mv;
	}
	
	/*更新角色*/
	@RequestMapping("/updateRoleInfo.do")
	@ResponseBody
	public Result updateRoleInfo(Roledefine record) throws Exception {
		return roledefineService.updateRoledefine(record);
	}
	
	/*刪除角色定义*/
	@RequestMapping("/removeRole.do")
	@ResponseBody
	public Result removeRole(String guid) throws Exception {
		return roledefineService.deleteByPrimaryKey(guid);
	}
	
	/*清空角色成员*/
	@RequestMapping("/removeRolemembers.do")
	@ResponseBody
	public Result removeRolemembers(String guid) throws Exception {
		return roledefineService.removeRolemembers(guid);
	}
	
	/*重置角色信息*/
	@RequestMapping("/resetRole.do")
	@ResponseBody
	public Result resetRole(String guid) throws Exception {
		return roledefineService.resetRole(guid);
	}

	/*保存某个人员或部门下所有人员到一个角色组*/
	@RequestMapping("/saveRolemembers.do")
	@ResponseBody
	public Result saveRolemembers(String roleid, String deptIds, String type)
			throws Exception {
		List<Employee> list = null;
		Result res = new Result();
		Rolemembers rolemembers = new Rolemembers();
		// 判断点的是部门还是人员
		if (type.equals("0")) {
			List idList = new ArrayList();
			if (null != deptIds && !"".equals(deptIds)) {
				for (String deptId : deptIds.split(",")) {
					if (StringUtils.isEmpty(deptId)) {
						continue;
					}
					idList.add(deptId);
				}
			}
			if (0 < idList.size()) {
				list = userService.findEmpNotRoleid(deptIds, roleid);
			} else {
				list = userService.findEmpNotRoleid(null, null);
			}
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					rolemembers.setUserid(list.get(i).getEmployeeId());
					rolemembers.setRoleid(roleid);
					rolemembersService.save(rolemembers);
				}
				res.setStatus(0);
			} else {
				res.setMessage("你选择的部门下面没有人员");
				res.setStatus(1);
			}
		} else {
			rolemembers.setUserid(deptIds);
			rolemembers.setRoleid(roleid);
			rolemembersService.save(rolemembers);
			res.setStatus(0);
		}
		return res;

	}

	/*查询与角色相关的用户信息*/
	@RequestMapping("/findRolemembers.do")
	@ResponseBody
	public List<Employee> findRolemembers(String roleId) {
		Map data = new HashMap();
		List<Rolemembers> list = rolemembersService.selectRoleId(roleId);
		List<String> idList = new ArrayList<String>();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				idList.add(list.get(i).getUserid());
			}
			data.put("idList", idList);
			data.put("roleId", roleId);
		}
		if (0 < idList.size()) {
			return userService.findEmployeeId(data);
		}
		return null;
	}

	/*删除与角色相关的用户信息*/
	@RequestMapping("/deleteByPrimaryKey.do")
	@ResponseBody
	public Result deleteByPrimaryKey(int rolerelateid) throws Exception {
		Result res = new Result();
		rolemembersService.deleteByPrimaryKey(rolerelateid);
		res.setStatus(0);
		return res;
	}
	
	/*显示角色定义菜单的所有角色列表*/
	@RequestMapping("/allRoles.do")
	@ResponseBody
	public List<SysRole> getAllRoles() throws Exception {
		return sysRoleService.findAllRoles();
	}

	
	/*保存角色关联的菜单对象 */
	@RequestMapping("/saveRoleMenus.do")
	@ResponseBody
	public Result saveMenuSetting(String roleId, String[] idList) {
		Result res = new Result();
		try {
			sysRoleService.removeRoleMenus(roleId);
			if (idList == null || idList.length == 0) {
				res.setStatus(0);
				res.setMessage("菜单权限保存成功");
				return res;
			}
			sysRoleService.insertRoleMenus(roleId, idList);
			res.setStatus(0);
			res.setMessage("菜单权限保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(1);
			res.setMessage("菜单权限保存失败");
		}
		return res;
	}

	/*返回当前登录人的所有角色*/
	@RequestMapping("/findCurrentUserRoles.do")
	@ResponseBody
	public Result findRolesForCurrentUser(HttpSession session) {
		StringBuffer sb = new StringBuffer();
		try {
			Employee emp = (Employee) session.getAttribute(Consts.userkey);
			List<SysRole> currentRoles = sysRoleService
					.findRolesForCurrentUser(emp.getEmployeeId());
			for (SysRole sl : currentRoles) {
				if (sl != null)
					sb.append(sl.getRoleName()).append(",");
			}
			return new Result(1, sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(0, "查询角色异常" + e.getMessage());
		}
	}

}
