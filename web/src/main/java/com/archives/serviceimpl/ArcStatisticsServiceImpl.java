package com.archives.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.dao.ArcStatisticsDao;
import com.archives.pojo.ArcStatistics;
import com.archives.service.ArcStatisticsService;

@Service
public class ArcStatisticsServiceImpl implements ArcStatisticsService{
	@Resource
	private ArcStatisticsDao arcStatisticsDao;
	@Override
	public int[] queryPlaceOnCount() {
		
		List<ArcStatistics> placeOnCountList = arcStatisticsDao.queryPlaceOnCount();
		
		int[] placeOnCount = new int[12];
		for(int i=0;i<12;i++){
			placeOnCount[i] = 0;
		}
		
		for(ArcStatistics placeOnCountTemp:placeOnCountList){
			int i = Integer.valueOf(placeOnCountTemp.getPlaceOnMonth());
			placeOnCount[i-1] = placeOnCountTemp.getPlaceOnCount();
		}
		return placeOnCount;
	}
	
	@Override
	public int[] queryBorrowCount() {
		List<ArcStatistics> borrowCountList = arcStatisticsDao.queryBorrowCount();
		
		int[] borrowCount = new int[12];
		for(int i=0;i<12;i++){
			borrowCount[i] = 0;
		}
		
		for(ArcStatistics borrowCountTemp:borrowCountList){
			int i = Integer.valueOf(borrowCountTemp.getBorrowMonth());
			borrowCount[i-1] = borrowCountTemp.getBorrowCount();
		}
		return borrowCount;

	}
	

}
