package com.archives.exchange.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.exchange.dao.DocHandlerDao;
import com.archives.exchange.service.DocumentQueryService;
import com.archives.pojo.DocumentMetaData;

@Service("queryDoc")
public class DocumentQueryServiceImpl implements DocumentQueryService {
	@Resource
	private DocHandlerDao docHandlerDao;

	@Override
	public List<DocumentMetaData> queryInfo(int index) {
		return docHandlerDao.queryInfo(index);
	}

	@Override
	public int queryDocMax(String docYear) {
		return docHandlerDao.queryDocMax(docYear);
	}

	@Override
	public int queryDocMin(String docYear) {
		return docHandlerDao.queryDocMin(docYear);
	}
	
	
}
