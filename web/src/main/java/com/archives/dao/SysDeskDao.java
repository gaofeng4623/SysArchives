package com.archives.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysDeskDao {
	
	// 当前库存
	int queryArchiveCount();
	// 借阅数量
	int queryborrowArchiveCount();
	// 催归数量
	int queryCollectionCount();
	// 催还数量
	int queryBorrowSheetCount();
	// 借阅待交付数量
	int queryBorrowNotDeliveredCount();
	// 统计某一借阅人借阅数量
	int queryUserBorrowArchiveCount(String userid);
	// 统计某一借阅人归还数量
	int queryUserReturnArchiveCount(String userid);
	// 统计待交付给某一借阅人数量
	int queryUserBorrowNotDeliveredCount(String userid);
	// 统计借阅实借阅数量
	int queryBorrowRoomBorrowArchiveCount();
	// 统计借阅实归还数量
	int queryBorrowRoomReturnArchiveCount();
	// 统计待交付给借阅实数量
	int queryBorrowRoomBorrowNotDeliveredCount();
	// 统计待交付给借阅实数量
	public List eachMonthCount(@Param("depot")String depot,@Param("month")String month);
	public List caseProertyCount();
	public List borrowCount(@Param("month")String month);
    // 2号库
	int twoLibraryCount();
	// 3号库
	int threeLibraryCount();
	// 5号库
	int fiveLibraryCount();
	int destroyArchives();
	//文书首页统计
	public int docCount();
	public int queryBorrowRoomBorrowCount();
	public int querBorrowSheetCount(@Param("day")int depot);//
	public int destroyCount();
	public List docBorrowCount(@Param("month")String month);
}