package com.archives.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.archives.dao.ArchivesInfoMapperDao;
import com.archives.pojo.ArchivesInfo;

//补全流水号
@Controller
@RequestMapping("/flowId")
public class FlowidController {
	@Resource
	private ArchivesInfoMapperDao archivesInfoMapperDao;
	private static final Logger logger = LoggerFactory
			.getLogger(FlowidController.class);
	
	
	@RequestMapping("/create.do")
	public void arcHistoryTable() {
		List<ArchivesInfo> lists = archivesInfoMapperDao.findArchivesByEmptyFlowId();
		logger.info("流水号为空的数据有" + lists.size() + "条");
		for (ArchivesInfo dbInfo : lists) {
			String year = dbInfo.getYear();
			String tnum = dbInfo.getNumber().replaceAll("^[0]*", ""); //去掉开头编码
			if (tnum.length() < 6) {
				if (tnum.length() == 1) {
					tnum = "000" + tnum;
				} else if (tnum.length() == 2) {
					tnum = "00" + tnum;
				} else if (tnum.length() == 3) {
					tnum = "0" + tnum;
				}
			}
			String flowId = year + "-" + dbInfo.getMarkType() + "-" + tnum; // 库房整理用的流水号,采用年度+档案号的形式
			dbInfo.setFlowid(flowId);
			archivesInfoMapperDao.updateByPrimaryKeySelective(dbInfo);
			logger.info(flowId + " " + dbInfo.getCaseno());
		}
	}
}
