package com.system.web.controller;

import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.system.base.pojo.Employee;
import com.system.base.pojo.Person;
import com.system.base.pojo.Result;
import com.system.core.service.UserService;
import com.system.util.common.Consts;
import com.system.util.common.Md5Util;
import com.system.workflow.activiti.commons.Pager;

/**
 * @info 用户的登录及功能转接
 * @author 高峰 2016-09-08
 */
@Controller
@RequestMapping("/user")
public class UserController {
	private Log logger = LogFactory.getLog(UserController.class);

	@Resource
	private UserService userService;

	/**
	 * 注册用户待完成
	 */
	@RequestMapping("/register.do")
	@ResponseBody
	public Result register(Person person, HttpServletRequest request) {
		userService.save(person);
		return new Result(person);
	}

	/**
	 * 登录验证
	 */
	@RequestMapping("/goLogin.do")
	@ResponseBody
	public Result userLogin(String name, String passWord,
			HttpServletRequest request) {
		return userService.login(name, passWord, request);
	}

	@RequestMapping("/loginExtend.do")
	@ResponseBody
	public ModelAndView loginExtend(String name, String passWord,
			HttpServletRequest request) {
		Result result = userService.login(name, passWord, request);
		return result.getStatus() == 0 ? new ModelAndView("system/main")
				: new ModelAndView("");
	}

	// 注销session
	@RequestMapping("/logout.do")
	@ResponseBody
	public Result logout(HttpSession session, HttpServletResponse response) {
		session.invalidate();
		logger.info("session已注销");
		Result result = new Result();
		result.setStatus(0);
		return result;
	}

	@RequestMapping("/updatePasswordInit.do")
	public ModelAndView updatePasswordInit(HttpSession session) {
		ModelAndView mv = new ModelAndView("system/alert/updatePassword");
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		mv.addObject("emp", emp);
		return mv;
	}

	// 修改密码
	@RequestMapping("/changePassword.do")
	@ResponseBody
	public Result changePassword(String lastPassword, String newPassword,
			HttpSession session) {
		Result res = new Result();
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		if (null == emp) {
			res.setStatus(1);
			res.setMessage("帐户信息异常,请重新登陆");
			return res;
		} else {
			if (emp.getPassword().equals(Md5Util.md5(lastPassword))) {
				emp.setPassword(Md5Util.md5(newPassword));
				userService.update(emp);
				res.setStatus(0);
				res.setMessage("修改成功");
				return res;
			} else {
				res.setStatus(1);
				res.setMessage("原密码输入有误");
				return res;
			}
		}
	}

	// 功能跳转
	@RequestMapping("/handleView.do")
	public String handleView(String src, HttpServletRequest request) {
		return src;
	}

	@RequestMapping("/findEmpByDeptId.do")
	@ResponseBody
	public Pager findEmpByDeptId(Employee emp, String deptIds, int rows,
			int page) {
		Pager resultPage = null;
		List<String> idList = new ArrayList<String>();
		if (null != deptIds && !"".equals(deptIds)) {
			for (String deptId : deptIds.split(",")) {
				if (StringUtils.isEmpty(deptId)) {
					continue;
				}
				idList.add(deptId);
			}
		}
		int start = (page - 1) * rows;
		if (0 < idList.size()) {
			resultPage = userService.findEmpByDeptIdsForPage(emp, idList,
					start, rows);
		} else {
			resultPage = userService.findEmpByDeptIdsForPage(emp, null, start,
					rows);
		}

		return resultPage;
	}

	@RequestMapping("/findEmpByCondition.do")
	@ResponseBody
	public List<Employee> findEmpByCondition(Employee emp) {
		List<Employee> res = null;
		res = userService.findEmpByCondition(emp);
		return res;
	}

	// 弹出用户详细信息
	@RequestMapping("/findEmployee.do")
	public ModelAndView findEmployee(HttpSession session) {
		ModelAndView mv = new ModelAndView("system/alert/employee");
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		mv.addObject("emp", emp);
		return mv;
	}

	//用户上传头像
	@RequestMapping(value = "/upload.do")
	public ModelAndView upload(
			@RequestParam(value = "photo", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model, HttpSession session) {
		Result result = new Result();
		ModelAndView mv = new ModelAndView("system/alert/employee");
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		try {
			byte[] content = file.getBytes();
			Employee em = new Employee();
			em.setPhoto(content);
			em.setEmployeeId(emp.getEmployeeId());
			userService.update(em);
			result.setStatus(0);
			result.setMessage("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	// 查看用户头像
	@RequestMapping("/findPhoto.do")
	public void findPhoto(HttpSession session, HttpServletResponse response)
			throws Exception {
		BufferedOutputStream bos = null;
		Employee user = (Employee) session.getAttribute(Consts.userkey);
		try {
			response.setCharacterEncoding("utf-8");
			bos = new BufferedOutputStream(response.getOutputStream());
			Employee employee = userService.findEmp(user.getEmployeeLoginName());
			bos.write(employee.getPhoto());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) bos.close();
		}
	}

}
