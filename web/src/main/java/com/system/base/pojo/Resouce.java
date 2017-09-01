package com.system.base.pojo;

import java.util.List;

public class Resouce {
	private String id;
	private String name;
	private String pid;
	private int type;

	private List<Resouce> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Resouce> getChildren() {
		return children;
	}

	public void setChildren(List<Resouce> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
