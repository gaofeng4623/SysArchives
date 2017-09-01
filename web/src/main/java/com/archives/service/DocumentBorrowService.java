package com.archives.service;
import com.archives.pojo.Document;
import com.archives.pojo.DocumentBorrow;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface DocumentBorrowService {
	
	Pager findDocumenBorrowtForPage(DocumentBorrow cocument, int start, int totalSize)throws Exception;

	public DocumentBorrow findDocumentBorrowById(int guid) throws Exception;
	public Result saveDocumentBorrow(DocumentBorrow documentBorrow)throws Exception;
	public void takeDown(String infoId) throws Exception;  //下架
	//public List<Document> findAllByIds(Document document,List<String> idList) throws Exception;
	public String getDocFormSeq() throws Exception; //生成借阅单号
	public DocumentBorrow findDocumentBorrowByformNo(String formNo) throws Exception;
    Result confirmReturn(int borrowId, String returnPerson,String[] allDetailIds);
	Pager findAllByIdsForPage(Document document, int start, int totalSize,String documentGuids)throws Exception;
	public DocumentBorrow findByformNo(String formNo) throws Exception;


}
