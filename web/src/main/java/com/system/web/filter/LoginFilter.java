package com.system.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.system.base.pojo.Employee;
import com.system.util.common.Consts;

public class LoginFilter implements Filter {
	
	// 不需要过滤得请求
	private String[] excludeRequest;  	
	// 项目名	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 不需要过滤的请求
		String excludeRequestStr = filterConfig.getInitParameter("excludeRequest");
		if(null != excludeRequestStr){
			excludeRequest = excludeRequestStr.split(",");
		}
		
	}
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String requestUri = request.getRequestURI();
		// 是否需要校验， 默认需要
		boolean validFlag = true;
		if(null != excludeRequest && 0 < excludeRequest.length){
			for(String s : excludeRequest){
				if(-1 <requestUri.indexOf(s)){
					validFlag = false;
				}
			}
		}
		
		if(validFlag){
			HttpSession session = request.getSession();
			Employee employee = (Employee) session.getAttribute(Consts.userkey);
			if(null != employee && StringUtils.isNotBlank(employee.getEmployeeLoginName())){
				filterChain.doFilter(request, response);
			}else{
				if (request.getHeader("x-requested-with") != null
						&& request.getHeader("x-requested-with").equals("XMLHttpRequest")) { // ajax请求
					response.setHeader("session-status", "timeout");
				} else {
					response.sendRedirect(request.getContextPath() + "/login.html");
					return;
				}
				return;
//				PrintWriter out = response.getWriter();
//				out.write("{\"status\":1,\"message\":\"请先登录.\"}");	
//				out.close();
				// request.getRequestDispatcher("/login.html").forward(request, response);
			}
		}else{
			filterChain.doFilter(request, response);
		}
	}


}
