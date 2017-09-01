package com.archives.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archives.common.ArchiveStatus;
import com.archives.dao.ArchivesInfoMapperDao;
import com.archives.dao.BorrowDao;
import com.archives.dao.ItemstatusDao;
import com.archives.dao.MapTotalDao;
import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.Itemstatus;
import com.archives.pojo.MapTotal;
import com.archives.service.ArchivesInfoService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Service
public class ArchivesInfoServiceImpl implements ArchivesInfoService {

	@Resource
	private ArchivesInfoMapperDao archivesInfoMapperDao;
	@Resource
	private BorrowDao borrowDao;
	@Resource
	private ItemstatusDao itemstatusDao;
	@Autowired
	private MapTotalDao mapTotalDao;
	public List findAll() throws Exception {
		// TODO Auto-generated method stub
		List list=archivesInfoMapperDao.findAll();
		return list;
	}

	public int save(ArchivesInfo p) throws Exception{
		// TODO Auto-generated method stub
		return 0;
	}

	public Result update(ArchivesInfo p) throws Exception{
		// TODO Auto-generated method stub
		Result res = new Result();
		int flag =archivesInfoMapperDao.updateByPrimaryKeySelective(p);
		if(-1 < flag){
			if(-1< flag){
				res.setStatus(0);
				res.setMessage("保存成功!");
				return res;
			}else{
				res.setStatus(1);
				res.setMessage("保存失败,请联系管理员!");
				return res;
			}
		}else{
			res.setStatus(1);
			res.setMessage("保存失败,请联系管理员!");
			return res;
		}
	}

	public int delete(ArchivesInfo p) throws Exception{
		// TODO Auto-generated method stub
		return 0;
	}
	public ArchivesInfo findByItemGuid(Integer itemGuid) throws Exception{
		 return archivesInfoMapperDao.selectByPrimaryKey(itemGuid);
	}

	@Override
	public Integer countArchivesInfo() throws Exception{
		// TODO Auto-generated method stub
		return archivesInfoMapperDao.countArchivesInfo();
	}

	@Override
	public List<ArchivesInfo> findEmpByArchivesForPage(Map paraMap) throws Exception{
		// TODO Auto-generated method stub
		return archivesInfoMapperDao.findEmpByArchivesForPage(paraMap);
	}

	@Override
	public Pager findEmpByArchiveForPage(ArchivesInfo archivesInfo, int start,
			int totalSize) throws Exception{
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("start", start);
		m.put("rows", totalSize);
		m.put("year", archivesInfo.getYear());//年度
		m.put("registertime", archivesInfo.getRegistertime());
		m.put("registertime2", archivesInfo.getRegistertime2());
		m.put("caseproerty", archivesInfo.getCaseproerty());//案件性质
		m.put("putonrecorddate", archivesInfo.getPutonrecorddate());
		m.put("putonrecorddate2",archivesInfo.getPutonrecorddate());
		m.put("caseword", archivesInfo.getCaseword());
		m.put("settledate", archivesInfo.getSettledate());
		m.put("settledate2", archivesInfo.getSettledate2());
		m.put("undertakedep", archivesInfo.getUndertakedep());
		m.put("placeondate", archivesInfo.getPlaceondate());
		m.put("placeondate2", archivesInfo.getPlaceondate2());
		m.put("undertakeper", archivesInfo.getUndertakeper());
		m.put("storagelife", archivesInfo.getStoragelife());
		m.put("caseno", archivesInfo.getCaseno());
		m.put("parties", archivesInfo.getParties());
		m.put("number1", archivesInfo.getNumber1());
		m.put("number", archivesInfo.getNumber());
		m.put("number2", archivesInfo.getNumber2());
		m.put("flowid", archivesInfo.getFlowid());
		m.put("status", archivesInfo.getStatus());
		m.put("itemstatus", archivesInfo.getItemstatus());
		List<ArchivesInfo> empList = archivesInfoMapperDao.findEmpArchiveForPage(m);
		// 设置档案状态中文名
		for(ArchivesInfo tempInfo : empList){
			if(null != ArchiveStatus.getInstance(tempInfo.getStatus())){
				String statusname = ArchiveStatus.getInstance(tempInfo.getStatus()).getText();
				tempInfo.setStatusName(statusname);
			}
		}
		int total = archivesInfoMapperDao.findCountArchivesForPage(m);
		return new Pager(total, empList);
	}
	
