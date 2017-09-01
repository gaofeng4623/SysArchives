package com.archives.pojo;

public class SynLog {
	private int infoid;
	private int status;
	private String syndate;
	
	public SynLog(int infoid, int status, String syndate) {
		this.infoid = infoid;
		this.status = status;
		this.syndate = syndate;
	}

	public int getInfoid() {
		return infoid;
	}

	public void setInfoid(int infoid) {
		this.infoid = infoid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSyndate() {
		return syndate;
	}

	public void setSyndate(String syndate) {
		this.syndate = syndate;
	}

}
