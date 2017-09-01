package com.system.dao.database;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.system.base.pojo.Rolemembers;

/*角色映射接口*/
public interface RolemembersDao {
	
	int deleteByPrimaryKey(Integer guid);

	int save(Rolemembers record);

	int insertSelective(Rolemembers record);

	Rolemembers selectByPrimaryKey(Integer guid);

	int updateByPrimaryKeySelective(Rolemembers record);

	int updateByPrimaryKey(Rolemembers record);

	public List selectRoleId(String troleId);

	void deleteByEmpId(String employeeId);

	void deleteByEmpIdList(@Param("idList") List<String> idList);

	void deleteByRoleId(String RoleId);

}