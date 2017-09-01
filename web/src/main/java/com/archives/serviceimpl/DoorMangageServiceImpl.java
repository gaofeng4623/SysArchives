package com.archives.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.dao.DoorMangageDao;
import com.archives.pojo.DoorMangage;
import com.archives.service.DoorMangageService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;
@Service
public class DoorMangageServiceImpl implements DoorMangageService{

	@Resource
	private DoorMangageDao doorMangageDao;
	@Override
	public int deleteByPrimaryKey(Integer guid)throws Exception {
		// TODO Auto-generated method stub
		return doorMangageDao.deleteByPrimaryKey(guid);
	}

	@Override
	public int insert(DoorMangage record)throws Exception {
		// TODO Auto-generated method stub
		return doorMangageDao.insert(record);
	}

	@Override
	public int insertSelective(DoorMangage record)throws Exception {
		// TODO Auto-generated method stub
		return doorMangageDao.insertSelective(record);
	}

	@Override
	public DoorMangage selectByPrimaryKey(Integer guid)throws Exception {
		// TODO Auto-generated method stub
		return doorMangageDao.selectByPrimaryKey(guid);
	}

	@Override
	public int updateByPrimaryKeySelective(DoorMangage record) throws Exception{
		// TODO Auto-generated method stub
		return doorMangageDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Pager findDoorMangagePage(DoorMangage door, int start, int totalSize)throws Exception {
		Map m = new HashMap();
		m.put("doorId", door.getDoorid());
		m.put("doortype", door.getDoortype());
		m.put("start", start);
		m.put("rows", totalSize);
		List<DoorMangage> empList = doorMangageDao.findDoorMangageForPage(m);
		int total = doorMangageDao.findCountDoorMangageForPage(m);
		return new Pager(total, empList);
	}

	@Override
	public Result insertDoorSelective(DoorMangage door)throws Exception {
		// TODO Auto-generated method stub
		Result result = new Result();
		int manage = doorMangageDao.insert(door);
		if (manage > -1) {
			result.setMessage("保存成功");
			result.setStatus(0);
			return result;
		} else {
			result.setMessage("保存失败");
			result.setStatus(1);
			return result;
		}
	}

	@Override
	public Result delDoorSelective(int guid) throws Exception{
		Result result = new Result();
		int del = doorMangageDao.deleteByPrimaryKey(guid);
		if (del > -1) {
			result.setMessage("删除成功");
			result.setStatus(0);
			return result;
		} else {
			result.setMessage("删除失败");
			result.setStatus(1);
			return result;
		}
	}

	@Override
	public Result updateDoorSelective(DoorMangage door)throws Exception {
		Result result = new Result();
		int manage = doorMangageDao.updateByPrimaryKeySelective(door);
		if (manage > -1) {
			result.setMessage("修改成功");
			result.setStatus(0);
			return result;
		} else {
			result.setMessage("修改失败");
			result.setStatus(1);
			return result;
		}
	}

	@Override
	public Result delDoor(List<String> idList)throws Exception {
		Result res = new Result();
		if (null == idList || 1 > idList.size()) {
			res.setStatus(1);
			res.setMessage("删除失败!");
			return res;
		} else {
			int flag = doorMangageDao.deleteDoorByIds(idList);
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

}
