package com.archives.web.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.DoorWarning;
import com.archives.pojo.Itemstatus;
import com.archives.pojo.LocationConfig;
import com.archives.pojo.LocationType;
import com.archives.service.ArchivesInfoService;
import com.archives.service.ItemstatusService;
import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.util.common.Consts;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/archivesQuery")
public class ArchivesQueryController {
//private Log logger = LogFactory.getLog(ArchivesInfoController.class);
	
	@Resource
	private ArchivesInfoService archivesInfoService;
	@Resource
	private ItemstatusService itemstatusService;
	// 查询所有数据
	@RequestMapping("/findArchivesInfo.do")
	@ResponseBody
	public Pager findArchivesInfo(ArchivesInfo archivesInfo, int rows, int page)throws Exception {
		Pager resultPage= null;
		int start = (page -1) * rows;
		resultPage = archivesInfoService.findLocationByArchiveForPage(archivesInfo, start, rows);
		return resultPage;
	}
	// 查询所有数据
		@RequestMapping("/synArchivesInfo.do")
		@ResponseBody
		public Pager synArchivesInfo(ArchivesInfo archivesInfo, int rows, int page)throws Exception {
			Pager resultPage= null;
			int start = (page -1) * rows;
			resultPage = archivesInfoService.synArchivesInfo(archivesInfo, start, rows);
			return resultPage;
		}
	// 根据guid查询所有数据
	@SuppressWarnings("static-access")
	@RequestMapping("/findById.do")
	@ResponseBody
	public ModelAndView findById(Integer itemGuid)throws Exception {
		ModelAndView mv = new ModelAndView("alert/archivesInfoDetail");
		ArchivesInfo archivesInfo = archivesInfoService.findByItemGuid(itemGuid);
		List<Itemstatus> varList = itemstatusService.findByItemstatusNo(archivesInfo.getGuid());
		
		if(null!= archivesInfo.getPutonrecorddate() && !"".equals(archivesInfo.getPutonrecorddate())){
			archivesInfo.setPutonrecorddate(archivesInfo.getPutonrecorddate().substring(0, 10));
		}
		if(null!= archivesInfo.getSettledate() && !"".equals(archivesInfo.getSettledate())){
			archivesInfo.setSettledate(archivesInfo.getSettledate().substring(0, 10));
		}
		if(null!= archivesInfo.getPlaceondate() && !"".equals(archivesInfo.getPlaceondate())){
			archivesInfo.setPlaceondate(archivesInfo.getPlaceondate().substring(0, 10));
		}
		if(null!= archivesInfo.getRegistertime() && !"".equals(archivesInfo.getRegistertime())){
			archivesInfo.setRegistertime(archivesInfo.getRegistertime().substring(0, 10));
		}
		mv.addObject("arc", archivesInfo);
		mv.addObject("varList", varList);
		/*String casename = archivesInfo.getCasename().replaceAll("\n", " ");
		casename = casename.replaceAll("：", "");
		archivesInfo.setCasename(casename);
		System.out.println(casename);*/
		return mv;
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
	
	// 获取打印页面信息--王芙麟  2016-11-24
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getCoverPrintInfo.do")
	public ModelAndView getCoverPrintInfo(String guidStr){
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
				if(null!= archivesInfo.getPutonrecorddate() && !"".equals(archivesInfo.getPutonrecorddate())){
					archivesInfo.setPutonrecorddate(archivesInfo.getPutonrecorddate().substring(0, 10));
				}
				if(null!= archivesInfo.getSettledate() && !"".equals(archivesInfo.getSettledate())){
					archivesInfo.setSettledate(archivesInfo.getSettledate().substring(0, 10));
				}
				if(null!= archivesInfo.getPlaceondate() && !"".equals(archivesInfo.getPlaceondate())){
					archivesInfo.setPlaceondate(archivesInfo.getPlaceondate().substring(0, 10));
				}
				datalistTemp.add(archivesInfo);
			}
			dataMap.put("datalist", datalistTemp);
		} catch (Exception e) {
			dataMap = new HashMap();
			dataMap.put("datalist", new ArrayList());
		}
		return new ModelAndView("print/coverPrint",dataMap);
	}
	
	@RequestMapping("/queryArcHistory.do")
	public ModelAndView queryArcHistory(String guid) throws Exception{
		ModelAndView mv = new ModelAndView("alert/arcHistoryManage");
		mv.addObject("infoId", guid);
		return mv;
	}
	@RequestMapping("/updateChives.do")
	public ModelAndView updateChives(Integer itemGuid) throws Exception{
		ModelAndView mv = new ModelAndView("alert/updateArchives");
		ArchivesInfo info=archivesInfoService.findByItemGuid(itemGuid); //分卷GUID
		   mv.addObject("info",info);
		return mv;
	}
	@RequestMapping("/saveChives.do")
	@ResponseBody
	public Result saveChives(ArchivesInfo archivesInfo)throws Exception{
		return archivesInfoService.update(archivesInfo);
	
	}
}
