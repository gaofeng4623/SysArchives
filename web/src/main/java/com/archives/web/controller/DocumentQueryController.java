package com.archives.web.controller;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.Document;
import com.archives.pojo.DocumentBorrow;
import com.archives.service.DocumentBorrowService;
import com.archives.service.DocumentService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/documentQuery")
public class DocumentQueryController {	
	@Resource
	private DocumentService documentService;
	@Resource
	DocumentBorrowService documentBorrowService;
	// 查询所有数据
	@RequestMapping("/findDocument.do")
	@ResponseBody
	public Pager findDocument(Document document, int rows, int page)throws Exception {
		Pager resultPage= null;
	
		int start = (page -1) * rows;
		resultPage = documentService.findDocumentForPage(document, start, rows);
		
		return resultPage;
	}
	//关联档案盒子
		@RequestMapping("/findDocumentBox.do")
		@ResponseBody
		public Pager findDocumentBox(Document document, int rows, int page)throws Exception {
			Pager resultPage= null;
		
			int start = (page -1) * rows;
			resultPage = documentService.findDocumentBoxForPage(document, start, rows);
			
			return resultPage;
		}
	// 根据guid查询所有数据
	@SuppressWarnings("static-access")
	@RequestMapping("/findById.do")
	@ResponseBody
	public ModelAndView findById(int guid)throws Exception {
		ModelAndView mv = new ModelAndView("alert/document");
		Document document = documentService.findDocumentById(guid);
		
		mv.addObject("arc", document);

		return mv;
	}
	// 根据guid查询所有数据
		@SuppressWarnings("static-access")
		@RequestMapping("/documentLibarary.do")
		@ResponseBody
		public ModelAndView documentLibarary(int guid)throws Exception {
			ModelAndView mv = new ModelAndView("alert/adddocumentLibarary");
			Document document = documentService.findDocumentById(guid);
			
			mv.addObject("arc", document);

			return mv;
		}
	@RequestMapping("/documentckLibrary.do")
	@ResponseBody
	public ModelAndView documentckLibrary()throws Exception {
		
		ModelAndView mv = new ModelAndView("alert/adddocumentckLibrary");
			return mv;

	}
	
	@RequestMapping("/savecodumentckLibaray.do")
	@ResponseBody
	public Result saveDocumentBorrow(DocumentBorrow documentBorrow)throws Exception{
		documentBorrow.setFormNo(documentBorrowService.getDocFormSeq()); //生成借阅单号

		return documentBorrowService.saveDocumentBorrow(documentBorrow);
	}
	//修改文书档案表中的boxid
	@RequestMapping("/updBoxGuid.do")
	@ResponseBody
	public Result updBoxGuid(Document document)throws Exception{
		return documentService.updBoxGuid(document);
	}
	//修改文书档案表中的boxid
	@RequestMapping("/updateDocketNo.do")
	@ResponseBody
	public Result updateDocketNo(String boxGuid,int docGuid)throws Exception{
		return documentService.updateDocketNo(boxGuid,docGuid);
	}
	@RequestMapping("/destroyAllByIds.do")
	@ResponseBody
	public Result destroyAllByIds(String destroyAllByIds)throws Exception{
		List<String> idList = new ArrayList<String>();

		if(null != destroyAllByIds && !"".equals(destroyAllByIds)){
			for(String ctrlId : destroyAllByIds.split(",")){
				if(StringUtils.isEmpty(ctrlId)){
					continue;
				}
				idList.add(ctrlId);
			}
		}
		
		return documentService.destroyAllByIds(idList);
	}
}
