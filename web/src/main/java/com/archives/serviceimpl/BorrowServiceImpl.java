package com.archives.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archives.dao.BorrowDao;
import com.archives.pojo.Borrow;
import com.archives.pojo.BorrowDetail;
import com.archives.service.BorrowService;
import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.base.pojo.SysRole;
import com.system.dao.database.SysRoleDao;
import com.system.util.common.Consts;
import com.system.workflow.activiti.commons.Pager;

@Service
public class BorrowServiceImpl implements BorrowService {
	@Resource
	private BorrowDao borrowDao;
	@Autowired
	private HttpServletRequest servletRequest;
	@Resource
	private SysRoleDao sysRoleDao;

	// 借阅查询
	@Override
	public Pager findBorrowAllList(Borrow bor, int start, int rows,
			HttpSession session) throws Exception {
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		String userId = emp.getEmployeeId();
		String loginName = emp.getEmployeeLoginName();
		Map<String, Object> m = new HashMap();
		List<Borrow> borrow = borrowDao.findByuserId(userId);
		List<SysRole> currentRoles = sysRoleDao.findRolesForCurrentUser(emp
				.getEmployeeId());
		if (!loginName.equals("admin") && !hasTheRole(currentRoles, "档案室")
				&& !hasTheRole(currentRoles, "借阅室")) {
			m.put("userId", userId);
		}

		m.put("userName", bor.getEmployeeName());
		m.put("returnTime", bor.getReturnTime());
		m.put("formNo", bor.getFormNo());
		m.put("borrowTime", bor.getBorrowTime());
		m.put("returnPerson", bor.getReturnPerson());
		m.put("borrowsstatus", bor.getBorrowsstatus());
		m.put("status", bor.getStatus());
		m.put("channel", bor.getChannel());
		m.put("active", bor.getActive());
		m.put("start", start);
		m.put("rows", rows);
		List<Borrow> list = borrowDao.findBorrowAllList(m);
		int total = borrowDao.findBorrowCountAllList(m);
		return new Pager(total, list);
	}

	@Override
	public Pager findBorrowRenewList(Borrow bor, int start, int rows,
			HttpSession session) throws Exception {
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		String userId = emp.getEmployeeId();
		String loginName = emp.getEmployeeLoginName();
		Map<String, Object> m = new HashMap();
		List<Borrow> borrow = borrowDao.findByuserId(userId);
		List<SysRole> currentRoles = sysRoleDao.findRolesForCurrentUser(emp
				.getEmployeeId());
		if (!loginName.equals("admin") && !hasTheRole(currentRoles, "档案室")
				&& !hasTheRole(currentRoles, "借阅室")) {
			m.put("userId", userId);
		}

		m.put("userName", bor.getEmployeeName());
		m.put("returnTime", bor.getReturnTime());
		m.put("formNo", bor.getFormNo());
		m.put("borrowTime", bor.getBorrowTime());
		m.put("returnPerson", bor.getReturnPerson());
		m.put("borrowsstatus", bor.getBorrowsstatus());
		m.put("status", bor.getStatus());
		m.put("channel", bor.getChannel());
		m.put("active", bor.getActive());
		m.put("start", start);
		m.put("rows", rows);
		List<Borrow> list = borrowDao.findBorrowRenewList(m);
		int total = borrowDao.findBorrowRenewCount(m);
		return new Pager(total, list);
	}

	// 个人借阅记录
	@Override
	public Pager findPersonBorrowList(Borrow bor, int start, int rows,
			HttpSession session) throws Exception {
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		String userId = emp.getEmployeeId();
		Map<String, Object> m = new HashMap();
		List<Borrow> borrow = borrowDao.findByuserId(userId);
		List<SysRole> currentRoles = sysRoleDao.findRolesForCurrentUser(emp
				.getEmployeeId());
		m.put("userId", userId);
		m.put("userName", bor.getEmployeeName());
		m.put("returnTime", bor.getReturnTime());
		m.put("formNo", bor.getFormNo());
		m.put("borrowTime", bor.getBorrowTime());
		m.put("returnPerson", bor.getReturnPerson());
		m.put("borrowsstatus", bor.getBorrowsstatus());
		m.put("status", bor.getStatus());
		m.put("channel", bor.getChannel());
		m.put("active", bor.getActive());
		
		m.put("beginTime", bor.getBeginTime());
		m.put("endTime", bor.getEndTime());
		m.put("departmentName", bor.getDepartmentName());
		m.put("start", start);
		m.put("rows", rows);
		List<Borrow> list = borrowDao.findPersonBorrowList(m);
		int total = borrowDao.findCountPersonBorrowList(m);
		return new Pager(total, list);
	}

