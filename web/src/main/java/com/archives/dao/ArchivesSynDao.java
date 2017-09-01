package com.archives.dao;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.Itemstatus;
import com.archives.pojo.SynLog;

public interface ArchivesSynDao {

	String findArcByArchivesNo(@Param("caseNo")String caseNo);

	int saveArchivesInfo(ArchivesInfo archivesInfo);

	void updateArchivesInfo(ArchivesInfo archivesInfo);

	void saveItemstatus(Itemstatus itemstatus);
	
	void insertSynLog(SynLog synLog);

}
