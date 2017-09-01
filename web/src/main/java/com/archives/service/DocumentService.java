package com.archives.service;
import java.util.List;

import com.archives.pojo.Document;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface DocumentService {
	

	Pager findDocumentForPage(Document cocument, int start, int totalSize)throws Exception;

	public Document findDocumentById(int guid) throws Exception;
	Result updBoxGuid(Document document)throws Exception;
	Result updateDocketNo(String boxGuid,int docGuid)throws Exception;
	public Result destroyAllByIds(List<String> idList)throws Exception;
	Pager findDocumentBoxForPage(Document cocument, int start, int totalSize)throws Exception;
}
