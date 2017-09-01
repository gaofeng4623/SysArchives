package com.system.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.base.pojo.Source;
import com.system.core.service.SourceService;
import com.system.dao.database.SourceDao;
import com.system.util.common.Consts;
import com.system.util.common.SpringContextUtil;

@Service
public class SourceServiceImpl implements SourceService{

	@Resource
	private SourceDao sourceDao;
	
	@Override
	public List<Source> findAllSource() {
		List<Source> sourceList = sourceDao.findAllSource();
		return createSourceTree(sourceList);
	}
	
	private List<Source> createSourceTree(List<Source> sourceList) {
		List<Source> nodeList = new ArrayList<Source>();
		// 自定义根目录
		Source root = new Source();
		root.setGuid("00000");
		root.setSuporId("-1");
		root.setTitle(Consts.ROOTSOURCE);
		root.setIsSource(1);
		sourceList.add(root);
		// 拼接资源列表
		for (Source node1 : sourceList) {
			boolean mark = false;
			for (Source node2 : sourceList) {
				if (null != node1.getSuporId()
						&& node1.getSuporId().equals(node2.getGuid())) {
					mark = true;
					node2.getChildren().add(node1);
					break;
				}
			}
			if (!mark) {
				nodeList.add(node1);
			}
		}
		return nodeList;
	}

	@Override
	public List<Source> selectSourceBySourceIds(List<String> idList,
			int startIndex, int perPageNum) {
		return sourceDao.selectSourceBySourceIds(idList, startIndex, perPageNum);
	}

	@Override
	public int countSourceBySourceIds(List<String> idList) {
		return sourceDao.countSourceBySourceIds(idList);
	}
	
	
	@Override
	public Source selectByPrimaryKey(String guid) {
		return sourceDao.selectBySourceId(guid);
	}

	@Override
	public List<Source> selectByTypeName(String type, ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		Employee emp = (Employee) req.getSession().getAttribute(Consts.userkey);
		String userId = emp.getEmployeeId();
		return sourceDao.selectByTypeName(type, userId);
	}
	
	@Override
	public Result insertSource(Source record) throws Exception {
		try {
			sourceDao.insertSelective(record);
			return new Result(0, "资源保存成功");
		} catch (Exception e) {
			return new Result(1, "资源保存失败");
		}
	}

	@Override
	public Result deleteByPrimaryKey(String guid) throws Exception {
		try {
			sourceDao.deleteBySourceId(guid);
			sourceDao.deleteMembersBySourceId(guid);
			return new Result(0, "删除资源成功");
		} catch (Exception e) {
			return new Result(1, "删除资源失败");
		}
	}

	@Override
	public Result updateBySelective(Source record) throws Exception {
		try {
			sourceDao.updateBySelective(record);
			return new Result(0, "更新资源成功");
		} catch (Exception e) {
			return new Result(1, "更新资源失败");
		}
	}

	@Override
	public List<Source> selectLimitedSourceByRoleId(String roleId) {
		List<Source> allSource = sourceDao.findAllSource();
		List<Source> limitedSources = null;
		try {
			limitedSources = sourceDao.selectLimitedSourceByRoleId(roleId);
			for (Source src : allSource) {
				for (Source s : limitedSources) {
					if (src.getGuid().equals(s.getGuid())) {
						src.setChecked(true);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return createSourceTree(allSource);
	}

	@Override
	public int insertRoleSource(String roleId, String[] data) {
		String dataBaseType = SpringContextUtil.getDataBaseType();
		if (dataBaseType == null) return 0;
		if (dataBaseType.equalsIgnoreCase(Consts.MYSQL)) {
			return sourceDao.insertRoleSourceForMysql(roleId, data);
		} else if (dataBaseType.equalsIgnoreCase(Consts.ORACLE)) {
			return sourceDao.insertRoleSourceForOracle(roleId, data);
		}
		return 0;
	}

	@Override
	public int removeRoleSource(String roleId) {
		return sourceDao.removeRoleSource(roleId);
	}

}
