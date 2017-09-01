package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.ArcHistory;

public interface ArcHistoryDao {
	public List<ArcHistory> findArcHistoryForPage(@Param("paraMap") Map<String, Object> paraMap);
	public int findCountArcHistoryForPage(@Param("paraMap") Map<String, Object> paraMap);
	int insertArcHistory(ArcHistory record);
}