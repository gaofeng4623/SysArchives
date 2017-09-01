package com.system.util.common;

import javax.servlet.http.HttpServletRequest;

/***
 * @info 转义处理html代码,支撑资源管理
 * @author 高峰 2016-09-05
 *
 */
public class HtmlUtils {
	
	public static String htmlFilter(HttpServletRequest request, String content) {
		if (content == null || "".equals(content.trim())) {
			return "";
		}
		String context = request.getContextPath();
		content = content.replaceAll("\"", "&quot;");
		content = content.replaceAll(" ", "&nbsp;");
		content = content.replaceAll(">", "&gt;");
		content = content.replaceAll("<", "&lt;");
		content = content.replace(context, "{context}");
		return content;
	}
	
	public static String htmlRestore(HttpServletRequest request, String content) {
		if (content == null || "".equals(content.trim())) {
			return "";
		}
		String context = request.getContextPath();
		content = content.replaceAll("&nbsp;", " ");
		content = content.replaceAll("\\{context\\}", context);
		return content;
	}
	
	public static String pageRestore(HttpServletRequest request, String content) {
		if (content == null || "".equals(content.trim())) {
			return "";
		}
		String context = request.getContextPath();
		content = content.replaceAll("&quot;", "\"");
		content = content.replaceAll("&nbsp;", " ");
		content = content.replaceAll("&gt;", ">");
		content = content.replaceAll("&lt;", "<");
		content = content.replaceAll("\\{context\\}", context);
		return content;
	}
	
	public static String createEditor(HttpServletRequest request, String name, String cssPath) {
		StringBuffer editor = new StringBuffer();
		String context = request.getContextPath();
		editor.append("<link href=\"").append(context).append("/kindeditor/themes/default/default.css\" rel=\"stylesheet\"/>");
		editor.append("<link href=\"").append(context).append("/kindeditor/plugins/code/prettify.css\" rel=\"stylesheet\"/>");
		editor.append("<script src=\"").append(context).append("/kindeditor/kindeditor.js\" charset=\"utf-8\"></script>");
		editor.append("<script src=\"").append(context).append("/kindeditor/lang/zh_CN.js\" charset=\"utf-8\"></script>");
		editor.append("<script src=\"").append(context).append("/kindeditor/plugins/code/prettify.js\" charset=\"utf-8\"></script>");
		editor.append("<script type=\"text/javascript\">\r\n");
		editor.append("KindEditor.ready(function(K) {\r\n");
		editor.append(" var editor1 = K.create('textarea[name=\"").append(name).append("\"]', {\r\n");
		editor.append("cssPath : '").append(context).append(String.valueOf(cssPath)).append("',\r\n");	
		editor.append("uploadJson : '").append(context).append("/kindeditor/jsp/upload_json.jsp',\r\n");		
		editor.append("fileManagerJson : '").append(context).append("/kindeditor/jsp/file_manager_json.jsp',\r\n");		
		editor.append("filterMode : false,\r\n");
		editor.append("allowFileManager : true,\r\n");
		editor.append("afterCreate : function() {\r\n");
		editor.append("this.sync();},\r\n");
		editor.append("afterBlur : function(){ \r\n");
		editor.append("this.sync();}});\r\n");
		editor.append("prettyPrint();});\r\n");
		editor.append("</script>\r\n");
		return editor.toString();
	}

}
