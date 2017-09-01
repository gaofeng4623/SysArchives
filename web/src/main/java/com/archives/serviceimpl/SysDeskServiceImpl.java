package com.archives.serviceimpl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archives.dao.BorrowReminderDao;
import com.archives.dao.SysDeskDao;
import com.archives.dao.WarnConfigDao;
import com.archives.pojo.WarnConfig;
import com.archives.service.SysDeskService;
import com.system.base.pojo.Employee;
import com.system.util.common.Consts;

@Service
public class SysDeskServiceImpl implements SysDeskService{

	@Resource
	private SysDeskDao sysDeskDao;
	@Autowired
	private HttpServletRequest servletRequest;
	@Resource
	private WarnConfigDao warnConfigDao;
	@Resource
	private BorrowReminderDao borrowReminderDao;
	@Override
	public int queryArchiveCount() {
		return sysDeskDao.queryArchiveCount();
	}
	@Override
	public int queryborrowArchiveCount() {
		return sysDeskDao.queryborrowArchiveCount();
	}
	@Override
	public int queryCollectionCount() {
		return sysDeskDao.queryCollectionCount();
	}
	@Override
	public int queryBorrowSheetCount() {
		return sysDeskDao.queryBorrowSheetCount();
	}
	@Override
	public int queryBorrowNotDeliveredCount() {
		return sysDeskDao.queryBorrowNotDeliveredCount();
	}
	@Override
	public int queryUserBorrowArchiveCount() {
		int result = 0;
		Employee employee = (Employee) servletRequest.getSession().getAttribute(Consts.userkey);
		if(null != employee){
			result = sysDeskDao.queryUserBorrowArchiveCount(employee.getEmployeeId());
		}
		return result;
	}
	@Override
	public int queryUserReturnArchiveCount() {
		int result = 0;
		Employee employee = (Employee) servletRequest.getSession().getAttribute(Consts.userkey);
		if(null != employee){
			result = sysDeskDao.queryUserReturnArchiveCount(employee.getEmployeeId());
		}
		return result;
	}
	@Override
	public int queryUserBorrowNotDeliveredCount() {
		int result = 0;
		Employee employee = (Employee) servletRequest.getSession().getAttribute(Consts.userkey);
		if(null != employee){
			result = sysDeskDao.queryUserBorrowNotDeliveredCount(employee.getEmployeeId());
		}
		return result;
	}
	@Override
	public int queryBorrowRoomBorrowArchiveCount() {
		return sysDeskDao.queryBorrowRoomBorrowArchiveCount();
	}
	@Override
	public int queryBorrowRoomReturnArchiveCount() {
		return sysDeskDao.queryBorrowRoomReturnArchiveCount();
	}
	@Override
	public int queryBorrowRoomBorrowNotDeliveredCount() {
		return sysDeskDao.queryBorrowRoomBorrowNotDeliveredCount();
	}
	@Override
	public List eachMonthCount(String depot,String month) {
		// TODO Auto-generated method stub
		return sysDeskDao.eachMonthCount(depot,month);
	}
	@Override
	public List caseProertyCount() {
		// TODO Auto-generated method stub
		return sysDeskDao.caseProertyCount();
	}
	@Override
	public List borrowCount(String month) {
		// TODO Auto-generated method stub
		return sysDeskDao.borrowCount(month);
	}
	@Override
	public int twoLibraryCount() {
		// TODO Auto-generated method stub
		return sysDeskDao.twoLibraryCount();
	}
	
	@Override
	public int threeLibraryCount() {
		// TODO Auto-generated method stub
		return sysDeskDao.threeLibraryCount();
	}
	@Override
	public int fiveLibraryCount() {
		// TODO Auto-generated method stub
		return sysDeskDao.fiveLibraryCount();
	}
	@Override
	public int destroyArchives() {
		// TODO Auto-generated method stub
		 return sysDeskDao.destroyArchives();
	}
	@Override
	public int docCount() {
		// TODO Auto-generated method stub
		return sysDeskDao.docCount();
	}
	@Override
	public int queryBorrowRoomBorrowCount() {
		// TODO Auto-generated method stub
		return sysDeskDao.queryBorrowRoomBorrowCount();
	}
	@Override
	public int querBorrowSheetCount() {
		// TODO Auto-generated method stub
		WarnConfig war=warnConfigDao.findDocWarnConfig();
		int day=war.getFirstWarning();
		return sysDeskDao.querBorrowSheetCount(day);
	}
	@Override
	public int destroyCount() {
		// TODO Auto-generated method stub
		return sysDeskDao.destroyCount();
	}
	@Override
	public List docBorrowCount(String month) {
		// TODO Auto-generated method stub
		return sysDeskDao.docBorrowCount(month);
	}
	@Override
	public int queryBorrowGzryCount() {
		// TODO Auto-generated method stub
		Map<String,Object> m = new HashMap();
		Employee emp = (Employee) servletRequest.getSession().getAttribute(Consts.userkey);
		m.put("param", "notice");
		m.put("empId", emp.getEmployeeId());
		int total = borrowReminderDao.findBorrowNoticeCountList(m);
		return total;
	}
}
