package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.BoxStatus;

public interface BoxStatusDao {
	
	int deleteByPrimaryKey(Integer guid);

	int insert(BoxStatus record);

	int insertSelective(BoxStatus record);

	BoxStatus selectByPrimaryKey(Integer guid);

	int updateByPrimaryKeySelective(BoxStatus record);

	int updateByPrimaryKey(BoxStatus record);

	public List<BoxStatus> findByBoxNo(@Param("boxid") Integer boxid);

	int updateRfidByPrimaryKey(@Param("paraMap") Map paraMap);

	int selectInfoIdByPrimaryKey(@Param("guid") Integer guid);
	
	int countArchive(@Param("locationPath") String locationPath);
	
}