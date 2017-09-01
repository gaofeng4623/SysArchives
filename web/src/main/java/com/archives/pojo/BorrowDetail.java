package com.archives.pojo;

import java.util.Date;

/**
 * 借阅明细
 * @author zfn
 *
 */
public class BorrowDetail {
	
	private Integer guid;
	
	private String borrowId;//借阅主表id
	
	private String itemGuid;//档案分表主键
	
	private String status;//借阅归还状态
	
	private Borrow borrow;
    private String caseNo;//案号
    private String caseWord;//案字
    private String parties;//当事人
    private String archivesNo;//档案号
    private String location;//位置信息
    private String putOnRecordDate;//
    
    private String formalDocument; //正卷册数
    private String counterpart; //副卷册数
    
    private String storageLife; //保存期限
    private String undertakePer; //承办人
    
    private String caseName;//案名
    
    private String formNo;// 借阅单号

	private String borrowTime;// 借阅时间

	private String mobile;// 联系方式

	private String expireDate;// 预计归还时间

	private String borrowCase;// 借阅用途

	private String caseDetail;// 用途说明

	private String depName;// 借阅部门

	private String employeeName;// 借阅人姓名
	
	private String userName;
	
	private String channel;//0转借阅1正常
	
	private String handleStatus;
	
	private String reborrowLock; 
	
	private String rfid;//标签号
	
	private String borrowdetailId;//借阅明细ID
	
	private String returnPerson;//归还人
	
	private Date returnTime;//归还时间
	
	private String zjstatus;//在架状态
	
	private String detailStatus;//借阅单详情状态
	
	private String detailTime;
	private String infguid;
	private String borrborrowId;
	private String tailguid;
	private String flowId;//流水号
	private String undertakeDep;
	private String locationPath;
	private String temsGuid;
	private String person;
	private String reborrow;
	private String guidCount;
	private String borrowCount;
	private String remark;
	public Integer getGuid() {
		return guid;
	}

	public void setGuid(Integer guid) {
		this.guid = guid;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public String getItemGuid() {
		return itemGuid;
	}

	public void setItemGuid(String itemGuid) {
		this.itemGuid = itemGuid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getCaseWord() {
		return caseWord;
	}

	public void setCaseWord(String caseWord) {
		this.caseWord = caseWord;
	}

	public String getParties() {
		return parties;
	}

	public void setParties(String parties) {
		this.parties = parties;
	}

	public String getFormalDocument() {
		return formalDocument;
	}

	public void setFormalDocument(String formalDocument) {
		this.formalDocument = formalDocument;
	}

	public String getCounterpart() {
		return counterpart;
	}

	public void setCounterpart(String counterpart) {
		this.counterpart = counterpart;
	}

	public String getArchivesNo() {
		return archivesNo;
	}

	public void setArchivesNo(String archivesNo) {
		this.archivesNo = archivesNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public String getPutOnRecordDate() {
		return putOnRecordDate;
	}

	public void setPutOnRecordDate(String putOnRecordDate) {
		this.putOnRecordDate = putOnRecordDate;
	}

	public String getStorageLife() {
		return storageLife;
	}

	public void setStorageLife(String storageLife) {
		this.storageLife = storageLife;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getFormNo() {
		return formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	public String getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(String borrowTime) {
		this.borrowTime = borrowTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getBorrowCase() {
		return borrowCase;
	}

	public void setBorrowCase(String borrowCase) {
		this.borrowCase = borrowCase;
	}

	public String getCaseDetail() {
		return caseDetail;
	}

	public void setCaseDetail(String caseDetail) {
		this.caseDetail = caseDetail;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getUndertakePer() {
		return undertakePer;
	}

	public void setUndertakePer(String undertakePer) {
		this.undertakePer = undertakePer;
	}
	public String getBorrowdetailId() {
		return borrowdetailId;
	}

	public void setBorrowdetailId(String borrowdetailId) {
		this.borrowdetailId = borrowdetailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReturnPerson() {
		return returnPerson;
	}

	public void setReturnPerson(String returnPerson) {
		this.returnPerson = returnPerson;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getZjstatus() {
		return zjstatus;
	}

	public void setZjstatus(String zjstatus) {
		this.zjstatus = zjstatus;
	}

	public String getDetailStatus() {
		return detailStatus;
	}

	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}

	public String getInfguid() {
		return infguid;
	}

	public void setInfguid(String infguid) {
		this.infguid = infguid;
	}

	public String getBorrborrowId() {
		return borrborrowId;
	}

	public void setBorrborrowId(String borrborrowId) {
		this.borrborrowId = borrborrowId;
	}

	public String getTailguid() {
		return tailguid;
	}

	public void setTailguid(String tailguid) {
		this.tailguid = tailguid;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getUndertakeDep() {
		return undertakeDep;
	}

	public void setUndertakeDep(String undertakeDep) {
		this.undertakeDep = undertakeDep;
	}

	public String getLocationPath() {
		return locationPath;
	}

	public void setLocationPath(String locationPath) {
		this.locationPath = locationPath;
	}

	public String getTemsGuid() {
		return temsGuid;
	}

	public void setTemsGuid(String temsGuid) {
		this.temsGuid = temsGuid;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getReborrow() {
		return reborrow;
	}

	public void setReborrow(String reborrow) {
		this.reborrow = reborrow;
	}

	public String getReborrowLock() {
		return reborrowLock;
	}

	public void setReborrowLock(String reborrowLock) {
		this.reborrowLock = reborrowLock;
	}

	public String getGuidCount() {
		return guidCount;
	}

	public void setGuidCount(String guidCount) {
		this.guidCount = guidCount;
	}

	public String getBorrowCount() {
		return borrowCount;
	}

	public void setBorrowCount(String borrowCount) {
		this.borrowCount = borrowCount;
	}

	public String getDetailTime() {
		return detailTime;
	}

	public void setDetailTime(String detailTime) {
		this.detailTime = detailTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
   


}
