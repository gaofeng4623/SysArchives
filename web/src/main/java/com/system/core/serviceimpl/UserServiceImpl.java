package com.system.core.serviceimpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.system.base.pojo.Employee;
import com.system.base.pojo.Person;
import com.system.base.pojo.Result;
import com.system.core.service.UserService;
import com.system.dao.database.EmployeeDao;
import com.system.util.common.Consts;
import com.system.util.common.Md5Util;
import com.system.workflow.activiti.commons.Pager;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	private EmployeeDao employeeDao;
	private Log logger = LogFactory.getLog(UserServiceImpl.class);

	public int update(Employee emp) {
		return employeeDao.updateByPrimaryKeySelective(emp);
	}

	// 登录审核用户
	public Result login(String loginName, String passWord,
			HttpServletRequest request) {
		Result res = new Result();
		Employee employee = employeeDao.findUser(loginName);
		if (employee != null) {
			HttpSession session = request.getSession();
			if (employee.getPassword().equals(Md5Util.md5(passWord))) {
				try {
					// 获得登录ip
					String ip = InetAddress.getLocalHost().getHostAddress();
					employee.setRemoteIp(ip);
					session.setAttribute(Consts.userkey, employee);
					res.setStatus(0);
					res.setMessage("登录成功");
					logger.info(employee.getEmployeeName() + "成功登录系统");
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			} else {
				res.setStatus(1);
				res.setMessage("用户名或者密码错误");
			}

		} else {
			res.setStatus(1);
			res.setMessage("用户名或者密码错误");
		}
		return res;
	}

	public int save(Person p) {
		return 0;
	}

	public List findAll() {
		return employeeDao.findAll();
	}

	public int delete(Person p) {
		return 0;
	}

	public Pager findEmpByDeptIdsForPage(Employee emp, List<String> idList,
			int start, int rows) {
		Map m = new HashedMap();
		m.put("idList", idList);
		m.put("employeeName", emp.getEmployeeName());
		m.put("employeeLoginName", emp.getEmployeeLoginName());
		m.put("start", start);
		m.put("rows", rows);
		List empList = employeeDao.findEmpByDeptIdsForPage(m);
		int total = employeeDao.findCountEmpByDeptIdsForPage(m);
		return new Pager(total, empList);
	}

	public List<Employee> findEmpByCondition(Employee emp) {
		return employeeDao.findEmpByCondition(emp);
	}

	public List<Employee> findEmpByDeptId(List<Long> idList) {
		Map m = new HashedMap();
		m.put("idList", idList);
		return employeeDao.findEmpByDeptIds(m);
	}

	public List findEmployeeId(Map m) {
		return employeeDao.findEmployeeId(m);
	}

	@Override
	public List<Employee> findRoleId(String roleId) {
		return employeeDao.findRoleId(roleId);
	}

	@Override
	public List<Employee> findEmpNotRoleid(String deptIds, String roleid) {
		return employeeDao.findEmpNotRoleid(deptIds, roleid);
	}

	@Override
	public Result findUser(String name) {
		Result result = new Result();
		Employee employee = employeeDao.findEmpByLoginName(name);
		if (employee != null) {
			result.setData(employee);
			result.setStatus(0);
		} else if (name.equals("")) {
			result.setMessage("请输入用户名！");
			result.setStatus(1);
		} else {
			result.setMessage("查无此人请确认！");
			result.setStatus(1);
		}

		return result;
	}

	@Override
	public Employee findEmp(String employeeLoginName) {
		// TODO Auto-generated method stub
		return employeeDao.findEmp(employeeLoginName);
	}

}
