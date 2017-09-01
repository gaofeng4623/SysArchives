package com.system.web.tablig;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.system.core.service.MenuFactoryService;
import com.system.util.common.Consts;
import com.system.util.common.SpringContextUtil;

/**
 * @info 目录权限控制（分级）
 * @author 高峰 2016-09-03
 */
public class MenuListTag extends TagSupport {
	  
		@Override
	    public int doStartTag() throws JspException {
		  try {
			  StringBuffer sb = new StringBuffer();
			  MenuFactoryService menuFactory = SpringContextUtil.getBean(MenuFactoryService.class);
			  String menus = menuFactory.createMenuString(this.pageContext.getRequest(), Consts.MENULIST);
			  JspWriter out = this.pageContext.getOut();
			  sb.append(getContainerHead());
			  sb.append(menus);
			  sb.append(getContainerFoot());
			  out.println(sb);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		  return SKIP_BODY;
	  }
	  
	  	@Override
	    public int doEndTag() throws JspException {
	        return EVAL_PAGE;
	    }
	 
	    @Override
	    public void release() {
	        super.release();
	    }

	    public String getContainerHead() {
	    	StringBuffer sb = new StringBuffer();
	    	sb.append("<div class = \"pageTitle\" style=\"float:left;width:10%;\"></div>\r\n");
			sb.append("<div id=\"nav\" class=\"nav\" style=\"float:right;width:900px;\">\r\n");
	    	return sb.toString();
	    }
	    
	    public String getContainerFoot() {
	    	StringBuffer sb = new StringBuffer();
	    	sb.append("</div>\r\n");
	    	return sb.toString();
	    }

}
