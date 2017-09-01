package com.archives.pojo;

public class DoorCamera {
    private Integer guid;

    private String cameraname;

    private String loginname;

    private String loginpassword;

    private String ipaddress;

    private String port;
    private String cameranamecf;
    private String loginnamecf;
    private String cameraName;
    
    public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	public Integer getGuid() {
        return guid;
    }

    public void setGuid(Integer guid) {
        this.guid = guid;
    }

    public String getCameraname() {
        return cameraname;
    }

    public void setCameraname(String cameraname) {
        this.cameraname = cameraname == null ? null : cameraname.trim();
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    public String getLoginpassword() {
        return loginpassword;
    }

    public void setLoginpassword(String loginpassword) {
        this.loginpassword = loginpassword == null ? null : loginpassword.trim();
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress == null ? null : ipaddress.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

	public String getCameranamecf() {
		return cameranamecf;
	}

	public void setCameranamecf(String cameranamecf) {
		this.cameranamecf = cameranamecf;
	}

	public String getLoginnamecf() {
		return loginnamecf;
	}

	public void setLoginnamecf(String loginnamecf) {
		this.loginnamecf = loginnamecf;
	}
    
}