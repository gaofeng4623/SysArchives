package com.system.core.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.base.pojo.SysLog;
import com.system.core.service.SysLogService;
import com.system.dao.database.SysLogDao;
import com.system.util.common.Consts;
import com.system.workflow.activiti.commons.Pager;

@Service
public class SysLogServiceImpl implements SysLogService {

	@Resource
	private SysLogDao sysLogDao;
	@Autowired
	private HttpServletRequest servletRequest;

	@Override
	public Pager findSysLogPage(SysLog sysLog, int start, int rows)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (null != sysLog.getEtimeStr() && !"".equals(sysLog.getEtimeStr())) {
			Date etime = format.parse(sysLog.getEtimeStr());
			sysLog.setEtime(etime);
		}
		if (null != sysLog.getStimeStr() && !"".equals(sysLog.getStimeStr())) {
			Date stime = format.parse(sysLog.getStimeStr());
			sysLog.setStime(stime);

		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("event", sysLog.getEvent());
		m.put("stime", sysLog.getStime());
		m.put("etime", sysLog.getEtime());
		m.put("operationType", sysLog.getOperationType());
		m.put("ip", sysLog.getIp());
		m.put("person", sysLog.getPerson());
		m.put("start", start);
		m.put("rows", rows);
		List<SysLog> sysLogList = sysLogDao.findSysLogForPage(m);
		int total = sysLogDao.findCountSysLogForPage(m);
		return new Pager(total, sysLogList);
	}

	public Result insertSelective(int operationType, String event) {
		Employee employee = (Employee) servletRequest.getSession()
				.getAttribute(Consts.userkey);
		Result res = new Result();
		int flag = -1;
		if (null != employee) {
			SysLog syslog = new SysLog();
			syslog.setOperationType(String.valueOf(operationType));
			syslog.setUpdateTime(new Date());
			syslog.setPerson(employee.getEmployeeId());
			syslog.setIp(employee.getRemoteIp());
			syslog.setEvent(event);
			flag = sysLogDao.insertSelective(syslog);
		}
		if (-1 < flag) {
			res.setStatus(0);
			res.setMessage("保存成功!");
		} else {
			res.setStatus(1);
			res.setMessage("保存失败,请联系管理员!");
		}
		return res;
	}

	@Override
	public Pager findSynLogPage(SysLog sysLog, int start, int rows)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (null != sysLog.getEtimeStr() && !"".equals(sysLog.getEtimeStr())) {
			Date etime = format.parse(sysLog.getEtimeStr());
			sysLog.setEtime(etime);
		}
		if (null != sysLog.getStimeStr() && !"".equals(sysLog.getStimeStr())) {
			Date stime = format.parse(sysLog.getStimeStr());
			sysLog.setStime(stime);

		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("event", sysLog.getEvent());
		m.put("stime", sysLog.getStime());
		m.put("etime", sysLog.getEtime());
		m.put("operationType", sysLog.getOperationType());
		m.put("ip", sysLog.getIp());
		m.put("person", sysLog.getPerson());
		m.put("start", start);
		m.put("rows", rows);
		List<SysLog> sysLogList = sysLogDao.findSynLogForPage(m);
		int total = sysLogDao.findCountSynLogForPage(m);
		return new Pager(total, sysLogList);
	}
}
