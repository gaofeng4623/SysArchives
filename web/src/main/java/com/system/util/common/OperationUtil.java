package com.system.util.common;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.system.base.pojo.Department;
import com.system.base.pojo.Employee;
import com.system.base.pojo.Menu;
import com.system.base.pojo.Roledefine;
import com.system.base.pojo.Rolemembers;
import com.system.core.service.SysLogService;

@Component
@Aspect
public class OperationUtil {
	@Resource
	SysLogService sysLogService;

	@Pointcut(value = "within(com.system..*)")
	public void operate() {
	}

	//登陆及操作日志写入
	@After(value = "operate()")
	public void beforeAdvice(JoinPoint joinPoint) {
		if (joinPoint.getSignature().getName().equals("login")) {
			//写入登陆日志
			sysLogService.insertSelective(Consts.operationType[1],"登录");
		} else {
			//写入操作日志，调用的类及方法名
			String className = joinPoint.getSignature().toString();
			//操作内容
			String opertionStr = "";
			switch(className){
			case "Result com.system.core.serviceimpl.MenuServiceImpl.insertSelective(Menu)":
				{
					Menu menu = (Menu)joinPoint.getArgs()[0];
					opertionStr = "添加菜单或资源:" + menu.getMenuName();
					break;
				}
			case "Result com.system.core.serviceimpl.MenuServiceImpl.updateByPrimaryKeySelective(Menu)":
				{
					Menu menu = (Menu)joinPoint.getArgs()[0];
					opertionStr = "更新菜单或资源" + menu.getMenuName();
					break;
				}
			case "Result com.system.core.serviceimpl.MenuServiceImpl.deleteByPrimaryKey(String)":
				{
					String key = (String)joinPoint.getArgs()[0];
					opertionStr = "删除菜单或资源:主键 " + key;
					break;
				}
			case "Result com.system.core.serviceimpl.RoledefineServiceImpl.Result insertRoledefine(Roledefine)":
				{
					Roledefine roledefine = (Roledefine)joinPoint.getArgs()[0];
					opertionStr = "添加角色" + roledefine.getRolename();
					break;
				}
			case "Result com.system.core.serviceimpl.RoledefineServiceImpl.deleteByPrimaryKey(String)":
				{
					String key = (String)joinPoint.getArgs()[0];
					opertionStr = "删除角色:主键 " + key;
					break;
				}
			case "int com.system.core.serviceimpl.RolemembersServiceImpl.save(Rolemembers)":
				{
					Rolemembers rolemembers = (Rolemembers)joinPoint.getArgs()[0];
					opertionStr = "保存角色组:角色ID " + rolemembers.getRoleid();
					break;
				}
			case "int com.system.core.serviceimpl.RolemembersServiceImpl.deleteByPrimaryKey(Integer)":
				{
					String roleid = joinPoint.getArgs()[0].toString();
					opertionStr = "删除角色组:角色组主键 " + roleid;
					break;
				}
			case "int com.system.core.serviceimpl.SysRoleServiceImpl.insertRoleMenus(Map)":
				{
					@SuppressWarnings("unchecked")
					Map<String,Object> map = (Map<String,Object>)joinPoint.getArgs()[0];
					String itemid = map.get("itemid").toString();
					opertionStr = "添加角色菜单:相关联的菜单ID " + itemid;
					break;
				}
			case "int com.system.core.serviceimpl.SysRoleServiceImpl.removeRoleMenus(String)":
				{
					String roleid = (String)joinPoint.getArgs()[0];
					opertionStr = "删除角色菜单:删除的角色ID " + roleid;
					break;
				}
			case "Result com.system.core.serviceimpl.UserLimitServiceImpl.insertSelective(Department)":
				{
					Department department = (Department)joinPoint.getArgs()[0];
					opertionStr = "添加部门:添加的部门名称 " + department.getDepartmentname();
					break;
				}
			case "Result com.system.core.serviceimpl.UserLimitServiceImpl.updateByPrimaryKeySelective(Department)":
				{
					Department department = (Department)joinPoint.getArgs()[0];
					opertionStr = "修改部门:修改后部门名称 " + department.getDepartmentname();
					break;
				}
			case "Result com.system.core.serviceimpl.UserLimitServiceImpl.deleteByPrimaryKey(String)":
				{
					String departmenId = (String)joinPoint.getArgs()[0];
					opertionStr = "删除部门:删除部门ID " + departmenId;
					break;
				}
			case "Result com.system.core.serviceimpl.UserLimitServiceImpl.insertEmpSelective(Employee,String[])":
				{
					Employee employee = (Employee)joinPoint.getArgs()[0];
					opertionStr = "添加用户:添加的用户ID " + employee.getEmployeeId();
					break;
				}
			case "Result com.system.core.serviceimpl.UserLimitServiceImpl.deleteEmployeeByIds(List<String>)":
				{
					@SuppressWarnings("unchecked")
					List<String> idList = (List<String>)joinPoint.getArgs()[0];
					String ids = "";
					for(int i = 0; i < idList.size(); i++){
						ids = idList.get(i) + ",";
					}
					opertionStr = "删除用户:删除用户ID " + ids;
					break;
				}
			case "Result com.system.core.serviceimpl.UserLimitServiceImpl.updateEmpSelective(Employee,String[])":
				{
					Employee employee = (Employee)joinPoint.getArgs()[0];
					opertionStr = "更新用户:更新的用户ID " + employee.getEmployeeId();
					break;
				}
			}
			if(opertionStr.length() > 0){
				sysLogService.insertSelective(Consts.operationType[2],opertionStr);
			}
		}
	}
	
	//异常日志
	@AfterThrowing(value="execution(* com.system.core..*.*(..))", 
			argNames="exception", throwing = "exception") 
	public void afterThrowingAdvice(JoinPoint joinPoint, Exception exception) { 
		sysLogService.insertSelective(Consts.operationType[0],exception.getMessage());
	} 
	
}
