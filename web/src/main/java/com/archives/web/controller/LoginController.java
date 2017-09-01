package com.archives.web.controller;
//用于集成其他业务系统的验证
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.core.service.UserService;
import com.system.util.common.Consts;

@Controller
@RequestMapping("/loginController")
public class LoginController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/findUser.do")
	@ResponseBody
	public Result findUser(String name) {
		return userService.findUser(name);
	}
	
	@RequestMapping("/putUser.do")
	@ResponseBody
	public Result putUser(Employee employee,HttpServletRequest request) {
		Result result = new Result();
		HttpSession session = request.getSession();
		session.setAttribute(Consts.userkey, employee);
		result.setStatus(0);
		return result;
	}

}
