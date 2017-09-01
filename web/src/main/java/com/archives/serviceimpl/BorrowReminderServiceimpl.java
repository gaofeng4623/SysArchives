package com.archives.serviceimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.dao.BorrowReminderDao;
import com.archives.pojo.Borrow;
import com.archives.pojo.BorrowReminder;
import com.archives.service.BorrowReminderService;
import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;
@Service
public class BorrowReminderServiceimpl implements BorrowReminderService {
	@Resource
	private BorrowReminderDao borrowReminderDao;

	@Override
	public Pager findBorrowSheet(String userName, String depName,String dayNum, int start, int rows) {
		Map<String,Object> m = new HashMap();
		m.put("start", start);
		m.put("rows", rows);
		m.put("userName", userName);
		m.put("depName", depName);
		m.put("dayNum", dayNum);
		List<Borrow> list = borrowReminderDao.findBorrowSheet(m);
		int total = borrowReminderDao.findBorrowSheetCountList(m);
		return new Pager(total, list);
	}

	@Override
	public Result addBorrowReminder(List<Map<String, String>> list, Employee emp) {
		Result result = new Result();
		try {
			for (Map<String, String> map : list) {
				BorrowReminder borrowReminder = new BorrowReminder();
				borrowReminder.setBorrowId(map.get("borrowId"));
				borrowReminder.setEmpId(map.get("userId"));
				borrowReminder.setSendEmpId(emp.getEmployeeId());
				borrowReminder.setSendEmpName(emp.getEmployeeName());
				borrowReminder.setSendDate(new Date());
				borrowReminderDao.saveBorrowReminder(borrowReminder);
			}
			
				result.setMessage("催还信息发送成功！");
				result.setStatus(0);
				return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("催还信息发送失败！");
			result.setStatus(1);
		}
		return result;
	}

	@Override
	public Pager findBorrowNotice(String sendBeginDate, String sendEndDate, String param, Employee emp,
			int start, int rows) {
		Map<String,Object> m = new HashMap();
		m.put("start", start);
		m.put("rows", rows);
		m.put("sendBeginDate", sendBeginDate);
		m.put("sendEndDate", sendEndDate);
		m.put("param", param);
		m.put("empId", emp.getEmployeeId());
		List<BorrowReminder> list = borrowReminderDao.findBorrowNotice(m);
		int total = borrowReminderDao.findBorrowNoticeCountList(m);
		return new Pager(total, list);
	}


}
