package com.system.base.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程角色定义
 * @author zfn
 *
 */
public class WorkflowRoleDefine {
	private String guid;
	
	private String groupId;//组id
	
	private String roleName;//角色名称
	
	private List<WorkflowRoleDefine> children;
	
	public WorkflowRoleDefine(){
		this.children = new ArrayList<>();
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<WorkflowRoleDefine> getChildren() {
		return children;
	}

	public void setChildren(List<WorkflowRoleDefine> children) {
		this.children = children;
	}
	


}
