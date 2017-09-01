package com.archives.exchange.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.exchange.service.OrgHandler;
import com.archives.exchange.service.OrgHandlerService;

@Service("schedule")
public class OrgHandlerImpl implements OrgHandler {
	@Resource
	private OrgHandlerService orgHandler;
	@Override
	public int synOrg() {
		int count = 0;
		try {
			count += orgHandler.synDepartments();
			count += orgHandler.synEmployees();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
