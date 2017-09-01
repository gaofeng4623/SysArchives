package com.archives.service;
import java.util.List;

import com.archives.pojo.Docborrowdetail;
import com.system.workflow.activiti.commons.Pager;

public interface DocborrowdetailService {
	public List selectByBorrowId(String BorrowId);
	Pager findAllDocborrowdetail(Docborrowdetail docborrowdetail, int start, int totalSize)throws Exception;


}
