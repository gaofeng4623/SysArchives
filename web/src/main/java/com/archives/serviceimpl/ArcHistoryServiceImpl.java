package com.archives.serviceimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archives.dao.ArcHistoryDao;
import com.archives.dao.ArchivesInfoMapperDao;
import com.archives.pojo.ArcHistory;
import com.archives.service.ArcHistoryService;
import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.util.common.Consts;
import com.system.workflow.activiti.commons.Pager;
@Service
public class ArcHistoryServiceImpl implements ArcHistoryService{

	@Resource
	private ArcHistoryDao arcHistoryDao;
	@Resource
	private ArchivesInfoMapperDao archivesInfoMapperDao;
	@Autowired
	private HttpServletRequest servletRequest;
	@Override
	public Pager findArcHistoryPage(ArcHistory arcHistory, int start, int rows) {
        Map<String,Object> m = new HashMap<String,Object>();
		m.put("itemGuid", arcHistory.getItemGuid());
		m.put("event",arcHistory.getEvent());
		m.put("remark", arcHistory.getRemark());
		m.put("start", start);
		m.put("rows", rows);
		List<ArcHistory> empList = arcHistoryDao.findArcHistoryForPage(m);
		int total = arcHistoryDao.findCountArcHistoryForPage(m);
		return new Pager(total, empList);
	}

	@Override
	public Result insertArcHistory(int itemGuid, String event, String personName) {
		Employee employee = (Employee) servletRequest.getSession().getAttribute(Consts.userkey);
		Result res = new Result();
		int flag = -1;
		if(null != employee){
			ArcHistory arcHistory = new ArcHistory();
			arcHistory.setItemGuid(itemGuid);
			arcHistory.setChangeDate(new Date());
			if (personName == null) 
				personName = employee.getEmployeeName();
			arcHistory.setChangeMan(personName);
			String pointName = "";
			 switch (event) {
			 	case "借阅人申请" : pointName = "借阅人申请,申请人:" + personName; break;
			 	case "庭长审批" : pointName = "庭长审批,审批人:" + personName; break;
			 	case "院领导审批" : pointName = "院领导审批,审批人:" + personName; break;
			 }
			arcHistory.setEvent(pointName);
			flag = arcHistoryDao.insertArcHistory(arcHistory);
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
}
