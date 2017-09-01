package com.archives.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.InsertDetail;
import com.archives.pojo.Insertform;
import com.archives.service.InsertFormService;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/insertFormManage")
public class InsertFormManageController {
	@Resource
	private InsertFormService insertFormService;
	// 查询所有数据
		@RequestMapping("/insertFormTable.do")
		@ResponseBody
		public Pager insertFormTable(Insertform insertform, int rows, int page) {
			Pager resultPage= null; 
			
			int start = (page -1) * rows;
			resultPage = insertFormService.findInsertFormPage(insertform, start, rows);
			return resultPage;
			
		}
		
		@RequestMapping("/insertFormDetailTable.do")
		@ResponseBody
		public Pager insertFormDetailTable(String formid, int rows, int page) {
			Pager resultPage= null; 
			
			int start = (page -1) * rows;
			resultPage = insertFormService.findInsertFormDetailPage(formid, start, rows);
			return resultPage;
			
		}
		// 获取打印页面信息
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping("/getPrintInfo.do")
		public ModelAndView getPrintInfo(String formid){
			Map dataMap = new HashMap();
			try {
				List<InsertDetail> datalist = insertFormService.findInsertformDetail(formid);
				List<InsertDetail> datalistTemp = new ArrayList();
				for(InsertDetail insertDetail:datalist){
					String fjstatus= insertDetail.getFjStatus();
					switch(fjstatus){
						case "0":insertDetail.setFjStatus("未上架");break;
						case "1":insertDetail.setFjStatus("在库");break;
						case "2":insertDetail.setFjStatus("下架");break;
						case "3":insertDetail.setFjStatus("销毁");break;
						case "4":insertDetail.setFjStatus("待上架");break;
					}
					datalistTemp.add(insertDetail);
				}
				dataMap.put("datalist", datalistTemp);
			} catch (Exception e) {
				dataMap = new HashMap();
				dataMap.put("datalist", new ArrayList());
			}
			return new ModelAndView("print/insertFormPrint",dataMap);
		}
}
