package com.archives.pojo;

import java.util.Date;

public class BoxStatus {
    private Integer guid;

    private Integer boxid;

    private String status;

    private String rfid;

    private String location;

    private Date uptime;
    
    public BoxStatus() {}
    
    public BoxStatus(Integer boxid, String status) {
    	this.boxid = boxid;
    	this.status = status;
    }

    public Integer getGuid() {
        return guid;
    }

    public void setGuid(Integer guid) {
        this.guid = guid;
    }


	public Integer getBoxid() {
		return boxid;
	}

	public void setBoxid(Integer boxid) {
		this.boxid = boxid;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid == null ? null : rfid.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }
}