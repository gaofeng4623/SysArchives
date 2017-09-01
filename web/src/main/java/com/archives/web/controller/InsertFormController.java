package com.archives.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.ArchivesInfo;
import com.archives.service.ArchivesInfoService;
import com.archives.service.InsertFormService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/insertForm")
public class InsertFormController {
	
	@Resource
	private ArchivesInfoService archivesInfoService;
	@Resource
	private InsertFormService insertFormService;

	// 查询所有数据
	@RequestMapping("/findArchivesInfo.do")
	@ResponseBody
	public Pager findArchivesInfo(ArchivesInfo archivesInfo, int rows, int page)throws Exception {
		Pager resultPage= null;
	
		int start = (page -1) * rows;
		resultPage = archivesInfoService.findLocationByArchiveForPage(archivesInfo, start, rows);
		
		return resultPage;
	}

	// 加载插卷单
	@RequestMapping("/selectInsertArc.do")
	@ResponseBody
	public Pager selectInsertArc(ArrayList<ArchivesInfo> archivesInfoList, int rows, int page)throws Exception {
		Pager resultPage= null;
		if(archivesInfoList.size() == 0){
			archivesInfoList = new ArrayList<ArchivesInfo>();
		}
		resultPage = new Pager(1, archivesInfoList);
		return resultPage;
	}
	//保存插卷单
	@RequestMapping("/saveInsertForm.do")
	@ResponseBody
	public Result saveInsertForm(String guids,String insertTitle, String insertRemark){
		Result result = new Result();
		try {
			insertFormService.saveInsertForm(guids, insertTitle, insertRemark);
			result.setStatus(0);
			result.setMessage("插卷单保存成功");
		} catch (Exception e) {
			result.setStatus(1);
			result.setMessage("插卷单保存失败");
		}
		return result;
	}
	// 获取打印页面信息
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getPrintInfo.do")
	public ModelAndView getPrintInfo(String guidStr){
		Map dataMap = new HashMap();
		List<String> idList = new ArrayList<String>();
		if(null != guidStr && !"".equals(guidStr)){
			for(String guid : guidStr.split(",")){
				if(StringUtils.isEmpty(guid)){
					continue;
				}
				idList.add(guid);
			}
		}
		try {
			List<ArchivesInfo> datalist = archivesInfoService.findAllByIds(idList);
			List<ArchivesInfo> datalistTemp = new ArrayList();
			for(ArchivesInfo archivesInfo:datalist){
				String fjstatus= archivesInfo.getFjStatus();
				switch(fjstatus){
					case "0":archivesInfo.setFjStatus("未上架");break;
					case "1":archivesInfo.setFjStatus("在库");break;
					case "2":archivesInfo.setFjStatus("下架");break;
					case "3":archivesInfo.setFjStatus("销毁");break;
					case "4":archivesInfo.setFjStatus("待上架");break;
				}
				datalistTemp.add(archivesInfo);
			}
			dataMap.put("datalist", datalistTemp);
		} catch (Exception e) {
			dataMap = new HashMap();
			dataMap.put("datalist", new ArrayList());
		}
		return new ModelAndView("print/insertFormPrint",dataMap);
	}
}
