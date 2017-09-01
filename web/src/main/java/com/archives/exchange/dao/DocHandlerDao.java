package com.archives.exchange.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.DocumentMetaData;


public interface DocHandlerDao {

	public List<DocumentMetaData> queryInfo(@Param("drid") int drid);

	public int queryDocMax(@Param("docYear") String docYear);
	
	public int queryDocMin(@Param("docYear") String docYear);
}
