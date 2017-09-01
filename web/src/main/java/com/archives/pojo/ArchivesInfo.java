package com.archives.pojo;

import java.math.BigDecimal;


public class ArchivesInfo {
	private Integer guid;
	
	private Integer itemId;
	
	private Integer itemGuid; //分卷表主键
	
	private String markType;
	
	private String gdbz; //归档标志，新增字段
	
	private String azbm; //案字编码，用于跟东软VIEW_CASEWORD视图匹配
	
	private String flowid;

	private String caseno;

	private String archivesno;

	private String year;

	private String caseproerty;

	private String caseword;

	private String courtshortname;

	private String number;

	private String casecategory;

	private String judgeprocedure;

	private String casename;

	private String shortdetailscase;

	private String parties;

	private String putonrecorddate;

	private String putonrecorddep;

	private String approveper;

	private String undertakedep;

	private String undertakeper;

	private String courtclerk;

	private String chiefjudge;

	private String collegiatebench;

	private Integer formaldocument;

	private Integer counterpart;

	private Integer otherdoucment;

	private Integer formaldocpagenum;

	private Integer counterpartpagenum;

	private String mergecase;

	private String settlemanner;

	private String settledate;

	private String settlereason;

	private String originalcourt;

	private String originalcaseno;

	private String firstresult;

	private String secondresult;

	private String lastresult;

	private String fulldocno;

	private String catalogno;

	private String evidencebag;

	private String degreeofsecrets;

	private String storagelife;

	private String applyper;

	private String receiveper;

	private String placeonper;

	private String placeondate;

	private String registerper;

	private String registertime;

	private String remark;

	private String status;

	private String statusName;
	// 档案位置信息
	private String location;
	// 档案分卷状态
	private String fjStatus;

	private String handleStatus;

	private String uptime;

	private String itmStatus;
	private String content;
	private String caseContent;
    
	private Integer number1;
	private Integer number2;
	private String itemstatus;
	private String placecount;
	private String guidCount;
	private String caseProertyCount;
	private String mark;
	///查询条件
	private String registertime2;
	private String caseproerty2;
	private String putonrecorddate2;
	private String settledate2;
	private String placeondate2;
	private String archivesno2;
	private String locationPath;
	private int putOnRecordDepCount;
	private int volumeCount;
	private int archiveVolumeCount;
	private Double archiveCount;
	private Integer updated;
	private String serialNoPath;
	private Integer reborrowlock;
	private String synStatus;//同步状态 0结案 1归档'
	private String syndate;//同步时间
	public String getSerialNoPath() {
		return serialNoPath;
	}

	public void setSerialNoPath(String serialNoPath) {
		this.serialNoPath = serialNoPath;
	}

	public Integer getUpdated() {
		return updated;
	}

	public void setUpdated(Integer updated) {
		this.updated = updated;
	}

	public Integer getGuid() {
		return guid;
	}

	public void setGuid(Integer guid) {
		this.guid = guid;
	}

	public String getMarkType() {
		return markType;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}


	public String getAzbm() {
		return azbm;
	}

	public void setAzbm(String azbm) {
		this.azbm = azbm;
	}

	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	public String getCaseno() {
		return caseno;
	}

	public void setCaseno(String caseno) {
		this.caseno = caseno;
	}

	public String getArchivesno() {
		return archivesno;
	}

