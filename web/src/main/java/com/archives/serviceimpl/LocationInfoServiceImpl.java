package com.archives.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archives.common.MarkUtil;
import com.archives.dao.LocationControlDao;
import com.archives.dao.LocationInfoDao;
import com.archives.pojo.LocationConfig;
import com.archives.pojo.LocationControl;
import com.archives.pojo.LocationInfo;
import com.archives.pojo.LocationType;
import com.archives.service.LocationInfoService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Service
public class LocationInfoServiceImpl implements LocationInfoService {
	@Autowired
	private LocationInfoDao locationInfoDao;
	
	@Autowired
	private LocationControlDao locationControlDao;
	@Override
	public List<LocationInfo> getNodesByParentId(Integer pid) {
		
		return locationInfoDao.getNodesByParentId(pid);
	}
	@Override
	public Pager queryPageLocationList(Integer pid, int start, int rows) {
		Map m = new HashedMap();
		m.put("pid", pid);
		m.put("start", start);
		m.put("rows", rows);
		List<LocationConfig> locationList = locationInfoDao.queryPageLocationList(m);
		int total = locationInfoDao.queryPageLocationCount(m);
		return new Pager(total, locationList);
	}
	@Override
	public LocationInfo getLocationInfoByGuid(Integer guid) {
		return locationInfoDao.selectByPrimaryKey(guid);
	}
	@Override
	public List<LocationType> getTypeList(int parentcode) {
		return locationInfoDao.getTypeList(parentcode);
	}
	
