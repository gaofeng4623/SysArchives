package com.system.base.pojo;

import java.util.List;


public class Department {
    private String departmentid;

    private String superiorid;

    private String departmentname;
    
    // @Transient
    private String superDeptname;
    
    private String tabindex;
    // @Transient
    private List<Department> children;
    private String id;
	private String text;

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname == null ? null : departmentname.trim();
    }


	public List<Department> getChildren() {
		return children;
	}

	public void setChildren(List<Department> children) {
		this.children = children;
	}

	public String getSuperDeptname() {
		return superDeptname;
	}

	public void setSuperDeptname(String superDeptname) {
		this.superDeptname = superDeptname;
	}

	public String getTabindex() {
		return tabindex;
	}

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getSuperiorid() {
		return superiorid;
	}

	public void setSuperiorid(String superiorid) {
		this.superiorid = superiorid;
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