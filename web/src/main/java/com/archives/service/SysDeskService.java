package com.archives.service;

import java.util.List;

public interface SysDeskService {
	
	public int queryArchiveCount();
	
	public int queryborrowArchiveCount();
	
	public int queryCollectionCount();
	
	public int queryBorrowSheetCount();
	
	public int queryBorrowNotDeliveredCount();
	public int queryBorrowGzryCount();//根据条件查询催还记录
	// 统计某一借阅人借阅数量
	public int queryUserBorrowArchiveCount();
	// 统计某一借阅人归还数量
	public int queryUserReturnArchiveCount();
	// 统计待交付给某一借阅人数量
	public int queryUserBorrowNotDeliveredCount();
	// 统计借阅实借阅数量
	public int queryBorrowRoomBorrowArchiveCount();
	// 统计借阅实归还数量
	public int queryBorrowRoomReturnArchiveCount();
	// 统计待交付给借阅实数量
	public int queryBorrowRoomBorrowNotDeliveredCount();
	// 统计销毁数量
	public int destroyArchives();
	// 查询各月归档量
	public List eachMonthCount(String depot,String month);
	public List caseProertyCount();
	public List borrowCount(String month);
	//2
	public int twoLibraryCount();
	//3
	public int threeLibraryCount();
	//5
	public int fiveLibraryCount();
	//文书首页统计
	//库存统计
	public int docCount();
	//借出统计
	public int queryBorrowRoomBorrowCount();
	public int querBorrowSheetCount();
	public int destroyCount();
	public List docBorrowCount(String month);

}

