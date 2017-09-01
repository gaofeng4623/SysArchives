package com.archives.serviceimpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.dao.DoorWarningDao;
import com.archives.pojo.DoorWarning;
import com.archives.service.DoorWarningService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Service
public class DoorWarningServiceImpl implements DoorWarningService {
	@Resource
	private DoorWarningDao doorWarningDao;

	@Override
	public List<DoorWarning> findDoorWarningForPage(Map paraMap) throws Exception{
		// TODO Auto-generated method stub
		return doorWarningDao.findDoorWarningForPage(paraMap);
	}
	@Override
	public List<DoorWarning> findAllByIds(DoorWarning door) {
		 Map m = new HashMap();
	        m.put("doorid", door.getDoorid());
			m.put("warnreason", door.getWarnreason());
			m.put("handler",door.getHandler());
			m.put("handlerresult",door.getHandlerresult());
		List<DoorWarning> doorWarningList= doorWarningDao.findAllByIds(m);
		return doorWarningList;
	}
	@Override
	public int findCountDoorWarningForPage(Map paraMap) throws Exception{
		// TODO Auto-generated method stub
		return doorWarningDao.findCountDoorWarningForPage(paraMap);
	}

	@Override
	public Pager findEmpByDoorWarningForPage(DoorWarning door, int start,
			int totalSize)throws Exception {
        Map m = new HashMap();
        m.put("doorid", door.getDoorid());
		m.put("warnreason", door.getWarnreason());
		m.put("handler",door.getHandler());
		m.put("handlerresult",door.getHandlerresult());
		m.put("start", start);
		m.put("rows", totalSize);
		
		List empList = doorWarningDao.findDoorWarningForPage(m);
		int total = doorWarningDao.findCountDoorWarningForPage(m);
		return new Pager(total, empList);
	}

	   @Override
		public Result updateDoorSelective(DoorWarning door) throws Exception{
			Result result = new Result();
			int manage = doorWarningDao.updateByPrimaryKeySelective(door);
			if (manage > -1) {
				result.setMessage("处理成功");
				result.setStatus(0);
				return result;
			} else {
				result.setMessage("处理失败");
				result.setStatus(1);
				return result;
			}
		}
	
	
}
