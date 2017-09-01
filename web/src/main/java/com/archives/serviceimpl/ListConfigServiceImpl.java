package com.archives.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archives.dao.MapDetailDao;
import com.archives.dao.MapTotalDao;
import com.archives.pojo.MapDetail;
import com.archives.pojo.MapTotal;
import com.archives.service.ListConfigService;
import com.system.base.pojo.Result;
import com.system.util.common.GUID;
import com.system.workflow.activiti.commons.Pager;
@Service
public class ListConfigServiceImpl implements ListConfigService {

	@Autowired
	private MapTotalDao mapTotalDao;
	@Autowired
	private MapDetailDao mapDetailDao;

	public List<MapTotal> findAllListItemForTree() {
		List<MapTotal> nodeList = new ArrayList<MapTotal>();
		List<MapTotal> mapTotalList= mapTotalDao.selectAllItems();
		// 自定义根目录	
		MapTotal root = new MapTotal();
		root.setGuid("00000");
		root.setContent("案件性质");
		root.setChildren(mapTotalList);
		nodeList.add(root);
		return nodeList;
	}

	public MapTotal selectByPrimaryKey(String guid) {
		MapTotal item = null;
		item = mapTotalDao.selectByPrimaryKey(guid);
		return item;
	}

	public Result insertSelective(MapTotal record) {
		Result res = new Result();
		MapTotal temp = mapTotalDao.selectByName(record.getContent());
		if(null != temp){
			res.setStatus(1);
			res.setMessage("项目名已经存在,请重新输入!");
		}else{
			record.setGuid(new GUID().toString());
			int flag = mapTotalDao.insertSelective(record);
			if(-1< flag){
				res.setStatus(0);
				res.setMessage("保存成功!");
			}else{
				res.setStatus(1);
				res.setMessage("保存失败,请联系管理员!");
			}
		}
		return res;
	}

	public Result updateByPrimaryKeySelective(MapTotal record) {
		Result res = new Result();
		MapTotal temp = mapTotalDao.selectByName(record.getContent());
		if(null != temp &&  temp.getGuid().equals(record.getGuid())&&temp.getMark().equals(record.getMark())){
			res.setStatus(1);
			res.setMessage("项目名已经存在,请重新输入!");
		}else{
			int flag = mapTotalDao.updateByPrimaryKeySelective(record);
			if(-1< flag){
				res.setStatus(0);
				res.setMessage("保存成功!");
			}else{
				res.setStatus(1);
				res.setMessage("保存失败,请联系管理员!");
			}
		}
		return res;
	}

	public Result deleteByPrimaryKey(String guid) {
		Result res = new Result();
		
		MapTotal temp = mapTotalDao.selectByPrimaryKey(guid);
		if(null == temp){
			res.setStatus(1);
			res.setMessage("项目不存在,请确认!");
			return res;
		}
		mapTotalDao.deleteByPrimaryKey(guid);
		//mapDetailDao.deleteByPrimaryKey(guid);
		res.setStatus(0);
		res.setMessage("删除成功!");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Pager findSubItemByPriorIdsForPage(MapDetail mapDetail,
			List<String> idList, int start, int rows ) {
		Map<String, Object> m = new HashedMap();
		m.put("idList", idList);
		m.put("subId", mapDetail.getSubId());
		m.put("caseContent", mapDetail.getCaseContent());
		m.put("start", start);
		m.put("rows", rows);
		List<MapDetail> mapDetailList = mapDetailDao.findSubItemByPriorIdsForPage(m);
		int total = mapDetailDao.findCountSubItemByPriorIdsForPage(m);
		return new Pager(total, mapDetailList);
	}
	
	public Result insertSubItem(MapDetail record) {
		Result res = new Result();
		record.setSubId(new GUID().toString());
		int flag = mapDetailDao.insertSubItem(record);
		if(-1< flag){
			res.setStatus(0);
			res.setMessage("保存成功!");
		}else{
			res.setStatus(1);
			res.setMessage("保存失败,请联系管理员!");
		}
		return res;
	}
	
	public Result updateSubItemSelective(MapDetail record) {
		Result res = new Result();
		int flag = mapDetailDao.updateSubItemSelective(record);
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
	public MapDetail selectSubItemByPrimaryKey(String subId) {
		MapDetail item = null;
		item = mapDetailDao.selectByPrimaryKey(subId);
		return item;
	}
	

	public Result deleteSubItemByIds(List<String> idList) {
		Result res = new Result();
		int flag = mapDetailDao.deleteSubItemByIds(idList);
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
