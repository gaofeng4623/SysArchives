package com.archives.pojo;

import java.util.Date;

public class Docborrowdetail {
    private Integer guid;

    private String borrowid;

    private String docId;

    private String status;

    private Date returntime;

    private String returnperson;

    private Integer reborrow;

    private String title;
    private String documentNo;
    private String docketNo;
    private String locationName;
    private String responsiblePerson;
    private String borrowCount;
    private String guidCount;
    public Integer getGuid() {
        return guid;
    }

    public void setGuid(Integer guid) {
        this.guid = guid;
    }

    public String getBorrowid() {
        return borrowid;
    }

    public void setBorrowid(String borrowid) {
        this.borrowid = borrowid == null ? null : borrowid.trim();
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getReturntime() {
        return returntime;
    }

    public void setReturntime(Date returntime) {
        this.returntime = returntime;
    }

    public String getReturnperson() {
        return returnperson;
    }

    public void setReturnperson(String returnperson) {
        this.returnperson = returnperson == null ? null : returnperson.trim();
    }

    public Integer getReborrow() {
        return reborrow;
    }

    public void setReborrow(Integer reborrow) {
        this.reborrow = reborrow;
    }

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocketNo() {
		return docketNo;
	}

	public void setDocketNo(String docketNo) {
		this.docketNo = docketNo;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getBorrowCount() {
		return borrowCount;
	}

	public void setBorrowCount(String borrowCount) {
		this.borrowCount = borrowCount;
	}

	public String getGuidCount() {
		return guidCount;
	}

	public void setGuidCount(String guidCount) {
		this.guidCount = guidCount;
	}
    
}