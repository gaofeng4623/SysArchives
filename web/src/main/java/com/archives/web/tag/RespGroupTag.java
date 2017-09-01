package com.archives.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.system.core.service.MenuFactoryService;
import com.system.util.common.Consts;
import com.system.util.common.SpringContextUtil;

/**
 * @info 目录权限控制（顶级）
 * @author 高峰 2016-09-08
 */
public class RespGroupTag extends TagSupport{
	
	@Override
    public int doStartTag() throws JspException {
		try {
			  MenuFactoryService menuFactory = SpringContextUtil.getBean(MenuFactoryService.class);
			  String groupMenus = menuFactory.createMenuString(this.pageContext.getRequest(), Consts.MENUGROUP);
			  JspWriter out = this.pageContext.getOut();
			  StringBuffer menus = new StringBuffer();
			  menus.append("<ul class=\"header_type\">\r\n");
			  menus.append(groupMenus);
			  menus.append("</ul>\r\n");
			  out.println(menus.toString());
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
    
}
