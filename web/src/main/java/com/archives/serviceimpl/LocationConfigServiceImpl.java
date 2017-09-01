package com.archives.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archives.dao.LocationConfigDao;
import com.archives.pojo.LocationConfig;
import com.archives.pojo.LocationType;
import com.archives.service.LocationConfigService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Service
public class LocationConfigServiceImpl implements LocationConfigService {
	@Autowired
	private LocationConfigDao locationConfigDao;
	@Override
	public List<LocationConfig> getConfigTreeNodes() {
		List<LocationConfig> nodeList = new ArrayList<LocationConfig>();
		
		List<LocationConfig> configs = locationConfigDao.selectAllLocationConfigs();
		// 自定义根目录
		LocationConfig root = new LocationConfig();
		root.setGuid(0);
		root.setParentid(-1);
		root.setLocationName("根目录");
		configs.add(root);
		// 拼接配置列表
		for(LocationConfig node1 : configs){
			boolean mark = false;
			for(LocationConfig node2 : configs){
				if(null != node1.getParentid() && node1.getParentid().equals(node2.getGuid())){
					mark = true;
					if(null == node2.getChildren()){
						node2.setChildren(new ArrayList<LocationConfig>());
					}
					node2.getChildren().add(node1);
					break;
				}
			}
			
			if(!mark){
				nodeList.add(node1);
			}
		}
		return nodeList;
	}
	@Override
	public Pager queryPageConfigList(Integer pid, int start, int rows) {
		Map m = new HashedMap();
		m.put("pid", pid);
		m.put("start", start);
		m.put("rows", rows);
		List<LocationConfig> locationList = locationConfigDao.queryPageConfigList(m);
		int total = locationConfigDao.queryPageConfigCount(m);
		return new Pager(total, locationList);
	}
	@Override
	public LocationConfig selectLocationConfigByPrimaryKey(Integer parentid) {
		return locationConfigDao.selectByPrimaryKey(parentid);
	}
	@Override
	public List<LocationType> getTypeList() {
		return locationConfigDao.getTypeList();
	}
	@Override
	public Result saveConfigInfo(LocationConfig config) {
		Result res = new Result();
		int flag = locationConfigDao.insertSelective(config);
		if(-1< flag){
			res.setStatus(0);
			res.setMessage("保存成功!");
		}else{
			res.setStatus(1);
			res.setMessage("保存失败,请联系管理员!");
		}
		return res;
	}
	@Override
	public Result delConfigInfo(List<String> idList) {
		
		Result res = new Result();
		if(null == idList || 1 > idList.size()){
			res.setStatus(1);
			res.setMessage("删除失败!");
			return res;
		}else{
			int flag = locationConfigDao.deleteConfigInfoByIds(idList);
			if(-1 < flag){
				res.setStatus(0);
				res.setMessage("删除成功!");
				return res;
			}else{
				res.setStatus(1);
				res.setMessage("删除失败!");
				return res;
			}
		}
		
	}

}
