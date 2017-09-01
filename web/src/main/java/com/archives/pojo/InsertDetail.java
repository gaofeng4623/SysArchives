package com.archives.pojo;

public class InsertDetail {
    private Integer guid;

    private String formid;

    private String infoid;
    
    private String archivesno;
    
    private String putonrecorddate;
    
    private String undertakeper;
    
    private String parties;
    
    private String storagelife;
    
    private String location;
    
    private String caseno;
    
    private String flowid;
    
    private String casename;
    
    private String undertakedep;
    
    private String fjStatus;
    
    private Integer arcguid;
    private Integer itemGuid;
    public Integer getGuid() {
        return guid;
    }

    public void setGuid(Integer guid) {
        this.guid = guid;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid == null ? null : formid.trim();
    }

    public String getInfoid() {
        return infoid;
    }

    public void setInfoid(String infoid) {
        this.infoid = infoid == null ? null : infoid.trim();
    }

	public String getArchivesno() {
		return archivesno;
	}

	public void setArchivesno(String archivesno) {
		this.archivesno = archivesno;
	}

	public String getPutonrecorddate() {
		return putonrecorddate;
	}

	public void setPutonrecorddate(String putonrecorddate) {
		this.putonrecorddate = putonrecorddate;
	}

	public String getUndertakeper() {
		return undertakeper;
	}

	public void setUndertakeper(String undertakeper) {
		this.undertakeper = undertakeper;
	}

	public String getParties() {
		return parties;
	}

	public void setParties(String parties) {
		this.parties = parties;
	}

	public String getStoragelife() {
		return storagelife;
	}

	public void setStoragelife(String storagelife) {
		this.storagelife = storagelife;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCaseno() {
		return caseno;
	}

	public void setCaseno(String caseno) {
		this.caseno = caseno;
	}

	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	public String getCasename() {
		return casename;
	}

	public void setCasename(String casename) {
		this.casename = casename;
	}

	public String getUndertakedep() {
		return undertakedep;
	}

	public void setUndertakedep(String undertakedep) {
		this.undertakedep = undertakedep;
	}

	public String getFjStatus() {
		return fjStatus;
	}

	public void setFjStatus(String fjStatus) {
		this.fjStatus = fjStatus;
	}

	public Integer getArcguid() {
		return arcguid;
	}

	public void setArcguid(Integer arcguid) {
		this.arcguid = arcguid;
	}

	public Integer getItemGuid() {
		return itemGuid;
	}

	public void setItemGuid(Integer itemGuid) {
		this.itemGuid = itemGuid;
	}
	
}