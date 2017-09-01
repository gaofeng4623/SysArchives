package com.archives.dao;

import java.util.List;

import com.archives.pojo.ArcStatistics;

public interface ArcStatisticsDao {
	List<ArcStatistics> queryPlaceOnCount();
	
	List<ArcStatistics> queryBorrowCount();
}