package com.system.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.system.base.pojo.SysRole;
import com.system.core.service.SysRoleService;
import com.system.dao.database.SysRoleDao;
import com.system.util.common.Consts;
import com.system.util.common.SpringContextUtil;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Resource
	private SysRoleDao sysRoleDao;

	@Override
	public List<SysRole> findAllRoles() throws Exception {
		List result = new ArrayList();
		List<SysRole> roles = sysRoleDao.findAllRoles();
		SysRole root = new SysRole();
		root.setGroupId("-1");
		root.setGuid("0");
		root.setRoleName(Consts.ROOTROLE);
		roles.add(root);
		for (SysRole sr : roles) {
			boolean mark = false;
			for (SysRole srl : roles) {
				if (sr.getGroupId() != null
						&& sr.getGroupId().equals(srl.getGuid())) {
					mark = true;
					srl.getChildren().add(sr);
				}
			}
			if (!mark)
				result.add(sr);
		}
		return result;
	}

	@Override
	public List<SysRole> findAllNoTreeRoles() {
		List<SysRole> roles = sysRoleDao.findAllRoles();
		return roles;
	}

	@Override
	public int insertRoleMenus(String roleId, String[] data) throws Exception {
		String dataBaseType = SpringContextUtil.getDataBaseType();
		if (dataBaseType == null) return 0;
		if (dataBaseType.equalsIgnoreCase(Consts.MYSQL)) {
			return sysRoleDao.insertRoleMenuForMysql(roleId, data);
		} else if (dataBaseType.equalsIgnoreCase(Consts.ORACLE)) {
			return sysRoleDao.insertRoleMenuForOracle(roleId, data);
		}
		return 0;
	}

	@Override
	public int removeRoleMenus(String roleid) throws Exception {
		return sysRoleDao.removeRoleMenus(roleid);
	}

	@Override
	public List<SysRole> findRolesForCurrentUser(String userid) {
		return sysRoleDao.findRolesForCurrentUser(userid);
	}

}
