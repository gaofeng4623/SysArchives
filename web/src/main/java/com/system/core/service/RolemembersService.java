package com.system.core.service;

import java.util.List;

import com.system.base.pojo.Rolemembers;

public interface RolemembersService {
	
	 public  int save(Rolemembers record) throws Exception;

		public List selectRoleId(String troleId);
        
	    public int deleteByPrimaryKey(Integer roleid) throws Exception;

	    public Rolemembers selectByPrimaryKey(Integer rolerelateid);


}