	public void setArchivesno(String archivesno) {
		this.archivesno = archivesno;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCaseproerty() {
		return caseproerty;
	}

	public void setCaseproerty(String caseproerty) {
		this.caseproerty = caseproerty;
	}

	public String getCaseword() {
		return caseword;
	}

	public void setCaseword(String caseword) {
		this.caseword = caseword;
	}

	public String getCourtshortname() {
		return courtshortname;
	}

	public void setCourtshortname(String courtshortname) {
		this.courtshortname = courtshortname;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCasecategory() {
		return casecategory;
	}

	public void setCasecategory(String casecategory) {
		this.casecategory = casecategory;
	}

	public String getJudgeprocedure() {
		return judgeprocedure;
	}

	public void setJudgeprocedure(String judgeprocedure) {
		this.judgeprocedure = judgeprocedure;
	}

	public String getCasename() {
		return casename;
	}

	public void setCasename(String casename) {
		this.casename = casename;
	}

	public String getShortdetailscase() {
		return shortdetailscase;
	}

	public void setShortdetailscase(String shortdetailscase) {
		this.shortdetailscase = shortdetailscase;
	}

	public String getParties() {
		return parties;
	}

	public void setParties(String parties) {
		this.parties = parties;
	}

	public String getPutonrecorddate() {
		return putonrecorddate;
	}

	public void setPutonrecorddate(String putonrecorddate) {
		this.putonrecorddate = putonrecorddate;
	}

	public String getPutonrecorddep() {
		return putonrecorddep;
	}

	public void setPutonrecorddep(String putonrecorddep) {
		this.putonrecorddep = putonrecorddep;
	}

	public String getApproveper() {
		return approveper;
	}

	public void setApproveper(String approveper) {
		this.approveper = approveper;
	}

	public String getUndertakedep() {
		return undertakedep;
	}

	public void setUndertakedep(String undertakedep) {
		this.undertakedep = undertakedep;
	}

	public String getUndertakeper() {
		return undertakeper;
	}

	public void setUndertakeper(String undertakeper) {
		this.undertakeper = undertakeper;
	}

	public String getCourtclerk() {
		return courtclerk;
	}

	public void setCourtclerk(String courtclerk) {
		this.courtclerk = courtclerk;
	}

	public String getChiefjudge() {
		return chiefjudge;
	}

	public void setChiefjudge(String chiefjudge) {
		this.chiefjudge = chiefjudge;
	}

	public String getCollegiatebench() {
		return collegiatebench;
	}

	public void setCollegiatebench(String collegiatebench) {
		this.collegiatebench = collegiatebench;
	}

	public Integer getFormaldocument() {
		return formaldocument;
	}

	public void setFormaldocument(Integer formaldocument) {
		this.formaldocument = formaldocument;
	}

	public Integer getCounterpart() {
		return counterpart;
	}

	public void setCounterpart(Integer counterpart) {
		this.counterpart = counterpart;
	}

	public Integer getOtherdoucment() {
		return otherdoucment;
	}

	public void setOtherdoucment(Integer otherdoucment) {
		this.otherdoucment = otherdoucment;
	}

	public Integer getFormaldocpagenum() {
		return formaldocpagenum;
	}

	public void setFormaldocpagenum(Integer formaldocpagenum) {
		this.formaldocpagenum = formaldocpagenum;
	}

	public Integer getCounterpartpagenum() {
		return counterpartpagenum;
	}

	public void setCounterpartpagenum(Integer counterpartpagenum) {
		this.counterpartpagenum = counterpartpagenum;
	}

	public String getMergecase() {
		return mergecase;
	}

	public void setMergecase(String mergecase) {
		this.mergecase = mergecase;
	}

	public String getSettlemanner() {
		return settlemanner;
	}

	public void setSettlemanner(String settlemanner) {
		this.settlemanner = settlemanner;
	}

	public String getSettledate() {
		return settledate;
	}

	public void setSettledate(String settledate) {
		this.settledate = settledate;
	}

	public String getSettlereason() {
		return settlereason;
	}

	public void setSettlereason(String settlereason) {
		this.settlereason = settlereason;
	}

	public String getOriginalcourt() {
		return originalcourt;
	}

	public void setOriginalcourt(String originalcourt) {
		this.originalcourt = originalcourt;
	}

	public String getOriginalcaseno() {
		return originalcaseno;
	}

	public void setOriginalcaseno(String originalcaseno) {
		this.originalcaseno = originalcaseno;
	}

	public String getFirstresult() {
		return firstresult;
	}

	public void setFirstresult(String firstresult) {
		this.firstresult = firstresult;
	}

	public String getSecondresult() {
		return secondresult;
	}

	public void setSecondresult(String secondresult) {
		this.secondresult = secondresult;
	}

	public String getLastresult() {
		return lastresult;
	}

	public void setLastresult(String lastresult) {
		this.lastresult = lastresult;
	}

	public String getFulldocno() {
		return fulldocno;
	}

	public void setFulldocno(String fulldocno) {
		this.fulldocno = fulldocno;
	}

	public String getCatalogno() {
		return catalogno;
	}

	public void setCatalogno(String catalogno) {
		this.catalogno = catalogno;
	}

	public String getEvidencebag() {
		return evidencebag;
	}

	public void setEvidencebag(String evidencebag) {
		this.evidencebag = evidencebag;
	}

	public String getDegreeofsecrets() {
		return degreeofsecrets;
	}

	public void setDegreeofsecrets(String degreeofsecrets) {
		this.degreeofsecrets = degreeofsecrets;
	}

	public String getStoragelife() {
		return storagelife;
	}

	public void setStoragelife(String storagelife) {
		this.storagelife = storagelife;
	}

	public String getApplyper() {
		return applyper;
	}

	public void setApplyper(String applyper) {
		this.applyper = applyper;
	}

	public String getReceiveper() {
		return receiveper;
	}

	public void setReceiveper(String receiveper) {
		this.receiveper = receiveper;
	}

	public String getPlaceonper() {
		return placeonper;
	}

	public void setPlaceonper(String placeonper) {
		this.placeonper = placeonper;
	}

	public String getPlaceondate() {
		return placeondate;
	}

	public void setPlaceondate(String placeondate) {
		this.placeondate = placeondate;
	}

	public String getRegisterper() {
		return registerper;
	}

	public void setRegisterper(String registerper) {
		this.registerper = registerper;
	}

	public String getRegistertime() {
		return registertime;
	}

	public void setRegistertime(String registertime) {
		this.registertime = registertime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFjStatus() {
		return fjStatus;
	}

	public void setFjStatus(String fjStatus) {
		this.fjStatus = fjStatus;
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public String getItmStatus() {
		return itmStatus;
	}

	public void setItmStatus(String itmStatus) {
		this.itmStatus = itmStatus;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCaseContent() {
		return caseContent;
	}

	public void setCaseContent(String caseContent) {
		this.caseContent = caseContent;
	}



	public Integer getNumber1() {
		return number1;
	}

	public void setNumber1(Integer number1) {
		this.number1 = number1;
	}

	public Integer getNumber2() {
		return number2;
	}

	public void setNumber2(Integer number2) {
		this.number2 = number2;
	}

	public String getItemstatus() {
		return itemstatus;
	}

	public void setItemstatus(String itemstatus) {
		this.itemstatus = itemstatus;
	}

	public String getGdbz() {
		return gdbz;
	}

	public void setGdbz(String gdbz) {
		this.gdbz = gdbz;
	}

	public String getPlacecount() {
		return placecount;
	}

	public void setPlacecount(String placecount) {
		this.placecount = placecount;
	}

	public String getGuidCount() {
		return guidCount;
	}

	public void setGuidCount(String guidCount) {
		this.guidCount = guidCount;
	}

	public String getCaseProertyCount() {
		return caseProertyCount;
	}

	public void setCaseProertyCount(String caseProertyCount) {
		this.caseProertyCount = caseProertyCount;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getRegistertime2() {
		return registertime2;
	}

	public void setRegistertime2(String registertime2) {
		this.registertime2 = registertime2;
	}

	public String getCaseproerty2() {
		return caseproerty2;
	}

	public void setCaseproerty2(String caseproerty2) {
		this.caseproerty2 = caseproerty2;
	}

	public String getPutonrecorddate2() {
		return putonrecorddate2;
	}

	public void setPutonrecorddate2(String putonrecorddate2) {
		this.putonrecorddate2 = putonrecorddate2;
	}

	

	public String getSettledate2() {
		return settledate2;
	}

	public void setSettledate2(String settledate2) {
		this.settledate2 = settledate2;
	}

	
	public String getPlaceondate2() {
		return placeondate2;
	}

	public void setPlaceondate2(String placeondate2) {
		this.placeondate2 = placeondate2;
	}

	public String getArchivesno2() {
		return archivesno2;
	}

	public void setArchivesno2(String archivesno2) {
		this.archivesno2 = archivesno2;
	}

	public String getLocationPath() {
		return locationPath;
	}

	public void setLocationPath(String locationPath) {
		this.locationPath = locationPath;
	}

	public int getPutOnRecordDepCount() {
		return putOnRecordDepCount;
	}

	public void setPutOnRecordDepCount(int putOnRecordDepCount) {
		this.putOnRecordDepCount = putOnRecordDepCount;
	}

	public int getVolumeCount() {
		return volumeCount;
	}

	public void setVolumeCount(int volumeCount) {
		this.volumeCount = volumeCount;
	}

	public int getArchiveVolumeCount() {
		return archiveVolumeCount;
	}

	public void setArchiveVolumeCount(int archiveVolumeCount) {
		this.archiveVolumeCount = archiveVolumeCount;
	}

	public Double getArchiveCount() {
		return archiveCount;
	}

	public void setArchiveCount(Double archiveCount) {
		this.archiveCount = archiveCount;
	}

	public Integer getReborrowlock() {
		return reborrowlock;
	}

	public void setReborrowlock(Integer reborrowlock) {
		this.reborrowlock = reborrowlock;
	}

	

	public String getSynStatus() {
		return synStatus;
	}

	public void setSynStatus(String synStatus) {
		this.synStatus = synStatus;
	}

	public String getSyndate() {
		return syndate;
	}

	public void setSyndate(String syndate) {
		this.syndate = syndate;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getItemGuid() {
		return itemGuid;
	}

	public void setItemGuid(Integer itemGuid) {
		this.itemGuid = itemGuid;
	}

	

}