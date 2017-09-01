package com.archives.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.archives.dao.DocumentDao;
import com.archives.pojo.Box;
import com.archives.pojo.Document;
import com.archives.service.BoxService;
import com.archives.service.DocumentFilterService;
import com.system.base.pojo.Result;

@Service
public class DocumentFilterServiceImpl implements DocumentFilterService{
	@Resource
	private BoxService boxService;
	@Resource
	private DocumentDao documentDao;
	
	private static final Logger logger = LoggerFactory
			.getLogger(DocumentFilterServiceImpl.class);

	
	@Override
	public int getTotal() {
		Map data = new HashMap();
		data.put("documentYear", 2000); //只同步2000年以后的文书
		return documentDao.findCountDocumentForPage(data);
	}
	
	public List<Document> queryDocForPage(int start, int rows) {
		Map<String, Object> data = new HashMap();
		data.put("start", start);
		data.put("rows", rows);
		data.put("documentYear", 2000); //只同步2000年以后的文书
		return documentDao.findDocumentForPage(data);
	}
	
	@Override
	public Result createOrReflectBox(Document doc) {
		Box boxPara = new Box();
		boxPara.setYear(doc.getDocumentYear()); //年度
		boxPara.setBoxNumber(doc.getDocketNo()); //盒号
		boxPara.setSafekeeping(doc.getDurationTorage()); //保管期限
		boxPara.setMechanism(doc.getDepartmentName()); //机构
		try {
			Box box = boxService.findBoxByParams(boxPara);
			if (box == null) {
				Result result = boxService.saveBox(boxPara);
				int boxId = Integer.parseInt(result.getData().toString());
				documentDao.updateBoxNumForDoc(doc.getGuid(), boxId); //关联盒子主键
				logger.info("创建盒号为" + boxPara.getBoxNumber() + "的盒子");
			} else {
				documentDao.updateBoxNumForDoc(doc.getGuid(), box.getGuid());
				logger.info("关联盒号为" + box.getBoxNumber() + "的盒子");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
