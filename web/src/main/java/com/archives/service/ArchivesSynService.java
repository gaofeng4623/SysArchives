package com.archives.service;

import javax.jws.WebService;

import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.SynLog;

/**
 * @info 同步档案业务数据
 * @author GF
 */
@WebService
public interface ArchivesSynService {
	
	public ArchivesInfo selectByCaseNo(String caseNo);
	
	public void synHistoryArchives(ArchivesInfo info, String time);
	
	public void insertSynLog(SynLog synLog);
}
