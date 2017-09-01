package com.archives.service;

import java.util.List;
import java.util.Map;

import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface BorrowReminderService {

	Pager findBorrowSheet(String userName, String depName, String dayNum, int start, int rows);

	Result addBorrowReminder(List<Map<String, String>> list, Employee emp);

	Pager findBorrowNotice(String sendBeginDate, String sendEndDate, String param, Employee emp, int start,
			int rows);

}
