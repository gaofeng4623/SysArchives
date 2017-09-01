package com.archives.serviceimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.dao.ArchivesInfoMapperDao;
import com.archives.dao.BorrowBackDao;
import com.archives.dao.BorrowDao;
import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.Borrow;
import com.archives.pojo.BorrowDetail;
import com.archives.service.BorrowBackService;
import com.system.base.pojo.Result;

@Service
public class BorrowBackServiceImpl implements BorrowBackService{
	@Resource
	private BorrowBackDao borrowBackDao;
	@Resource
	private BorrowDao borrowDao;
	@Resource
	private ArchivesInfoMapperDao infoDao;

	@Override
	public List<BorrowDetail> findBorrowDetail(String formNo) {
		Map<String,Object> m = new HashMap();
		m.put("formNo", formNo);
		return borrowBackDao.findBorrowDetail(m);
	}

	@Override
	public List<Borrow> findBorrowByCondtion(String query_formNo,
			String query_caseNo, String rfid) {
		Map<String,Object> m = new HashMap();
		m.put("query_formNo", query_formNo);
		m.put("query_caseNo", query_caseNo);
		m.put("rfid", rfid);
		return borrowBackDao.findBorrowByCondtion(m);
	}

	@Override
	public Result confirmReturn(String borrowId, String returnPerson,
			String[] allDetailIds) {
			Result result = new Result();
		try {
			Borrow borrow = borrowDao.findBorrowById(borrowId);
			Map<String,Object> m = new HashMap();
			m.put("formNo", borrow.getFormNo());
			//根据借阅单号查出来的档案明细
			List<BorrowDetail> list = borrowBackDao.findBorrowDetail(m);
			// 判断档案是否全部还清
			if(allDetailIds.length == list.size()){
				borrow.setStatus("2");//已归还
				borrow.setActive(0);
				borrowBackDao.updateBorrowAll(borrow);
			}else{
				borrow.setStatus("-2");//部分归还
				borrowBackDao.updateBorrowAll(borrow);
			}
			for (String guid : allDetailIds) {
				BorrowDetail borrowDetail = borrowBackDao.queryBorrowDetailById(guid);
				borrowDetail.setReturnPerson(returnPerson);
				borrowDetail.setReturnTime(new Date());
				borrowDetail.setStatus("2");
				borrowBackDao.updateBorrowDetail(borrowDetail);
				ArchivesInfo archivesInfo = borrowBackDao.queryArchivesInfo(borrowDetail.getItemGuid());
				archivesInfo.setHandleStatus("0");
				borrowBackDao.updateArchivesInfo(archivesInfo);
				Map<String,Object> infoMap = new HashMap();
				infoMap.put("status","1");
				infoMap.put("guid", archivesInfo.getGuid());
				infoDao.updateItemStatus(infoMap);
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
