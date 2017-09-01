package com.archives.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.dao.WarnConfigDao;
import com.archives.pojo.Borrow;
import com.archives.pojo.WarnConfig;
import com.archives.service.WarnConfigService;
import com.system.base.pojo.Employee;
@Service
public class WarnConfigServiceImpl implements WarnConfigService {
	@Resource
	private WarnConfigDao warnConfigDao;

	@Override
	public WarnConfig findWarnConfig() {
		return warnConfigDao.findWarnConfig();
	}

	@Override
	public void updateWarnConfig(WarnConfig warnConfig) {
		warnConfigDao.updateWarnConfig(warnConfig);
		
	}

	@Override
	public void saveWarnConfig(WarnConfig warnConfig) {
		warnConfigDao.saveWarnConfig(warnConfig);
		
	}

	@Override
	public List<Borrow> findBorrowWarn(Employee emp) {
		return warnConfigDao.findBorrowWarn(emp);
	}

	@Override
	public WarnConfig findDocWarnConfig() {
		// TODO Auto-generated method stub
		return warnConfigDao.findDocWarnConfig();
	}

}
