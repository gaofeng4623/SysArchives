package com.system.base.pojo;

import java.util.ArrayList;
import java.util.List;

import com.system.util.common.Consts;

public class Menu {

	private String guid;

	private String suporId;

	private String menuName;

	private Integer isGroup;

	private Integer isMenu;

	private Integer isExtend;

	private boolean checked;
	
	private String basePath;

	private String icon;

	private String url;

	private List<Menu> children;

	private String defaultPage;

	private Integer tabIndex;

	public Menu() {
		this.children = new ArrayList<Menu>();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSuporId() {
		return suporId;
	}

	public void setSuporId(String suporId) {
		this.suporId = suporId;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(Integer isGroup) {
		this.isGroup = isGroup;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public Integer getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isChecked() {
		return checked;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getDefaultPage() {
		return defaultPage;
	}

	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public Integer getIsExtend() {
		return isExtend;
	}

	public void setIsExtend(Integer isExtend) {
		this.isExtend = isExtend;
	}
	
	public String toString() {
		  StringBuffer menus = new StringBuffer();
		  String context = getContext(basePath, this);
		  menus.append("<li id=\"").append(getGuid()).append("\"  page=\"");
		  menus.append(String.valueOf(getDefaultPage())).append("\" ");
		  menus.append("context=\"").append(context).append("\" ");
		  menus.append("extend=\"").append(getIsExtend()).append("\" ");
		  menus.append("src=\"").append(getUrl()).append("\">");
		  menus.append("<i class=\"icon iconfont\">").append(getIcon()).append("</i>&nbsp;");
		  menus.append(getMenuName()).append("<i class=\"icon iconfont\">&nbsp;").append("&#xe617;").append("</i></li>\r\n");
		  return menus.toString();
	}
	
	public String getContext(String basePath, Menu mu) {
    	String src = String.valueOf(mu.getUrl());
    	String id = String.valueOf(mu.getGuid());
    	String page = mu.getDefaultPage();
    	if (!src.startsWith("/")) src = "/" + src;
    	if (!src.contains("?"))  {
    		if (src.endsWith("/")) src= src.substring(0, src.length() -1);
    		src += "?params=sys";
    	}
    	boolean isExtend = mu.getIsExtend() != null && mu.getIsExtend() == 1;
    	return isExtend ? mu.getUrl() : basePath + src + "&" + Consts.groupId + "=" 
    			+ id + "&" + Consts.homePage + "=" + page;
    }

}
