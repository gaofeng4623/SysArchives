package com.system.core.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.system.base.pojo.Result;
import com.system.base.pojo.Roledefine;
import com.system.core.service.RoledefineService;
import com.system.dao.database.RoledefineDao;
import com.system.dao.database.RolemembersDao;
import com.system.dao.database.SourceDao;
import com.system.dao.database.SysRoleDao;
import com.system.util.common.GUID;

@Service
public class RoledefineServiceImpl implements RoledefineService {

	@Resource
	private RoledefineDao roledefineDao;
	@Resource
	private RolemembersDao rolemembersDao;
	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private SourceDao sourceDao;

	public List<Roledefine> findAllRoledefine() {

		return roledefineDao.selectAllRoledefine();
	}

	@Override
	public Roledefine selectByPrimaryKey(String guid) {
		return roledefineDao.selectByPrimaryKey(guid);
	}

	@Override
	public Result insertRoledefine(Roledefine record) throws Exception {
		Result res = new Result();
		Roledefine temp = roledefineDao.selectByName(record.getRolename());
		if (null != temp) {
			res.setStatus(1);
			res.setMessage("角色名称已经存在,请换个名字!");
		} else {
			Roledefine fine = new Roledefine();
			GUID guidee = new GUID();
			record.setGuid(new GUID().toString());
			int flag = roledefineDao.save(record);
			if (-1 < flag) {
				res.setStatus(0);
				res.setMessage("保存成功!");
			} else {
				res.setStatus(1);
				res.setMessage("保存失败,请联系管理员!");
			}
		}
		return res;
	}

	public Result deleteByPrimaryKey(String guid) throws Exception {
		try {
			Roledefine temp = roledefineDao.selectByPrimaryKey(guid);
			if (null == temp) {
				return new Result(1, "角色不存在,请确认!");
			}
			List<Roledefine> childDept = roledefineDao.selectByGroupId(guid);
			if (null != childDept && 0 < childDept.size()) {
				return new Result(1, "当前角色存在附属部门,不允许删除,请确认!");
			}
			roledefineDao.deleteByPrimaryKey(guid);
			rolemembersDao.deleteByRoleId(guid);
			sysRoleDao.removeRoleMenus(guid);
			sourceDao.removeRoleSource(guid);
			return new Result(0, "删除成功!");
		} catch (Exception e) {
			return new Result(1, "删除失败!");
		}
	}

	@Override
	public Result updateRoledefine(Roledefine record) throws Exception {
		try {
			roledefineDao.updateByPrimaryKeySelective(record);
			return new Result(0, "更新成功!");
		} catch (Exception e) {
			return new Result(1, "更新失败!");
		}
	}

	/*清空一个角色下的全部成员*/
	@Override
	public Result removeRolemembers(String guid) throws Exception {
		try {
			rolemembersDao.deleteByRoleId(guid);
			return new Result(0, "角色成员清空完成!");
		} catch (Exception e) {
			return new Result(1, "角色成员清空失败!");
		}
	}

	/*重置选中的角色*/
	@Override
	public Result resetRole(String guid) throws Exception {
		try {
			rolemembersDao.deleteByRoleId(guid);
			sysRoleDao.removeRoleMenus(guid);
			sourceDao.removeRoleSource(guid);
			return new Result(0, "角色重置完成!");
		} catch (Exception e) {
			return new Result(1, "角色重置失败!");
		}
	}
}
