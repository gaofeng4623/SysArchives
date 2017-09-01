package com.archives.serviceimpl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archives.dao.InsertDetailDao;
import com.archives.dao.InsertformDao;
import com.archives.pojo.InsertDetail;
import com.archives.pojo.Insertform;
import com.archives.service.InsertFormService;
import com.system.base.pojo.Employee;
import com.system.util.common.Consts;
import com.system.util.common.GUID;
import com.system.workflow.activiti.commons.Pager;
@Service
public class InsertFormServiceImpl implements InsertFormService{

	@Resource
	private InsertformDao insertformDao;
	@Resource
	private InsertDetailDao insertDetailDao;
	@Autowired
	private HttpServletRequest servletRequest;

	@Override
	@Transactional
	public int saveInsertForm(String guids, String insertTitle,
			String insertRemark) throws Exception {
		//创建插卷单ID
		String formId = new GUID().toString();
		//取得当前用户信息
		Employee employee = (Employee) servletRequest.getSession().getAttribute(Consts.userkey);
		//初始化插卷单信息
		Insertform insertform = new Insertform();
		insertform.setCreatedate(new Date());
		insertform.setFormid(formId);
		insertform.setMark(insertRemark);
		insertform.setTitle(insertTitle);
		insertform.setUserid(employee.getEmployeeId());
		//插入插卷单
		insertformDao.insertSelective(insertform);
		//初始化档案主键
		String[] guidArr = guids.split(",");
		for (String archivesId : guidArr) {
			//初始化插卷单详细表
			InsertDetail insertDetail = new InsertDetail();
			insertDetail.setFormid(formId);
			insertDetail.setInfoid(archivesId);
			//插入插卷单详细表
			insertDetailDao.insertSelective(insertDetail);
		}
		return 0;
	}

	@Override
	public Pager findInsertFormPage(Insertform insertform, int start, int rows) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("insertTitle", insertform.getTitle());
		m.put("insertRemark",insertform.getMark());
		m.put("start", start);
		m.put("rows", rows);
		List<Insertform> insertformList =insertformDao.findInsertformForPage(m);
		int total = insertformDao.findCountInsertformForPage(m);
		return new Pager(total, insertformList);
	}

	@Override
	public Pager findInsertFormDetailPage(String formid, int start,int rows) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("formid", formid);
		m.put("start", start);
		m.put("rows", rows);
		List<InsertDetail> insertformDetailList =insertDetailDao.findInsertformDetailForPage(m);
		int total = insertDetailDao.findCountInsertformDetailForPage(m);
		return new Pager(total, insertformDetailList);
	}

	@Override
	public List<InsertDetail> findInsertformDetail(String formid) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("formid", formid);
		List<InsertDetail> insertformDetailList =insertDetailDao.findInsertformDetail(m);
		return insertformDetailList;
	}
}
