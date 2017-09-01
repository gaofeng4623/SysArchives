package com.system.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.base.pojo.Department;
import com.system.base.pojo.Employee;
import com.system.base.pojo.OrgNode;
import com.system.base.pojo.Result;
import com.system.base.pojo.Rolemembers;
import com.system.core.service.OrganizationService;
import com.system.dao.database.DepartmentDao;
import com.system.dao.database.EmployeeDao;
import com.system.dao.database.OrgNodeDao;
import com.system.dao.database.RolemembersDao;
import com.system.dao.database.WorkflowRoleDao;
import com.system.util.common.Consts;
import com.system.util.common.GUID;
import com.system.util.common.Md5Util;

@Service
public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	private DepartmentDao departmentDao;
	@Resource
	private OrgNodeDao orgNodeDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private RolemembersDao rolemembersDao;
	@Resource
	private WorkflowRoleDao workflowRoleDao;

	@Override
	public List<Department> findAllDepartmentsforTree() {
		List<Department> nodeList = new ArrayList<Department>();
		List<Department> depts = departmentDao.selectAllDepratments();
		Department root = new Department();
		root.setDepartmentid("0");
		root.setSuperiorid("-1");
		root.setDepartmentname(Consts.ROOTDEPT);
		depts.add(root);

		// 拼接部门列表
		for (Department node1 : depts) {
			boolean mark = false;
			for (Department node2 : depts) {
				if (null != node1.getSuperiorid()
						&& node1.getSuperiorid()
								.equals(node2.getDepartmentid())) {
					mark = true;
					if (null == node2.getChildren()) {
						node2.setChildren(new ArrayList<Department>());
					}
					node2.getChildren().add(node1);
					break;
				}
			}
			if (!mark) {
				nodeList.add(node1);
			}
		}

		return nodeList;
	}

	@Override
	/* 创建组织机构树 */
	public List<OrgNode> createOrgNodeTree() throws Exception {
		List<OrgNode> result = new ArrayList();
		List<OrgNode> depts = orgNodeDao.getDeptNode();
		List<OrgNode> emps = orgNodeDao.getEmpNode();
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
	public Department selectByPrimaryKey(String departmentid) {
		Department dept = null;
		dept = departmentDao.selectByPrimaryKey(departmentid);
		if (null == dept) {
			dept = new Department();
			dept.setDepartmentid("0");
			dept.setSuperiorid("-1");
			dept.setDepartmentname("无");
		}
		return dept;
	}

	public Result insertSelective(Department record) throws Exception {
		Result res = new Result();
		Department temp = departmentDao
				.selectByName(record.getDepartmentname());
		if (null != temp) {
			res.setStatus(1);
			res.setMessage("部门名称已经存在,请换个名字!");
		} else {
			record.setDepartmentid(new GUID().toString());
			int flag = departmentDao.insertSelective(record);
			if (-1 < flag) {
				res.setStatus(0);
				res.setMessage("保存成功!");
			} else {
				res.setStatus(1);
				res.setMessage("保存失败,请联系管理员!");
			}
		}
		return res;
	}

	public Result updateByPrimaryKeySelective(Department dept) throws Exception {
		Result res = new Result();
		Department temp = departmentDao.selectByName(dept.getDepartmentname());
		if (null != temp
				&& temp.getDepartmentid().equals(dept.getDepartmentid())) {
			res.setStatus(1);
			res.setMessage("部门名称已经存在,请换个名字!");
		} else {
			int flag = departmentDao.updateByPrimaryKeySelective(dept);
			if (-1 < flag) {
				res.setStatus(0);
				res.setMessage("保存成功!");
			} else {
				res.setStatus(1);
				res.setMessage("保存失败,请联系管理员!");
			}
		}
		return res;
	}

	public Result deleteByPrimaryKey(String departmentid) throws Exception {
		Result res = new Result();

		Department temp = departmentDao.selectByPrimaryKey(departmentid);
		if (null == temp) {
			res.setStatus(1);
			res.setMessage("部门不存在,请确认!");
			return res;
		}

		List<Department> childDept = departmentDao
				.selectBySuperId(departmentid);
		if (null != childDept && 0 < childDept.size()) {
			res.setStatus(1);
			res.setMessage("当前部门存在附属部门,不允许删除,请确认!");
			return res;
		}

		List<Employee> empList = employeeDao.findEmpByDeptId(departmentid);
		if (null != empList && 0 < empList.size()) {
			res.setStatus(1);
			res.setMessage("当前部门存在附属员工,不允许删除,请确认!");
			return res;
		}

		departmentDao.deleteByPrimaryKey(departmentid);
		res.setStatus(0);
		res.setMessage("删除成功!");
		return res;
	}

	public Result insertEmpSelective(Employee emp, String[] roleId)
			throws Exception {
		Result res = new Result();
		Employee temp = employeeDao.findEmpByLoginName(emp
				.getEmployeeLoginName());
		if (null != temp) {
			res.setStatus(1);
			res.setMessage("登陆名已经存在,请换一个登陆名!");
			return res;
		}
		// 新增帐户的初始密码设置为123
		emp.setPassword(Md5Util.md5("123"));
		emp.setEmployeeId(new GUID().toString());
		int flag = employeeDao.insertSelective(emp);
		if (-1 < flag) {
			res.setStatus(0);
			res.setMessage("保存成功!");
			// 保存新增人员角色
			for (String id : roleId) {
				Rolemembers record = new Rolemembers();
				record.setRoleid(id);
				record.setUserid(emp.getEmployeeId());
				rolemembersDao.save(record);
			}
			return res;
		} else {
			res.setStatus(1);
			res.setMessage("保存失败,请联系管理员!");
			return res;
		}
	}

	public Result deleteEmployeeByIds(List<String> idList) throws Exception {
		Result res = new Result();
		// 删除人员角色对应信息
		rolemembersDao.deleteByEmpIdList(idList);
		// 删除人员、工作流角色对应信息
		workflowRoleDao.deleteRoleByEmpIdList(idList);
		int flag = employeeDao.deleteEmployeeByIds(idList);
		if (-1 < flag) {
			res.setStatus(0);
			res.setMessage("删除成功!");
			return res;
		} else {
			res.setStatus(1);
			res.setMessage("删除失败!");
			return res;
		}
	}

	public Employee findEmployeeById(String empId) {
		return employeeDao.findEmployeeById(empId);
	}

	public Result updateEmpSelective(Employee emp, String[] roleId)
			throws Exception {
		Result res = new Result();
		int flag = employeeDao.updateByPrimaryKeySelective(emp);
		if (-1 < flag) {
			res.setStatus(0);
			res.setMessage("修改成功!");
			// 根据用户删除原有的角色
			rolemembersDao.deleteByEmpId(emp.getEmployeeId());
			for (String id : roleId) {
				Rolemembers record = new Rolemembers();
				record.setRoleid(id);
				record.setUserid(emp.getEmployeeId());
				rolemembersDao.save(record);
			}

		} else {
			res.setStatus(1);
			res.setMessage("修改失败,请联系管理员!");
		}
		return res;
	}

	@Override
	public List<Department> selectBySuperId(String departmentid) {
		// TODO Auto-generated method stub
		return departmentDao.selectBySuperId(departmentid);
	}

}
