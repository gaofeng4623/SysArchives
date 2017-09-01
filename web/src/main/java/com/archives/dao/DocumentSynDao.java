package com.archives.dao;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.Document;

public interface DocumentSynDao {
	public Document queryDocument(@Param("drid") int drid);
	public int savaDocument(Document doc);
	public int selectMaxBusinessId();
	/*public Box queryBox(@Param("boxNumber") String boxNumber);
	public int saveBox(Box box);
	public int saveBoxStatus(BoxStatus status);*/
}
