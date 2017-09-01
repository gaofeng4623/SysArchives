package com.archives.service;

import java.util.List;

import com.system.workflow.activiti.commons.WFComment;

public interface BorrowComments {
	public List<WFComment> findProcessCommentsByBorrowId(String processInstanceId) ;
	public List<WFComment> findProcessCommentsByInstanceId(String processInstanceId) ;
}
