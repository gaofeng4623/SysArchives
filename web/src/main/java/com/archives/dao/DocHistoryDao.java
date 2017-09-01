package com.archives.dao;

import com.archives.pojo.DocHistory;

public interface DocHistoryDao {
    int deleteByPrimaryKey(Integer guid)throws Exception;

    int insert(DocHistory record)throws Exception;

    int insertSelective(DocHistory record)throws Exception;

    DocHistory selectByPrimaryKey(Integer guid)throws Exception;

    int updateByPrimaryKeySelective(DocHistory record)throws Exception;

    int updateByPrimaryKey(DocHistory record)throws Exception;
}