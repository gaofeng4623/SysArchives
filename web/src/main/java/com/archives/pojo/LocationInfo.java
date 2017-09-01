package com.archives.pojo;

import java.util.List;

public class LocationInfo {
    private Integer guid;

    private String companyId;

    private String locationName;

    private Integer parentId;

    private String locationPath;

    private Integer locationTypeGuid;

    private String typeName;
    
    private String state;
    
    private Integer serialNo;

    private String serialNoPath;
    
    private Integer controlId;

    private String controlAddress;

    private Integer branchNum;

    private String branchAddress;

    private Integer antennaNum;
    
    private List<LocationInfo> locations;
 
    private Integer sign;
	public Integer getGuid() {
		return guid;
	}

	public void setGuid(Integer guid) {
		this.guid = guid;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getLocationPath() {
		return locationPath;
	}

	public void setLocationPath(String locationPath) {
		this.locationPath = locationPath;
	}

	public Integer getLocationTypeGuid() {
		return locationTypeGuid;
	}

	public void setLocationTypeGuid(Integer locationTypeGuid) {
		this.locationTypeGuid = locationTypeGuid;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public String getSerialNoPath() {
		return serialNoPath;
	}

	public void setSerialNoPath(String serialNoPath) {
		this.serialNoPath = serialNoPath;
	}

	public String getControlAddress() {
		return controlAddress;
	}

	public void setControlAddress(String controlAddress) {
		this.controlAddress = controlAddress;
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
		this.branchAddress = branchAddress;
	}

	public Integer getAntennaNum() {
		return antennaNum;
	}

	public void setAntennaNum(Integer antennaNum) {
		this.antennaNum = antennaNum;
	}

	public Integer getControlId() {
		return controlId;
	}

	public void setControlId(Integer controlId) {
		this.controlId = controlId;
	}

	public List<LocationInfo> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationInfo> locations) {
		this.locations = locations;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}
    
}