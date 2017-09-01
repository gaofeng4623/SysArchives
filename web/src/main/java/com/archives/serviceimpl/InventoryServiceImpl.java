package com.archives.serviceimpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archives.dao.BoxStatusDao;
import com.archives.dao.InventoryPlanDao;
import com.archives.dao.InventoryResultDao;
import com.archives.dao.ItemstatusDao;
import com.archives.dao.LocationInfoDao;
import com.archives.pojo.InventoryPlan;
import com.archives.pojo.InventoryResult;
import com.archives.pojo.LocationInfo;
import com.archives.pojo.SpeakResult;
import com.archives.pojo.Status;
import com.archives.service.InventoryService;
import com.system.workflow.activiti.commons.Pager;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryPlanDao inventoryPlanDao;

	@Autowired
	private ItemstatusDao itemStatusDao;
	
	@Autowired
	private BoxStatusDao boxStatusDao;

	@Autowired
	private InventoryResultDao inventoryResultDao;

	@Autowired
	private LocationInfoDao locationInfoDao;

	@Override
	public List<InventoryPlan> getAllInventoryPlanList(String inventoryPlanName,
			String createrName, String stime, String etime, String type) throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("inventoryPlanName", inventoryPlanName);
		m.put("createrName", createrName);
		m.put("stime", stime);
		m.put("etime", etime);
		m.put("type", type);
		List<InventoryPlan> planList = inventoryPlanDao
				.getAllInventoryPlanList(m);
		for (InventoryPlan plan : planList) {
			String locationIds = plan.getInventoryLocationGuid();
			if (StringUtils.isNotBlank(locationIds)) {
				String[] locationIdArry = locationIds.split(",");
				List<LocationInfo> locationList = new ArrayList<LocationInfo>();
				for (String idStr : locationIdArry) {
					LocationInfo lInfo = locationInfoDao
							.selectByPrimaryKey(Integer.parseInt(idStr));
					locationList.add(lInfo);
				}

				plan.setLocationList(locationList);
			}
		}
		return planList;
	}
	
	public List<InventoryPlan> getInventoryPlanListForPage(int start, int rows, String type, String show, String planId) throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("start", start);
		m.put("rows", rows);
		m.put("type", type);
		m.put("show", show);
		if (StringUtils.isNotEmpty(planId)) {
			m.put("planId", planId);
		}
		List<InventoryPlan> planList = inventoryPlanDao
				.getInventoryPlanListForPage(m);
		for (InventoryPlan plan : planList) {
			String locationIds = plan.getInventoryLocationGuid();
			if (StringUtils.isNotBlank(locationIds)) {
				String[] locationIdArry = locationIds.split(",");
				List<LocationInfo> locationList = new ArrayList<LocationInfo>();
				for (String idStr : locationIdArry) {
					LocationInfo lInfo = locationInfoDao
							.selectByPrimaryKey(Integer.parseInt(idStr));
					locationList.add(lInfo);
				}

				plan.setLocationList(locationList);
			}
		}
		return planList;
	}
	
	

	//查询正在运行的盘点计划及进度
	@Override
	public List<Status> prosLibrary(int start, int rows, String type, String show, String planId) throws Exception {
		List<Status> status = new ArrayList<Status>();
		// 库
		List<InventoryPlan> planList = this.getInventoryPlanListForPage(start, rows, type, show, planId);
		for (InventoryPlan l : planList) {
			String locationIds = l.getInventoryLocationGuid();
			if (StringUtils.isNotBlank(locationIds)) {
				String[] locationIdArry = locationIds.split(",");
				for (String idStr : locationIdArry) {
					LocationInfo lInfo = locationInfoDao
							.selectByPrimaryKey(Integer.parseInt(idStr));
					Status s = new Status();
					s.setLocationId("" + lInfo.getGuid());
					s.setLocationType("" + lInfo.getLocationTypeGuid());
					int countcell = inventoryResultDao
							.getTotalCellByLocationGuid(lInfo.getGuid());
					int countPass = inventoryResultDao
							.getPassCellByLocationGuid(l.getGuid(), lInfo.getGuid());
					s.setCount(countPass + "/" + countcell); // 盘点数
					String p = getPercent(countPass, countcell);
					// System.out.println(l.getLocationname() + "进度--" + p);
					if ("2".equals(l.getIsEnd()) && 0 != countcell) {
						p = "100%";
					}
					int warningCount = 0;
					if ("0".equals(type)) { //诉讼检测
						warningCount = inventoryResultDao.testWarningCount(l.getGuid(), lInfo.getGuid());
					} else if ("1".equals(type)) { //文书检测
						warningCount = inventoryResultDao.testBoxWarningCount(l.getGuid(), lInfo.getGuid());
					}
					if (warningCount > 0) {
						s.setRight("0");
					}
					s.setSchedule(p);// 百分比
					s.setStatus(l.getIsEnd());
					s.setInventoryPlanId(l.getGuid() + "");
					s.setInventoryType(l.getInventoryType());
					status.add(s);
				}
			}
		}
		return status;
	}
	

	public String getPercent(int x, int y) {
		/*if (x == 0 || y == 0) {
			return "";
		}*/
		//System.out.println(x + "----" + y);
		double percentX = x * 1.0;
		double percentY = y * 1.0;
		double percent = percentX / percentY;
		if (x == 1 && y == 0) {
			percent = 1.0;
		}
		if (percent > 1.0) percent = 1.0;
		DecimalFormat df = new DecimalFormat("##%");
		return df.format(percent);
	}

	@Override
	public List<LocationInfo> loadLocationFrame(int locationId)
			throws Exception {
		return locationInfoDao.getNodesByParentId(locationId);
	}

	@Override
	public List<Status> prosFrame(String locationId, int inventoryPlanId)
			throws Exception {
		List<Status> status = new ArrayList<Status>();
		InventoryPlan inventoryPlan = inventoryPlanDao
				.selectByPrimaryKey(inventoryPlanId);
		// 架
		List<LocationInfo> location = this.loadLocationFrame(Integer
				.valueOf(locationId));
		for (LocationInfo l : location) {
			Status s = new Status();
			s.setLocationId(l.getGuid() + "");
			s.setLocationType("" + l.getLocationTypeGuid());
			int countArchive = inventoryResultDao
					.getTotalCellByLocationGuid(l.getGuid());
			int countPass = inventoryResultDao
					.getPassCellByLocationGuid(inventoryPlanId, l.getGuid());
			
			s.setCount(countPass + "/" + countArchive); // 盘点数
			String p = getPercent(countPass, countArchive);
			if ("2".equals(inventoryPlan.getIsEnd()) && 0 != countArchive) {
				p = "100%";
			}
			int warningCount = inventoryResultDao.testWarningCount(inventoryPlanId, l.getGuid());
			if (warningCount > 0) {
				s.setRight("0");
			}
			s.setStatus(inventoryPlan.getIsEnd());
			s.setSchedule(p);// 百分比
			status.add(s);
		}
		return status;
	}

	@Override
	public List<LocationInfo> loadLocationGrid(int locationId) throws Exception {
		// 查询 列
		List<LocationInfo> list = locationInfoDao
				.getNodesByParentId(locationId);
		for (LocationInfo h : list) {
			List<LocationInfo> list_l = locationInfoDao.getNodesByParentId(h
					.getGuid());
			h.setLocations(list_l);
		}
		return list;
	}
	
	
	@Override
	public List<LocationInfo> loadLocationLineGrid(int locationId, int serialNo) throws Exception {
		// 查询 列
		List<LocationInfo> list = new ArrayList<LocationInfo>();
		LocationInfo info = locationInfoDao.selectByPrimaryKey(locationId);
		List<LocationInfo> childs = locationInfoDao.getNodesByParentId(locationId);
		info.setLocations(childs);
		for (int i = 1; i <=6; i++) {
			if (i == serialNo) {
				list.add(info);
			} else {
				LocationInfo loc = new LocationInfo();
				List<LocationInfo> temp = new ArrayList<LocationInfo>();
				for (int y = 1; y <= 6; y++) {
					LocationInfo child = new LocationInfo();
					child.setGuid(0);
					temp.add(child);
				}
				loc.setLocations(temp);
				list.add(loc);
			}
		}
		return list;
	}
	
	
	/**
	 * 诉讼的盘点架子进度
	 */
	@Override
	public List<Status> prosGrid(int locationId, InventoryPlan inventoryPlan, int width)
			throws Exception {
		List<Status> status = new ArrayList<Status>();
		List<LocationInfo> locations = locationInfoDao.getCellsByParentId(locationId);
		List<InventoryResult> archives = inventoryResultDao.selectArchivesByLocation(locationId); //查询上架的档案
		List<InventoryResult> allResults = inventoryResultDao.selectInventoryResult(inventoryPlan.getGuid()); //查询盘点结果
		for (LocationInfo lc : locations) {
			List<InventoryResult> cellArchives = this.getInventoryResultsByLocation(lc, archives);
			List<InventoryResult> cellResults = this.getInventoryResultsByLocation(lc, allResults);
			Status s = createStatus(lc, cellArchives, cellResults, inventoryPlan, 14, width, false); //实时监测封装单元格的信息
			status.add(s);
		}
		return status;
	}
	
	//根据位置获取信息
	private List<InventoryResult> getInventoryResultsByLocation(LocationInfo lc, List<InventoryResult> allResults) {
		List<InventoryResult> results = new ArrayList<InventoryResult>();
		for (InventoryResult result : allResults) {
			if (String.valueOf(result.getInventoryLoactionGuid()).equals(String.valueOf(lc.getGuid()))) {
				results.add(result);
			}
		}
		return results;
	}
	
	
	/**
	 * 诉讼的盘点单元格进度
	 */
	@Override
	public List<Status> prosCellGrid(int locationId, InventoryPlan inventoryPlan)
			throws Exception {
		List<Status> status = new ArrayList<Status>();
		List<LocationInfo> locations = locationInfoDao.getCellsByParentId(locationId);
		List<InventoryResult> archives = inventoryResultDao.selectArchivesByLocation(locationId); //查询上架的档案
		List<InventoryResult> allResults = inventoryResultDao.selectInventoryResult(inventoryPlan.getGuid()); //查询盘点结果
		for (LocationInfo lc : locations) {
			List<InventoryResult> cellArchives = this.getInventoryResultsByLocation(lc, archives);
			List<InventoryResult> cellResults = this.getInventoryResultsByLocation(lc, allResults);
			Status s = createStatus(lc, cellArchives, cellResults, inventoryPlan, 19, 1027, true); //实时监测封装单元格的信息
			status.add(s);
		}
		return status;
	}
	
	/**
	 * 实时封装一个单元格的状态信息
	 * @param lc 位置信息
	 * @param inventoryPlanId 盘点计划ID
	 * @param inventoryPlan 盘点计划信息
	 * @param isCell 是否是单元格
	 * @return
	 */
	@Override
	public Status createStatus(LocationInfo lc, List<InventoryResult> cellArchives, List<InventoryResult> cellResults, InventoryPlan inventoryPlan, int maxlength, int width, boolean isCell) {
		Status status = new Status();
		status.setLocationId(lc.getGuid().toString());
		status.setLocationType("" + lc.getLocationTypeGuid());
		if (!isCell) inventoryResultDao.readInventoryResult(inventoryPlan.getGuid(), lc.getGuid()); //更新档案的读取状态
		if ("1".equals(inventoryPlan.getInventoryType())) { //盘点模式
			for (int i = 0; i < cellArchives.size(); i++) {
				InventoryResult r = cellArchives.get(i);
				for (Iterator<InventoryResult> it = cellResults.iterator(); it.hasNext();) {
					InventoryResult result = it.next();
					if (result.getRfid().equals(r.getRfid())) {
						cellArchives.set(i, result);
						it.remove();
					}
				}
			}
			cellResults.addAll(cellArchives);
		}
		String cellHtml = createCellHtml(cellResults, maxlength, width, isCell);
		status.setStatus(inventoryPlan.getIsEnd());
		status.setCellHtml(cellHtml);
		return status;
	}
	
	
	
	/**
	 * 
	 * @param inventoryResult
	 * @param maxlength 单元格容纳元素的容积
	 * @param width 单元格的宽度
	 * @param type 0表示架展示方式  1表示格展示方式
	 * @return
	 */
	private String createCellHtml(List<InventoryResult> inventoryResult, int maxlength, int width, boolean isCell) {
		StringBuffer sb = new StringBuffer("<ul class='main-ul'>\r\n");
	
		for (InventoryResult r : inventoryResult) {
			String style = "";
			if (inventoryResult.size() > maxlength) {
				style = " style='width:" + (double) width/inventoryResult.size() + "px'";
			}
			switch(String.valueOf(r.getStatus())) {
				case "-1" : sb.append("<li class='main-li-red'").append(style).append(">")
					.append(createItem(r, inventoryResult.size(), maxlength, isCell)).append("</li>\r\n"); break;
				case "0"  : sb.append("<li class='main-li-yellow'").append(style).append(">")
					.append(createItem(r, inventoryResult.size(), maxlength, isCell)).append("</li>\r\n"); break;
				case "1"  : sb.append("<li class='main-li-green'").append(style).append(">")
					.append(createItem(r,  inventoryResult.size(), maxlength, isCell)).append("</li>\r\n"); break;
				default   : sb.append("<li class='main-li-yuan'").append(style).append(">")
					.append(createItem(r, inventoryResult.size(), maxlength, isCell)).append("</li>\r\n");
			}
		}
		sb.append("</ul\r\n>");
		return sb.toString();
	}
	
	//创建档案字体
	private String createItem(InventoryResult result, int size, int maxlength, boolean isCellModel) {
		StringBuffer sb = new StringBuffer();
		if (isCellModel) {
			double zi = 14 - size / 14;
			sb.append("<p class='main-p'>");
			StringBuffer style = new StringBuffer();
			style.append(" style='font-size:" + zi + " px");
			if (size > maxlength) {
				if (zi <= 7) {zi = 7;}
				double zi2 = 14 - size / 13;
				style.append(";width:" + zi2 + "px'");
			} else {
				style.append("'");
			}
			sb.append("<span").append(style).append(">");
			sb.append(trim(result.getYear()) + "年");
			sb.append("</span>");
			sb.append("<span ").append(style).append(">");
			sb.append("鲁" + trim(result.getCaseWord()));
			sb.append("</span>");
			sb.append("<span ").append(style).append(">");
			sb.append("字第" + trim(result.getNumber()) + "号");
			sb.append("</span>");
			sb.append("</p>");
		}
		return sb.toString();
	}
	
	private String trim(String number) {
		if(number==null){
			number="";
		}
		return number;
	}
	
	
	/**
	 * 文书的盘点格子进度
	 */
	@Override
	public List<Status> prosBoxGrid(int locationId, int inventoryPlanId) throws Exception {
		List<Status> status = new ArrayList<Status>();
		InventoryPlan inventoryPlan = inventoryPlanDao.selectByPrimaryKey(inventoryPlanId);
		List<LocationInfo> locations = locationInfoDao.getCellsByParentId(locationId);
		for (LocationInfo lc : locations) {
			Status s = new Status();
			s.setLocationId(lc.getGuid() + "");
			s.setLocationType("" + lc.getLocationTypeGuid());
			// 获取要盘点的位置盒子的总数量
			int countArchive = boxStatusDao.countArchive(lc
					.getLocationPath());// 盒子数量
			// 获取缺失盒子的总数量
			int countLostInventory = inventoryResultDao.countLostInventory(
					inventoryPlan.getGuid(), lc.getLocationPath());
			// 获取多余（错放）盒子的总数量
			int countSurplusInventory = inventoryResultDao.countBoxSurplusInventory(
					inventoryPlan.getGuid(), lc.getLocationPath());
			
			// 获取已经盘点出来的正常的盒子的数量
			int countRightInventory = inventoryResultDao
					.countRightInventory(inventoryPlan.getGuid(),
							lc.getLocationPath());// 正常的盘点数量
			int countInventory = countRightInventory + countSurplusInventory; //实际的盘点数量
			s.setCount(countInventory + "/" + countArchive);// 盘点数
			String p = getPercent(countInventory, countArchive);
			if (countSurplusInventory > 0 || countLostInventory > 0) {
				s.setRight("0");
			}
			int passCell = inventoryResultDao.testPassCess(inventoryPlanId, lc.getGuid());
			if (passCell > 0 && (countArchive == 0 || countInventory == 0)) {
				p = "100%";
			}
			s.setStatus(inventoryPlan.getIsEnd());
			s.setSchedule(p);// 百分比
			status.add(s);
		}
		return status;
	}
	
	
	

	/**
	 * 文书的格子上架进度
	 */
	@Override
	public List<Status> prosShelvesBoxGrid(int locationId, int inventoryPlanId) throws Exception {
		List<Status> status = new ArrayList<Status>();
		InventoryPlan inventoryPlan = inventoryPlanDao.selectByPrimaryKey(inventoryPlanId);
		// 架
		List<LocationInfo> locations = locationInfoDao.getCellsByParentId(locationId);
		for (LocationInfo lc : locations) {
			Status s = new Status();
			s.setLocationId(lc.getGuid() + "");
			s.setLocationType("" + lc.getLocationTypeGuid());
			// 获取已经盘点出来的正常的盒子的数量
			int countRightInventory = inventoryResultDao.countRightInventory(inventoryPlan.getGuid(),
							lc.getLocationPath());// 正常的盘点数量
			s.setCount(String.valueOf(countRightInventory));// 盘点数
			int passCell = inventoryResultDao.testPassCess(inventoryPlanId, lc.getGuid());
			String p = passCell > 0 ? "100%" : "0";
			s.setStatus(inventoryPlan.getIsEnd());
			s.setSchedule(p);// 百分比
			status.add(s);
		}
		return status;
	}
	
	
	@Override
	public Pager loadDiffDetailDataGrid(Integer inventoryPlanId, Integer inventoryLoactionGuid, int page, int rows)
			throws Exception {
		//System.out.println(inventoryPlanId + " -- " + inventoryLoactionGuid);
		// 开始查询位置
		int start = (page - 1) * rows;
		List<InventoryResult> resultList = inventoryResultDao
				.loadDiffDetailDataGrid(inventoryPlanId, inventoryLoactionGuid, start, rows);
		int totalSize = inventoryResultDao.loadCountDiffDetailDataGrid(inventoryPlanId, inventoryLoactionGuid);
		for (InventoryResult result : resultList) {
			Integer sysLocationGuid = result.getSysLocationGuid();
			if (null != sysLocationGuid) {
				LocationInfo location = locationInfoDao
						.selectByPrimaryKey(sysLocationGuid);
				result.setSysLocationPath(location.getLocationPath());
			}
			

			if ("-1".equals(result.getStatus())) {
				/*int count = isExistInInventoryPlan(inventoryPlanId, result.getRfid());
				if (count > 0) {
					result.setStatusName("错放档案");
				} else {
					result.setStatusName("缺失档案");
				}*/
				result.setStatusName("缺失档案"); //高峰修改 2016-08-13 ，在某个位置状态为-1，在另一个位置必然为0，所以在该位置就应定义为缺失.
			} else if ("0".equals(result.getStatus())) {
				result.setStatusName("错放档案");
			} else if ("2".equals(result.getStatus())) {
				result.setStatusName("补录档案");
			} else {
				result.setStatusName("正常档案");
			}
		}
		return new Pager(totalSize, resultList);
	}
	
	@Override
	public Pager loadBoxDiffDetailDataGrid(Integer inventoryPlanId,
			Integer inventoryLoactionGuid, int page, int rows) throws Exception {
		int start = (page - 1) * rows;
		List<InventoryResult> resultList = inventoryResultDao
				.loadBoxDiffDetailDataGrid(inventoryPlanId, inventoryLoactionGuid, start, rows);
		int totalSize = inventoryResultDao.loadBoxCountDiffDetailDataGrid(inventoryPlanId, inventoryLoactionGuid);
		for (InventoryResult result : resultList) {
			Integer sysLocationGuid = result.getSysLocationGuid();
			if (null != sysLocationGuid) {
				LocationInfo location = locationInfoDao
						.selectByPrimaryKey(sysLocationGuid);
				result.setSysLocationPath(location.getLocationPath());
			}

			if ("-1".equals(result.getStatus())) {
				/*int count = isExistInInventoryPlan(inventoryPlanId, result.getRfid());
				if (count > 0) {
					result.setStatusName("错放档案");
				} else {
					result.setStatusName("缺失档案");
				}*/
				result.setStatusName("缺失档案盒"); //高峰修改 2016-08-13 ，在某个位置状态为-1，在另一个位置必然为0，所以在该位置就应定义为缺失.
			} else if ("0".equals(result.getStatus())) {
				result.setStatusName("错放档案盒");
			} else if ("1".equals(result.getStatus())) {
				result.setStatusName("正常档案盒");
			}
		}
		return new Pager(totalSize, resultList);
	}

	/**
	 * 
	 * @Title: isExistInInventoryPlan
	 * @Description: TODO
	 * @param
	 * @return int
	 */
	private int isExistInInventoryPlan(Integer inventoryPlanId, String rfid) {
		Map m = new HashMap();
		m.put("inventoryPlanId", inventoryPlanId);
		m.put("rfid", rfid);
		return inventoryResultDao.isExistInInventoryPlan(m);
	}

	@Override
	public void okInventoryPlan(String inventoryPlanId) throws Exception {
		InventoryPlan plan = new InventoryPlan();
		plan.setGuid(Integer.parseInt(inventoryPlanId));
		plan.setIsEnd("4");
		inventoryPlanDao.updateByPrimaryKeySelective(plan);
	}

	@Override
	public InventoryPlan getInventoryPlanByGuid(Integer guid) throws Exception {
		return inventoryPlanDao.selectByPrimaryKey(guid);
	}

	@Override
	public List<InventoryPlan> upShelvesPlanList() throws Exception {
		return inventoryPlanDao.upShelvesPlanList();
	}

	@Override
	public Pager toListHistory(InventoryPlan inventoryPlan, int start,
			int totalSize) throws Exception {
		Map m = new HashMap();
		m.put("start", start);
		m.put("rows", totalSize);
		m.put("inventoryPlanName", inventoryPlan.getInventoryPlanName());
		m.put("sign", inventoryPlan.getSign());
		List<InventoryPlan> plans = inventoryPlanDao.toListHistory(m);
		int count = inventoryPlanDao.toListHistoryCount(inventoryPlan);
		return new Pager(count, plans);
	}

	@Override
	public int getTotalPlanList(String type, String show) throws Exception {
		Map m = new HashMap();
		m.put("sign", type);
		m.put("show", show);
		return inventoryPlanDao.getTotalPlanList(m);
	}

	@Override
	public List<SpeakResult> getMissResult(Integer planId) {
		return inventoryResultDao.getMissResult(planId);
	}
	
	@Override
	public List<SpeakResult> getWrongResult(Integer planId) {
		return inventoryResultDao.getWrongResult(planId);
	}
	
	@Override
	public int getWarningCount(Integer planId) {
		return inventoryResultDao.getWarningCount(planId);
	}
	
	@Override
	public void insertSpeakResult(SpeakResult speakResults) {
		inventoryResultDao.insertSpeakResult(speakResults);
	}

	@Override
	public void clear(Integer planId) {
		inventoryResultDao.clear(planId);
	}

	@Override
	public int getUnReadCount(int locationId, int inventoryPlanId)
			throws Exception {
		return inventoryResultDao.getUnReadCount(inventoryPlanId, locationId);
	}
	
}
