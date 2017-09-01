package com.archives.web.controller;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.archives.common.Consts;
import com.archives.common.MarkUtil;
import com.archives.service.ArcHistoryService;
import com.archives.service.BoxStatusService;
import com.archives.service.DocHistoryService;
import com.system.base.pojo.Result;

@Controller
@RequestMapping("/boxStatus")
public class BoxStatusController {
	private static final Logger logger = LoggerFactory
			.getLogger(BoxStatusController.class);
	@Resource
	private BoxStatusService boxStatusService;
	@Resource
	private DocHistoryService docHistoryService;
	@RequestMapping("/rewriteRfid.do")
	@ResponseBody
	public Result rewriteRfid(Integer guid, String rfid, String status) throws Exception{
		Result result  = boxStatusService.updateRfidByPrimaryKey(guid, rfid,status); 
		int infoId = boxStatusService.selectInfoIdByPrimaryKey(guid);
		docHistoryService.insertArcHistory(guid, "写入电子标签", null);
		return result;
	}
	@RequestMapping("/creatRfid.do")
	@ResponseBody
	public Result creatRfid(Integer guid, String rfid) throws Exception{
		Result result = new Result();
		if(null == rfid){
			rfid = MarkUtil.generateMarkNo(Consts.ARCHIVE_PREFIX, String.valueOf(guid), null, null);
		}
		result.setData(rfid);
		return result;
	}

}
