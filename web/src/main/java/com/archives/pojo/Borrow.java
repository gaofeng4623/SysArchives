package com.archives.pojo;

/**
 * 借阅主表
 * 
 * @author zfn
 * 
 */
public class Borrow {
	private Integer borrowId;

	private Integer parentId;

	private String formNo;// 借阅单号

	private String userId;// 借阅人id

	private String borrowTime;// 借阅时间

	private String mobile;// 联系方式

	private String expireDate;// 预计归还时间

	private String returnTime;// 归还时间

	private String returnPerson;// 归还人

	private String status;// 档案的交付状态：0待交付，1已交付，-1部分交付，2已归还, -2 部分归还

	private String borrowCase;// 借阅用途

	private String caseDetail;// 用途说明

	private String borrowType; // 借阅方式

	private String channel; // 借阅渠道，0为转借阅，1为正常借阅

	private String depName;// 借阅部门

	private String employeeName;// 借阅人姓名

	private String itemGuids;
	private String workflow;
	private String borrowsstatus;
	private int active;
	private String selectPersons;
	private String workflowStatus;
	private String reborrow;
	private int borrowDays;// 借阅天数

	private int taked; // 是否提交，0为未提交，1为提交
	private String departmentName;
	private String beginTime;
	private String endTime;

	public String getSelectPersons() {
		return selectPersons;
	}

	public void setSelectPersons(String selectPersons) {
		this.selectPersons = selectPersons;
	}

	private String userName;


	public String getItemGuids() {
		return itemGuids;
	}

	public void setItemGuids(String itemGuids) {
		this.itemGuids = itemGuids;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public String getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(String borrowTime) {
		this.borrowTime = borrowTime;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getReturnPerson() {
		return returnPerson;
	}

	public void setReturnPerson(String returnPerson) {
		this.returnPerson = returnPerson;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBorrowCase() {
		return borrowCase;
	}

	public void setBorrowCase(String borrowCase) {
		this.borrowCase = borrowCase;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getFormNo() {
		return formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCaseDetail() {
		return caseDetail;
	}

	public void setCaseDetail(String caseDetail) {
		this.caseDetail = caseDetail;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public String getBorrowsstatus() {
		return borrowsstatus;
	}

	public void setBorrowsstatus(String borrowsstatus) {
		this.borrowsstatus = borrowsstatus;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(String borrowType) {
		this.borrowType = borrowType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getBorrowDays() {
		return borrowDays;
	}

	public void setBorrowDays(int borrowDays) {
		this.borrowDays = borrowDays;
	}

	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public int getTaked() {
		return taked;
	}

	public void setTaked(int taked) {
		this.taked = taked;
	}

	public String getReborrow() {
		return reborrow;
	}

	public void setReborrow(String reborrow) {
		this.reborrow = reborrow;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
