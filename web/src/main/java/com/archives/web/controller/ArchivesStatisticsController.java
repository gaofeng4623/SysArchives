package com.archives.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.archives.service.ArcStatisticsService;

@Controller
@RequestMapping("/archivesStatistics")
public class ArchivesStatisticsController {
	@Resource
	ArcStatisticsService arcStatisticsService;
	
	@RequestMapping("/initStatistics.do")
	@ResponseBody
	public Map<String,Object> initStatistics(){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		dataMap.put("PlaceOnCount", arcStatisticsService.queryPlaceOnCount());
		dataMap.put("BorrowCount", arcStatisticsService.queryBorrowCount());
		
		return dataMap;
	}
	

}
