package com.archives.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.archives.service.BorrowReminderService;
import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.util.common.Consts;
import com.system.workflow.activiti.commons.Pager;

/**
 * 借阅催还
 * @author zfn
 *
 */
@Controller
@RequestMapping("/borrowReminder")
public class BorrowReminderController {
	@Resource
	private BorrowReminderService borrowReminderService;
	@Autowired
	private HttpServletRequest servletRequest;
	
	/**
	 * 查询已选列表
	 * @return
	 */
	@RequestMapping("/findBorrowSheet.do")
	@ResponseBody
	public Pager findBorrowSheet(String userName,String depName,String dayNum,int rows, int page){
		Pager resultPage= null; 
		try {
			int start = (page -1) * rows;
			resultPage = borrowReminderService.findBorrowSheet(userName,depName,dayNum,start, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultPage;
	}
	/**
	 * 插入催还
	 * @param borrowId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/addBorrowReminder.do")
	@ResponseBody
	public Result addBorrowReminder(String[] borrowId,String[] userId){
		List<Map<String, String>> list = new ArrayList();
		int legth = borrowId.length;
		for(int i=0; i< legth;i++){
			Map<String, String> map = new HashMap<String, String>();
			map.put("borrowId", borrowId[i]);
			map.put("userId", userId[i]);
			list.add(map);
		}
		Employee emp = (Employee) servletRequest.getSession().getAttribute(Consts.userkey);
		return borrowReminderService.addBorrowReminder(list,emp);
	}

	/**
	 * 查询发送通知列表
	 * @return
	 */
	@RequestMapping("/findBorrowNotice.do")
	@ResponseBody
	public Pager findBorrowNotice(String sendBeginDate,String sendEndDate,String param,int rows, int page){
		Pager resultPage= null; 
		Employee emp = (Employee) servletRequest.getSession().getAttribute(Consts.userkey);
		try {
			int start = (page -1) * rows;
			resultPage = borrowReminderService.findBorrowNotice(sendBeginDate,sendEndDate,param,emp,start, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultPage;
	}
}
