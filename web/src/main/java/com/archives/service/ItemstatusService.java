package com.archives.service;

import java.text.ParseException;
import java.util.List;

import com.archives.pojo.Itemstatus;
import com.system.base.pojo.Result;

public interface ItemstatusService {
	
	public List<Itemstatus> findByArchiveId(Integer infoid);
	public Itemstatus selectByPrimaryKey(Integer archiveGuid);
	public Result updateRfidByPrimaryKey(Integer itemGuid, String rfid, String status) throws ParseException;
	public int selectInfoIdByPrimaryKey(Integer guid);
	public int createItemStatus(Itemstatus status);
	public int getNextIdByItemType(Integer infoid, Integer itemType);
	public int deleteById(Integer guid);
	public List<Itemstatus> findByItemstatusNo(Integer infoid);
}
