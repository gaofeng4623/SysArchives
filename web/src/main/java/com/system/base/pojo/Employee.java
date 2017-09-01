package com.system.base.pojo;

import java.util.Date;

import org.activiti.engine.identity.User;
import org.springframework.format.annotation.DateTimeFormat;

public class Employee implements User {
	private String employeeId;
	private String employeeName;
	private String sex;
	private String mobile;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	private byte[] photo;
	private String departmentId;
	private String departmentName;
	private String employeeLoginName;
	private String password;
	private String remoteIp;
	private String userEmail;
	private String guid;
	private String post;
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getEmployeeLoginName() {
		return employeeLoginName;
	}
	public void setEmployeeLoginName(String employeeLoginName) {
		this.employeeLoginName = employeeLoginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	
	
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getId() {
		return this.employeeLoginName;
	}
	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isPictureSet() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setEmail(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFirstName(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setId(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastName(String arg0) {
		// TODO Auto-generated method stub
		
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}


}
