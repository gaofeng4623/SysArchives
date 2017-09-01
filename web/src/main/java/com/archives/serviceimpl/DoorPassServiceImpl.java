package com.archives.serviceimpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.dao.DoorPassDao;
import com.archives.pojo.DoorPass;
import com.archives.service.DoorPassService;
import com.system.workflow.activiti.commons.Pager;

@Service
public class DoorPassServiceImpl implements DoorPassService {
	@Resource
	private DoorPassDao doorPassDao;

	@Override
	public List<DoorPass> findDoorPassForPage(Map paraMap) throws Exception{
		// TODO Auto-generated method stub
		return doorPassDao.findDoorPassForPage(paraMap);
	}

	@Override
	public int findCountDoorPassForPage(Map paraMap)throws Exception {
		// TODO Auto-generated method stub
		return doorPassDao.findCountDoorPassForPage(paraMap);
	}

	@Override
	public Pager findEmpByDoorPassForPage(DoorPass door, int start,
			int totalSize)throws Exception {
        Map m = new HashMap();
        m.put("CaseNo", door.getCaseNo());
		m.put("locatdoorNo", door.getLocatdoorNo());
		
		m.put("passDate",door.getPassDate());
		m.put("start", start);
		m.put("rows", totalSize);
		
		List empList = doorPassDao.findDoorPassForPage(m);
		int total = doorPassDao.findCountDoorPassForPage(m);
		return new Pager(total, empList);
	}

	
	
	
}