	@Override
	public Pager findArchivesInfoForShelves(ArchivesInfo archivesInfo, int start, int totalSize) throws Exception{
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("start", start);
		m.put("rows", totalSize);
		m.put("year", archivesInfo.getYear());//年度
		m.put("registertime", archivesInfo.getRegistertime());
		m.put("registertime2", archivesInfo.getRegistertime2());
		m.put("caseproerty", archivesInfo.getCaseproerty());//案件性质
		m.put("putonrecorddate", archivesInfo.getPutonrecorddate());
		m.put("putonrecorddate2",archivesInfo.getPutonrecorddate());
		m.put("caseword", archivesInfo.getCaseword());
		m.put("settledate", archivesInfo.getSettledate());
		m.put("settledate2", archivesInfo.getSettledate2());
		m.put("undertakedep", archivesInfo.getUndertakedep());
		m.put("placeondate", archivesInfo.getPlaceondate());
		m.put("placeondate2", archivesInfo.getPlaceondate2());
		m.put("undertakeper", archivesInfo.getUndertakeper());
		m.put("storagelife", archivesInfo.getStoragelife());
		m.put("caseno", archivesInfo.getCaseno());
		m.put("parties", archivesInfo.getParties());
		m.put("number1", archivesInfo.getNumber1());
		m.put("number", archivesInfo.getNumber());
		m.put("number2", archivesInfo.getNumber2());
		m.put("flowid", archivesInfo.getFlowid());
	/*	m.put("flowId", arc.getFlowid());
		m.put("caseNo",arc.getCaseno());
		m.put("archivesNo",arc.getArchivesno());
		m.put("start", start);
		m.put("rows", totalSize);
		
		m.put("undertakePer", arc.getUndertakeper());
		m.put("parties",arc.getParties());
		m.put("year",arc.getYear());//ni
		m.put("settleDate", arc.getSettledate());
		m.put("catalogNo", arc.getCatalogno());
		m.put("fullDocNo", arc.getFulldocno());
		
		m.put("number", arc.getNumber());
		m.put("registerper", arc.getRegisterper());
		m.put("status", arc.getStatus());*/
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		m.put("nowDate", sdf.format(date));
		List<ArchivesInfo> empList = archivesInfoMapperDao.findArchivesInfoForShelves(m);
		// 设置档案状态中文名
		for(ArchivesInfo tempInfo : empList){
			if(null != ArchiveStatus.getInstance(tempInfo.getStatus())){
				String statusname = ArchiveStatus.getInstance(tempInfo.getStatus()).getText();
				tempInfo.setStatusName(statusname);
			}
		}
		int total = archivesInfoMapperDao.findCountArchivesForShelves(m);
		return new Pager(total, empList);
	}

