package com.system.base.pojo;

public class MenuItem {
	
	private String guid;
	
	private String suporId;
	
	private String text;
	
	private String url;
	
	private String className;

	public MenuItem(String text, String url, String className) {
		this.text = text;
		this.url = url;
		this.className = className;
	}

	public MenuItem() {
		this.className = "lista_first";
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getSuporId() {
		return suporId;
	}

	public void setSuporId(String suporId) {
		this.suporId = suporId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String toString() {
		String text = String.valueOf(this.text);
		StringBuffer sb = new StringBuffer();
		sb.append("<li>").append("<a class =\"").append(className)
				.append("\" href=\"#\"").append(" onclick=\"panelRefresh('")
				.append(text).append("','").append(String.valueOf(this.url))
				.append("')\">").append(text).append("</a></li>\r\n");
		return sb.toString();
	}

}
