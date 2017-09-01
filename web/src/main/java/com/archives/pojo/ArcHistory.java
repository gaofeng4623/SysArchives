package com.archives.pojo;

import java.util.Date;

public class ArcHistory {
    private Integer guid;

    private Integer itemGuid;

    private String event;

    private String location;

    private String remark;

    private Date changeDate;

    private String changeMan;
    
    private String archivesNo;
    

    public String getArchivesNo() {
		return archivesNo;
	}

	public void setArchivesNo(String archivesNo) {
		this.archivesNo = archivesNo;
	}

	public Integer getGuid() {
        return guid;
    }

    public void setGuid(Integer guid) {
        this.guid = guid;
    }
    

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event == null ? null : event.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
	public Integer getItemGuid() {
		return itemGuid;
	}

	public void setItemGuid(Integer itemGuid) {
		this.itemGuid = itemGuid;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public String getChangeMan() {
		return changeMan;
	}

	public void setChangeMan(String changeMan) {
		this.changeMan = changeMan;
	}
	
	

}