	@Override
	public Pager findLocationByArchiveForPage(ArchivesInfo archivesInfo,
			int start, int totalSize) throws Exception {
		Map<String,Object> m = new HashMap<String,Object>();
		
		/*m.put("flowId", archivesInfo.getFlowid());
		m.put("caseNo",archivesInfo.getCaseno());
		m.put("archivesNo",archivesInfo.getArchivesno());*/
		m.put("start", start);
		m.put("rows", totalSize);
		m.put("year", archivesInfo.getYear());//年度
		m.put("registertime", archivesInfo.getRegistertime());
		m.put("registertime2", archivesInfo.getRegistertime2());
		m.put("caseproerty", archivesInfo.getCaseproerty());//案件性质
		m.put("putonrecorddate", archivesInfo.getPutonrecorddate());
		m.put("putonrecorddate2",archivesInfo.getPutonrecorddate());
		m.put("caseword", archivesInfo.getCaseword());
		m.put("settledate", archivesInfo.getSettledate());
		m.put("settledate2", archivesInfo.getSettledate2());
		m.put("undertakedep", archivesInfo.getUndertakedep());
		m.put("placeondate", archivesInfo.getPlaceondate());
		m.put("placeondate2", archivesInfo.getPlaceondate2());
		m.put("undertakeper", archivesInfo.getUndertakeper());
		m.put("storagelife", archivesInfo.getStoragelife());
		m.put("caseno", archivesInfo.getCaseno());
		m.put("parties", archivesInfo.getParties());
		m.put("number1", archivesInfo.getNumber1());
		m.put("number", archivesInfo.getNumber());
		m.put("number2", archivesInfo.getNumber2());
		m.put("flowid", archivesInfo.getFlowid());
		m.put("status", archivesInfo.getStatus());
		m.put("serialNoPath", archivesInfo.getSerialNoPath());
		/*m.put("undertakePer", archivesInfo.getUndertakeper());
		m.put("parties",archivesInfo.getParties());
		m.put("putOnRecordDate",archivesInfo.getPutonrecorddate());
		m.put("settleDate", archivesInfo.getSettledate());
		m.put("catalogNo", archivesInfo.getCatalogno());
		m.put("fullDocNo", archivesInfo.getFulldocno());
		
		m.put("number", archivesInfo.getNumber());
		m.put("number1", archivesInfo.getNumber1());
		m.put("number2", archivesInfo.getNumber2());
		m.put("registerper", archivesInfo.getRegisterper());*/
		List empList = archivesInfoMapperDao.findLocationByArchiveForPage(m);
		int total = archivesInfoMapperDao.findCountLocationArchivesForPage(m);
		return new Pager(total, empList);
	}
	@Override
	public Pager synArchivesInfo(ArchivesInfo archivesInfo,
			int start, int totalSize) throws Exception {
		Map<String,Object> m = new HashMap<String,Object>();

		m.put("start", start);
		m.put("rows", totalSize);
		m.put("year", archivesInfo.getYear());//年度
		m.put("registertime", archivesInfo.getRegistertime());
		m.put("registertime2", archivesInfo.getRegistertime2());
		m.put("caseproerty", archivesInfo.getCaseproerty());//案件性质
		m.put("putonrecorddate", archivesInfo.getPutonrecorddate());
		m.put("putonrecorddate2",archivesInfo.getPutonrecorddate());
		m.put("caseword", archivesInfo.getCaseword());
		m.put("settledate", archivesInfo.getSettledate());
		m.put("settledate2", archivesInfo.getSettledate2());
		m.put("undertakedep", archivesInfo.getUndertakedep());
		m.put("placeondate", archivesInfo.getPlaceondate());
		m.put("placeondate2", archivesInfo.getPlaceondate2());
		m.put("undertakeper", archivesInfo.getUndertakeper());
		m.put("storagelife", archivesInfo.getStoragelife());
		m.put("caseno", archivesInfo.getCaseno());
		m.put("parties", archivesInfo.getParties());
		m.put("number1", archivesInfo.getNumber1());
		m.put("number", archivesInfo.getNumber());
		m.put("number2", archivesInfo.getNumber2());
		m.put("flowid", archivesInfo.getFlowid());
		m.put("status", archivesInfo.getStatus());
		m.put("serialNoPath", archivesInfo.getSerialNoPath());
		m.put("synStatus", archivesInfo.getSynStatus());
		List empList = archivesInfoMapperDao.synLocationByArchiveForPage(m);
		int total = archivesInfoMapperDao.synCountLocationArchivesForPage(m);
		return new Pager(total, empList);
	}

	@Override
	public List<ArchivesInfo> findAllByIds(List<String> idList) {
		List<ArchivesInfo> ArchivesInfoList= archivesInfoMapperDao.findAllByIds(idList);
		return ArchivesInfoList;
	}