	@Override
	public Result saveLocationInfo(LocationInfo locationInfo) {
		Result res = new Result();
		int parentId = locationInfo.getParentId();
		// 处理序号
		Integer num = locationInfoDao.queryMaxSerialNo(parentId);
		if(null == num){
			num = 0;
		}
		locationInfo.setSerialNo(num+1);
		// 处理中文全路径、序号全路径
		if(0 != parentId){
			LocationInfo tempInfo = this.getLocationInfoByGuid(parentId);
			locationInfo.setLocationPath(tempInfo.getLocationPath() + ">" + locationInfo.getLocationName());
			locationInfo.setSerialNoPath(tempInfo.getSerialNoPath() + MarkUtil.numberToFormatString(num + 1, 3));
		}else{
			locationInfo.setLocationPath(locationInfo.getLocationName());
			locationInfo.setSerialNoPath(MarkUtil.numberToFormatString(num + 1, 3));
		}
		int flag = locationInfoDao.insertSelective(locationInfo);
		if(-1< flag){
			res.setStatus(0);
			res.setMessage("保存成功!");
			return res;
		}else{
			res.setStatus(1);
			res.setMessage("保存失败,请联系管理员!");
			return res;
		}
	}
	@Override
	public Result updateLocationInfo(LocationInfo locationInfo) {
		
		Result res = new Result();
		LocationInfo tempInfo = this.getLocationInfoByGuid(locationInfo.getGuid());
		String locationPath_old = tempInfo.getLocationPath() == null?"":tempInfo.getLocationPath();
		String locationName = locationInfo.getLocationName() == null?"":locationInfo.getLocationName();
		String locationPath_new = locationPath_old.substring(0,locationPath_old.lastIndexOf('>')+1) + locationName;
		int len = locationPath_old.length() + 1;
		Map map = new HashMap();
		map.put("locationPath_new", locationPath_new);
		map.put("len", len);
		map.put("locationPath_old", tempInfo.getGuid());
		int flag = locationInfoDao.updateLocationPath(map);
		
		if(-1 < flag){
			flag = locationInfoDao.updateByPrimaryKeySelective(locationInfo);
			if(-1< flag){
				res.setStatus(0);
				res.setMessage("保存成功!");
				return res;
			}else{
				res.setStatus(1);
				res.setMessage("保存失败,请联系管理员!");
				return res;
			}
		}else{
			res.setStatus(1);
			res.setMessage("保存失败,请联系管理员!");
			return res;
		}
	}
	@Override
	public Result delLocationInfo(List<String> idList) {
		Result res = new Result();
		if(null == idList || 1 > idList.size()){
			res.setStatus(1);
			res.setMessage("删除失败!");
			return res;
		}else{
			// TODO 判断删除位置是否有档案使用
			int count=locationInfoDao.getLocationCount(idList);
			if(count>0){
				res.setStatus(1);
				res.setMessage("请先删除子节点!");
				return res;
			}else{
				// 如果没有使用时，删除 
				int flag = locationInfoDao.delLocationInfoByIds(idList);
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
	@Override
	public List<LocationInfo> getOrderList(Integer guid) {
		return locationInfoDao.getNodesByParentId(guid);
	}
	
	@Override
	public Result saveOrderInfo(String orderStr) {
		Result res = new Result();
		int flag = 0;
		if(!orderStr.equals("")){
			String[] orders = orderStr.split(";");
			for(int i = 0;i < orders.length;i++){
				int num = i+1;
				int tempGuid = Integer.parseInt(orders[i]);
				LocationInfo tempLocat = locationInfoDao.selectByPrimaryKey(tempGuid);
				int serialNo = tempLocat.getSerialNo();
				if(serialNo == num){
					continue;
				}else{
					//更新序号
					Map map = new HashMap();
					map.put("tempGuid", tempGuid);
					map.put("num", num);
					flag = locationInfoDao.updateSerialNoByGuid(map);
					
					//更新序号全路径
					LocationInfo locationMap = this.getLocationInfoByGuid(tempGuid);
					//原记录序号全路径
					String serialNoPath_old = locationMap.getSerialNoPath();
					//新序号（处理过的三位字符串）
					String serialNoStr = MarkUtil.numberToFormatString(serialNo, 3);
					//新序号全路径
					String serialNoPath_new = serialNoPath_old.substring(0, serialNoPath_old.length() - 3) + serialNoStr;
					this.updateSerialNoPath(serialNoPath_new, serialNoPath_old, tempGuid);
				}
			}
		}
		
		if(-1 < flag){
			res.setStatus(0);
			res.setMessage("保存成功!");
			return res;
		}else{
			res.setStatus(1);
			res.setMessage("保存失败!");
			return res;
		}
	}
	
	/**
	 * 递归更新序列号全路径
	 * @param newSerial
	 * @param oldSerial
	 * @param guid
	 */
	public void updateSerialNoPath(String newSerial,String oldSerial,Integer guid){
		int len = newSerial.length() + 1;
		Map m = new HashMap();
		m.put("newSerial", newSerial);
		m.put("len", len);
		m.put("oldSerial", oldSerial);
		m.put("guid", guid);
		locationInfoDao.updateSerialNoPath(m);
		List<LocationInfo> childList = locationInfoDao.getNodesByParentId(guid);
		for(LocationInfo li : childList){
			int tguid = li.getGuid();
			updateSerialNoPath(newSerial,oldSerial,tguid);
		}
	}

	@Override
	public List<LocationInfo> queryPageLocationAll() {
		return locationInfoDao.queryPageLocationAll();
	}

	@Override
	public Pager queryPageControlList(String controlAddress, String branchAddress , int start, int rows) {
		Map m = new HashedMap();
		m.put("controlAddress", controlAddress);
		m.put("branchAddress", branchAddress);
		m.put("start", start);
		m.put("rows", rows);
		List<LocationControl> locationList = locationControlDao.queryPageControlList(m);
		int total = locationControlDao.queryPageControlListCount(m);
		return new Pager(total, locationList);
	}
	@Override
	public Result saveLocationControl(LocationControl locationControl) {
		Result res = new Result();
		LocationControl queryCtrl = new LocationControl();
		queryCtrl.setControlAddress(locationControl.getControlAddress());
		queryCtrl.setBranchNum(locationControl.getBranchNum());
		LocationControl tempCtrl = locationControlDao.queryControlByCondation(queryCtrl);
		if(null != tempCtrl){
			res.setStatus(1);
			res.setMessage("当前控制器{"+ locationControl.getControlAddress() +"}的通道{"+ locationControl.getBranchNum() +"}已经配置，不允许重复配置！");
			return res;
		}
		queryCtrl = new LocationControl();
		queryCtrl.setControlAddress(locationControl.getControlAddress());
		queryCtrl.setBranchAddress(locationControl.getBranchAddress());
		tempCtrl = locationControlDao.queryControlByCondation(queryCtrl);
		
		if(null != tempCtrl){
			res.setStatus(1);
			res.setMessage("当前控制器{"+ locationControl.getControlAddress() +"}下已经配置分支器{"+ locationControl.getBranchAddress() +"}，不允许重复配置！");
			return res;
		}
		
		int flag = locationControlDao.insertSelective(locationControl);
		if(-1< flag){
			res.setStatus(0);
			res.setMessage("保存成功!");
			return res;
		}else{
			res.setStatus(1);
			res.setMessage("保存失败,请联系管理员!");
			return res;
		}
	}
	@Override
	public LocationControl queryLocationControlById(Integer ctrlId) {
		return locationControlDao.selectByPrimaryKey(ctrlId);
	}
	@Override
	public Result updLocationControl(LocationControl locationControl) {
		Result res = new Result();
		LocationControl queryCtrl = new LocationControl();
		queryCtrl.setControlAddress(locationControl.getControlAddress());
		queryCtrl.setBranchNum(locationControl.getBranchNum());
		LocationControl tempCtrl = locationControlDao.selectByPrimaryKey(locationControl.getGuid());
		if(null == tempCtrl){
			res.setStatus(1);
			res.setMessage("修改失败，控制器不存在，请确认！");
			return res;
		}
		tempCtrl = locationControlDao.queryControlByCondation(queryCtrl);
		if(null != tempCtrl && tempCtrl.getGuid() != locationControl.getGuid()){
			res.setStatus(1);
			res.setMessage("当前控制器{"+ locationControl.getControlAddress() +"}的通道{"+ locationControl.getBranchNum() +"}已经配置，不允许重复配置！");
			return res;
		}
		queryCtrl = new LocationControl();
		queryCtrl.setControlAddress(locationControl.getControlAddress());
		queryCtrl.setBranchAddress(locationControl.getBranchAddress());
		tempCtrl = locationControlDao.queryControlByCondation(queryCtrl);
		
		if(null != tempCtrl && tempCtrl.getGuid() != locationControl.getGuid()){
			res.setStatus(1);
			res.setMessage("当前控制器{"+ locationControl.getControlAddress() +"}下已经配置分支器{"+ locationControl.getBranchAddress() +"}，不允许重复配置！");
			return res;
		}
		
		int flag = locationControlDao.updateByPrimaryKeySelective(locationControl);
		
		if(-1< flag){
			res.setStatus(0);
			res.setMessage("修改成功!");
			return res;
		}else{
			res.setStatus(1);
			res.setMessage("修改失败,请联系管理员!");
			return res;
		}
	}
	@Override
	public Result delLocationControlInfo(List<Integer> idList) {
		Result res = new Result();
		if(null == idList || 1 > idList.size()){
			res.setStatus(1);
			res.setMessage("删除失败!");
			return res;
		}else{
			// TODO 判断删除位置是否有档案使用
			
			
			// 如果没有使用时，删除 
			int flag = locationControlDao.delLocationControlByIds(idList);
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
	@Override
	public List<LocationControl> queryLocationControlListForCombobox(String condation) {
		// TODO Auto-generated method stub
		return locationControlDao.queryLocationControlListForCombobox(condation);
	}
	@Override
	public List<LocationInfo> NodesByParentId(Integer pid, String type) {
		// TODO Auto-generated method stub
		return locationInfoDao.NodesByParentId(pid,type);
	}

}
