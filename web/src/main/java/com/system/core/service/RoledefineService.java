package com.system.core.service;

import java.util.List;

import com.system.base.pojo.Result;
import com.system.base.pojo.Roledefine;

public interface RoledefineService {
	public List<Roledefine> findAllRoledefine();
    public Roledefine selectByPrimaryKey(String guid);
	public Result insertRoledefine(Roledefine record) throws Exception;
	public Result updateRoledefine(Roledefine record) throws Exception;
	public Result deleteByPrimaryKey(String guid) throws Exception;
	public Result removeRolemembers(String guid) throws Exception;
	public Result resetRole(String guid) throws Exception;
}
