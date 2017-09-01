package com.archives.exchange.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.archives.exchange.service.DocHandlerService;
import com.archives.exchange.service.DocumentQueryService;
import com.archives.pojo.Document;
import com.archives.pojo.DocumentMetaData;
import com.archives.service.DocumentSynService;

@Service("docHandler")
public class DocHandlerServiceImpl implements DocHandlerService{
	private static final Logger logger = LoggerFactory
			.getLogger(DocHandlerServiceImpl.class);
	@Resource
	private DocumentQueryService queryDoc;
	@Resource
	private DocumentSynService syndoc;
	
	@Override
	public int synDocument() throws Exception {
		List<DocumentMetaData> result = null;
		DocumentMetaData data = null;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String targetDate = sdf.format(cal.getTime());
		int max = queryDoc.queryDocMax(targetDate);
		int min = queryDoc.queryDocMin(targetDate);
		//min = 200432; //设置断点续传
		//int min = syndoc.selectMaxBusinessId(); //查询高院本地的同步断点
		logger.info("年度:" + targetDate + " 文书开始断点=" + max + "  结束断点=" + min);
		for (int i = min; i <= max; i++) {
			Document document = new Document();
			document.setDrid(i); //业务主键
			result = queryDoc.queryInfo(i);
			if (result == null) continue;
			for (int y = 0; y < result.size(); y++) {
				data = result.get(y);
				switch (data.getPropertyNo()) {
					case "1" :document.setDocumentYear(data.getContent()); break;
					case "2" :document.setDurationTorage(data.getContent()); break;
					case "3" :document.setDepartmentName(data.getContent()); break;
					case "4" :document.setDepartmentId(data.getContent()); break;
					case "5" :document.setTypeName(data.getContent()); break;
					case "6" :document.setFileType(data.getContent()); break;
					case "7" :document.setDocketNo(data.getContent()); break; 
					case "8" :document.setPartNo(data.getContent()); break;
					case "9" :document.setResponsiblePerson(data.getContent()); break;
					case "10" :document.setDocumentNo(data.getContent()); break;
					case "11" :document.setTitle(data.getContent()); break;
					case "12" :document.setDocumentDate(data.getContent()); break;
					case "13" :document.setNumberPages(data.getContent()); break;
					case "14" :document.setRemarks(data.getContent()); break;
					case "81" :document.setConcentrated(data.getContent()); break;
				}
			}
			if (isBlank(document.getTitle()) && isBlank(document.getDocketNo())) {
				logger.info(i + "--标题和盒号都为空，已被过滤");
				continue;
			}
			syndoc.saveDocument(document); 
			
		}
		return max - min;
	}
	
	public boolean isBlank(String content) {
		if (content == null || content.length() == 0) {
			return true;
		}
		return false;
	}

}
