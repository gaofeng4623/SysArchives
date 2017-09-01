package com.archives.service;

import java.util.List;

import com.archives.pojo.Borrow;
import com.archives.pojo.WarnConfig;
import com.system.base.pojo.Employee;

public interface WarnConfigService {

	WarnConfig findWarnConfig();

	void updateWarnConfig(WarnConfig warnConfig);

	void saveWarnConfig(WarnConfig warnConfig);

	List<Borrow> findBorrowWarn(Employee emp);
	WarnConfig findDocWarnConfig();
}
