package com.archives.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archives.pojo.Borrow;
import com.archives.pojo.BorrowDetail;
import com.system.base.pojo.Result;

public interface BorrowDao {

	Integer addBorrow(Borrow borrow);

	Integer updateBorrow(Borrow borrow);

	void addBorrowDetail(BorrowDetail borrowDetail);

	int clearBorrowDetail(@Param("borrowId") String borrowId);

	void deleteBorrow(@Param("borrowId") String borrowId);

	int findBorrowCountList();
	
	int updateArchivesInfoStatus(@Param("infguid") List<String> infguid); //交付-孙家武

	List<Borrow> queryBorrowInfoTable(@Param("paraMap") Map<String, Object> paraMap);

	int queryBorrowInfoTableCount(@Param("paraMap") Map<String, Object> paraMap);

	// 借阅查询
	List<Borrow> findBorrowAllList(@Param("paraMap") Map<String, Object> paraMap);

	// 个人借阅记录
	List<Borrow> findPersonBorrowList(@Param("paraMap") Map<String, Object> paraMap);

	int findCountPersonBorrowList(@Param("paraMap") Map<String, Object> paraMap);

	int findBorrowCountAllList(@Param("paraMap") Map<String, Object> paraMap);

	// 根据用户id 查询所在角色组
	List<Borrow> findByuserId(String userId);

	public List findDetail(String borrowId) throws Exception;

	public List findTableDetail(String borrowId) throws Exception;

	public BorrowDetail findrfid(String rfid) throws Exception;

	Borrow findBorrowById(@Param("borrowId") String borrowId);

	int createBorrowStatus(@Param("borrowId") String borrowId, @Param("status") String status);

	int updateBorrowStatus(@Param("borrowId") String borrowId);
	
	int isReborrow(@Param("borrowId") String borrowId);

	int updateWorkflowStatus(@Param("borrowId") String borrowId, @Param("status") String status);

	int setReborrowStatus(@Param("borrowId") String borrowId);  //更新续借状态
	
	int setReborrowDetailStatus(@Param("borrowId") String borrowId, @Param("itemGuid")String infoId); //更新借阅详细的续借状态

	void takeDown(@Param("itemGuid") String infoId); // 下架

	void borrowLock(@Param("itemGuid") String infoId); // 锁定借阅状态
	
	int testBorrowLock(@Param("itemGuid") String infoId);
	
	void reborrowLock(@Param("itemGuid") String infoId); //锁定续借状态
	
	void unReborrowLock(@Param("borrowId") String borrowId);

	void takedLock(@Param("borrowId") String borrowId, @Param("status") String status);

	void setBorrowMainStatus(@Param("borrowId") String borrowId);

	void setBorrowDetailStatus(@Param("itemGuid") String infoId); // 更新借阅详细表交付状态

	int updateBorrowDetailByStatus(@Param("borrowId") String borrowId);

	public int findBorrowtableCount(String borrowId) throws Exception;

	int updateBorrowByStatus(@Param("paraMap") Map<String, Object> paraMap);

	int updateBorrowByStatusList(@Param("tailguid") List<String> tailguid);

	public int getFormSeq(Result result); // 借阅单号

	List<Borrow> findBorrowRenew(String parentId);

	List<Borrow> findBorrowRenewList(@Param("paraMap") Map<String, Object> paraMap);

	int findBorrowRenewCount(@Param("paraMap") Map<String, Object> paraMap);
}
