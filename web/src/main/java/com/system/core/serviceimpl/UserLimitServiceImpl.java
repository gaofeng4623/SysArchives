package com.system.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.base.pojo.Department;
import com.system.base.pojo.Employee;
import com.system.base.pojo.OrgNode;
import com.system.base.pojo.Resouce;
import com.system.base.pojo.Roledefine;
import com.system.core.service.UserLimitService;
import com.system.core.service.UserService;
import com.system.dao.database.DepartmentDao;
import com.system.dao.database.OrgNodeDao;
import com.system.dao.database.RoledefineDao;
import com.system.util.common.Consts;

@Service
public class UserLimitServiceImpl implements UserLimitService {
	@Resource
	private UserService userService;
	@Autowired
	private DepartmentDao departmentDao;
	@Resource
	private OrgNodeDao orgNodeDao;
	@Autowired
	private RoledefineDao roledefineDao;

	public List<Department> findAllDepartments() {
		return departmentDao.selectAllDepratments();
	}

	// 获得角色信息
	public List<OrgNode> createOrgNodeTreeRole() throws Exception {
		List<OrgNode> result = new ArrayList();
		List<OrgNode> work = orgNodeDao.getRole();
		List<OrgNode> emps = orgNodeDao.getWorkflowrolemembers();
		OrgNode root = new OrgNode();
		root.setGuid("0");
		root.setSuporId("-1");
		root.setNodeName(Consts.ROOTDEPT);
		work.add(root);
		for (OrgNode node1 : work) {
			boolean mark = false;
			for (OrgNode node2 : work) {
				if (null != node1.getSuporId()
						&& node1.getSuporId().equals(node2.getGuid())) {
					mark = true;
					node2.getChildren().add(node1);
					break;
				}
			}

			for (OrgNode emp : emps) {
				if (emp.getSuporId().equals(node1.getGuid())) {
					node1.getChildren().add(emp);
				}
			}
			if (!mark) {
				result.add(node1);
			}
		}
		return result;
	}


	

	


	@Override
	public List<OrgNode> createOrgNodeTreeByRoleName(String roleId)
			throws Exception {
		List<OrgNode> result = new ArrayList();
		List<OrgNode> depts = orgNodeDao.getDeptNodeByRoleName(roleId);
		List<OrgNode> emps = orgNodeDao.getEmpNodeByRoleName(roleId);
		OrgNode root = new OrgNode();
		root.setGuid("0");
		root.setSuporId("-1");
		root.setNodeName(Consts.ROOTDEPT);
		depts.add(root);
		for (OrgNode node1 : depts) {
			boolean mark = false;
			for (OrgNode node2 : depts) {
				if ((String.valueOf(node1.getSuporId()))
						.equals(node2.getGuid())) {
					mark = true;
					node2.getChildren().add(node1);
					break;
				}
			}

			for (OrgNode emp : emps) {
				if ((String.valueOf(emp.getSuporId())).equals(node1.getGuid())) {
					node1.getChildren().add(emp);
				}
			}
			if (!mark) {
				result.add(node1);
			}
		}
		return result;
	}
	
	@Override
	public List<Roledefine> findRoleNameById(String empId) {
		return roledefineDao.findRoleNameById(empId);
	}
	

	/*角色设置页面的人员和部门树*/
	public List<Resouce> findAllResouce(String roleId) {
		List<Resouce> resouceList = new ArrayList<Resouce>();
		List<Department> depts = departmentDao.selectAllDepratments();
		// 自定义根目录
		Department root = new Department();
		root.setDepartmentid("0");
		root.setSuperiorid("-1");
		root.setDepartmentname(Consts.ROOTDEPT);
		depts.add(root);
		Resouce res = null;
		List<Employee> employees = null;
		if (roleId == "") {
			employees = userService.findAll();

		} else {
			employees = userService.findRoleId(roleId);
		}

		if (null != depts && 0 < depts.size()) {
			for (Department node1 : depts) {
				res = new Resouce();
				res.setId(node1.getDepartmentid());
				res.setName(node1.getDepartmentname());
				res.setType(0);
				res.setPid(node1.getSuperiorid());
				resouceList.add(res);
			}
		}
		if (null != employees && 0 < employees.size()) {
			for (Employee emp : employees) {
				res = new Resouce();
				res.setId(emp.getEmployeeId());
				res.setName(emp.getEmployeeName());
				res.setType(1);
				res.setPid(emp.getDepartmentId());
				resouceList.add(res);
			}
		}

		List<Resouce> nodeList = new ArrayList<Resouce>();
		for (Resouce res1 : resouceList) {
			boolean mark = false;
			for (Resouce res2 : resouceList) {
				if (null != res1.getPid() && res1.getPid().equals(res2.getId())) {
					mark = true;
					if (null == res2.getChildren()) {
						res2.setChildren(new ArrayList<Resouce>());
					}
					res2.getChildren().add(res1);
					break;
				}
			}

			if (!mark) {
				nodeList.add(res1);
			}
		}

		return nodeList;

	}

}
