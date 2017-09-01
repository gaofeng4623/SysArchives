package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.Borrow;

public interface ArchivesCollectionDao {

	List<Borrow> findCollection(@Param("m")Map<String, Object> m);

	int findCollectionCountList(@Param("m")Map<String, Object> m);

	List<ArchivesInfo> findCollectionNopage(@Param("middleTime")String middleTime,@Param("undertakeDep")String undertakeDep);
	List<ArchivesInfo> listInfo(@Param("middleTime")String middleTime,@Param("undertakeDep")String undertakeDep);
	List<ArchivesInfo> filingReate(@Param("m")Map<String, Object> m);

	List<ArchivesInfo> filingReateCount(@Param("m")Map<String, Object> m);

}