	@Override
	public int saveBorrowInfo(Borrow borrow) throws Exception {
		String[] guidArr = borrow.getItemGuids().split(",");
		borrow.setStatus("0"); // 待交付
		borrowDao.addBorrow(borrow);
		int borrowId = borrow.getBorrowId();
		for (String id : guidArr) {
			BorrowDetail borrowDetail = new BorrowDetail();
			// 生成借阅明细信息
			borrowDetail.setItemGuid(id);
			borrowDetail.setBorrowId(String.valueOf(borrowId));
			borrowDetail.setStatus("0"); // 待交付
			borrowDao.addBorrowDetail(borrowDetail);
		}
		return borrowId;
	}

	@Override
	public int updateBorrowInfo(Borrow borrow) throws Exception {
		borrowDao.updateBorrow(borrow); // 更新表单
		String[] guidArr = borrow.getItemGuids().split(",");
		int borrowId = borrow.getBorrowId();
		for (String id : guidArr) {
			BorrowDetail borrowDetail = new BorrowDetail();
			// 生成借阅明细信息
			borrowDetail.setItemGuid(id);
			borrowDetail.setBorrowId(String.valueOf(borrowId));
			borrowDetail.setStatus("0"); // 待交付
			borrowDao.addBorrowDetail(borrowDetail);
		}
		return 0;
	}

	public Pager queryBorrowInfo(int start, int rows, String[] borrowIdArr)
			throws Exception {
		Map<String, Object> m = new HashMap();
		m.put("start", start);
		m.put("rows", rows);
		m.put("borrowIdArr", borrowIdArr);
		List<Borrow> list = borrowDao.queryBorrowInfoTable(m);
		int total = borrowDao.queryBorrowInfoTableCount(m);
		return new Pager(total, list);
	}

	// 借阅详细信息（借哪几本）
	@Override
	public List findDetail(String borrowId) throws Exception {
		return borrowDao.findDetail(borrowId);
	}

	@Override
	public int findBorrowtableCount(String borrowId) throws Exception {

		return borrowDao.findBorrowtableCount(borrowId);
	}

	// 借阅详细信息（借哪几本）
	@Override
	public BorrowDetail findrfid(String rfid) throws Exception {

		return borrowDao.findrfid(rfid);
	}

	@Override
	public Borrow findBorrowById(String borrowId) {
		return borrowDao.findBorrowById(borrowId);
	}

	@Override
	public void takeDown(String itemGuids) throws Exception {
		if (itemGuids.contains(",")) {
			String[] arr = itemGuids.split(",");
			for (String s : arr) {
				borrowDao.takeDown(s);
			}
		} else {
			borrowDao.takeDown(itemGuids);
		}
	}

	/* 锁定借阅状态 */
	@Override
	public void borrowLock(String itemGuids) throws Exception {
		if (itemGuids.contains(",")) {
			String[] arr = itemGuids.split(",");
			for (String s : arr) {
				borrowDao.borrowLock(s);
			}
		} else {
			borrowDao.borrowLock(itemGuids);
		}
	}

	@Override
	public String getFormSeq() {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		Result result = new Result();
		borrowDao.getFormSeq(result);
		String number;
		Integer no= result.getStatus();
		int form=no.toString().length();
		if(form==1){
			number="0000"+no;
		}else if(form==2){
			number="000"+no;
		}else if(form==3){
			number="00"+no;
		}else if(form==4){
			number="0"+no;
		}else{
			number=no+"";
		}
		return date +number;
	}

	@Override
	public Result updateArchivesInfoStatus(List<String> infguid,
			String borrborrowId, List<String> tailguid) throws Exception {
		Result result = new Result();
		int manage = borrowDao.updateArchivesInfoStatus(infguid);
		int borrD = borrowDao.updateBorrowByStatusList(tailguid);
		int count = borrowDao.findBorrowtableCount(borrborrowId);

		Map<String, Object> m = new HashMap<String, Object>();
		int borr = 0;
		if (count == 0) {
			m.put("status", "1");
			m.put("borrborrowId", borrborrowId);
			borr = borrowDao.updateBorrowByStatus(m);
		} else {
			m.put("status", "-1");
			m.put("borrborrowId", borrborrowId);
			borr = borrowDao.updateBorrowByStatus(m);
		}
		if (-1 < manage && -1 < borr && -1 < borrD) {
			result.setStatus(0);
			result.setMessage("修改成功!");
			return result;
		} else {
			result.setStatus(1);
			result.setMessage("修改失败!");
			return result;
		}
		/*
		 * Result res = new Result(); if (null == infguid || 1 > infguid.size())
		 * { res.setStatus(1); res.setMessage("修改失败!"); return res; } else { int
		 * flag = archivesInfoMapperDao.updateArchivesInfoStatus(infguid); if
		 * (-1 < flag) { res.setStatus(0); res.setMessage("修改成功!"); return res;
		 * } else { res.setStatus(1); res.setMessage("修改失败!"); return res; } }
		 */
	}

