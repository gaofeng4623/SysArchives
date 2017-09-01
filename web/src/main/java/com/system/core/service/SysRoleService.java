package com.system.core.service;

import java.util.List;

import com.system.base.pojo.SysRole;

public interface SysRoleService {
	
	public List<SysRole> findAllRoles() throws Exception;
	
	public int insertRoleMenus(String roleId, String[] data) throws Exception;	
	
	public int removeRoleMenus(String roleid) throws Exception;

	public List<SysRole> findAllNoTreeRoles();	
	
	public List<SysRole> findRolesForCurrentUser(String userid); //查询当前登录人的所有角色
	
}
