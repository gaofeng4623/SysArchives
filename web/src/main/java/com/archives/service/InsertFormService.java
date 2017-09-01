package com.archives.service;

import java.util.List;

import com.archives.pojo.InsertDetail;
import com.archives.pojo.Insertform;
import com.system.workflow.activiti.commons.Pager;

public interface InsertFormService {
	public int saveInsertForm(String guids,String insertTitle, String insertRemark) throws Exception;
	public Pager findInsertFormPage(Insertform insertform,int start, int totalSize );
	public Pager findInsertFormDetailPage(String formid,int start, int totalSize );
	public List<InsertDetail> findInsertformDetail(String formid);
}

