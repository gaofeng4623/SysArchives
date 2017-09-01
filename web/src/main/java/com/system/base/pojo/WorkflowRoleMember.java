package com.system.base.pojo;
/**
 * 
 * @author zfn
 *工作流角色人员关联表
 */
public class WorkflowRoleMember {
	
	private Integer guid;
	
	private String userId;//用户id
	
	private String roleId;//角色id

	public Integer getGuid() {
		return guid;
	}

	public void setGuid(Integer guid) {
		this.guid = guid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	

}
