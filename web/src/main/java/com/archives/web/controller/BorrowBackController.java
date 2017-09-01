package com.archives.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.archives.pojo.Borrow;
import com.archives.pojo.BorrowDetail;
import com.archives.service.ArcHistoryService;
import com.archives.service.BorrowBackService;
import com.archives.service.BorrowService;
import com.system.base.pojo.Result;

@Controller
@RequestMapping("/borrowBack")
public class BorrowBackController {
	@Resource
	private BorrowBackService borrowBackService;
	@Resource
	private BorrowService borrowService;
	@Resource
	private ArcHistoryService arcHistoryService;
	
	@RequestMapping("/findBorrowDetail.do")
	@ResponseBody
	public List<BorrowDetail> findBorrowDetail(String formNo){
		List<BorrowDetail> list = borrowBackService.findBorrowDetail(formNo);
		return list;
	}
	/**
	 * 根据条件查询借阅单
	 * @param borrowId
	 * @return
	 */
	@RequestMapping("/findBorrowByCondtion.do")
	@ResponseBody
	public Result findBorrowByCondtion(String query_formNo,String query_caseNo,String rfid){
		Result result = new Result();
		List<Borrow> borrows = borrowBackService.findBorrowByCondtion(query_formNo,query_caseNo,rfid);
		if(borrows != null && borrows.size() >0){
			result.setData(borrows.get(0));
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
	public Result confirmReturn(String borrowId,String returnPerson,String[] allDetailIds, String [] ifids){
		if (allDetailIds != null) {
			for (String guid : ifids) {
				arcHistoryService.insertArcHistory(Integer.parseInt(guid), "归还档案,归还人为" + returnPerson,  returnPerson);  //借阅归还
			}
		}
		return borrowBackService.confirmReturn(borrowId,returnPerson,allDetailIds);
	}
	
	

}
