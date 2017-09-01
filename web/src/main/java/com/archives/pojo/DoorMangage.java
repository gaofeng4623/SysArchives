package com.archives.pojo;

/**
 * @author 123
 *
 */
public class DoorMangage {
    private Integer guid;

    private String doorid;

    private String note;

    private String ip;

    private String doortype;

    private Integer loactionguid;
    private String locationName;
    private Integer cameraGuid;
    private String cameraName;
    public Integer getCameraGuid() {
		return cameraGuid;
	}

	public void setCameraGuid(Integer cameraGuid) {
		this.cameraGuid = cameraGuid;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getGuid() {
        return guid;
    }

    public void setGuid(Integer guid) {
        this.guid = guid;
    }

    public String getDoorid() {
        return doorid;
    }

    public void setDoorid(String doorid) {
        this.doorid = doorid == null ? null : doorid.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getDoortype() {
        return doortype;
    }

    public void setDoortype(String doortype) {
        this.doortype = doortype == null ? null : doortype.trim();
    }

    public Integer getLoactionguid() {
        return loactionguid;
    }

    public void setLoactionguid(Integer loactionguid) {
        this.loactionguid = loactionguid;
    }

	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	
}