package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.Borrow;
import com.archives.pojo.BorrowDetail;

public interface BorrowBackDao {

	List<BorrowDetail> findBorrowDetail(@Param("m")Map<String, Object> m);

	List<Borrow> findBorrowByCondtion(@Param("m")Map<String, Object> m);

	void updateBorrowAll(Borrow borrow);

	BorrowDetail queryBorrowDetailById(@Param("guid")String guid);

	void updateBorrowDetail(BorrowDetail borrowDetail);

	ArchivesInfo queryArchivesInfo(@Param("itemGuid")String itemGuid);

	void updateArchivesInfo(ArchivesInfo archivesInfo);

}
