package com.archives.pojo;

//语音播报
public class SpeakResult {
	private int inventoryPlanId;
	private String locationPath;
	private String locationGuid;
	private int status; //-1缺失、0错放
	private int count;

	public int getInventoryPlanId() {
		return inventoryPlanId;
	}

	public void setInventoryPlanId(int inventoryPlanId) {
		this.inventoryPlanId = inventoryPlanId;
	}

	public String getLocationPath() {
		return locationPath;
	}

	public void setLocationPath(String locationPath) {
		this.locationPath = locationPath;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getLocationGuid() {
		return locationGuid;
	}

	public void setLocationGuid(String locationGuid) {
		this.locationGuid = locationGuid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
}
