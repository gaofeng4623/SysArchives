package com.archives.pojo;

import java.util.Date;

public class DocHistory {
    private Integer guid;

    private Integer infoid;

    private String event;

    private String location;

    private String remark;

    private Date changedate;

    private String changeman;

    public Integer getGuid() {
        return guid;
    }

    public void setGuid(Integer guid) {
        this.guid = guid;
    }

    public Integer getInfoid() {
        return infoid;
    }

    public void setInfoid(Integer infoid) {
        this.infoid = infoid;
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

    public Date getChangedate() {
        return changedate;
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }

    public String getChangeman() {
        return changeman;
    }

    public void setChangeman(String changeman) {
        this.changeman = changeman == null ? null : changeman.trim();
    }
}