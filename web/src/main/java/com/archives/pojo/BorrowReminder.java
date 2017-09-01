package com.archives.pojo;

import java.util.Date;

/**
 * 催还通知表
 * @author zfn
 *
 */
public class BorrowReminder {

	private Integer id;
	
	private String borrowId;//借阅单主表
	
	private String empId;//借阅人id
	
	private String sendEmpId;//发送人id
	
	private String sendEmpName;//发送人姓名
	
	private Date sendDate;//发送时间
	
	private String expireDate;//预计归还时间
	
	private String depName;//借阅部门
	
    private String employeeName;
    
    private String status;//0已借阅1已归还
    
    private String formNo;//借阅单号

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getSendEmpId() {
		return sendEmpId;
	}

	public void setSendEmpId(String sendEmpId) {
		this.sendEmpId = sendEmpId;
	}

	public String getSendEmpName() {
		return sendEmpName;
	}

	public void setSendEmpName(String sendEmpName) {
		this.sendEmpName = sendEmpName;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFormNo() {
		return formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}
	
	
}
