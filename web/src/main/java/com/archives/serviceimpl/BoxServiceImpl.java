package com.archives.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.archives.common.ArchiveStatus;

import org.springframework.stereotype.Service;

import com.archives.dao.BoxDao;
import com.archives.dao.BoxStatusDao;
import com.archives.dao.DocumentDao;
import com.archives.pojo.Box;
import com.archives.pojo.BoxStatus;
import com.archives.service.BoxService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Service
public class BoxServiceImpl implements BoxService {

	@Resource
	private BoxDao boxDao;
	@Resource
	private DocumentDao documentDao;
	@Resource
	private BoxStatusDao boxStatusDao;

	@Override
	public Pager findBoxForPage(Box box, int start, int totalSize)
			throws Exception {
		Map m = new HashMap();
		if(box.getMechanism()==null){
			box.setMechanism("");
		}else{
			if(box.getMechanism().equals("请选择")){
				box.setMechanism("");
			}
		}
		
		m.put("boxNumber", box.getBoxNumber());
		m.put("year", box.getYear());
		m.put("safekeeping", box.getSafekeeping());
		m.put("mechanism", box.getMechanism());
		m.put("status", box.getStatus());
		m.put("start", start);
		m.put("rows", totalSize);
		m.put("boxNumber1", box.getBoxNumber1());
		m.put("boxNumber2", box.getBoxNumber2());
		List documents = boxDao.findBoxForPage(m);
		int total = boxDao.findCountBoxForPage(m);
		return new Pager(total, documents);
	}

	@Override
	public Box findBoxById(int guid) throws Exception {
		return boxDao.selectByPrimaryKey(guid);
	}

	@Override
	public Result saveBox(Box box) {
		Result res = new Result();
		int flag = boxDao.insertSelective(box);
		BoxStatus boxstatus = new BoxStatus();
		boxstatus.setBoxid(box.getGuid());
		boxstatus.setStatus("0");
		boxStatusDao.insertSelective(boxstatus);
		if (-1 < flag) {
			res.setData(box.getGuid());
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
	public Result updBox(Box box) {
		Result res = new Result();
		List doucment = documentDao.selectByDocketNo(box.getBoxNumber());
		int flag = boxDao.updateByPrimaryKeySelective(box);

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
	public Result delBox(List<Integer> idList) {
		Result res = new Result();
		if (null == idList || 1 > idList.size()) {
			res.setStatus(1);
			res.setMessage("删除失败!");
			return res;
		} else {
			// 如果没有使用时，删除
			int flag = boxDao.delBoxByIds(idList);
			if (-1 < flag) {
				res.setStatus(0);
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
	public Result findAllByIds(List<String> idList) {
		/*
		 * List<Document> documentList= documentDao.findDocketNoByIds(idList);
		 * return documentList;
		 */
		Result res = new Result();
		if (null == idList || 1 > idList.size()) {
			res.setStatus(1);
			res.setMessage("删除失败!");
			return res;
		} else {
			// 如果没有使用时，删除
			List list = documentDao.findDocketNoByIds(idList);
			if (list.size() > 0) {
				res.setStatus(1);
				res.setMessage("已经存放档案,不允许操作!");
				return res;
			}
		}
		return res;
	}

	@Override
	public Box findBoxByParams(Box box) throws Exception {
		return boxDao.selectByParams(box);
	}

	@Override
	public Pager findBoxForShelves(Box box, int start, int totalSize)
			throws Exception {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("start", start);
		m.put("rows", totalSize);
		m.put("boxNumber", box.getBoxNumber());
		m.put("year", box.getYear());
		m.put("safekeeping", box.getSafekeeping());
		m.put("mechanism", box.getMechanism());
		m.put("status", box.getStatus());
		m.put("start", start);
		m.put("rows", totalSize);
		m.put("boxNumber1", box.getBoxNumber1());
		m.put("boxNumber2", box.getBoxNumber2());
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		m.put("nowDate", sdf.format(date));
		List<Box> empList = boxDao.findBoxForShelves(m);
		// 设置档案状态中文名
		for(Box tempInfo : empList){
			if(null != ArchiveStatus.getInstance(tempInfo.getStatus())){
				String statusname = ArchiveStatus.getInstance(tempInfo.getStatus()).getText();
				tempInfo.setStatusName(statusname);
			}
		}
		int total = boxDao.findCountBoxForShelves(m);
		return new Pager(total, empList);
	}

}
