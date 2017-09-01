package com.archives.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.archives.dao.OrgSynDao;
import com.archives.service.OrgSynService;
import com.system.base.pojo.Department;
import com.system.base.pojo.Employee;
import com.system.util.common.GUID;
import com.system.util.common.Md5Util;

@WebService(endpointInterface = "com.archives.service.OrgSynService")
@Service("synorg")
public class OrgSynServiceImpl implements OrgSynService {
	@Resource
	private OrgSynDao orgSynDao;
	
	public OrgSynServiceImpl() {
	}

	@Override
	public int initDepartments(String nodesXml) {
		String res = "";
		int count = 0;
		String remoteId = "";
		String departmentName = "";
		String remoteParentId = "";
		String departmentTabIndex;
		InputStreamReader isr = null;
		SAXReader reader = new SAXReader();
		try {
			isr = new InputStreamReader(new ByteArrayInputStream(
					nodesXml.getBytes("utf-8")), "utf-8");
			Document doc = reader.read(isr);
			Element root = doc.getRootElement();
			List<Element> depar = root.elements("department");
			for (Element e : depar) {
				Department department = new Department();
				remoteId = getPropertyValue(e, "departmentId");
				departmentName = getPropertyValue(e,"department_name");
				departmentTabIndex = String.valueOf(getPropertyValue(e,
						"department_tabIndex"));
				remoteParentId = e.attributeValue("parent");
				String guid = new GUID().toString(); // 创建主键
				String testid = orgSynDao.selecByRemoteId(remoteId); // 判断映射关系是否存在
				String parentGuid = "0".equals(remoteParentId) ? "0"
						: findParentKey(remoteParentId); // 获得父节点ID
				if (remoteId.equals("") || remoteId == null) {
					res = "缺少部门ID";
				} else if (departmentName.equals("") || departmentName == null) {
					res = "部门名称";
				}
				if (StringUtils.isEmpty(testid)) {
					department.setSuperiorid(parentGuid);
					department.setDepartmentid(guid);
					department.setDepartmentname(departmentName);
					department.setTabindex(departmentTabIndex);
					count += orgSynDao.createDepartment(department);
					orgSynDao.createDepartmentRef(remoteId, guid); // 创建映射关系
				} else {
					count += orgSynDao.updateDepartment(remoteId, parentGuid,
							departmentName,departmentTabIndex);
				}
			}
			res = "部门初始化完成,共同步" + count + "条部门信息";
		} catch (Exception e) {
			res = "部门初始化失败";
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException E) {
				}
			}
		}
		return count;
	}

	@Override
	public int initEmployees(String empXml) {
		SAXReader reader = new SAXReader();
		InputStreamReader isr = null;
		String res = "";
		int count = 0;
		try {
			isr = new InputStreamReader(new ByteArrayInputStream(
					empXml.getBytes("utf-8")), "utf-8");
			Document doc = reader.read(isr);
			Element root = doc.getRootElement();
			List<Element> emps = root.elements("employee");
			String departmentId = "";// 部门名称
			String remoteId = "";// 人员编号
			String employeeLoginName = "";// 账号名称
			String password = "";// 密码
			String employeeName = "";// 人员姓名
			String birthday = "";// 时间
			String post = "";// 职务
			Date date2 = null;
			Department department = new Department();
			for (Element e : emps) {
				departmentId = getPropertyValue(e,"departmentId");
				remoteId = getPropertyValue(e,"employeeId");
				employeeLoginName = getPropertyValue(e,"employeeLoginName");
				password = getPropertyValue(e,"password");
				employeeName = getPropertyValue(e,"employeeName");
				// employeeName = getPropertyValue(e,"employeeName");
				birthday = getPropertyValue(e,"birthday");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				post = getPropertyValue(e,"post");
				if (birthday != null) {
					date2 = sdf.parse(birthday);
				}
				if (departmentId.equals("") || departmentId == null) {
					res = "缺少部门ID";
				} else if (remoteId.equals("") || remoteId == null) {
					res = "缺少人员ID";
				} else if (employeeLoginName.equals("")
						|| employeeLoginName == null) {
					res = "缺少系统登录账号";
				} else if (password.equals("") || password == null) {
					res = "缺少登录密码";
				} else if (employeeName.equals("") || employeeName == null) {
					res = "缺少人员名称";
				} else {
					String empIde = orgSynDao.selecByremoteIdEmploy(remoteId);
					departmentId = orgSynDao.selecByRemoteId(departmentId);
					String employeeId = new GUID().toString();
					Employee employee = new Employee();
					employee.setDepartmentId(departmentId);
					employee.setEmployeeId(employeeId);
					employee.setEmployeeLoginName(employeeLoginName);
					//employee.setPassword(Md5Util.md5(password)); //名文密码
					employee.setPassword(password); //被对方加密的密码
					employee.setEmployeeName(employeeName);
					employee.setSex(getPropertyValue(e,"sex"));
					employee.setMobile(getPropertyValue(e,"mobile"));
					employee.setPost(post);
					if (date2 != null) {employee.setBirthday(date2);}
					sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
					byte[] bt = decoder.decodeBuffer(String.valueOf(getPropertyValue(e,"photo")));
					employee.setPhoto(bt);
					employee.setUserEmail(getPropertyValue(e,"userEmail"));
					if (StringUtils.isEmpty(empIde)) {
						orgSynDao.createEmployeeRef(remoteId, employeeId);
						count += orgSynDao.createEmployee(employee);
					} else {
						count += orgSynDao.updateEmployee(employee);
					}
				}
			}
			res = "人员初始化完成,共同步" + count + "条数据";
		} catch (Exception e) {
			res = "人员初始化失败,请联系管理员!";
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException E) {
				}

			}
		}
		return count;
	}

	// 创建部门
	@Override
	public String addDepartment(String departXml) {
		String res = "";
		String departmentId = "";
		String departmentName = "";
		String parentId = "";
		InputStreamReader isr = null;
		String department_tabIndex;
		SAXReader reader = new SAXReader();
		try {
			isr = new InputStreamReader(new ByteArrayInputStream(
					departXml.getBytes("utf-8")), "utf-8");
			Document doc = reader.read(isr);
			Element root = doc.getRootElement();
			List<Element> dempar = root.elements("department");
			Department department = new Department();
			for (Element e : dempar) {
				departmentId = getPropertyValue(e,"departmentId");
				departmentName = getPropertyValue(e,"department_name");
				parentId = e.attributeValue("parent");
				department_tabIndex = String.valueOf(getPropertyValue(e,
						"department_tabIndex"));
				if (departmentId.equals("") || departmentId == null) {
					res = "缺少部门ID";
				} else if (departmentName.equals("") || departmentName == null) {
					res = "缺少部门名称";
				} else {
					String depGuid = new GUID().toString();
					String superiorGuid = parentId.equals("")
							|| parentId.equals("0") ? "0" : orgSynDao
							.selecByRemoteId(parentId);
					department.setSuperiorid(superiorGuid);
					department.setDepartmentid(depGuid);
					department.setDepartmentname(departmentName);
					department.setTabindex(department_tabIndex);
					int dep = orgSynDao.createDepartment(department);
					int depRef = orgSynDao.createDepartmentRef(departmentId,
							depGuid);
					if (dep == 0 && depRef == 0) {
						res = "操作失败";
					} else {
						res = "操作成功";
					}
				}
			}
		} catch (Exception e) {
			res = "添加部门失败,请联系管理员!";

			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException E) {
				}
			}
		}
		return res;
	}

	// 更新部门信息
	@Override
	public String updateDepartment(String departXml) {
		SAXReader reader = new SAXReader();
		InputStreamReader isr = null;
		String res = "";
		try {
			isr = new InputStreamReader(new ByteArrayInputStream(
					departXml.getBytes("utf-8")), "utf-8");
			Document doc = reader.read(isr);
			Element root = doc.getRootElement();
			List<Element> dempar = root.elements("department");
			String deptNo = "";
			String departmentName = "";
			String departmentTabIndex;
			for (Element e : dempar) {
				deptNo = getPropertyValue(e,"departmentId");
				departmentName = getPropertyValue(e,"department_name");
				departmentTabIndex = String.valueOf(getPropertyValue(e,
						"department_tabIndex"));
				if (deptNo.equals("") || deptNo == null) {
					res = "缺少部门ID";
				} else if (departmentName.equals("") || departmentName == null) {
					res = "缺少部门名称";
				} else {
					// 0失败1成功
					int dep = orgSynDao.updateDepartment(deptNo, null,
							departmentName,departmentTabIndex);
					if (dep == 0) {
						res = "更新失败";
					} else {
						res = "更新成功";
					}
				}
			}
		} catch (Exception e) {
			res = "更新部门失败,请联系管理员!";
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException E) {
				}
			}
		}
		return res;
	}

	// 根据远程部门id 删除部门
	@Override
	public String deleteDepartment(String deptNo) {
		String res = "";
		String departmentId = "";
		InputStreamReader isr = null;
		SAXReader reader = new SAXReader();
		try {
			isr = new InputStreamReader(new ByteArrayInputStream(
					deptNo.getBytes("utf-8")), "utf-8");
			Document doc = reader.read(isr);
			Element root = doc.getRootElement();
			List<Element> dempar = root.elements("department");
			for (Element e : dempar) {
				departmentId = getPropertyValue(e,"departmentId");
				if (departmentId.equals("") || departmentId == null) {
					res = "缺少部门ID";
				} else {
					// 0失败1成功
					int result = orgSynDao.deleteDepartment(departmentId); //删除部门
					int depRef = orgSynDao.deleteDepartmentRef(departmentId); //删除映射
					
					if (result == 0 || depRef == 0) {
						res = "删除失败";
					} else {
						res = "删除成功";
					}
				}
			}
		} catch (Exception e) {
			res = "删除部门失败,请联系管理员!";
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException E) {
				}
			}
		}
		return res;
	}

	// 添加人员信息
	@Override
	public String addEmployee(String empXml) {
		String res = "";
		InputStreamReader isr = null;
		String departmentId = "";// 部门名称
		String remoteId = "";// 人员编号
		String employeeLoginName = "";// 账号名称
		String password = "";// 密码
		String employeeName = "";// 人员姓名
		String birthday = "";// 时间
		String post = "";// 职务
		Date date2 = null;
		SAXReader reader = new SAXReader();
		try {
			isr = new InputStreamReader(new ByteArrayInputStream(
					empXml.getBytes("utf-8")), "utf-8");
			Document doc = reader.read(isr);
			Element root = doc.getRootElement();
			List<Element> emps = root.elements("employee");
			
			Department department = new Department();
			for (Element e : emps) {
				departmentId = getPropertyValue(e,"departmentId");
				remoteId = getPropertyValue(e,"employeeId");
				employeeLoginName = getPropertyValue(e,"employeeLoginName");
				password = getPropertyValue(e,"password");
				employeeName = getPropertyValue(e,"employeeName");
				birthday = getPropertyValue(e,"birthday");
				post = getPropertyValue(e,"post");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (birthday != null) {
					date2 = sdf.parse(birthday);
				}
				if (departmentId.equals("") || departmentId == null) {
					res = "缺少部门ID";
				} else if (remoteId.equals("") || remoteId == null) {
					res = "缺少人员ID";
				} else if (employeeLoginName.equals("")
						|| employeeLoginName == null) {
					res = "缺少系统登录账号";
				} else if (password.equals("") || password == null) {
					res = "缺少登录密码";
				} else if (employeeName.equals("") || employeeName == null) {
					res = "缺少人员名称";
				} else {
					String empIde = orgSynDao.selecByremoteIdEmploy(remoteId);
					departmentId = orgSynDao.selecByRemoteId(departmentId);
					if (StringUtils.isEmpty(empIde)) {
						String employeeId = new GUID().toString();
						Employee employee = new Employee();
						employee.setDepartmentId(departmentId);
						employee.setEmployeeId(employeeId);
						employee.setEmployeeLoginName(employeeLoginName);
						employee.setPassword(Md5Util.md5(password));
						employee.setEmployeeName(employeeName);
						employee.setSex(getPropertyValue(e,"sex"));
						employee.setPost(post);
						employee.setMobile(getPropertyValue(e,"mobile"));
						if (date2 != null) {
							employee.setBirthday(date2);
						}
						sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
						byte[] bt = decoder.decodeBuffer(getPropertyValue(e,"photo"));
						employee.setPhoto(bt);
						employee.setUserEmail(getPropertyValue(e,"userEmail"));
						int empref = orgSynDao.createEmployeeRef(remoteId, employeeId);
						int emp = orgSynDao.createEmployee(employee);
						if (empref == 0 && emp == 0) {
							res = "添加人员失败";
						} else {
							res = "添加人员成功";
						}
					}
				}
			}
		} catch (Exception e) {
			res = "添加人员失败,请联系管理员!";
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException E) {
				}
			}
		}
		return res;
	}

	// 删除人员信息
	@Override
	public String deleteEmployee(String empNo) {
		SAXReader reader = new SAXReader();
		InputStreamReader isr = null;
		String res = "";
		String employeeId = "";
		try {
			isr = new InputStreamReader(new ByteArrayInputStream(
					empNo.getBytes("utf-8")), "utf-8");
			Document doc = reader.read(isr);
			Element root = doc.getRootElement();
			List<Element> emps = root.elements("employee");
			for (Element e : emps) {
				employeeId = getPropertyValue(e,"employeeId");
				if (employeeId.equals("") || employeeId == null) {
					res = "缺少人员ID";
				} else {
					// 0失败1成功
					int result = orgSynDao.deleteEmployee(employeeId);
					int employee = orgSynDao.deleteEmployeeRef(employeeId);
					if (result == 0 && employee == 0) {
						res = "删除失败";
					} else {
						res = "删除成功";
					}
				}
			}
		} catch (Exception e) {
			res = "删除人员失败,请联系管理员!";
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException E) {
				}
			}
		}
		return res;
	}

	// 修改人员信息
	@Override
	public String updateEmployee(String empXml) {
		String res = "";
		String departmentId = "";// 部门名称
		String remoteId = "";// 人员编号
		String employeeLoginName = "";// 账号名称
		String password = "";// 密码
		String employeeName = "";// 人员姓名
		String birthday = "";// 时间
		String post = "";// 职务
		Date date = null;
		InputStreamReader isr = null;
		SAXReader reader = new SAXReader();
		try {
			isr = new InputStreamReader(new ByteArrayInputStream(
					empXml.getBytes("utf-8")), "utf-8");
			Document doc = reader.read(isr);
			Element root = doc.getRootElement();
			List<Element> emps = root.elements("employee");
			for (Element e : emps) {
				departmentId = getPropertyValue(e,"departmentId");
				remoteId = getPropertyValue(e,"employeeId");
				employeeLoginName = getPropertyValue(e,"employeeLoginName");
				password = getPropertyValue(e,"password");
				employeeName = getPropertyValue(e,"employeeName");
				employeeName = getPropertyValue(e,"employeeName");
				birthday = getPropertyValue(e,"birthday");
				post = getPropertyValue(e,"post");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (birthday != null) {
					date = sdf.parse(birthday);
				}
				if (departmentId.equals("") || departmentId == null) {
					res = "缺少部门ID";
				} else if (remoteId.equals("") || remoteId == null) {
					res = "缺少人员ID";
				} else if (employeeLoginName.equals("")
						|| employeeLoginName == null) {
					res = "缺少系统登录账号";
				} else if (password.equals("") || password == null) {
					res = "缺少登录密码";
				} else if (employeeName.equals("") || employeeName == null) {
					res = "缺少人员名称";
				} else {
					String empIde = orgSynDao.selecByremoteIdEmploy(remoteId);
					departmentId = orgSynDao.selecByRemoteId(departmentId);
					if (empIde != null) {
						Employee employee = new Employee();
						employee.setDepartmentId(departmentId);
						employee.setEmployeeId(empIde);
						employee.setEmployeeLoginName(employeeLoginName);
						employee.setPassword(Md5Util.md5(password));
						employee.setEmployeeName(employeeName);
						employee.setSex(getPropertyValue(e,"sex"));
						employee.setMobile(getPropertyValue(e,"mobile"));
						employee.setPost(post);
						if (date != null) {
							employee.setBirthday(date);
						}
						sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
						byte[] bt = decoder.decodeBuffer(getPropertyValue(e,"photo"));
						employee.setPhoto(bt);
						employee.setUserEmail(getPropertyValue(e,"userEmail"));
						int emp = orgSynDao.updateEmployee(employee);
						if (emp == 0) {
							res = "更改人员失败";
						} else {
							res = "更改人员成功";
						}
					}
				}
			}
		} catch (Exception e) {
			res = "修改人员失败,请联系管理员!";
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException E) {
				}
			}
		}
		return res;
	}

	// 更换部门
	@Override
	public String removeEmployee(String empXml) {
		String res = "";
		String departmentId = "";
		String employeeId = "";
		SAXReader reader = new SAXReader();
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(new ByteArrayInputStream(
					empXml.getBytes("utf-8")), "utf-8");
			Document doc = reader.read(isr);
			Element root = doc.getRootElement();
			List<Element> emps = root.elements("employee");
			for (Element e : emps) {
				departmentId = getPropertyValue(e,"departmentId");
				employeeId = getPropertyValue(e,"employeeId");
				if (departmentId.equals("") || departmentId == null) {
					res = "缺少部门ID";
				} else if (employeeId.equals("") || employeeId == null) {
					res = "缺少人员ID";
				} else {
					int result = orgSynDao.removeEmployee(departmentId, employeeId);
					if (result == 0) {
						res = "操作失败";
					} else {
						res = "操作成功";
					}
				}
			}
		} catch (Exception e) {
			res = "操作失败,请联系管理员!";
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException E) {
				}
			}
		}
		return res;
	}

	/**
	 * 查询父部门主键
	 * 
	 * @param nodesXml
	 * @return
	 */
	public String findParentKey(String remoteId) {

		String parentGuid = "";
		try {
			// 判断映射表是否存在
			String departmentRef = orgSynDao.selecByRemoteId(remoteId);
			if (departmentRef == null) {
				parentGuid = new GUID().toString();
				orgSynDao.createDepartmentRef(remoteId, parentGuid);
				Department department = new Department();
				department.setDepartmentid(parentGuid);
				orgSynDao.createDepartment(department);
			} else {
				parentGuid = departmentRef;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return parentGuid;
	}
	
	public String getPropertyValue(Element e, String property) {
		Element child = e.element(property);
		if (child != null) {
			return child.getTextTrim();
		}
		return null;
	}
}
