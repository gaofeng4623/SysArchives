package com.archives.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class InventoryPlan {
    private Integer guid;

    private String inventoryPlanName;

    private String inventoryType;
    
    private String inventoryTypeName;

    private String creater;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    
    private String beginTimeStr;// 绕过时间类型传到datetimebox中后 时间变成00:00:00的jQuery easyui bug

    private String employeeName; // createrName;//创建人
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    
    private String isEnd; // status;//盘点是否结束
    
    private Integer sysCount;
    
    private Integer isCircle;
    
    private String isCircleName;
    
    private Integer circleSize;
    
    private Integer cicleType;
    
    private String cicleTypeName;
    
    private String status;//盘点是否结束
    
	private String inventoryLocationGuid;//盘点位置id拼接
	private String location;//盘点位置id拼接
	
	private int inventoryCount;//盘点数量
	private String inventoryResult;//盘点结果 一致，不一致
	private String diffCount;//差异数量
	private String inventoryLocationName;//盘点位置中文名
	private Integer locationGuid;//盘点位置
	private String locationname;//盘点位置中文名
	
	private String inventoryLocationPath;//盘点位置全路径拼接
	
	private List<LocationInfo> locationList;// 盘点位置列表
	private String sign;
	
	/**
	 * @return the inventoryLocationGuid
	 */
	public String getInventoryLocationGuid() {
		return inventoryLocationGuid;
	}

	public Integer getIsCircle() {
		return isCircle;
	}

	public void setIsCircle(Integer isCircle) {
		this.isCircle = isCircle;
	}

	public String getIsCircleName() {
		return isCircleName;
	}

	public void setIsCircleName(String isCircleName) {
		this.isCircleName = isCircleName;
	}

	public Integer getCircleSize() {
		return circleSize;
	}

	public void setCircleSize(Integer circleSize) {
		this.circleSize = circleSize;
	}

	public Integer getCicleType() {
		return cicleType;
	}

	public void setCicleType(Integer cicleType) {
		this.cicleType = cicleType;
	}

	public String getCicleTypeName() {
		return cicleTypeName;
	}

	public void setCicleTypeName(String cicleTypeName) {
		this.cicleTypeName = cicleTypeName;
	}

	/**
	 * @param inventoryLocationGuid the inventoryLocationGuid to set
	 */
	public void setInventoryLocationGuid(String inventoryLocationGuid) {
		this.inventoryLocationGuid = inventoryLocationGuid;
	}

	/**
	 * @return the inventoryCount
	 */
	public int getInventoryCount() {
		return inventoryCount;
	}

	/**
	 * @param inventoryCount the inventoryCount to set
	 */
	public void setInventoryCount(int inventoryCount) {
		this.inventoryCount = inventoryCount;
	}

	/**
	 * @return the inventoryResult
	 */
	public String getInventoryResult() {
		return inventoryResult;
	}

	/**
	 * @param inventoryResult the inventoryResult to set
	 */
	public void setInventoryResult(String inventoryResult) {
		this.inventoryResult = inventoryResult;
	}

	/**
	 * @return the diffCount
	 */
	public String getDiffCount() {
		return diffCount;
	}

	/**
	 * @param diffCount the diffCount to set
	 */
	public void setDiffCount(String diffCount) {
		this.diffCount = diffCount;
	}

	/**
	 * @return the inventoryLocationName
	 */
	public String getInventoryLocationName() {
		return inventoryLocationName;
	}

	/**
	 * @param inventoryLocationName the inventoryLocationName to set
	 */
	public void setInventoryLocationName(String inventoryLocationName) {
		this.inventoryLocationName = inventoryLocationName;
	}

	public Integer getGuid() {
		return guid;
	}

	public void setGuid(Integer guid) {
		this.guid = guid;
	}

	public String getInventoryPlanName() {
		return inventoryPlanName;
	}

	public void setInventoryPlanName(String inventoryPlanName) {
		this.inventoryPlanName = inventoryPlanName;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getSysCount() {
		return sysCount;
	}

	public void setSysCount(Integer sysCount) {
		this.sysCount = sysCount;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public String getInventoryTypeName() {
		return inventoryTypeName;
	}

	public void setInventoryTypeName(String inventoryTypeName) {
		this.inventoryTypeName = inventoryTypeName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public Integer getLocationGuid() {
		return locationGuid;
	}

	public void setLocationGuid(Integer locationGuid) {
		this.locationGuid = locationGuid;
	}

	public List<LocationInfo> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<LocationInfo> locationList) {
		this.locationList = locationList;
	}

	public String getInventoryLocationPath() {
		return inventoryLocationPath;
	}

	public void setInventoryLocationPath(String inventoryLocationPath) {
		this.inventoryLocationPath = inventoryLocationPath;
	}

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
	
	
}