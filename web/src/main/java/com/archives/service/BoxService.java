package com.archives.service;
import java.util.List;

import com.archives.pojo.Box;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface BoxService {
	

	Pager findBoxForPage(Box box, int start, int totalSize)throws Exception;
	public Box findBoxById(int guid) throws Exception;
	public Box findBoxByParams(Box box) throws Exception;
	Result saveBox(Box box);
	Result updBox(Box box);
	Result delBox(List<Integer> idList);
	public Result findAllByIds(List<String> idList);
	public Pager findBoxForShelves(Box box, int start, int rows) throws Exception;

}
