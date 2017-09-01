package com.system.base.pojo;

import java.util.ArrayList;
import java.util.List;

public class SysRole {
	private String guid;
	private String groupId;
	private String roleName;
	private List<SysRole> children;
	private boolean checked;

	private String id;
	private String text;

	public SysRole() {
		this.children = new ArrayList();
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<SysRole> getChildren() {
		return children;
	}

	public void setChildren(List<SysRole> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
