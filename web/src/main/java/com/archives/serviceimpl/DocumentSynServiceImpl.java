package com.archives.serviceimpl;

import javax.annotation.Resource;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.archives.dao.DocumentSynDao;
import com.archives.pojo.Document;
import com.archives.service.DocumentFilterService;
import com.archives.service.DocumentSynService;

@Service("syndoc")
public class DocumentSynServiceImpl implements DocumentSynService {
	private static final Logger logger = LoggerFactory
			.getLogger(DocumentSynServiceImpl.class);
	@Resource
	private DocumentSynDao documentSynDao;
	@Resource
	private DocumentFilterService documentFilterServiceImpl;

	@Override
	public int saveDocument(Document doc) {
		int drid = doc.getDrid(); //业务主键
		logger.info("准备同步--" + String.valueOf(doc.getTitle()));
		Document queryDoc = documentSynDao.queryDocument(drid);
		if (queryDoc == null && !isEmpty(doc.getDocketNo())) {
			logger.info("该档案验证通过,准许入库......");
			try {
				documentSynDao.savaDocument(doc);
				if (!StringUtils.isEmpty(doc.getDocumentYear())
					&& Integer.parseInt(doc.getDocumentYear()) > 2000) {
					documentFilterServiceImpl.createOrReflectBox(doc);  //只创建关联2000年以后的盒子
				}
			} catch (NumberFormatException e) {
				logger.info("年度格式化异常");
			}
		} else {
			logger.error("该档案验证未通过......");
		}
		return 0;
	}

	@Override
	public int selectMaxBusinessId() {
		return documentSynDao.selectMaxBusinessId();
	}
	
	private boolean isEmpty(String content) {
		return content == null || "".equals(content.trim());
	}

}
