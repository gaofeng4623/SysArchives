package com.system.base.pojo;

import java.util.ArrayList;
import java.util.List;

import com.system.util.common.Consts;

public class SubMenuModel {
	private String id;
	private String icon;
	private String text;
	private List children;
	private boolean limited;
	
	public SubMenuModel() {
		this.children = new ArrayList();
	}
	
	public SubMenuModel(Menu menu) {
		this.text = menu.getMenuName();
		this.icon = menu.getIcon();
		this.children = new ArrayList();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public boolean isLimited() {
		return limited;
	}
	public void setLimited(boolean limited) {
		this.limited = limited;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("<ul>");
		sb.append("<li id=\"").append(Consts.first_menu).append("\">");
		sb.append("<a href=\"#\">");
		sb.append("<i class=\"icon iconfont\">").append(icon).append("</i>&nbsp;");
		sb.append(text).append("<i class=\"icon iconfont\">&nbsp;").append("&#xe613;");
		sb.append("</i></a>\r\n");
		if (this.children != null && this.children.size()> 0) {
			sb.append("<ul id=\"").append(Consts.second_menu).append("\" class=\"submenu\">\r\n");
			for (Object obj : children) {
				sb.append(obj.toString()).append("\n\r");
			}
			sb.append("</ul>\r\n");
		}
		sb.append("</li>\r\n");
		sb.append("<ul>");
		return sb.toString();
	}

}
