package com.system.workflow.activiti.commons;



import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 异常处理
 */
public class MyHandlerExceptionResolver extends SimpleMappingExceptionResolver {
	protected static final Logger logger = LoggerFactory.getLogger("gtzn.log");
	protected ModelAndView doResolveException(
			javax.servlet.http.HttpServletRequest httpServletRequest,
			javax.servlet.http.HttpServletResponse httpServletResponse,
			java.lang.Object o, java.lang.Exception ex) {
		ex.printStackTrace();
		String message = "";
		if (ex instanceof DataAccessException) {
			message = "数据库操作失败！";
		} else if (ex instanceof NullPointerException) {
			message = "空指针，调用了未经初始化的对象或者是不存在的对象！";
		} else if (ex instanceof IOException) {
			message = "IO异常！";
		} else if (ex instanceof ClassNotFoundException) {
			message = "指定的类不存在！";
		} else if (ex instanceof ArithmeticException) {
			message = "数学运算异常！";
		} else if (ex instanceof ArrayIndexOutOfBoundsException) {
			message = "数组下标越界!";
		} else if (ex instanceof IllegalArgumentException) {
			message = "方法的参数错误！";
		} else if (ex instanceof ClassCastException) {
			message = "类型强制转换错误！";
		} else if (ex instanceof SecurityException) {
			message = "违背安全原则异常！";
		} else if (ex instanceof SQLException) {
			message = "操作数据库异常！";
		}  else if ("".equals(message)) {
			message = "程序内部出现异常！";
		}
		logger.error(message);
		logger.error(">>"+ex);
		return super.doResolveException(httpServletRequest,
				httpServletResponse, o, ex);
	}
}
