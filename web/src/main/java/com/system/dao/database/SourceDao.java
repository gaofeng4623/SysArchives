package com.system.dao.database;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.system.base.pojo.Source;

public interface SourceDao {

	List<Source> findAllSource();

	int countSourceBySourceIds(@Param("idList") List<String> idList);

	List<Source> selectSourceBySourceIds(@Param("idList") List<String> idList, @Param("start") int start, @Param("rows") int rows);

	List<Source> selectLimitedSourceByRoleId(@Param("roleId") String roleId);
	
	Source selectBySourceId(@Param("sourceId") String guid);
	
	List<Source> selectByTypeName(@Param("type") String type, @Param("userId") String userId);

	int deleteBySourceId(@Param("sourceId") String sourceId);
	
	int deleteMembersBySourceId(@Param("sourceId") String sourceId);

	int insertSelective(Source record);

	int updateBySelective(Source record);
	
    int insertRoleSourceForMysql(@Param("roleId")String roleId, @Param("data")String[] data);
	
	int insertRoleSourceForOracle(@Param("roleId")String roleId, @Param("data")String[] data);

	int removeRoleSource(@Param("roleId")String roleId);
}