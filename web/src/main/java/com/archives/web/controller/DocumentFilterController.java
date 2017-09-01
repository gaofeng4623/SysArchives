package com.archives.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.archives.pojo.Document;
import com.archives.service.DocumentFilterService;
import com.system.base.pojo.Result;

@Controller
@RequestMapping("/docFilter")
public class DocumentFilterController {
	@Resource
	private DocumentFilterService documentFilterService;
	private static final Logger logger = LoggerFactory
			.getLogger(DocumentFilterController.class);
	
		@RequestMapping("/createBoxs.do")
		@ResponseBody
		public Result createBoxs() {
			int start = 0;
			int perpage = 500;
			List<Document> temp = null;
			int total = documentFilterService.getTotal();
			int pages = (total % 500) == 0 ? total/500 : total/500 + 1;
			logger.info("总共" + total + "条数据,共" + pages + "页");
			for (int i = 1; i <= pages; i++) {
				logger.info("开始处理第" + i + "批文书数据");
				start = (i - 1) * perpage + 1;
				temp = documentFilterService.queryDocForPage(start, perpage);
				if (temp == null) continue;
				for (Document doc : temp) {
					if (isEmpty(doc.getDocketNo())) continue;
					documentFilterService.createOrReflectBox(doc);
				}
			}
			return new Result(0 , "盒子同步完成！");
		}
		
		public boolean isEmpty(String content) {
			return content == null || "".equals(content.trim());
		}
}
