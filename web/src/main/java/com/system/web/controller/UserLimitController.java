package com.system.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.base.pojo.OrgNode;
import com.system.base.pojo.Resouce;
import com.system.base.pojo.Roledefine;
import com.system.base.pojo.SysRole;
import com.system.core.service.SysRoleService;
import com.system.core.service.UserLimitService;

/**
 * @info 实现权限控制模块相关服务 
 * @author 高峰 2016-09-08
 */
@Controller
@RequestMapping("/limit")
public class UserLimitController {

	@Resource
	private UserLimitService userLimitService;
	@Resource
	private SysRoleService sysRoleService;
	

	/* 获取角色对应的组织机构树形结构 */
	@RequestMapping("/createOrgNodeTreeByRoleId.do")
	@ResponseBody
	public List<OrgNode> createOrgNodeTreeByRoleName(String roleId)
			throws Exception {
		return userLimitService.createOrgNodeTreeByRoleName(roleId);
	}

	/* 创建组织机构树形结构角色信息 */
	@RequestMapping("/createOrgNodeTreeRole.do")
	@ResponseBody
	public List<OrgNode> createOrgNodeTreeRole() throws Exception {
		return userLimitService.createOrgNodeTreeRole();
	}


	/**
	 * 创建和编辑用户时查询某个用户已有的角色
	 * 
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findSelectRole.do")
	@ResponseBody
	public List<SysRole> findSelectRole(String empId) throws Exception {
		// 查询出已有的角色
		List<Roledefine> roledefineList = userLimitService
				.findRoleNameById(empId);
		// 全部
		List<SysRole> sysRoles = sysRoleService.findAllNoTreeRoles();
		for (SysRole sysRole : sysRoles) {
			for (Roledefine roledefine : roledefineList) {
				if (sysRole.getGuid().equals(roledefine.getGuid())) {
					sysRole.setChecked(true);
				}
			}
			sysRole.setId(sysRole.getGuid());
			sysRole.setText(sysRole.getRoleName());
		}
		List<SysRole> result = new ArrayList<SysRole>();
		for (SysRole sr : sysRoles) {
			for (SysRole srl : sysRoles) {
				if (srl.getGroupId() != null
						&& srl.getGroupId().equals(sr.getGuid())) {
					sr.getChildren().add(srl);
				}
			}
			if ("0".equals(sr.getGroupId())) {
				result.add(sr);
			}
		}

		return result;
	}

	/**
	 * 新增用户时，查询全部角色
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findAllRole.do")
	@ResponseBody
	public List<SysRole> findAllRole() throws Exception {
		// 全部
		List<SysRole> sysRoles = sysRoleService.findAllNoTreeRoles();
		List<SysRole> result = new ArrayList<SysRole>();
		for (SysRole sr : sysRoles) {
			for (SysRole srl : sysRoles) {
				if (srl.getGroupId() != null
						&& srl.getGroupId().equals(sr.getGuid())) {
					sr.getChildren().add(srl);
					srl.setId(srl.getGuid());
					srl.setText(srl.getRoleName());
				}
			}
			if ("0".equals(sr.getGroupId())) {
				sr.setId(sr.getGuid());
				sr.setText(sr.getRoleName());
				result.add(sr);
			}
		}

		return result;
	}

	/*角色设置页面的人员部门树*/
	@RequestMapping("/initDeptAndEmpTree.do")
	@ResponseBody
	public List<Resouce> initDeptAndEmpTree(String roleId) {
		return userLimitService.findAllResouce(roleId);
	}
	
}
