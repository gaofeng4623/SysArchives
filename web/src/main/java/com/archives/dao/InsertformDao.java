package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.Insertform;

public interface InsertformDao {

    int insertSelective(Insertform record);

    Insertform selectByPrimaryKey(String formid);

    int updateByPrimaryKeySelective(Insertform record);

    public List<Insertform> findInsertformForPage(@Param("paraMap") Map<String, Object> paraMap);

    public int findCountInsertformForPage(@Param("paraMap") Map<String, Object> paraMap);

}