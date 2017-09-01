package com.archives.serviceimpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.archives.dao.DocHistoryDao;
import com.archives.dao.DocborrowdetailDao;
import com.archives.dao.DocumentBorrowDao;
import com.archives.dao.DocumentDao;
import com.archives.pojo.Docborrowdetail;
import com.archives.pojo.Document;
import com.archives.pojo.DocumentBorrow;
import com.archives.service.DocHistoryService;
import com.archives.service.DocumentBorrowService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Service
public class DocumentBorrowServiceImpl implements DocumentBorrowService {

	@Resource
	private DocumentBorrowDao documentBorrowdao;
	@Resource
	private DocumentDao documentDao;
	@Resource
	private DocborrowdetailDao docborrowdetailDao;
	@Resource
	private DocHistoryService docHistoryService;
	@Override
	public Pager findDocumenBorrowtForPage(DocumentBorrow document, int start, int totalSize)
			throws Exception {
		  Map m = new HashMap();
	        m.put("borrowDate",document.getBorrowDate());
	        m.put("borrowContent",document.getBorrowContent());
			m.put("borrowingPerson", document.getBorrowingPerson());
			m.put("dayNum",document.getDayNum());
			m.put("status",document.getStatus());
			m.put("start", start);
			m.put("rows", totalSize);
			
			List documents = documentBorrowdao.findDocumentBorrowForPage(m);
			int total = documentBorrowdao.findCountDocumentBorrowForPage(m);
			return new Pager(total, documents);
	}
	@Override
	public DocumentBorrow findDocumentBorrowById(int guid) throws Exception {
		// TODO Auto-generated method stub
		return documentBorrowdao.selectByPrimaryKey(guid);
	}
	@Override
	public DocumentBorrow findDocumentBorrowByformNo(String formNo) throws Exception {
		// TODO Auto-generated method stub
		return documentBorrowdao.findDocumentBorrowByformNo(formNo);
	}
	@Override
	public DocumentBorrow findByformNo(String formNo) throws Exception {
		// TODO Auto-generated method stub
		return documentBorrowdao.findByformNo(formNo);
	}
	@Override
	public Result saveDocumentBorrow(DocumentBorrow documentBorrow) throws Exception {
		Result res = new Result();
		int flag = -1 ;
		documentBorrow.setStatus("1");
		 documentBorrowdao.insertSelective(documentBorrow);
		//往详细表里面添加数据
		String[] guidArr = documentBorrow.getDocumentGuids().split(",");
		for (String id : guidArr) {
			Docborrowdetail borrowDetail = new Docborrowdetail();
			//往轨迹表插入数据
			docHistoryService.insertArcHistory(Integer.parseInt(id), "借阅成功", null);
			//生成借阅明细信息
			borrowDetail.setDocId(id);
			borrowDetail.setBorrowid(documentBorrow.getGuid()+"");
			borrowDetail.setStatus("0");  //以交付
			flag=docborrowdetailDao.insertSelective(borrowDetail);
		}
		//保存数据
		
		String documentGuids=documentBorrow.getDocumentGuids();
		try {
			this.takeDown(documentGuids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(-1< flag){
			res.setStatus(0);
			res.setMessage("保存成功!");
			return res;
		}else{
			res.setStatus(1);
			res.setMessage("保存失败,请联系管理员!");
			return res;
		}
	}
	@Override
	public void takeDown(String documentIds) throws Exception{
		if (documentIds.contains(",")) {
			String[] arr = documentIds.split(",");
			for (String s : arr) {
				documentDao.takeDown(s);
			}
		} else {
			documentDao.takeDown(documentIds);
		}
	}
	@Override
	public Pager findAllByIdsForPage(Document document, int start, int totalSize,String documentGuids)throws Exception {
		 Map m = new HashMap();
	        m.put("yearm1",document.getYearm1());
	        m.put("yearm2",document.getYearm2());
			m.put("durationTorage", document.getDurationTorage());
			
			m.put("departmentName",document.getDepartmentName());
			m.put("fileType",document.getFileType());
			m.put("typeName",document.getTypeName());
			m.put("docketNo1",document.getDocketNo1());
			m.put("docketNo2",document.getDocketNo2());
			m.put("partNo",document.getPartNo());
			m.put("responsiblePerson",document.getResponsiblePerson());
			m.put("nocumentNo",document.getDocumentNo());
			m.put("title",document.getTitle());
			m.put("documentDate",document.getDocumentDate());
			m.put("numberPages1",document.getNumberPages1());
			m.put("numberPages2",document.getNumberPages2());
			m.put("remarks",document.getRemarks());
			m.put("concentrated",document.getConcentrated());
			m.put("status",document.getStatus());
			m.put("start", start);
			m.put("rows", totalSize);
			List<String> idList = new ArrayList<String>();
			if(null != documentGuids && !"".equals(documentGuids)){
				for(String guid : documentGuids.split(",")){
					if(StringUtils.isEmpty(guid)){
						continue;
					}
					idList.add(guid);
				}
			}
			m.put("idList", idList);
		//List<Document> documentList= documentDao.findAllByIdsForPage(idList);
		List documents = documentDao.findAllByIdsForPage(m);
		int total = documentDao.findCountfindAllByIdsForPage(m);
		return new Pager(total, documents);
	}
	@Override
	public String getDocFormSeq() {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		Result result = new Result();
		documentDao.getDocFormSeq(result);
		String number;
		Integer no= result.getStatus();
		int form=no.toString().length();
		if(form==1){
			number="0000"+no;
		}else if(form==2){
			number="000"+no;
		}else if(form==3){
			number="00"+no;
		}else if(form==4){
			number="0"+no;
		}else{
			number=no+"";
		}
		return date +number;
	}
	@Override
	public Result confirmReturn(int borrowId, String returnPerson,
			String[] allDetailIds) {
		Result result = new Result();
		try {
			int doc = docborrowdetailDao.selectListTail(borrowId);
			DocumentBorrow borrow=new DocumentBorrow();
			borrow.setGuid(borrowId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			// 判断档案是否全部还清
			if(allDetailIds.length ==doc){
				borrow.setStatus("2");//已归还
				borrow.setRturnDate(date);
			}else{
				borrow.setStatus("-2");//部分归还
				borrow.setRturnDate(date);
			}
			documentBorrowdao.updateByPrimaryKeySelective(borrow);
			for (String guid : allDetailIds) {
				Docborrowdetail detail=new Docborrowdetail();
				detail.setGuid(Integer.parseInt(guid));
				detail.setReturnperson(returnPerson);
				detail.setReturntime(new Date());
				detail.setStatus("1");
				Docborrowdetail tail=docborrowdetailDao.selectByPrimaryKey(Integer.parseInt(guid));
				docborrowdetailDao.updateByPrimaryKeySelective(detail);
				Document document=new Document();
				
				document.setStatus("0");
				document.setGuid(Integer.parseInt(tail.getDocId()));
				docHistoryService.insertArcHistory(Integer.parseInt(tail.getDocId()), "归还成功", null);
				documentDao.updateByPrimaryKeySelective(document);
			}
			result.setStatus(0);
			result.setMessage("归还成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(1);
			result.setMessage("归还失败!");
		}
		return result;
	}


}
