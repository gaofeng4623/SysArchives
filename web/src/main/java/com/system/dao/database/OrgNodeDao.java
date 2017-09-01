package com.system.dao.database;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.system.base.pojo.OrgNode;

public interface OrgNodeDao {
	
	public List<OrgNode> getDeptNode();
	public List<OrgNode> getDeptNodeByRoleName(@Param("roleId") String roleId); //根据角色获得对应的部门
	public List<OrgNode> getEmpNode();
	public List<OrgNode> getEmpNodeByRoleName(@Param("roleId") String roleId); //根据角色获得对应的人员
	public List<OrgNode> getRole();
	public List<OrgNode> getWorkflowrolemembers();
	
}
