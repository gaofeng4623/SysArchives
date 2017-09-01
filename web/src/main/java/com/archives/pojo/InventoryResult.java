package com.archives.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class InventoryResult {
	
	private Integer guid;

	private String rfid;

	private Integer inventoryPlanId;

	private Integer inventoryLoactionGuid;

	private Integer sysLocationGuid;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date inventoryDate;

	private String status;// -1：表示少数据，0-多数据，1-表示正常数据，3表示补录数据
	
	private Integer read; //0表示未读，1表示已读

	private String inventoryLocationPath;// 盘点位置
	private String sysLocationPath;// 系统位置
	private String itemId;// 分卷编码
	private Integer archiveId;// 案名
	private String caseName;// 案名
	private String caseNo; // 案号
	private String archivesNo; // 档案号
	private String year; // 年度
	private String caseWord; // 案字
	private String number; // 案件编号
	private String statusName;// 状态类别
	private String caseCategory;// 状态类别
	private String creater;// 盘点人，默认为创建盘点计划的人
	private String resultName;

	/****** 文书的属性 **********/
	private Integer boxId;
	private Integer boxNumber;
	private String safekeeping;
	private String mechanism;
	private String itemGuid;

	public Integer getBoxId() {
		return boxId;
	}

	public void setBoxId(Integer boxId) {
		this.boxId = boxId;
	}

	public Integer getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(Integer boxNumber) {
		this.boxNumber = boxNumber;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSafekeeping() {
		return safekeeping;
	}

	public void setSafekeeping(String safekeeping) {
		this.safekeeping = safekeeping;
	}

	public String getMechanism() {
		return mechanism;
	}

	public void setMechanism(String mechanism) {
		this.mechanism = mechanism;
	}

	/**
	 * @return the guid
	 */
	public Integer getGuid() {
		return guid;
	}

	/**
	 * @param guid
	 *            the guid to set
	 */
	public void setGuid(Integer guid) {
		this.guid = guid;
	}

	/**
	 * @return the rfid
	 */
	public String getRfid() {
		return rfid;
	}

	/**
	 * @param rfid
	 *            the rfid to set
	 */
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	/**
	 * @return the inventoryPlanId
	 */
	public Integer getInventoryPlanId() {
		return inventoryPlanId;
	}

	/**
	 * @param inventoryPlanId
	 *            the inventoryPlanId to set
	 */
	public void setInventoryPlanId(Integer inventoryPlanId) {
		this.inventoryPlanId = inventoryPlanId;
	}

	/**
	 * @return the inventoryLoactionGuid
	 */
	public Integer getInventoryLoactionGuid() {
		return inventoryLoactionGuid;
	}

	/**
	 * @param inventoryLoactionGuid
	 *            the inventoryLoactionGuid to set
	 */
	public void setInventoryLoactionGuid(Integer inventoryLoactionGuid) {
		this.inventoryLoactionGuid = inventoryLoactionGuid;
	}

	/**
	 * @return the sysLocationGuid
	 */
	public Integer getSysLocationGuid() {
		return sysLocationGuid;
	}

	/**
	 * @param sysLocationGuid
	 *            the sysLocationGuid to set
	 */
	public void setSysLocationGuid(Integer sysLocationGuid) {
		this.sysLocationGuid = sysLocationGuid;
	}

	/**
	 * @return the inventoryDate
	 */
	public Date getInventoryDate() {
		return inventoryDate;
	}

	/**
	 * @param inventoryDate
	 *            the inventoryDate to set
	 */
	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getInventoryLocationPath() {
		return inventoryLocationPath;
	}

	public void setInventoryLocationPath(String inventoryLocationPath) {
		this.inventoryLocationPath = inventoryLocationPath;
	}

	public String getSysLocationPath() {
		return sysLocationPath;
	}

	public void setSysLocationPath(String sysLocationPath) {
		this.sysLocationPath = sysLocationPath;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCaseCategory() {
		return caseCategory;
	}

	public void setCaseCategory(String caseCategory) {
		this.caseCategory = caseCategory;
	}

	public Integer getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(Integer archiveId) {
		this.archiveId = archiveId;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getArchivesNo() {
		return archivesNo;
	}

	public void setArchivesNo(String archivesNo) {
		this.archivesNo = archivesNo;
	}

	public String getItemGuid() {
		return itemGuid;
	}

	public void setItemGuid(String itemGuid) {
		this.itemGuid = itemGuid;
	}

	public String getCaseWord() {
		return caseWord;
	}

	public void setCaseWord(String caseWord) {
		this.caseWord = caseWord;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}
	
	

}