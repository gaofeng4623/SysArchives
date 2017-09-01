package com.archives.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.MapTotal;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface ArchivesInfoService {
	
	public int save (ArchivesInfo p) throws Exception;
	public List findAll() throws Exception;
	public Result update(ArchivesInfo p) throws Exception;
	public int delete(ArchivesInfo p) throws Exception;
	public ArchivesInfo findByItemGuid(Integer guid) throws Exception; //根据分表ID关联查询多表
	public ArchivesInfo findByInfoId(Integer guid) throws Exception; //根据主键查询信息,不关联分表
    public Integer countArchivesInfo() throws Exception;
	public List<ArchivesInfo> findEmpByArchivesForPage(@Param("paraMap") Map paraMap)throws Exception;
	public Pager findEmpByArchiveForPage(ArchivesInfo emp,int start, int totalSize )throws Exception;
	public Pager findLocationByArchiveForPage(ArchivesInfo archivesInfo,int start, int totalSize )throws Exception;
	public List<ArchivesInfo> findAllByIds(List<String> idList);
	public Pager findArchivesInfoForShelves(ArchivesInfo archivesInfo, int start, int rows) throws Exception;
	public Pager findArchivesForChange(ArchivesInfo archivesInfo, int start, int rows)throws Exception;
	public Pager findBorrowByArchiveForPage(ArchivesInfo emp,int start, int totalSize )throws Exception;
	public Result findUpArchivesForTodo();
	public Result destroyAllByIds(List<String> idList);
	Result saveArchivesInfo(ArchivesInfo info);
	Result updateArchivesInfo(ArchivesInfo info);
	Result delArchivesInfo(List<Integer> idList);
	public List<MapTotal> findTotal();
	public Pager synArchivesInfo(ArchivesInfo archivesInfo,int start, int totalSize )throws Exception;
}
