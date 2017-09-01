package com.archives.service;


import java.util.List;

import javax.servlet.http.HttpSession;

import com.archives.pojo.Borrow;
import com.archives.pojo.BorrowDetail;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

public interface BorrowService {
	int saveBorrowInfo(Borrow borrow) throws Exception;
	int updateBorrowInfo(Borrow borrow) throws Exception;
	int clearBorrowDetail(String borrowId);
	void delete(String borrowId) throws Exception;
	Pager queryBorrowInfo(int start, int rows,String[] guids) throws Exception;
	Pager findBorrowAllList(Borrow bor,int start, int rows,HttpSession session) throws Exception;
	Pager findPersonBorrowList(Borrow bor,int start, int rows,HttpSession session) throws Exception;
	public List findTableDetail(String borrowId) throws Exception;
	public List findDetail(String borrowId) throws Exception;
	public BorrowDetail findrfid(String rfid) throws Exception;
	Borrow findBorrowById(String borrowId);
	public void takeDown(String infoId) throws Exception;  //下架
	public void borrowLock(String infoId) throws Exception;
	public boolean testBorrowLock(String infoIds) throws Exception;
	public void reborrowLock(String infoId) throws Exception;
	public void unReborrowLock(String borrowId) throws Exception;
	public void takedLock(String borrowId, String status) throws Exception;
	public void setBorrowMainStatus(String borrowId);  //更新借阅交付状态
	public void setBorrowDetailStatus(String infoIds); //更新借阅明细的交付状态
	public void setReborrowStatus(String borrowId);
	public void setReborrowDetailStatus(String borrowId, String infoIds);
	public Result updateArchivesInfoStatus(List<String> infguid,String borrborrowId ,List<String> tailguid) throws Exception;
	public int findBorrowtableCount(String borrowId) throws Exception;
	public String getFormSeq(); //生成借阅单号
	void createBorrowStatus(String borrowId, String status) throws Exception;
	void applyBorrowForChange(Borrow borrow)throws Exception;
	List<Borrow> findBorrowRenew(String parentId);
	Pager findBorrowRenewList(Borrow bor, int start, int rows,
			HttpSession session) throws Exception;
	
	void updateBorrowStatus(String borrowId) throws Exception;
	void updateReborrowStatus(String borrowId) throws Exception;
	boolean isReborrow(String borrowId);
}
