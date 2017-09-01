package com.archives.serviceimpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.dao.DocborrowdetailDao;
import com.archives.pojo.Docborrowdetail;
import com.archives.service.DocborrowdetailService;
import com.system.workflow.activiti.commons.Pager;

@Service
public class DocborrowdetailServiceImpl implements DocborrowdetailService {

	@Resource
	private DocborrowdetailDao docborrowdetailDao;

	@Override
	public List selectByBorrowId(String BorrowId) {
		// TODO Auto-generated method stub
		return docborrowdetailDao.selectByBorrowId(BorrowId);
	}

	@Override
	public Pager findAllDocborrowdetail(Docborrowdetail docborrowdetail, int start,
			int totalSize) throws Exception {
		Map m = new HashMap();
/*        m.put("borrowDate",document.getBorrowDate());
        m.put("borrowContent",document.getBorrowContent());*/
		m.put("borrowId", docborrowdetail.getBorrowid());
		m.put("status", docborrowdetail.getStatus());
		
		m.put("start", start);
		m.put("rows", totalSize);
		
		List documents = docborrowdetailDao.findDocborrowdetailForPage(m);
		int total = docborrowdetailDao.findCountDocborrowdetailForPage(m);
		return new Pager(total, documents);
	}

	
	


}