	@Override
	public void createBorrowStatus(String borrowId, String status)
			throws Exception {
		borrowDao.createBorrowStatus(borrowId, status);
	}

	public void setBorrowMainStatus(String borrowId) {
		borrowDao.setBorrowMainStatus(borrowId);
	}

	public void setBorrowDetailStatus(String itemGuids) {
		if (itemGuids.contains(",")) {
			String[] arr = itemGuids.split(",");
			for (String s : arr) {
				borrowDao.setBorrowDetailStatus(s);
			}
		} else {
			borrowDao.setBorrowDetailStatus(itemGuids);
		}
	}

	@Override
	public void applyBorrowForChange(Borrow borrow) throws Exception {
		borrow.setFormNo(this.getFormSeq()); // 生成借阅单号
		String borrowId = String.valueOf(this.saveBorrowInfo(borrow));
		String itemGuids = borrow.getItemGuids();
		this.takeDown(itemGuids); // 下架处理
		this.borrowLock(itemGuids); // 锁定借阅状态
		this.setBorrowMainStatus(borrowId); // 更改借阅主表的交付状态
		this.setBorrowDetailStatus(itemGuids); // 更改借阅详细表的交付状态
		this.createBorrowStatus(borrowId, "1"); // 更新流程状态

	}

	public boolean hasTheRole(List<SysRole> currentRoles, String roleName) {
		for (SysRole s : currentRoles) {
			if (s == null)
				continue;
			if (!StringUtils.isEmpty(s.getRoleName())
					&& s.getRoleName().contains(roleName))
				return true;
		}
		return false;
	}

	@Override
	public int clearBorrowDetail(String borrowId) {
		return borrowDao.clearBorrowDetail(borrowId);
	}

	@Override
	public List findTableDetail(String borrowId) throws Exception {
		return borrowDao.findTableDetail(borrowId);
	}

	@Override
	public void takedLock(String borrowId, String status) throws Exception {
		borrowDao.takedLock(borrowId, status);
	}

	@Override
	public void delete(String borrowId) throws Exception {
		borrowDao.deleteBorrow(borrowId);
		borrowDao.clearBorrowDetail(borrowId);
	}

	@Override
	public void setReborrowStatus(String borrowId) {
		borrowDao.setReborrowStatus(borrowId);
	}

	@Override
	public void setReborrowDetailStatus(String borrowId, String itemGuids) {
		if (itemGuids.contains(",")) {
			String[] arr = itemGuids.split(",");
			for (String s : arr) {
				borrowDao.setReborrowDetailStatus(borrowId, s);
			}
		} else {
			borrowDao.setReborrowDetailStatus(borrowId, itemGuids);
		}

	}

	@Override
	public List<Borrow> findBorrowRenew(String parentId) {
		return borrowDao.findBorrowRenew(parentId);
	}

	@Override
	public void reborrowLock(String itemGuids) throws Exception {
		if (itemGuids.contains(",")) {
			String[] arr = itemGuids.split(",");
			for (String s : arr) {
				borrowDao.reborrowLock(s);
			}
		} else {
			borrowDao.reborrowLock(itemGuids);
		}
	}

	@Override
	public void unReborrowLock(String borrowId) throws Exception {
		borrowDao.unReborrowLock(borrowId);
	}

	@Override
	public void updateReborrowStatus(String borrowId) throws Exception {
		Map paraMap = new HashMap();
		paraMap.put("borrborrowId", borrowId);
		paraMap.put("status", "1");
		borrowDao.updateBorrowDetailByStatus(borrowId);
		borrowDao.updateBorrowByStatus(paraMap);
	}

	@Override
	public void updateBorrowStatus(String borrowId) throws Exception {
		borrowDao.updateBorrowStatus(borrowId);
	}

	@Override
	public boolean isReborrow(String borrowId) {
		return borrowDao.isReborrow(borrowId) > 0;
	}

	@Override
	public boolean testBorrowLock(String itemGuids) throws Exception {
		String[] arr = itemGuids.contains(",") ? itemGuids.split(",") : new String[]{itemGuids};
		for (String s : arr) {
			int testcount = borrowDao.testBorrowLock(s);
			if (testcount ==0) return false;
		}
		return true;
	}
	

}
