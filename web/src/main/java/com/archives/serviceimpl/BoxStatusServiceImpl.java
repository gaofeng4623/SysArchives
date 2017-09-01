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

import com.archives.common.ArchiveStatus;
import com.archives.dao.BoxStatusDao;
import com.archives.pojo.BoxStatus;
import com.archives.service.BoxStatusService;
import com.system.base.pojo.Result;

@Service
public class BoxStatusServiceImpl implements BoxStatusService {
	private static final Logger logger = LoggerFactory
			.getLogger(BoxStatusServiceImpl.class);
	@Resource
	private BoxStatusDao boxStatusDao;

	@Override
	public List<BoxStatus> findByBoxNo(Integer boxid) {
		return boxStatusDao.findByBoxNo(boxid);
	}

	@Override
	public BoxStatus selectByPrimaryKey(Integer boxGuid) {
		return boxStatusDao.selectByPrimaryKey(boxGuid);
	}

	@Override
	public Result updateRfidByPrimaryKey(Integer guid, String rfid,
			String status) throws ParseException {
		Result result = new Result();
		Map m = new HashMap();
		m.put("guid", guid);
		BoxStatus item = boxStatusDao.selectByPrimaryKey(guid);
		if (null != item) {
			m.put("rfid", rfid);
		} else {
			result.setStatus(1);
			result.setMessage("找不到分卷,标签写入失败！");
			return result;
		}
		m.put("status", ArchiveStatus.READY_SHELIVES.getValue());
		m.put("uptime", new Date());
		int flag = boxStatusDao.updateRfidByPrimaryKey(m);
		item = boxStatusDao.selectByPrimaryKey(guid);

		if (-1 < flag) {
			result.setStatus(0);
			result.setMessage("电子标签写入成功！");
			result.setData(item);
			logger.info("文书盒子标签写入成功,标签号--" + rfid);
		} else {
			result.setStatus(1);
			result.setMessage("电子标签写入失败！");
			logger.info("文书盒子标签写入失败！");
		}
		return result;
	}
	@Override
	public int selectInfoIdByPrimaryKey(Integer guid) {
		return boxStatusDao.selectInfoIdByPrimaryKey(guid);
	}
}
