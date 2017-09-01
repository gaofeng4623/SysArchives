package com.archives.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.dao.DoorCameraDao;
import com.archives.pojo.DoorCamera;
import com.archives.service.DoorCameraService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;
@Service
public class DoorCameraServiceImpl implements DoorCameraService{

	@Resource
	private DoorCameraDao doorCameraDao;

	@Override
	public int deleteByPrimaryKey(Integer guid) throws Exception{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(DoorCamera record)throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(DoorCamera record)throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DoorCamera selectByPrimaryKey(Integer guid) throws Exception{
		// TODO Auto-generated method stub
		return doorCameraDao.selectByPrimaryKey(guid);
	}

	@Override
	public int updateByPrimaryKeySelective(DoorCamera record) throws Exception{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Pager findDoorCameraPage(DoorCamera door, int start, int totalSize)throws Exception {
		Map m = new HashMap();
		m.put("cameraName", door.getCameraname());
		m.put("loginName", door.getLoginname());
		m.put("start", start);
		m.put("rows", totalSize);
		List<DoorCamera> empList = doorCameraDao.findDoorCameraForPage(m);
		int total = doorCameraDao.findCountDoorCameraForPage(m);
		return new Pager(total, empList);
	}

	@Override
	public Result insertDoorSelective(DoorCamera door)throws Exception {
		Result result = new Result();
		int manage = doorCameraDao.insert(door);
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
	public Result delDoorSelective(int guid)throws Exception {
		Result result = new Result();
		int del = doorCameraDao.deleteByPrimaryKey(guid);
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
	public Result updateDoorSelective(DoorCamera door) throws Exception{
		Result result = new Result();
		int manage = doorCameraDao.updateByPrimaryKeySelective(door);
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
	public Result delDoor(List<String> idList) throws Exception{
		Result res = new Result();
		if (null == idList || 1 > idList.size()) {
			res.setStatus(1);
			res.setMessage("删除失败!");
			return res;
		} else {
			int flag = doorCameraDao.deleteDoorByIds(idList);
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

	public List<DoorCamera> querydoorCameraAll()throws Exception{
		return doorCameraDao.querydoorCameraAll();
	}

}
