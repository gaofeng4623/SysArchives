package com.archives.pojo;

public class Status {
	
	private String locationId;//位置id
	private String locationType;
	private String status;//状态 
	private String schedule;//进度
	private String count;//盘点数/总数  78/189
	private String inventoryPlanId;//盘点计划id
	private String inventoryType; //盘点类型
	private String right;//盘点数和总数是否一致
	private String warningMessage;
	private String cellHtml;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getInventoryPlanId() {
		return inventoryPlanId;
	}
	public void setInventoryPlanId(String inventoryPlanId) {
		this.inventoryPlanId = inventoryPlanId;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getInventoryType() {
		return inventoryType;
	}
	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}
	public String getWarningMessage() {
		return warningMessage;
	}
	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}
	public String getCellHtml() {
		return cellHtml;
	}
	public void setCellHtml(String cellHtml) {
		this.cellHtml = cellHtml;
	}
	
}
