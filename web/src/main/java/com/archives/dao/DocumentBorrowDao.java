package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.DocumentBorrow;
import com.system.base.pojo.Result;


public interface DocumentBorrowDao {
    int deleteByPrimaryKey(Integer guid);

    int insert(DocumentBorrow record);

    int insertSelective(DocumentBorrow record);

    DocumentBorrow selectByPrimaryKey(Integer guid);

    int updateByPrimaryKeySelective(DocumentBorrow record);

    int updateByPrimaryKey(DocumentBorrow record);
    public List<DocumentBorrow> findDocumentBorrowForPage(@Param("paraMap") Map paraMap);
   	public int findCountDocumentBorrowForPage(@Param("paraMap") Map paraMap);
	public int getDocFormSeq(Result result); // 借阅单号
	 DocumentBorrow  findDocumentBorrowByformNo(String formNo) throws Exception;
	 DocumentBorrow  findByformNo(String formNo) throws Exception;

}