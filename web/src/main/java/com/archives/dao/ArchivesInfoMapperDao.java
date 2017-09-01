package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.ArchivesInfo;
import com.system.workflow.activiti.commons.Pager;

public interface ArchivesInfoMapperDao {
   //查询所有数据
	public List findAll();
	public ArchivesInfo selectByPrimaryKey(@Param("itemGuid")Integer itemGuid);
	public ArchivesInfo findByInfoId(@Param("infoId")Integer infoId);
	public ArchivesInfo selectByCaseNo(String caseNo); //根据案号查找
    public Integer countArchivesInfo();
	public List<ArchivesInfo> findEmpByArchivesForPage(@Param("paraMap") Map paraMap);
	public Pager findEmpByArchiveForPage(ArchivesInfo emp,int start, int totalSize );
	///////
	public List<ArchivesInfo> findEmpArchiveForPage(@Param("paraMap") Map paraMap);
	public int findCountArchivesForPage(@Param("paraMap") Map paraMap);
	
	public List<ArchivesInfo> findLocationByArchiveForPage(@Param("paraMap") Map paraMap);
	public int findCountLocationArchivesForPage(@Param("paraMap") Map paraMap);
	public int updateByPrimaryKeySelective(ArchivesInfo info);
	public int updateItemByPrimaryKey(@Param("data") Map data);	
	public List<ArchivesInfo> findAllByIds(@Param("idList")List<String> idList);
	public List<ArchivesInfo> findArchivesInfoForShelves(@Param("paraMap")Map<String, Object> m);
	public int findCountArchivesForShelves(@Param("paraMap")Map<String, Object> m);
	public List<ArchivesInfo> findArchivesForChange(@Param("paraMap")Map<String, Object> m);
	public int findCountArchivesForChange(@Param("paraMap")Map<String, Object> m);
	public List<ArchivesInfo> findBorrowArchiveForPage(@Param("paraMap") Map paraMap);
	public int findCountBorrowArchivesForPage(@Param("paraMap") Map paraMap);
	public int findUpArchivesForTodo();
	public int destroyByIds(@Param("idList")List<String> idList);
	public int updateItemStatus(@Param("data") Map data);
	
	public List<ArchivesInfo> findArchivesByEmptyFlowId(); //查询流水号为空的
	int insertSelective(ArchivesInfo info);
	int delInfoByIds(@Param("idList") List<Integer> idList);
	public List<ArchivesInfo> synLocationByArchiveForPage(@Param("paraMap") Map paraMap);
	public int synCountLocationArchivesForPage(@Param("paraMap") Map paraMap);

}