	@Override
	public Pager findArchivesForChange(ArchivesInfo archivesInfo, int start,
			int totalSize) throws Exception {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("start", start);
		m.put("rows", totalSize);
		m.put("year", archivesInfo.getYear());//年度
		m.put("registertime", archivesInfo.getRegistertime());
		m.put("registertime2", archivesInfo.getRegistertime2());
		m.put("caseproerty", archivesInfo.getCaseproerty());//案件性质
		m.put("putonrecorddate", archivesInfo.getPutonrecorddate());
		m.put("putonrecorddate2",archivesInfo.getPutonrecorddate());
		m.put("caseword", archivesInfo.getCaseword());
		m.put("settledate", archivesInfo.getSettledate());
		m.put("settledate2", archivesInfo.getSettledate2());
		m.put("undertakedep", archivesInfo.getUndertakedep());
		m.put("placeondate", archivesInfo.getPlaceondate());
		m.put("placeondate2", archivesInfo.getPlaceondate2());
		m.put("undertakeper", archivesInfo.getUndertakeper());
		m.put("storagelife", archivesInfo.getStoragelife());
		m.put("caseno", archivesInfo.getCaseno());
		m.put("parties", archivesInfo.getParties());
		m.put("number1", archivesInfo.getNumber1());
		m.put("number", archivesInfo.getNumber());
		m.put("number2", archivesInfo.getNumber2());
		m.put("flowid", archivesInfo.getFlowid());
		/*m.put("flowId", arc.getFlowid());
		m.put("caseNo",arc.getCaseno());
		m.put("archivesNo",arc.getArchivesno());
		m.put("start", start);
		m.put("rows", totalSize);
		
		m.put("undertakePer", arc.getUndertakeper());
		m.put("parties",arc.getParties());
		m.put("putOnRecordDate",arc.getPutonrecorddate());
		m.put("settleDate", arc.getSettledate());
		m.put("catalogNo", arc.getCatalogno());
		m.put("fullDocNo", arc.getFulldocno());
		
		m.put("number", arc.getNumber());
		m.put("registerper", arc.getRegisterper());*/
		m.put("status", archivesInfo.getStatus());
		List<ArchivesInfo> empList = archivesInfoMapperDao.findArchivesForChange(m);
		// 设置档案状态中文名
		for(ArchivesInfo tempInfo : empList){
			if(null != ArchiveStatus.getInstance(tempInfo.getStatus())){
				String statusname = ArchiveStatus.getInstance(tempInfo.getStatus()).getText();
				tempInfo.setStatusName(statusname);
			}
		}
		int total = archivesInfoMapperDao.findCountArchivesForChange(m);
		return new Pager(total, empList);
	}

	@Override
	public Pager findBorrowByArchiveForPage(ArchivesInfo archivesInfo, int start,
			int totalSize) throws Exception {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("start", start);
		m.put("rows", totalSize);
		m.put("year", archivesInfo.getYear());//年度
		m.put("registertime", archivesInfo.getRegistertime());
		m.put("registertime2", archivesInfo.getRegistertime2());
		m.put("caseproerty", archivesInfo.getCaseproerty());//案件性质
		m.put("putonrecorddate", archivesInfo.getPutonrecorddate());
		m.put("putonrecorddate2",archivesInfo.getPutonrecorddate());
		m.put("caseword", archivesInfo.getCaseword());
		m.put("settledate", archivesInfo.getSettledate());
		m.put("settledate2", archivesInfo.getSettledate2());
		m.put("undertakedep", archivesInfo.getUndertakedep());
		m.put("placeondate", archivesInfo.getPlaceondate());
		m.put("placeondate2", archivesInfo.getPlaceondate2());
		m.put("undertakeper", archivesInfo.getUndertakeper());
		m.put("storagelife", archivesInfo.getStoragelife());
		m.put("caseno", archivesInfo.getCaseno());
		m.put("parties", archivesInfo.getParties());
		m.put("number1", archivesInfo.getNumber1());
		m.put("number", archivesInfo.getNumber());
		m.put("number2", archivesInfo.getNumber2());
		m.put("flowid", archivesInfo.getFlowid());
		m.put("status", archivesInfo.getStatus());
		List<ArchivesInfo> empList = archivesInfoMapperDao.findBorrowArchiveForPage(m);
		// 设置档案状态中文名
		for(ArchivesInfo tempInfo : empList){
			if(null != ArchiveStatus.getInstance(tempInfo.getStatus())){
				String statusname = ArchiveStatus.getInstance(tempInfo.getStatus()).getText();
				tempInfo.setStatusName(statusname);
			}
		}
		int total = archivesInfoMapperDao.findCountBorrowArchivesForPage(m);
		return new Pager(total, empList);
	}

