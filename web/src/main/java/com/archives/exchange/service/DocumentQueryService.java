package com.archives.exchange.service;

import java.util.List;

import com.archives.pojo.DocumentMetaData;

public interface DocumentQueryService {
	
	public List<DocumentMetaData> queryInfo(int index);

	public int queryDocMax(String docYear);
	
	public int queryDocMin(String docYear);
	
}
