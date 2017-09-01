package com.system.web.tablig;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.system.base.pojo.Source;
import com.system.core.service.SourceService;
import com.system.util.common.HtmlUtils;
import com.system.util.common.SpringContextUtil;

/**
 * @info 资源权限控制(单一)
 * @author 高峰 2016-09-08
 */
public class SingleSourceTag extends BodyTagSupport {

	public int doAfterBody() throws JspException {
		try {
			StringBuffer buffer = new StringBuffer();
			BodyContent bodycontent = getBodyContent();
			String body = String.valueOf(bodycontent.getString());
			String context = this.pageContext.getRequest().getServletContext().getContextPath();
			SourceService sourceService = SpringContextUtil.getBean(SourceService.class);
			List<Source> list = sourceService.selectByTypeName(body, this.pageContext.getRequest());
			if (list == null || list.size() == 0) return SKIP_BODY;
			Source source = list.get(0);
			if (String.valueOf(source.getClassName()).endsWith(".css")) {
				buffer.append("<link href=\"").append(context).append(source.getClassName())
					.append("\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
			}
			if (!StringUtils.isEmpty(source.getContent())) {
				buffer.append(HtmlUtils.pageRestore((HttpServletRequest)
					this.pageContext.getRequest(), source.getContent()));
			}
			if (!StringUtils.isEmpty(source.getInitMethod())) {
				buffer.append("<script type=\"text/javascript\">\r\n");
				buffer.append(source.getInitMethod()).append("();");
				buffer.append("</script>\r\n");
			}
			if (!StringUtils.isEmpty(source.getScripts())) {
				buffer.append("<script type=\"text/javascript\">\r\n");
				buffer.append(HtmlUtils.pageRestore((HttpServletRequest)
						this.pageContext.getRequest(), source.getScripts()));
				buffer.append("</script>\r\n");
			}
			JspWriter out = bodycontent.getEnclosingWriter();
			if (body != null) {
				out.print(buffer);
			}
		} catch (IOException ioe) {
			throw new JspException("Error:" + ioe.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public void doInitBody() throws JspException {
		super.doInitBody();
	}

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

}