	@Override
	public Result findUpArchivesForTodo() {
		int total = archivesInfoMapperDao.findUpArchivesForTodo();
		return new Result(1, String.valueOf(total));
	}

	@Override
	public Result destroyAllByIds(List<String> idList) {
		Result res = new Result();
		if(null == idList || 1 > idList.size()){
			res.setStatus(1);
			res.setMessage("修改失败!");
			return res;
		}else{
			// 如果没有使用时，删除 
			int flag = archivesInfoMapperDao.destroyByIds(idList);
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
	@Override
	public Result saveArchivesInfo(ArchivesInfo info) {
		Result res = new Result();
	String	markType=this.getMarkType(info.getCaseproerty());
	           info.setMarkType(markType);
		int flag = archivesInfoMapperDao.insertSelective(info);
		Itemstatus item=new Itemstatus();
		item.setInfoid(info.getGuid());
		item.setStatus("0");
		itemstatusDao.insertSelective(item);
		if (-1 < flag) {
			res.setStatus(0);
			res.setMessage("保存成功!");
			return res;
		} else {
			res.setStatus(1);
			res.setMessage("保存失败,请联系管理员!");
			return res;
		}
	}

	@Override
	public Result updateArchivesInfo(ArchivesInfo info) {
		Result res = new Result();
		String	markType=this.getMarkType(info.getCaseproerty());
        info.setMarkType(markType);
		int flag = archivesInfoMapperDao.updateByPrimaryKeySelective(info);
		
		if (-1 < flag) {
			res.setStatus(0);
			res.setMessage("修改成功!");
			return res;
		} else {
			res.setStatus(1);
			res.setMessage("修改失败,请联系管理员!");
			return res;
		}
	}

	@Override
	public Result delArchivesInfo(List<Integer> idList) {
		Result res = new Result();
		if (null == idList || 1 > idList.size()) {
			res.setStatus(1);
			res.setMessage("删除失败!");
			return res;
		} else {
			// 如果没有使用时，删除
			int flag = archivesInfoMapperDao.delInfoByIds(idList);
			int item=itemstatusDao.delItemsByIds(idList);
			if (-1 < flag&&-1<item) {
				res.setStatus(1);
				res.setMessage("删除成功!");
				return res;
			} else {
				res.setStatus(1);
				res.setMessage("删除失败!");
				return res;
			}
		}
	}

	@Override
	public List<MapTotal> findTotal() {
		List<MapTotal> mapTotalList= mapTotalDao.selectAllItems();
		return mapTotalList;
	}

	private String getMarkType(String azbm) {
		String result = "";
		if (azbm == null || "".equals(azbm.trim())) {
			result = "OTHER";
		} else {
			String sign = azbm.substring(0, 1);
			switch (sign) {
				case "1" : result = "XS"; break; //刑事
				case "2" : result = "MS"; break; //民事
				case "3" : result = "XZ"; break; //行政
				case "4" : result = "ZX"; break; //执行
				case "5" : result = "GJPCYSFJZ"; break; //国家赔偿与司法救助
				case "6" : result = "FSBQ"; break; //非诉保全
				case "7" : result = "GJSFXZ"; break; //国际司法协助
				case "8" : result = "QJSFXZ"; break; //区际司法协助
				case "9" : result = "SFZC"; break; //司法制裁
				case "A" : result = "XF"; break; //信访
				default : result = "OTHER"; //其他类别
			}
		}
		return result;
	}

	@Override
	public ArchivesInfo findByInfoId(Integer guid) throws Exception {
		return archivesInfoMapperDao.findByInfoId(guid);
	}

}
