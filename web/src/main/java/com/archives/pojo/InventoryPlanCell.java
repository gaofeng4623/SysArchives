package com.archives.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class InventoryPlanCell {
    private Integer guid;

    private Integer locationGuid;

    private Integer inventoryPlanId;

    private String isInventory;
    
    private Integer inventoryLoactionGuid;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date pdate;
    
    private String inventoryLocationPath;
    
    private Integer sysCount;

    private Integer inventoryCount;
    
    private Integer normalCount;
    
    private Integer missCount;
    
    private Integer errorCount;
    
    private Integer rewriteCount;
    
    private String employeeName;
    
    
    
	public Integer getInventoryLoactionGuid() {
		return inventoryLoactionGuid;
	}

	public void setInventoryLoactionGuid(Integer inventoryLoactionGuid) {
		this.inventoryLoactionGuid = inventoryLoactionGuid;
	}

	public Date getPdate() {
		return pdate;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	public String getInventoryLocationPath() {
		return inventoryLocationPath;
	}

	public void setInventoryLocationPath(String inventoryLocationPath) {
		this.inventoryLocationPath = inventoryLocationPath;
	}

	public Integer getSysCount() {
		return sysCount;
	}

	public void setSysCount(Integer sysCount) {
		this.sysCount = sysCount;
	}

	public Integer getInventoryCount() {
		return inventoryCount;
	}

	public void setInventoryCount(Integer inventoryCount) {
		this.inventoryCount = inventoryCount;
	}

	public Integer getNormalCount() {
		return normalCount;
	}

	public void setNormalCount(Integer normalCount) {
		this.normalCount = normalCount;
	}

	public Integer getMissCount() {
		return missCount;
	}

	public void setMissCount(Integer missCount) {
		this.missCount = missCount;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public Integer getRewriteCount() {
		return rewriteCount;
	}

	public void setRewriteCount(Integer rewriteCount) {
		this.rewriteCount = rewriteCount;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * @return the guid
	 */
	public Integer getGuid() {
		return guid;
	}

	/**
	 * @param guid the guid to set
	 */
	public void setGuid(Integer guid) {
		this.guid = guid;
	}

	/**
	 * @return the locationGuid
	 */
	public Integer getLocationGuid() {
		return locationGuid;
	}

	/**
	 * @param locationGuid the locationGuid to set
	 */
	public void setLocationGuid(Integer locationGuid) {
		this.locationGuid = locationGuid;
	}

	/**
	 * @return the inventoryPlanId
	 */
	public Integer getInventoryPlanId() {
		return inventoryPlanId;
	}

	/**
	 * @param inventoryPlanId the inventoryPlanId to set
	 */
	public void setInventoryPlanId(Integer inventoryPlanId) {
		this.inventoryPlanId = inventoryPlanId;
	}

	/**
	 * @return the isInventory
	 */
	public String getIsInventory() {
		return isInventory;
	}

	/**
	 * @param isInventory the isInventory to set
	 */
	public void setIsInventory(String isInventory) {
		this.isInventory = isInventory;
	}
}