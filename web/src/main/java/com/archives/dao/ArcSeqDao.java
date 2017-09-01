package com.archives.dao;

import com.archives.pojo.ArcSeq;

public interface ArcSeqDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ArcSeq record);

    int insertSelective(ArcSeq record);

    ArcSeq selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArcSeq record);

    int updateByPrimaryKey(ArcSeq record);

	ArcSeq selectByCondition(ArcSeq condition);
}