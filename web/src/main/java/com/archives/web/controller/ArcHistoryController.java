package com.archives.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.archives.pojo.ArcHistory;
import com.archives.service.ArcHistoryService;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/arcHistory")
public class ArcHistoryController {
	@Resource
	private ArcHistoryService arcHistoryService;
	// 查询所有数据
		@RequestMapping("/arcHistoryTable.do")
		@ResponseBody
		public Pager arcHistoryTable(ArcHistory arcHistory, int rows, int page) {
			Pager resultPage= null; 
			
			int start = (page -1) * rows;
			resultPage = arcHistoryService.findArcHistoryPage(arcHistory, start, rows);
			return resultPage;
			
		}
}
