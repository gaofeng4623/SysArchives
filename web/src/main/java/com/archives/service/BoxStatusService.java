package com.archives.service;
import java.text.ParseException;
import java.util.List;

import com.archives.pojo.BoxStatus;
import com.system.base.pojo.Result;

public interface BoxStatusService {
	
	public  List<BoxStatus> findByBoxNo(Integer boxid);
	public BoxStatus selectByPrimaryKey(Integer boxGuid);
	public Result updateRfidByPrimaryKey(Integer guid, String rfid, String status) throws ParseException;
	public int selectInfoIdByPrimaryKey(Integer guid);

}
