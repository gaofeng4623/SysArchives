package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.Document;
import com.system.base.pojo.Result;

public interface DocumentDao {
	
	int deleteByPrimaryKey(Integer guid);

	int insert(Document record);

	int insertSelective(Document record);

	Document selectByPrimaryKey(Integer guid);

	int updateByPrimaryKeySelective(Document record);

	int updateDocketNo(Document record);

	int updateByPrimaryKey(Document record);

	public List<Document> findDocumentForPage(@Param("paraMap") Map paraMap);

	public int findCountDocumentForPage(@Param("paraMap") Map paraMap);
	//box
	public List<Document> findDocumentBoxForPage(@Param("paraMap") Map paraMap);
	public int findCountDocumentBoxForPage(@Param("paraMap") Map paraMap);

	void takeDown(@Param("documentId") String documentId); // 出库
	// public List<Document> findAllByIds(@Param("idList")List<String> idList);

	public int getDocFormSeq(Result result); // 借阅单号

	public List<Document> findAllByIdsForPage(@Param("paraMap") Map paraMap);

	public int findCountfindAllByIdsForPage(@Param("paraMap") Map paraMap);

	List selectByDocketNo(@Param("docketNo") String docketNo);

	public List<Document> findDocketNoByIds(@Param("idList") List<String> idList);
	
	public int updateBoxNumForDoc(@Param("guid") int guid, @Param("boxNumber") int boxNumber); //更新文书的盒子主键
	public int destroyByIds(@Param("idList")List<String> idList);
	public int updateDocument(Document document);//批量更新
	int updateboxNo(Document record);
}