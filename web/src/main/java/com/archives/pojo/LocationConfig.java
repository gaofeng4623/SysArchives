package com.archives.pojo;

import java.util.List;

public class LocationConfig {
    private Integer guid;

    private String companyid;

    private Integer typeguid;

    private Integer parentid;
    
    private String locationName;
    
    private List<LocationConfig> children;
    
    // private String state = "closed";

    public Integer getGuid() {
        return guid;
    }

    public void setGuid(Integer guid) {
        this.guid = guid;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid == null ? null : companyid.trim();
    }

    public Integer getTypeguid() {
        return typeguid;
    }

    public void setTypeguid(Integer typeguid) {
        this.typeguid = typeguid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

	public List<LocationConfig> getChildren() {
		return children;
	}

	public void setChildren(List<LocationConfig> children) {
		this.children = children;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}