package com.system.core.serviceimpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.base.pojo.Rolemembers;
import com.system.core.service.RolemembersService;
import com.system.dao.database.RolemembersDao;

@Service
public class RolemembersServiceImpl implements RolemembersService {

	@Autowired
	private RolemembersDao rolemembersDao;

	public int save(Rolemembers record) throws Exception{
		return rolemembersDao.save(record);
	}

	public List selectRoleId(String troleId) {
		return rolemembersDao.selectRoleId(troleId);
	}

	public int deleteByPrimaryKey(Integer roleid) throws Exception{
		return rolemembersDao.deleteByPrimaryKey(roleid);
	}

	@Override
	public Rolemembers selectByPrimaryKey(Integer rolerelateid) {
		return rolemembersDao.selectByPrimaryKey(rolerelateid);
	}
	
}
