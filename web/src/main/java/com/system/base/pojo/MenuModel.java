package com.system.base.pojo;

import java.util.ArrayList;
import java.util.List;

import com.system.util.common.Consts;

public class MenuModel {
	private String text;
	private List<MenuItem> children;
	
	public MenuModel(Menu menu) {
		this.text = menu.getMenuName();
		this.children = new ArrayList();
	}
	
	public MenuModel() {
		this.children = new ArrayList();
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}
	
	public String toString(){	
		StringBuffer sb = new StringBuffer();
		sb.append("<li>");
		sb.append("<a href=\"#\">");
		sb.append(text);
		sb.append("</a>");
		if (this.children != null && this.children.size()> 0) {
			sb.append("<ul id=\"").append(Consts.third_menu).append("\" class=\"submenu\">\r\n");
			for (MenuItem mm : children) {
				sb.append(mm.toString()).append("\n\r");
			}
			sb.append("</ul>\r\n");
		}
		sb.append("</li>\r\n");
		return sb.toString();
	}
}
