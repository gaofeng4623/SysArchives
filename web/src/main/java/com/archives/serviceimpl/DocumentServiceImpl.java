package com.archives.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.dao.DocumentDao;
import com.archives.pojo.Document;
import com.archives.service.DocHistoryService;
import com.archives.service.DocumentService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Resource
	private DocumentDao documentdao;
	@Resource
	private DocHistoryService docHistoryService;
	@Override
	public Pager findDocumentForPage(Document document, int start, int totalSize)
			throws Exception {
		/*
		 * SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); String
		 * doumentDate=""; if(!"".equals(document.getDocumentDate())){ Date
		 * doumentDate1=format.parse(document.getDocumentDate());
		 * doumentDate=format.format(doumentDate1); }
		 */
		Map m = new HashMap();
		m.put("yearm1", document.getYearm1());
		m.put("yearm2", document.getYearm2());
		m.put("durationTorage", document.getDurationTorage());
		m.put("documentYear", document.getDocumentYear());
		m.put("departmentName", document.getDepartmentName());
		m.put("fileType", document.getFileType());
		m.put("typeName", document.getTypeName());
		m.put("docketNo", document.getDocketNo());
		m.put("boxNo", document.getBoxNo());
		m.put("docketNo1", document.getDocketNo1());
		m.put("docketNo2", document.getDocketNo2());
		m.put("partNo", document.getPartNo());
		m.put("responsiblePerson", document.getResponsiblePerson());
		m.put("documentNo", document.getDocumentNo());
		m.put("title", document.getTitle());
		m.put("documentDate", document.getDocumentDate());
		m.put("numberPages1", document.getNumberPages1());
		m.put("numberPages2", document.getNumberPages2());
		m.put("remarks", document.getRemarks());
		m.put("concentrated", document.getConcentrated());
		m.put("status", document.getStatus());
		m.put("start", start);
		m.put("rows", totalSize);

		List documents = documentdao.findDocumentForPage(m);
		int total = documentdao.findCountDocumentForPage(m);
		return new Pager(total, documents);
	}
	@Override
	public Pager findDocumentBoxForPage(Document document, int start, int totalSize)
			throws Exception {

		Map m = new HashMap();
		m.put("documentYear", document.getDocumentYear());
		m.put("docketNo", document.getDocketNo());
		m.put("durationTorage", document.getDurationTorage());
		m.put("departmentName", document.getDepartmentName());
		m.put("start", start);
		m.put("rows", totalSize);

		List documents = documentdao.findDocumentBoxForPage(m);
		int total = documentdao.findCountDocumentBoxForPage(m);
		return new Pager(total, documents);
	}
	@Override
	public Document findDocumentById(int guid) throws Exception {
		// TODO Auto-generated method stub
		return documentdao.selectByPrimaryKey(guid);
	}

	@Override
	public Result updBoxGuid(Document document) {
/*		Map m = new HashMap();
		m.put("documentYear", document.getDocumentYear());
		m.put("docketNo", document.getDocketNo());
		m.put("durationTorage", document.getDurationTorage());
		m.put("departmentName", document.getDepartmentName());
		m.put("boxNo", document.getBoxNo());*/
		/*Document document = new Document();
		document.setGuid(docGuid);
		document.setDocketNo(boxGuid);*/
		Result res = new Result();
		int flag = documentdao.updateDocument(document);

		if (-1 < flag) {
			res.setStatus(0);
			res.setMessage("添加成功!");
			return res;
		} else {
			res.setStatus(1);
			res.setMessage("添加失败,请联系管理员!");
			return res;
		}
	}

	@Override
	public Result updateDocketNo(String boxGuid, int docGuid) {
		Document document = new Document();
		document.setGuid(docGuid);
		document.setBoxNo(null);
		Result res = new Result();
		int flag = documentdao.updateboxNo(document);
		if (-1 < flag) {
			res.setStatus(0);
			res.setMessage("移除成功!");
			return res;
		} else {
			res.setStatus(1);
			res.setMessage("移除失败,请联系管理员!");
			return res;
		}
	}
	@Override
	public Result destroyAllByIds(List<String> idList)throws Exception {
		Result res = new Result();
		if(null == idList || 1 > idList.size()){
			res.setStatus(1);
			res.setMessage("修改失败!");
			return res;
		}else{
			for(int i=0;i<idList.size();i++){
			  docHistoryService.insertArcHistory(Integer.parseInt(idList.get(i)), "销毁成功", null);
			}
			// 如果没有使用时，删除 
			int flag = documentdao.destroyByIds(idList);
			if(-1 < flag){
				res.setStatus(0);
				res.setMessage("修改成功!");
				return res;
			}else{
				res.setStatus(1);
				res.setMessage("修改失败!");
				return res;
			}
		}
	}
}
