package com.system.base.pojo;

import java.util.Date;

public class SysLog {
    private Integer guid;

    private String event;

    private Date updateTime;

    private String person;

    private String ip;

    private String operationType;

    private Date stime;

    private Date etime;
    
    private String stimeStr;

    private String etimeStr;

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

	public Date getUpdateTime() {
		return updateTime;
	}

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person == null ? null : person.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }

	public Date getStime() {
		return stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
	}

	public Date getEtime() {
		return etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

	public String getStimeStr() {
		return stimeStr;
	}

	public void setStimeStr(String stimeStr) {
		this.stimeStr = stimeStr;
	}

	public String getEtimeStr() {
		return etimeStr;
	}

	public void setEtimeStr(String etimeStr) {
		this.etimeStr = etimeStr;
	}
	
}