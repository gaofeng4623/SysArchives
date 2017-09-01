package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.InsertDetail;

public interface InsertDetailDao {

    int insertSelective(InsertDetail record);

    InsertDetail selectByPrimaryKey(Integer guid);

    int updateByPrimaryKeySelective(InsertDetail record);
    
    public List<InsertDetail> findInsertformDetailForPage(@Param("paraMap") Map<String, Object> paraMap);

    public int findCountInsertformDetailForPage(@Param("paraMap") Map<String, Object> paraMap);
    
    public List<InsertDetail> findInsertformDetail(@Param("paraMap") Map<String, Object> paraMap);
}