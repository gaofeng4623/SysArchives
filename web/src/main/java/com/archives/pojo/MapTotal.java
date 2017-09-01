package com.archives.pojo;

import java.util.List;

public class MapTotal {
    private String guid;

    private String content;

    private String remark1;

    private String remark2;
    
    private String mark;
	private List<MapTotal> children;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid == null ? null : guid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }
	public List<MapTotal> getChildren() {
		return children;
	}

	public void setChildren(List<MapTotal> children) {
		this.children = children;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
}