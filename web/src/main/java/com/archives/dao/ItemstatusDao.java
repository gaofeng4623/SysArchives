package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.Itemstatus;

public interface ItemstatusDao {
	int deleteByPrimaryKey(Integer guid);

	int insert(Itemstatus record);

	int insertSelective(Itemstatus record);

	Itemstatus selectByPrimaryKey(Integer guid);

	int updateByPrimaryKeySelective(Itemstatus record);

	int updateByPrimaryKey(Itemstatus record);

	public List<Itemstatus> findByArchivesNo(@Param("infoid") Integer infoid);

	int updateRfidByPrimaryKey(@Param("paraMap") Map paraMap);

	int countArchive(@Param("locationPath") String locationPath);

	Itemstatus selectItemWithFlowId(Integer guid);

	int selectInfoIdByPrimaryKey(@Param("guid") Integer guid);

	int delItemsByIds(@Param("idList") List<Integer> idList);
	
	int delItemById(@Param("guid") Integer guid);
	
	Integer findMaxIdByItemType(@Param("infoid") Integer infoid, @Param("itemType") Integer itemType);
	public List<Itemstatus> findByItemstatusNo(@Param("infoid") Integer infoid);
}