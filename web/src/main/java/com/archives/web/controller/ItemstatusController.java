package com.archives.web.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.archives.common.ArchiveStatus;
import com.archives.common.Consts;
import com.archives.common.MarkUtil;
import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.Itemstatus;
import com.archives.service.ArcHistoryService;
import com.archives.service.ArchivesInfoService;
import com.archives.service.ItemstatusService;
import com.system.base.pojo.Result;

@Controller
@RequestMapping("/itemstatus")
public class ItemstatusController {
	private static final Logger logger = LoggerFactory
			.getLogger(ItemstatusController.class);
	@Resource
	private ItemstatusService itemstatusService;
	
	@Resource
	private ArchivesInfoService archivesInfoService;
	
	@Resource
	private ArcHistoryService arcHistoryService;

	// 查询所有数据
	@RequestMapping("/findByArchivesNo.do")
	@ResponseBody
	public List<Itemstatus> findByArchivesNo(String  infoid) {
		Integer infoidInt = Integer.parseInt(infoid);
		return itemstatusService.findByArchiveId(infoidInt);
	}
	
	
	@RequestMapping("/rewriteRfid.do")
	@ResponseBody
	public Result rewriteRfid(Integer itemGuid, String rfid, String status) throws Exception{
		Result result  = itemstatusService.updateRfidByPrimaryKey(itemGuid, rfid, status);
		return result;
	}
	
	
	@RequestMapping("/creatRfid.do")
	@ResponseBody
	public Result creatRfid(Integer itemGuid, Integer itemType, Integer itemid) throws Exception{
		Result result = new Result();
		try {
			ArchivesInfo arc = archivesInfoService.findByItemGuid(itemGuid);
			String rfid = MarkUtil.generateMarkNo(Consts.ARCHIVE_PREFIX, String.valueOf(arc.getGuid()), itemid, itemType);
			logger.info("生成标签号: " + rfid);
			result.setData(rfid);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//创建分卷
	@RequestMapping("/creatItem.do")
	@ResponseBody
	public Result creatItem(Integer infoId, int itemType) throws Exception{
		Result result = new Result();
		try {
			String suffix = null;
			int itemId = itemstatusService.getNextIdByItemType(infoId, itemType);
			ArchivesInfo info = archivesInfoService.findByInfoId(infoId);
			String caseNo = info.getCaseno();
			switch(itemType) {
				case 1 : suffix = "正卷" + itemId; break;
				case 2 : suffix = "副卷" + itemId; break;
			}
			if (suffix != null) {
				Itemstatus item = new Itemstatus();
				item.setStatus(ArchiveStatus.NOT_SHELVES.getValue()); //未上架状态
				item.setItemid(itemId);
				item.setItemType(itemType);
				item.setInfoid(infoId);
				item.setItemNo(caseNo + "-" + suffix);
				itemstatusService.createItemStatus(item);
				logger.info("生成分卷号: " + itemId + " 分卷标题" + caseNo + suffix);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("itemId", itemId);
				data.put("itemGuid", item.getGuid());
				result.setData(data);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/deleteRfid.do")
	@ResponseBody
	public Result deleteRfid(Integer guid) throws Exception{
		Result result = new Result();
		try {
			itemstatusService.deleteById(guid);
			logger.info("删除分卷:" + guid);
			result.setStatus(0);
		} catch(Exception e) {
			result.setStatus(1);
			e.printStackTrace();
		}
		return result;
	}
	
	
//	// 查询所有数据
//	@RequestMapping("/findById.do")
//	@ResponseBody
//	public ModelAndView findById(String id, String ah, String arcid) {
//		ModelAndView mv = new ModelAndView("archivesInfoWin");
//		Map map = new HashMap();
//		map.put("id", id);
//		map.put("ah", ah);
//		map.put("arcid", arcid);
//		ArchivesInfo archivesInfo = archivesInfoService.findById(map);
//		mv.addObject("archivesInfo", archivesInfo);
//		return mv;
//	}
	

}
