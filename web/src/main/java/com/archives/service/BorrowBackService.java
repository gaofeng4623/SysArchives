package com.archives.service;

import java.util.List;

import com.archives.pojo.Borrow;
import com.archives.pojo.BorrowDetail;
import com.system.base.pojo.Result;

public interface BorrowBackService {

	List<BorrowDetail> findBorrowDetail(String formNo);

	List<Borrow> findBorrowByCondtion(String query_formNo, String query_archivesNo, String rfid);

	Result confirmReturn(String borrowId, String returnPerson,
			String[] allDetailIds);

}
