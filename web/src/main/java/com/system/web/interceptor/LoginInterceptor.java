package com.system.web.interceptor;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.system.base.pojo.Employee;
import com.system.util.common.Consts;

/**
 *	登录检查拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object arg2) throws Exception {
		Employee employee =(Employee) request.getSession().getAttribute(Consts.userkey) ;
		if(employee == null) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
			Writer witer = response.getWriter();
			witer.write("{\"status\":1,\"message\":\"请先登录.\"}");	
			witer.close();
			return false;
		} else {
			return true;
		}
	}

}
