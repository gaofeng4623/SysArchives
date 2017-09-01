package com.system.core.serviceimpl;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.system.core.service.InitDataService;
import com.system.dao.database.DataBaseDao;
import com.system.dao.database.InitSystemDataDao;

@Service
public class InitDataServiceimpl implements InitDataService {
	@Resource
	private DataBaseDao dataBaseDao;
	@Resource
	private InitSystemDataDao initSystemDataDao;

	@PostConstruct
	public void init() {
		createTable();
		initDataBase();
	}

	public void createTable() {
		Method[] methods = dataBaseDao.getClass().getDeclaredMethods();
		for (int i = 1; i <= methods.length; i++) {
			try {
				Method md = dataBaseDao.getClass().getMethod("table" + i);
				md.invoke(dataBaseDao);
			} catch (Exception e) {
				continue;
			}
		}
	}

	public void initDataBase() {
		// 初始化组织机构和菜单数据
		String userNum = dataBaseDao.queryAdmin();
		String menuNum = dataBaseDao.queryMenus();
		if ("0".equals(userNum)) {
			dataBaseDao.insertAdmin();
		}
		if ("0".equals(menuNum)) {
			initSystemDataDao.initSysData();
		}
		
	}

}
