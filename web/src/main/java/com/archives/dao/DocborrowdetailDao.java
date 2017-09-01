package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.Docborrowdetail;

public interface DocborrowdetailDao {
    int deleteByPrimaryKey(Integer guid);

    int insert(Docborrowdetail record);

    int insertSelective(Docborrowdetail record);

    Docborrowdetail selectByPrimaryKey(Integer guid);

    int updateByPrimaryKeySelective(Docborrowdetail record);

    int updateByPrimaryKey(Docborrowdetail record);
    public List selectByBorrowId(String borrowId);
    public List<Docborrowdetail> findDocborrowdetailForPage(@Param("paraMap") Map paraMap);
   	public int findCountDocborrowdetailForPage(@Param("paraMap") Map paraMap);
    public int selectListTail(int borrowId);
}