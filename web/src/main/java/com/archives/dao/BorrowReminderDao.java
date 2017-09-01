package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.Borrow;
import com.archives.pojo.BorrowReminder;

public interface BorrowReminderDao {

	List<Borrow> findBorrowSheet(@Param("paraMap")Map<String, Object> paraMap);

	int findBorrowSheetCountList(@Param("paraMap")Map<String, Object> paraMap);

	void saveBorrowReminder(BorrowReminder borrowReminder);

	Borrow queryBorrow(@Param("paraMap")Map<String, String> paraMap);

	List<BorrowReminder> findBorrowNotice(@Param("m")Map<String, Object> m);

	int findBorrowNoticeCountList(@Param("m")Map<String, Object> m);


}
