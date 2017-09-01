package com.archives.pojo;

public class DoorWarning {
    private Integer guid;

    private String rfid;

    private String doorid;

    private String warndate;

    private String warnreason;

    private String handler;

    private String handlerresult;

    private String handlerdate;

    private String handlerurl;
    private String caseNo;
    private String infoGuid;
    private String mangaerDoorId;
    private String note;
    private String itemGuid;
    public Integer getGuid() {
        return guid;
    }

    public void setGuid(Integer guid) {
        this.guid = guid;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid == null ? null : rfid.trim();
    }

    public String getDoorid() {
        return doorid;
    }

    public void setDoorid(String doorid) {
        this.doorid = doorid == null ? null : doorid.trim();
    }

    public String getWarndate() {
        return warndate;
    }

    public void setWarndate(String warndate) {
        this.warndate = warndate;
    }

    public String getWarnreason() {
        return warnreason;
    }

    public void setWarnreason(String warnreason) {
        this.warnreason = warnreason == null ? null : warnreason.trim();
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler == null ? null : handler.trim();
    }

    public String getHandlerresult() {
        return handlerresult;
    }

    public void setHandlerresult(String handlerresult) {
        this.handlerresult = handlerresult == null ? null : handlerresult.trim();
    }

    public String getHandlerdate() {
        return handlerdate;
    }

    public void setHandlerdate(String handlerdate) {
        this.handlerdate = handlerdate;
    }

    public String getHandlerurl() {
        return handlerurl;
    }

    public void setHandlerurl(String handlerurl) {
        this.handlerurl = handlerurl == null ? null : handlerurl.trim();
    }

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getInfoGuid() {
		return infoGuid;
	}

	public void setInfoGuid(String infoGuid) {
		this.infoGuid = infoGuid;
	}

	public String getMangaerDoorId() {
		return mangaerDoorId;
	}

	public void setMangaerDoorId(String mangaerDoorId) {
		this.mangaerDoorId = mangaerDoorId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getItemGuid() {
		return itemGuid;
	}

	public void setItemGuid(String itemGuid) {
		this.itemGuid = itemGuid;
	}
    
	
}