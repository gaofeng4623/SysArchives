package com.archives.web.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.Docborrowdetail;
import com.archives.pojo.Document;
import com.archives.pojo.DocumentBorrow;
import com.archives.service.DocborrowdetailService;
import com.archives.service.DocumentBorrowService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/documentBorrow")
public class  DocumentBorrowController {	
	@Resource
	private DocumentBorrowService documentBorrowService;
	@Resource
	private DocborrowdetailService docborrowdetailService;
	// 查询所有数据
	@RequestMapping("/findDocumentBorrow.do")
	@ResponseBody
	public Pager findDocumentBorrow(DocumentBorrow document, int rows, int page)throws Exception {
		Pager resultPage= null;
	
		int start = (page -1) * rows;
		resultPage = documentBorrowService.findDocumenBorrowtForPage(document,start, rows);
		
		return resultPage;
	}
	@RequestMapping("/documentList.do")
	@ResponseBody
	public ModelAndView documentList(String formNo)throws Exception {
		DocumentBorrow documentBorrow = documentBorrowService.findDocumentBorrowByformNo(formNo);
		ModelAndView mv = new ModelAndView("alert/documentList");
		mv.addObject("borrow", documentBorrow);
			return mv;

	}
	// 根据guid查询所有数据
	@SuppressWarnings("static-access")
	@RequestMapping("/findBorrowById.do")
	@ResponseBody
	public Pager findBorrowById(Document document,int rows, int page,String documentGuids)throws Exception {
		/*List<String> idList = new ArrayList<String>();
		if(null != documentGuids && !"".equals(documentGuids)){
			for(String guid : documentGuids.split(",")){
				if(StringUtils.isEmpty(guid)){
					continue;
				}
				idList.add(guid);
			}
		}
		List<Document> datalist = documentBorrowService.findAllByIds(document,idList);

		return datalist;*/
		Pager resultPage= null;
		
		int start = (page -1) * rows;
		resultPage = documentBorrowService.findAllByIdsForPage(document, start, rows,documentGuids);
		return resultPage;
	}
	/**
	 * 获取打印页面信息
	 * 
	 * @param borrowId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getPrintDocument.do")
	public ModelAndView getPrintDocument(String borrowId){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		DocumentBorrow borrow = null;
		try {
			 borrow = documentBorrowService.findDocumentBorrowByformNo(borrowId);
			List datalist = docborrowdetailService.selectByBorrowId(borrow.getGuid()+"");
			dataMap.put("datalist", datalist);
			dataMap.put("borrow", borrow);
			//dataMap.put("userName", userName);
		} catch (Exception e) {
			dataMap = new HashMap();
			dataMap.put("datalist", new ArrayList());
			//dataMap.put("userName", userName);
			dataMap.put("borrow", borrow);
		}
		return new ModelAndView("print/docBorrowFormPrint",dataMap);
	}
	// 查询所有数据
		@RequestMapping("/findAllDocborrowdetail.do")
		@ResponseBody
		public Pager findAllDocborrowdatail(Docborrowdetail docborrowdetail, int rows, int page)throws Exception {
			Pager resultPage= null;
		
			int start = (page -1) * rows;
			resultPage = docborrowdetailService.findAllDocborrowdetail(docborrowdetail,start, rows);
			
			return resultPage;
		}
		// 根据单号查询
		@RequestMapping("/findByformNo.do")
		@ResponseBody
		public Result findByformNo(String formNo)throws Exception {
			Result result = new Result();
			DocumentBorrow documentBorrow = documentBorrowService.findByformNo(formNo);
			if(documentBorrow != null){
				result.setData(documentBorrow);
				result.setMessage("查询成功！");
				result.setStatus(0);
			}else{
				result.setMessage("查询失败！");
				result.setStatus(1);
			}
			return result;
		}
		

/**
	 * 确认归还
	 * @param query_formNo
	 * @param query_archivesNo
	 * @return
	 */
	@RequestMapping("/confirmReturn.do")
	@ResponseBody
	public Result confirmReturn(int borrowId,String returnPerson,String[] allDetailIds){
		return documentBorrowService.confirmReturn(borrowId,returnPerson,allDetailIds);
	}
}
