package com.system.dao.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.system.base.pojo.SysLog;

public interface SysLogDao {

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Integer guid);

    int updateByPrimaryKeySelective(SysLog record);

    public List<SysLog> findSysLogForPage(@Param("paraMap") Map<String, Object> paraMap);
	
    public int findCountSysLogForPage(@Param("paraMap") Map<String, Object> paraMap);
    
    public List<SysLog> findSynLogForPage(@Param("paraMap") Map<String, Object> paraMap);
    
    public int findCountSynLogForPage(@Param("paraMap") Map<String, Object> paraMap);

}