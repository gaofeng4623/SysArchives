package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.LocationConfig;
import com.archives.pojo.LocationInfo;
import com.archives.pojo.LocationType;

public interface LocationInfoDao {
	int deleteByPrimaryKey(Integer guid);

	int insert(LocationInfo record);

	int insertSelective(LocationInfo record);

	LocationInfo selectByPrimaryKey(Integer guid);

	int updateByPrimaryKeySelective(LocationInfo record);

	int updateByPrimaryKey(LocationInfo record);

	List<LocationInfo> getNodesByParentId(Integer pid);
	
	List<LocationInfo> getCellsByParentId(@Param("pid") Integer pid);

	List<LocationInfo> NodesByParentId(@Param("pid") Integer pid,
			@Param("type") String type);

	List<LocationConfig> queryPageLocationList(@Param("m") Map m);

	int queryPageLocationCount(@Param("m") Map m);

	List<LocationType> getTypeList(@Param("parentcode") Integer parentcode);

	Integer queryMaxSerialNo(Integer pid);

	int updateLocationPath(@Param("map") Map map);

	int delLocationInfoByIds(@Param("idList") List<String> idList);

	int updateSerialNoByGuid(@Param("map") Map map);

	int updateSerialNoPath(@Param("map") Map map);

	public List<LocationInfo> queryPageLocationAll();

	List<LocationInfo> getCellLocationByLocationName(@Param("map") Map map);

	String getLocationPathByIds(@Param("idList") List<Integer> idList);

	int getLocationCount(@Param("idList") List<String> idList);

}