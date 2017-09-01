package com.archives.exchange.serviceimpl;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

import com.archives.exchange.dao.OrgHandlerDao;
import com.archives.exchange.service.OrgHandlerService;
import com.archives.service.OrgSynService;
import com.system.base.pojo.Department;
import com.system.base.pojo.Employee;
import com.system.base.pojo.SysLog;
import com.system.dao.database.SysLogDao;
import com.system.util.common.Consts;

@Service("orgHandler")
public class OrgHandlerServiceImpl implements OrgHandlerService {
	@Resource
	private OrgHandlerDao orgHandlerDao;
	@Resource
	private OrgSynService synorg;
	@Resource
	private SysLogDao sysLogDao; //日志

	@Override
	public int synDepartments() throws Exception {
		int count = 0;
		Element eldept = null;
		try {
			 StringWriter stringWriter = new StringWriter();  
			 Element root = DocumentHelper.createElement("departments");  
		     Document document = DocumentHelper.createDocument(root);  
			 List<Department> departments = orgHandlerDao.queryDepartments();
			 for (Department dept : departments) {
				eldept = root.addElement("department");
				eldept.addAttribute("parent", "0"); //经调研都是平级部门，所以父节点为0
				eldept.addElement("departmentId").setText(String.valueOf(dept.getDepartmentid()));
				eldept.addElement("department_name").setText(String.valueOf(dept.getDepartmentname()));
				eldept.addElement("department_tabIndex").setText(String.valueOf(dept.getTabindex()));
			}
	        OutputFormat xmlFormat = new OutputFormat();  
	        xmlFormat.setEncoding("UTF-8"); 
	        xmlFormat.setNewlines(true); 
	        xmlFormat.setIndent(true); 
	        xmlFormat.setIndent("    "); 
	        XMLWriter xmlWriter = new XMLWriter(stringWriter, xmlFormat);  
	        xmlWriter.write(document);  
	        xmlWriter.close(); 
	      //System.out.println(stringWriter.toString()); 
	        count = synorg.initDepartments(stringWriter.toString());
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String optime = format.format(new java.util.Date());
	        insertSelective(optime + "同步" + count + "条部门数据");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return count;
	}

	@Override
	public int synEmployees() throws Exception {
		int count = 0;
		Element elemp = null;
		StringWriter stringWriter = new StringWriter();  
		Element root = DocumentHelper.createElement("employees");  
	    Document document = DocumentHelper.createDocument(root);  
		try {
			List<Employee> employees = orgHandlerDao.queryEmployees();
			for (Employee emp : employees) {
				if (invalidateUser(emp)) continue; 
				elemp = root.addElement("employee");
				elemp.addElement("departmentId").setText(String.valueOf(emp.getDepartmentId()));
				elemp.addElement("employeeId").setText(String.valueOf(emp.getEmployeeId()));
				elemp.addElement("employeeLoginName").setText(String.valueOf(emp.getEmployeeLoginName()));
				elemp.addElement("password").setText(String.valueOf(emp.getPassword()));  //待解密
				elemp.addElement("employeeName").setText(String.valueOf(emp.getEmployeeName()));
				elemp.addElement("sex").setText(String.valueOf(emp.getSex()));
				elemp.addElement("post").setText(String.valueOf(String.valueOf(emp.getPost())));
				elemp.addElement("mobile").setText(String.valueOf(emp.getMobile()));
				elemp.addElement("userEmail").setText(String.valueOf(emp.getEmail()));
			}
			OutputFormat xmlFormat = new OutputFormat();  
			xmlFormat.setEncoding("UTF-8"); 
			xmlFormat.setNewlines(true); 
			xmlFormat.setIndent(true); 
			xmlFormat.setIndent("    "); 
			XMLWriter xmlWriter = new XMLWriter(stringWriter, xmlFormat);  
			xmlWriter.write(document);  
			xmlWriter.close();
		  //System.out.println(stringWriter.toString());
			count = synorg.initEmployees(stringWriter.toString());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String optime = format.format(new java.util.Date());
	        insertSelective(optime + " 同步" + count + "条人员数据");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return count;
	}

	private boolean invalidateUser(Employee emp) {
		return emp.getEmployeeLoginName() == null || "".equals(emp.getEmployeeLoginName().trim());
	}

	private void insertSelective(String event) {
		SysLog syslog = new SysLog();
		syslog.setOperationType(String.valueOf(Consts.operationType[3]));
		syslog.setUpdateTime(new Date());
		syslog.setPerson("系统定时任务");
		syslog.setIp("localhost");
		syslog.setEvent(event);
		this.sysLogDao.insertSelective(syslog);
	}
	
}
