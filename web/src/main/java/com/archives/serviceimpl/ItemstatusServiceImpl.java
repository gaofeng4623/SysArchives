package com.archives.serviceimpl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.archives.common.ArchiveStatus;
import com.archives.dao.ArcSeqDao;
import com.archives.dao.ArchivesInfoMapperDao;
import com.archives.dao.ItemstatusDao;
import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.Itemstatus;
import com.archives.service.ItemstatusService;
import com.system.base.pojo.Result;

@Service
public class ItemstatusServiceImpl implements ItemstatusService {
	private static final Logger logger = LoggerFactory
			.getLogger(ItemstatusServiceImpl.class);
	@Resource
	private ItemstatusDao itemstatusDao;

	@Resource
	private ArchivesInfoMapperDao archivesInfoMapperDao;

	@Resource
	private ArcSeqDao arcSeqDao;

	@Override
	public List<Itemstatus> findByArchiveId(Integer infoid) {
		// TODO Auto-generated method stub
		return itemstatusDao.findByArchivesNo(infoid);
	}

	@Override
	public Itemstatus selectByPrimaryKey(Integer archiveGuid) {
		return itemstatusDao.selectByPrimaryKey(archiveGuid);
	}

	@Override
	public Result updateRfidByPrimaryKey(Integer itemGuid, String rfid, String status) throws ParseException {
		Result result = new Result();
		Map m = new HashMap();
		m.put("guid", itemGuid);
		Itemstatus item = itemstatusDao.selectByPrimaryKey(itemGuid);
		if (null != item) {
			m.put("rfid", rfid);
		} else {
			result.setStatus(1);
			result.setMessage("找不到分卷,标签写入失败！");
			return result;
		}
		m.put("status", "");
		m.put("uptime", new Date());
		int flag = itemstatusDao.updateRfidByPrimaryKey(m);
		if ("0".equals(item.getStatus())) {
			Map param = new HashMap();
			param.put("status", ArchiveStatus.READY_SHELIVES.getValue());
			param.put("guid", item.getGuid());
			archivesInfoMapperDao.updateItemByPrimaryKey(param);// 将第一次贴标签的档案设置为“待上架”状态
		}
		// 生成流水号
		Integer infoId = item.getInfoid();
		ArchivesInfo dbInfo = archivesInfoMapperDao.selectByPrimaryKey(itemGuid);
		if (null != dbInfo) {
			/*String flowId = "";
			String dbFlowId = dbInfo.getFlowid();
			// if(StringUtils.isBlank(dbFlowId)){
			String year = dbInfo.getYear();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			ArcSeq condition = new ArcSeq();
			condition.setSeqyear(year);
			// 2016/06/04临时更改,应该根据实际数据的档案性质来设置
			condition.setSeqname(dbInfo.getMarkType());
			condition.setSeqgroup(Consts.GROUP_ARC_FLOW);
			ArcSeq seq = arcSeqDao.selectByCondition(condition);
			// TODO dbInfo.getCasecategory() 需要替换成类型标记（民事：MS；刑事：XS 等）
			status = status == null ? "" : status;
			if (null == seq) {
				condition.setSeqvalue(2);
				arcSeqDao.insertSelective(condition);
				flowId = year + "-" + dbInfo.getMarkType() + "-" + "1";
			} else {
				flowId = year + "-" + dbInfo.getMarkType() + "-"
						+ seq.getSeqvalue();
				seq.setSeqvalue(seq.getSeqvalue() + 1);
				arcSeqDao.updateByPrimaryKeySelective(seq);
			}*/
			// }
			/*String[] flowIds = flowId.split("-");
			if (flowIds[2].length() < 4) {
				if (flowIds[2].length() == 1) {
					flowId = flowIds[0] + "-" + flowIds[1] + "-" + "000"
							+ flowIds[2];
				} else if (flowIds[2].length() == 2) {
					flowId = flowIds[0] + "-" + flowIds[1] + "-" + "00"
							+ flowIds[2];
				} else if (flowIds[2].length() == 3) {
					flowId = flowIds[0] + "-" + flowIds[1] + "-" + "0"
							+ flowIds[2];
				}
			}
			//库房整理重写规则
			if ("1".equals(status)) {
				String tnum = dbInfo.getNumber().replaceAll("^[0]*", ""); //去掉开头编码
				if (tnum.length() < 6) {
					if (tnum.length() == 1) {
						tnum = "000" + tnum;
					} else if (tnum.length() == 2) {
						tnum = "00" + tnum;
					} else if (tnum.length() == 3) {
						tnum = "0" + tnum;
					}
				}
				flowId = year + "-" + dbInfo.getMarkType() + "-" + tnum; // 库房整理用的流水号,采用年度+档案号的形式
			}
			ArchivesInfo info = new ArchivesInfo();
			info.setGuid(infoId);
			if (StringUtils.isEmpty(dbInfo.getFlowid())) { //重写标签时,流水号不重写!
				info.setFlowid(flowId);
			}
			info.setStatus(ArchiveStatus.READY_SHELIVES.getValue());
			flag = archivesInfoMapperDao.updateByPrimaryKeySelective(info);*/
		} else {
			result.setStatus(1);
			result.setMessage("找不到档案！");
			return result;
		}

		item = itemstatusDao.selectItemWithFlowId(itemGuid);
		if (-1 < flag) {
			result.setStatus(0);
			result.setMessage("电子标签写入成功！");
			result.setData(item);
			logger.info(String.valueOf(dbInfo.getCaseno()) + " -- 电子标签写入成功！");
		} else {
			result.setStatus(1);
			result.setMessage("电子标签写入失败！");
			logger.info("电子标签写入失败！");
		}
		return result;
	}
	
	public static void main(String[] args) {
		String str = "00008090";
		System.out.println(str.replaceAll("^[0]*", ""));
	}

	@Override
	public int selectInfoIdByPrimaryKey(Integer guid) {
		return itemstatusDao.selectInfoIdByPrimaryKey(guid);
	}

	@Override
	public int createItemStatus(Itemstatus status) {
		return itemstatusDao.insertSelective(status);
	}

	@Override
	public int getNextIdByItemType(Integer infoid, Integer itemType) {
		Integer maxId = itemstatusDao.findMaxIdByItemType(infoid, itemType);
		maxId = maxId == null ? 0 : maxId;
		if (itemType == 1 && maxId == 0) {
			maxId = 1; //主卷即是正卷1,所以正卷分卷应该从正卷2开始
		}
		return maxId + 1;
	}

	@Override
	public int deleteById(Integer guid) {
		return itemstatusDao.delItemById(guid);
	}

	@Override
	public List<Itemstatus> findByItemstatusNo(Integer infoid) {
		// TODO Auto-generated method stub
		return itemstatusDao.findByItemstatusNo(infoid);
	}

}
