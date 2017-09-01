package com.archives.serviceimpl;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.archives.dao.ArchivesInfoMapperDao;
import com.archives.dao.ArchivesSynDao;
import com.archives.exchange.serviceimpl.ArcHandlerServiceImpl;
import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.Itemstatus;
import com.archives.pojo.SynLog;
import com.archives.service.ArchivesSynService;

@WebService
@Service("synarc")
public class ArchivesSynServiceImpl implements ArchivesSynService {
	@Resource
	private ArchivesSynDao archivesSynDao;
	@Resource
	private ArchivesInfoMapperDao archivesInfoMapperDao;
	
	private static final Logger logger = LoggerFactory
			.getLogger(ArcHandlerServiceImpl.class);

	/**
	 * 历史数据初始化
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void synHistoryArchives(ArchivesInfo info, String time) {
		try {
			if (!StringUtils.isEmpty(info.getCaseno())) {
				String caseNo = archivesSynDao.findArcByArchivesNo(info.getCaseno());
				if (StringUtils.isEmpty(caseNo)) {
					logger.info("开始写入新增档案" + caseNo);
					info.setStatus("0");
					archivesSynDao.saveArchivesInfo(info);// 新增
					int guid = info.getGuid();
					Itemstatus itemstatus = new Itemstatus();
					itemstatus.setItemNo(info.getCaseno()); //默认同步主表的标题
					itemstatus.setItemid(0); //设置主卷的分表id为0
					itemstatus.setInfoid(guid);
					itemstatus.setStatus("0");
					archivesSynDao.saveItemstatus(itemstatus);
					insertSynLog(new SynLog(guid, 0, time));
				} else {
					//logger.info("更新归档标识 - " + caseNo);
					info.setUpdated(1);
					archivesSynDao.updateArchivesInfo(info);// 修改
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArchivesInfo selectByCaseNo(String caseNo) {
		return archivesInfoMapperDao.selectByCaseNo(caseNo);
	}

	@Override
	public void insertSynLog(SynLog synLog) {
		archivesSynDao.insertSynLog(synLog);
	}

}
