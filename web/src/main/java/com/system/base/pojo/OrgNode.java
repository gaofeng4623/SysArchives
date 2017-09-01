package com.system.base.pojo;

import java.util.ArrayList;
import java.util.List;

public class OrgNode {
	private String suporId;
	private String guid;
	private String nodeName;
	private String loginName;
	private List<OrgNode> children;
	
	public OrgNode() {
		this.children = new ArrayList();
	}
	
	public String getSuporId() {
		return suporId;
	}

	public void setSuporId(String suporId) {
		this.suporId = suporId;
	}


	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public List<OrgNode> getChildren() {
		return children;
	}

	public void setChildren(List<OrgNode> children) {
		this.children = children;
	}
	

}
