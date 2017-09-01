package com.archives.exchange.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.ArchivesInfo;
public interface ArcHandlerDao {
	
	public List<ArchivesInfo> queryInfo();
	public int queryInfoCount();
	public List<ArchivesInfo> queryIntervalInfo(@Param("targetDate") String targetDate, @Param("now") String now);
	public int queryIntervalInfoCount(@Param("targetDate") String targetDate, @Param("now") String now);
	public int queryIntervalAddInfoCount(@Param("targetDate") String targetDate, @Param("now") String now);
}
