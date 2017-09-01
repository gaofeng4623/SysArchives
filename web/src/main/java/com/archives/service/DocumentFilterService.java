package com.archives.service;

import java.util.List;

import com.archives.pojo.Document;
import com.system.base.pojo.Result;

public interface DocumentFilterService {

	public int getTotal();

	public List<Document> queryDocForPage(int start, int rows);

	public Result createOrReflectBox(Document doc); //根据文书档案创建或者关联盒子

}
