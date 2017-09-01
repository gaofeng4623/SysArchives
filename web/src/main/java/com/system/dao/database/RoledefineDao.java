package com.system.dao.database;

import java.util.List;

import com.system.base.pojo.Roledefine;

public interface RoledefineDao {

	int deleteByPrimaryKey(String guid);

	int save(Roledefine record);

	Roledefine selectByPrimaryKey(String guid);

	int updateByPrimaryKeySelective(Roledefine record);

	int updateByPrimaryKey(Roledefine record);

	List<Roledefine> selectAllRoledefine();

	public Roledefine selectByName(String roleName);

	List<Roledefine> selectByGroupId(String groupId);

	List<Roledefine> findRoleNameById(String empId);

}