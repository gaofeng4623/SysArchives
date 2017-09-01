package com.system.dao.database;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.system.base.pojo.SysRole;

public interface SysRoleDao {
	public List<SysRole> findAllRoles();
	
	public int insertRoleMenuForMysql(@Param("roleId")String roleId, @Param("data")String[] data);
	
	public int insertRoleMenuForOracle(@Param("roleId")String roleId, @Param("data")String[] data);
	
	public int removeRoleMenus(@Param("roleid")String roleid);
	
	public List<SysRole> findRolesForCurrentUser(@Param("userid") String userid);  //查询当前登录人的所有角色

}
