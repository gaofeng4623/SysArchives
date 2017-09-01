package com.system.base.pojo;

import java.util.List;

public class Roledefine {
	private String guid;

	private String groupid;

	private String rolename;
	
	private String groupName;
	
	private Integer tabIndex;

	private List<Roledefine> children;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid == null ? null : groupid.trim();
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename == null ? null : rolename.trim();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public List<Roledefine> getChildren() {
		return children;
	}

	public void setChildren(List<Roledefine> children) {
		this.children = children;
	}

}