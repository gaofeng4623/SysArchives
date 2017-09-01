package com.system.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.system.base.pojo.Department;
import com.system.base.pojo.Employee;
import com.system.base.pojo.OrgNode;
import com.system.base.pojo.Result;
import com.system.core.service.OrganizationService;


/**
 * @info 实现组织机构相关功能 
 * @author 高峰 2016-09-08
 */
@Controller
@RequestMapping("/org")
public class OrganizationController {

	@Resource
	private OrganizationService organizationService;

	/* 获取组织机构部门树 */
	@RequestMapping("/initDeptTree.do")
	@ResponseBody
	public List<Department> initDepartmentTree() {
		List<Department> depts = organizationService.findAllDepartmentsforTree();
		return depts;
	}
	/* 所有部门 */
	@RequestMapping("/AllsuperiorId.do")
	@ResponseBody
	public List<Department> AllsuperiorId(String superiorId) {
		List<Department> depts = organizationService.selectBySuperId(superiorId);
		Department  de=new Department();
		de.setDepartmentname("请选择");
		depts.add(0,de);
		return depts;
	}
	/* 获取组织机构部门人员树 */
	@RequestMapping("/createOrgNodeTree.do")
	@ResponseBody
	public List<OrgNode> createOrgNodeTree() throws Exception {
		return organizationService.createOrgNodeTree();
	}
	
	
	@RequestMapping("/addDeptInit.do")
	public ModelAndView addDeptInit(String deptId) {
		ModelAndView mv = new ModelAndView("system/alert/addDept");
		Department dept = null;
		if ("0".equals(deptId)) {
			dept = new Department();
			dept.setDepartmentid("0");
			dept.setDepartmentname("无");
		} else {
			dept = organizationService.selectByPrimaryKey(deptId);
		}
		mv.addObject("superDept", dept);
		return mv;
	}

	@RequestMapping("/updDeptInit.do")
	public ModelAndView updDeptInit(String deptId) {
		ModelAndView mv = new ModelAndView("system/alert/updDept");
		Department dept = organizationService.selectByPrimaryKey(deptId);
		Department superDept = null;
		if ("0".equals(dept.getSuperiorid())) {
			superDept = new Department();
			superDept.setDepartmentid("0");
			superDept.setDepartmentname("无");
		} else {
			superDept = organizationService.selectByPrimaryKey(dept
					.getSuperiorid());
		}
		if (null != dept && null != superDept) {
			dept.setSuperDeptname(superDept.getDepartmentname());
		}
		mv.addObject("dept", dept);
		return mv;
	}

	@RequestMapping("/saveDeptInfo.do")
	@ResponseBody
	public Result saveDeptInfo(Department dept) throws Exception {
		return organizationService.insertSelective(dept);
	}

	@RequestMapping("/updDeptInfo.do")
	@ResponseBody
	public Result updDeptInfo(Department dept) throws Exception {
		return organizationService.updateByPrimaryKeySelective(dept);
	}

	@RequestMapping("/removeDept.do")
	@ResponseBody
	public Result removeDept(String deptId) throws Exception {
		return organizationService.deleteByPrimaryKey(deptId);
	}

	@RequestMapping("/addEmployeeInit.do")
	public ModelAndView addEmployeeInit(String deptId) {
		ModelAndView mv = new ModelAndView("system/alert/addEmployee");
		Department dept = organizationService.selectByPrimaryKey(deptId);
		mv.addObject("dept", dept);
		return mv;
	}

	@RequestMapping("/saveEmployeeInfo.do")
	@ResponseBody
	public Result addEmployee(Employee emp, String[] roleId) throws Exception {
		return organizationService.insertEmpSelective(emp, roleId);
	}

	@RequestMapping("/removeEmployees.do")
	@ResponseBody
	public Result removeEmployee(String ids) throws Exception {
		List<String> idList = new ArrayList<String>();
		if (null != ids && !"".equals(ids)) {
			for (String empId : ids.split(",")) {
				if (StringUtils.isEmpty(empId)) {
					continue;
				}
				idList.add(empId);
			}
		}
		return organizationService.deleteEmployeeByIds(idList);
	}

	@RequestMapping("/updEmployeeInit.do")
	public ModelAndView updEmployeeInit(String empId) {
		ModelAndView mv = new ModelAndView("system/alert/updEmployee");
		Employee emp = organizationService.findEmployeeById(empId);
		if ("0".equals(emp.getDepartmentId())) {
			emp.setDepartmentName("无");
		}
		mv.addObject("emp", emp);
		return mv;
	}
	
	@RequestMapping("/updateEmployeeInfo.do")
	@ResponseBody
	public Result updateEmployeeInfo(Employee emp, String[] roleId)
			throws Exception {
		return organizationService.updateEmpSelective(emp, roleId);
	}
	
	
}
