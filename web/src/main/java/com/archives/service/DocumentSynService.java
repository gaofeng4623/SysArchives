package com.archives.service;

import com.archives.pojo.Document;


/**
 * @info 同步文书档案数据
 * @author GF
 */
public interface DocumentSynService {
	public int saveDocument(Document doc);
	public int selectMaxBusinessId();
}
