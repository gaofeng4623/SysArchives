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
import com.archives.dao.DocHistoryDao;
import com.archives.pojo.ArcHistory;
import com.archives.pojo.DocHistory;
import com.archives.service.ArcHistoryService;
import com.archives.service.DocHistoryService;
import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.util.common.Consts;
import com.system.workflow.activiti.commons.Pager;
@Service
public class DocHistoryServiceImpl implements DocHistoryService{

	@Resource
	private DocHistoryDao docHistoryDao;
	///@Resource
	///private ArchivesInfoMapperDao archivesInfoMapperDao;
	@Autowired
	private HttpServletRequest servletRequest;
/*	@Override
	public Pager findArcHistoryPage(DocHistory docHistory, int start, int rows) {
        Map<String,Object> m = new HashMap<String,Object>();
		m.put("infoId", docHistory.getInfoid());
		m.put("event",docHistory.getEvent());
		m.put("remark", docHistory.getRemark());
		m.put("start", start);
		m.put("rows", rows);
		List<ArcHistory> empList = arcHistoryDao.findArcHistoryForPage(m);
		int total = arcHistoryDao.findCountArcHistoryForPage(m);
		return new Pager(total, empList);
	}*/

	@Override
	public Result insertArcHistory(int arcGuid, String event, String personName) throws Exception{
		Employee employee = (Employee) servletRequest.getSession().getAttribute(Consts.userkey);
		Result res = new Result();
		int flag = -1;
		if(null != employee){
			DocHistory arcHistory = new DocHistory();
			arcHistory.setInfoid(arcGuid);
			arcHistory.setChangedate(new Date());
			if (personName == null) 
				personName = employee.getEmployeeName();
			arcHistory.setChangeman(personName);
			arcHistory.setEvent(event);
			flag = docHistoryDao.insertSelective(arcHistory);
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
