package com.system.base.pojo;

import java.util.ArrayList;
import java.util.List;

/*@info说明：外接资源主要用于零散的组件配置，需要组合起来使用
 *内接资源可以直接获取使用，或配合初始化事件使用
 *@author 高峰 2016-09-09	  
 * */
public class Source {
	
	private String guid;
	
	private String suporId; //上级节点
	
	private List children; 
	
	private boolean checked;

	private String title; // 标题

	private String type; // 类型

	private String url; // 外接链接

	private String imgs; // 外接图片资源

	private String onclick; // 外接单击事件

	private String initMethod; // 外接初始化方法
	
	private String scripts; //运行脚本
	
	private String objectEditor; //资源管理器

	private String sname; // 资源名称

	private String sid; // 资源标识

	private String className; // 外接样式

	private String content; // 内接资源
	
	private Integer isSource; //是否资源

	private Integer tabIndex; // 排序值
	
	public Source() {
		this.children = new ArrayList();
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid == null ? null : guid.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs == null ? null : imgs.trim();
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick == null ? null : onclick.trim();
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname == null ? null : sname.trim();
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid == null ? null : sid.trim();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getInitMethod() {
		return initMethod;
	}

	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod;
	}

	public String getSuporId() {
		return suporId;
	}

	public void setSuporId(String suporId) {
		this.suporId = suporId;
	}

	public Integer getIsSource() {
		return isSource;
	}

	public void setIsSource(Integer isSource) {
		this.isSource = isSource;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getObjectEditor() {
		return objectEditor;
	}

	public void setObjectEditor(String objectEditor) {
		this.objectEditor = objectEditor;
	}

	public String getScripts() {
		return scripts;
	}

	public void setScripts(String scripts) {
		this.scripts = scripts;
	}
	
	

}