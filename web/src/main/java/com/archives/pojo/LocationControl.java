package com.archives.pojo;

public class LocationControl {
    private Integer guid;

    private String controlAddress;

    private Integer branchNum;

    private String branchAddress;


    public String getControlAddress() {
        return controlAddress;
    }

    public void setControlAddress(String controlAddress) {
        this.controlAddress = controlAddress == null ? null : controlAddress.trim();
    }

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress == null ? null : branchAddress.trim();
    }

	public Integer getGuid() {
		return guid;
	}

	public void setGuid(Integer guid) {
		this.guid = guid;
	}
}