package com.archives.pojo;


public class DoorPass {
    private Integer guid;

    private String rfid;

    private String locatdoorNo;

    private String passDate;

    private String locatdoorNocf;

    private String passDatecf;
    private String infoGuid;
    private String caseNo;
    private String note;
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

   

    public String getLocatdoorNo() {
		return locatdoorNo;
	}

	public void setLocatdoorNo(String locatdoorNo) {
		this.locatdoorNo = locatdoorNo;
	}

	public String getPassDate() {
		return passDate;
	}

	public void setPassDate(String passDate) {
		this.passDate = passDate;
	}

	public String getLocatdoorNocf() {
		return locatdoorNocf;
	}

	public void setLocatdoorNocf(String locatdoorNocf) {
		this.locatdoorNocf = locatdoorNocf;
	}

	public String getPassDatecf() {
		return passDatecf;
	}

	public void setPassDatecf(String passDatecf) {
		this.passDatecf = passDatecf;
	}

	public String getInfoGuid() {
		return infoGuid;
	}

	public void setInfoGuid(String infoGuid) {
		this.infoGuid = infoGuid;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
    


}