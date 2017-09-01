package com.system.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.system.base.pojo.Department;
import com.system.base.pojo.Employee;
import com.system.base.pojo.Resouce;
import com.system.base.pojo.Result;
import com.system.base.pojo.Rolemembers;
import com.system.base.pojo.WorkflowRoleDefine;
import com.system.base.pojo.WorkflowRoleMember;
import com.system.core.service.WorkflowRoleService;
import com.system.dao.database.DepartmentDao;
import com.system.dao.database.WorkflowRoleDao;
import com.system.util.common.Consts;
import com.system.util.common.GUID;
@Service
public class WorkFlowRoleServiceimpl implements WorkflowRoleService{
	@Resource
	private WorkflowRoleDao workflowRoleDao;
	@Resource
	private DepartmentDao departmentDao;
	
	@Override
	public List<WorkflowRoleDefine> findAllRoles() {
		List<WorkflowRoleDefine> listResult  = new ArrayList<>();
		List<WorkflowRoleDefine> listAll = workflowRoleDao.findAllRoles();
		WorkflowRoleDefine root = new WorkflowRoleDefine();
		root.setGuid("0");
		root.setGroupId("-1");
		root.setRoleName(Consts.ROOTWORKFLOW);
		listAll.add(root);
		for (WorkflowRoleDefine Child : listAll) {
			boolean mark = false;
			for (WorkflowRoleDefine workflowRoleDefine : listAll) {
				if(Child.getGroupId() != null && Child.getGroupId().equals(workflowRoleDefine.getGuid())){
					workflowRoleDefine.getChildren().add(Child);
					mark = true;
					break;
				}
			}
			if (!mark) {
				listResult.add(Child);
			}
			
		}
		return listResult;
	}

	@Override
	public WorkflowRoleDefine selectByPrimaryKey(String guid) {
		return workflowRoleDao.selectByPrimaryKey(guid);
	}

	@Override
	public Result saveRoleInfo(WorkflowRoleDefine workflowRoleDefine) {
		Result res = new Result();
		WorkflowRoleDefine temp = workflowRoleDao.selectByName(workflowRoleDefine.getRoleName());
		if(null != temp){
			res.setStatus(1);
			res.setMessage("角色名称已经存在,请换个名字!");
		}else{
			workflowRoleDefine.setGuid(new GUID().toString());
			int flag = workflowRoleDao.saveRole(workflowRoleDefine);
			if(-1< flag){
				res.setStatus(0);
				res.setMessage("保存成功!");
			}else{
				res.setStatus(1);
				res.setMessage("保存失败,请联系管理员!");
			}
		}
		return res;
	}

	@Override
	public Result deleteByPrimaryKey(String guid) {
		Result res = new Result();
		workflowRoleDao.deleteByPrimaryKey(guid);
		workflowRoleDao.deleteWorkflowRoleMembers(guid);
		res.setStatus(0);
		res.setMessage("删除成功!");
		return res;
	}

	@Override
	public List<Resouce> findDepEmp(String roleId) {
		List<Resouce> resouceList=new ArrayList<Resouce>();
   		List<Department> depts = departmentDao.selectAllDepratments();
		Department root = new Department();
		root.setDepartmentid("0");
		root.setSuperiorid("-1");
		root.setDepartmentname(Consts.ROOTDEPT);
		depts.add(root);
   		Resouce res = null;
   		//查出用户id不等于roleId的
   		List<Employee>listEmps = workflowRoleDao.findRoleId(roleId);
   		
   		if(null != depts && 0 < depts.size()){
   			for(Department node1 : depts){
   				res = new Resouce();
   				res.setId(node1.getDepartmentid());
   				res.setName(node1.getDepartmentname());
   				res.setType(0);
   				res.setPid(node1.getSuperiorid());
   				resouceList.add(res);
   			}
   		}
   		if(null != listEmps && 0 < listEmps.size()){
   			for(Employee emp : listEmps){
   				res = new Resouce();
   				res.setId(emp.getEmployeeId());
   				res.setName(emp.getEmployeeName());
   				res.setType(1);
   				res.setPid(emp.getDepartmentId());
   				resouceList.add(res);
   			}
   		}
		
		List<Resouce>  nodeList= new ArrayList<Resouce>();
		for(Resouce res1 : resouceList){
			boolean mark = false;
			for(Resouce res2 : resouceList){
				if(null != res1.getPid() && res1.getPid().equals(res2.getId())){
					mark = true;
					if(null == res2.getChildren()){
						res2.setChildren(new ArrayList<Resouce>());
					}
					res2.getChildren().add(res1);
					break;
				}
			}
			
			if(!mark){
				nodeList.add(res1);
			}
		}
		return nodeList;
    	   
       }

	@Override
	public List<Rolemembers> selectRoleId(String roleId) {
		return workflowRoleDao.selectRoleId(roleId);
	}

	@Override
	public List<Employee> findEmployeeId(List<String> userIdList) {
		return workflowRoleDao.findEmployeeId(userIdList);
	}

	@Override
	public List<Employee> queryEmpByDepId(String deptIds) {
		return workflowRoleDao.queryEmpByDepId(deptIds);
	}

	@Override
	public void saveWorkflowRoleMember(WorkflowRoleMember workflowRoleMember) {
		workflowRoleDao.saveWorkflowRoleMember(workflowRoleMember);
		
	}

	@Override
	public void deleteEmpByPrimaryKey(String employeeId) {
		workflowRoleDao.deleteEmpByPrimaryKey(employeeId);
		
	}

}
