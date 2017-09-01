package com.archives.web.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.Borrow;
import com.archives.pojo.BorrowDetail;
import com.archives.service.ArcHistoryService;
import com.archives.service.BorrowComments;
import com.archives.service.BorrowService;
import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.base.pojo.SysRole;
import com.system.core.service.SysRoleService;
import com.system.util.common.Consts;
import com.system.workflow.activiti.commons.Pager;
import com.system.workflow.activiti.service.WorkflowProcessCoreService;
import com.system.workflow.activiti.util.UserUtil;

/**
 * 借阅
 * 
 * @author zfn
 * 
 */
@Controller
@RequestMapping("/borrow")
public class BorrowController {
	@Resource
	private BorrowService borrowService;
	@Resource
	private SysRoleService sysRoleService;
	@Autowired
	private HttpServletRequest servletRequest;
	@Autowired
	WorkflowProcessCoreService workflowProcessCoreService;
	@Autowired
	BorrowComments borrowComments;
	@Resource
	private ArcHistoryService arcHistoryService;
	private Properties data;
	/**
	 * 借阅查询
	 * 
	 * @return
	 */
	@RequestMapping("/findBorrowAllList.do")
	@ResponseBody
	public Pager findBorrowAllList(Borrow bor, int rows, int page,
			HttpSession session) {
		Pager resultPage = null;
		try {
			int start = (page - 1) * rows;
			resultPage = borrowService.findBorrowAllList(bor, start, rows,
					session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultPage;
	}
	/**
	 * 续借查询
	 * 
	 * @return
	 */
	@RequestMapping("/findBorrowRenewList.do")
	@ResponseBody
	public Pager findBorrowRenewList(Borrow bor, int rows, int page,
			HttpSession session) {
		Pager resultPage = null;
		try {
			int start = (page - 1) * rows;
			resultPage = borrowService.findBorrowRenewList(bor, start, rows,
					session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultPage;
	}
	
	public BorrowController () {
		 try {
			    InputStream in = this.getClass().getClassLoader().getResourceAsStream("data.properties");
			    Properties p = new Properties();
			    p.load(in);
			    this.data = p;
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
	}
	
	/**
	 * 借阅查询
	 * 
	 * @return
	 */
	@RequestMapping("/findPersonBorrowList.do")
	@ResponseBody
	public Pager findPersonBorrowList(Borrow bor, int rows, int page,
			HttpSession session) {
		Pager resultPage = null;
		try {
			int start = (page - 1) * rows;
			resultPage = borrowService.findPersonBorrowList(bor, start, rows,
					session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultPage;
	}
	
	
	/**
	 * 返还跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toBorrowAllList.do")
	@ResponseBody
	public ModelAndView toBorrowAllList() {
		return  new ModelAndView("borrowManage");
	}
	
	/**
	 * 保存借阅单(借阅人)
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/saveBorrow.do")
	@ResponseBody
	public Result saveBorrow(Borrow borrow, HttpSession session)
			throws Exception {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			borrow.setFormNo(borrowService.getFormSeq()); //生成借阅单号
			borrow.setBorrowTime(sdf.format(new Date())); 
			if ("0".equals(borrow.getWorkflow())) return new Result(0, "请选择审批流程");
			borrowService.saveBorrowInfo(borrow);
			return new Result(1, "保存成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(0, "出现异常" + e.getMessage());
		}
	}
	
	
	/**
	 * 保存借阅单(阅卷室)
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/saveBorrowCommon.do")
	@ResponseBody
	public Result saveBorrowCommon(Borrow borrow, HttpSession session)
			throws Exception {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String itemGuids = borrow.getItemGuids();
			borrow.setFormNo(borrowService.getFormSeq()); //生成借阅单号
			borrow.setBorrowTime(sdf.format(new Date())); 
			//应设为已提交
			borrowService.takeDown(itemGuids);  //下架处理
			borrowService.borrowLock(itemGuids); //锁定借阅状态
			String borrowId = String.valueOf(borrowService.saveBorrowInfo(borrow)); 
			borrowService.takedLock(borrowId, "1");
			borrowService.setBorrowMainStatus(borrowId); //更改借阅主表的交付状态
			borrowService.setBorrowDetailStatus(itemGuids); //更改借阅详细表的交付状态 
			return new Result(1, "交付成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(0, "出现异常" + e.getMessage());
		}
	}
	
	@RequestMapping("/findUpdateView.do")
	@ResponseBody
	public ModelAndView findUpdateView(String borrowId)
			throws Exception {
		Borrow borrow = borrowService.findBorrowById(borrowId);
		ModelAndView view = new ModelAndView("borrowRequestEdit");
		view.addObject("borrow", borrow);
		return view;
	}
	
	@RequestMapping("/findFormView.do")
	@ResponseBody
	public ModelAndView findFormView(String borrowId)
			throws Exception {
		Borrow borrow = borrowService.findBorrowById(borrowId);
		ModelAndView view = new ModelAndView("borrowRequestShow");
		view.addObject("borrow", borrow);
		return view;
	}
	
	
	/**
	 * 更新借阅单
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/updateBorrow.do")
	@ResponseBody
	public Result updateBorrow(Borrow borrow, HttpSession session)
			throws Exception {
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Employee emp = (Employee) session.getAttribute(Consts.userkey);
			borrow.setFormNo(borrowService.getFormSeq()); //生成借阅单号
			borrow.setBorrowTime(sdf.format(new Date())); 
			if ("0".equals(borrow.getWorkflow())) return new Result(0, "请选择审批流程");
			borrowService.clearBorrowDetail(borrow.getBorrowId().toString()); //先清空借阅明细
			borrowService.updateBorrowInfo(borrow);
			return new Result(1, "保存成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(0, "出现异常" + e.getMessage());
		}
	}
	
	
	/**
	 * 发起借阅申请
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/applyBorrow.do")
	@ResponseBody
	public Result borrow(String borrowId, HttpSession session) throws Exception {
		Borrow borrow = borrowService.findBorrowById(borrowId);
		String itemGuids = borrow.getItemGuids();
		if (!borrowService.testBorrowLock(itemGuids)) {
			return new Result(0, "您选择的档案已经被借出!");
		}
		String loginNames = borrow.getSelectPersons();
		Map variables = new HashMap();
		Map urlParam = new HashMap();
		urlParam.put("borrowId", borrowId);
		String userId =UserUtil.getUserFromSession(session).getId(); 
		Map src = new HashMap();
		src.put("userId", userId);
		src.put("loginNames", loginNames);
		src.put("reborrow", "0");
		initDataPackage(src, variables);
		String instanceId = workflowProcessCoreService.startProcess(String.valueOf(variables.get("procDefKey")), borrowId, userId, variables, urlParam); 
		String taskId = workflowProcessCoreService.findCurrentTask(instanceId).get(0).getId();
		if ("1".equals(borrow.getWorkflow())) { //同庭档案审批
			variables.put("pass", 1);
			workflowProcessCoreService.commitProcess(taskId, userId, borrow.getCaseDetail(), variables);
		} else if ("2".equals(borrow.getWorkflow())){ //跨庭档案审批
			variables.put("pass", 2);
			workflowProcessCoreService.commitProcess(taskId, userId, borrow.getCaseDetail(), variables);
		} else {
			return new Result(0, "请选择审批流程");
		}
		borrowService.borrowLock(itemGuids);
		borrowService.takedLock(borrowId, "1");
		return new Result(0, "提交成功");
	}
	
	
	/**
	 * 填写借阅原因
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/borrowCase.do")
	@ResponseBody
	public ModelAndView borrowCase(String borrowId, String itemGuids, HttpSession session) throws Exception {
		ModelAndView model = new ModelAndView("/alert/borrowCase");
		Borrow borrow = borrowService.findBorrowById(borrowId);
		borrow.setItemGuids(itemGuids);
		model.addObject("borrow", borrow);
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		model.addObject("userattr", emp);
		return model;
	}
	
	
	/**
	 * 续借
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/reborrow.do")
	@ResponseBody
	public Result reborrow(Borrow borrow, HttpSession session) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		String itemGuids = borrow.getItemGuids();
		borrow.setFormNo(borrowService.getFormSeq()); //生成借阅单号
		borrow.setBorrowTime(sdf.format(new Date())); 
		if ("0".equals(borrow.getWorkflow())) return new Result(0, "请选择审批流程");
		int borrowId = borrowService.saveBorrowInfo(borrow);
		//更新原表续借状态
		borrowService.setReborrowStatus(borrow.getParentId().toString());
		borrowService.setReborrowDetailStatus(borrow.getParentId().toString(), itemGuids);
		Borrow reborrow = borrowService.findBorrowById(String.valueOf(borrowId));
		String loginNames = reborrow.getSelectPersons();
		Map variables = new HashMap();
		Map urlParam = new HashMap();
		urlParam.put("borrowId", borrowId);
		String userId =UserUtil.getUserFromSession(session).getId(); 
		Map src = new HashMap();
		src.put("userId", userId);
		src.put("loginNames", loginNames);
		src.put("reborrow", "1");
		initDataPackage(src, variables);
		String instanceId = workflowProcessCoreService.startProcess(
				String.valueOf(variables.get("procDefKey")), String.valueOf(borrowId), userId, variables, urlParam); 
		String taskId = workflowProcessCoreService.findCurrentTask(instanceId).get(0).getId();
		if ("1".equals(reborrow.getWorkflow())) { //同庭档案审批
			variables.put("pass", 1);
			workflowProcessCoreService.commitProcess(taskId, userId, reborrow.getCaseDetail(), variables);
		} else if ("2".equals(reborrow.getWorkflow())){ //跨庭档案审批
			variables.put("pass", 2);
			workflowProcessCoreService.commitProcess(taskId, userId, reborrow.getCaseDetail(), variables);
		} else {
			return new Result(0, "请选择审批流程");
		}
		borrowService.reborrowLock(itemGuids);
		borrowService.takedLock(String.valueOf(borrowId), "1");
		return new Result(0, "续借申请已提交");
	}
	
	
	
	
	public void initDataPackage(Map srcMap, Map variables) {
		for (Iterator it = data.keySet().iterator(); it.hasNext();) {
			String key = String.valueOf(it.next()).trim();
			String value = data.getProperty(key);
			if (value.startsWith("#")) {
				Object val = srcMap.get(value.replaceFirst("#", ""));
				if (val != null) variables.put(key, val) ;
			} else {
				 variables.put(key, value);
			}
		}
	}
	
	/**
	 * 转借阅操作
	 * 注：此操作在档案数据同步过来之后进行。
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/applyBorrowForChange.do")
	@ResponseBody
	public Result applyBorrowForChange(Borrow borrow, HttpSession session, String operation)
			throws Exception {
			try {
				borrow.setChannel("0");  // 设置转借阅类型 
				String itemGuids = borrow.getItemGuids();
				if (itemGuids == null || itemGuids.length() == 0) {
					return new Result(0, "请选择要转借阅的档案!"); 
				}
				/********记录档案轨迹开始***********/
				borrowService.applyBorrowForChange(borrow);
				if (itemGuids.contains(",")) {
					String[] arr = itemGuids.split(",");
					for (String s : arr) {
						arcHistoryService.insertArcHistory(Integer.parseInt(s), "转借阅成功", null);
					}
				} else {
					arcHistoryService.insertArcHistory(Integer.parseInt(itemGuids), "转借阅成功", null);
				}
				/********记录档案轨迹结束***********/
				return new Result(1, "转借阅成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new Result(0, "转借阅失败,错误信息 : " + e.getMessage());
			}  
	}

	
	public boolean hasTheRole(List<SysRole> currentRoles, String roleName) {
		for (SysRole s : currentRoles) {
			if (s == null) continue;
			if (!StringUtils.isEmpty(s.getRoleName())
					&& s.getRoleName().contains(roleName))
				return true;
		}
		return false;
	}
	
	@RequestMapping("/handle.do")
	@ResponseBody
	public ModelAndView handle(String borrowId) {
		Borrow borrow = borrowService.findBorrowById(borrowId);
		ModelAndView form = new ModelAndView("handleFrom");
		form.addObject("borrow", borrow);
		return form;
	}
	

	/**
	 * 借阅详细信息（借哪几本）
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/findDetail.do")
	@ResponseBody
	public ModelAndView findDetail(String borrowId) throws Exception {
		Borrow borrow = borrowService.findBorrowById(borrowId);
		ModelAndView mv = new ModelAndView("alert/findBorrowDetail");
		mv.addObject("borrow", borrow);
		return mv; 
	}
	@RequestMapping("/findBorrowManagement.do")
	@ResponseBody
	public ModelAndView findBorrowManagement(String borrowId,String rfid) throws Exception {
		Borrow borrow = borrowService.findBorrowById(borrowId);
		ModelAndView mv = new ModelAndView("alert/findBorrowManagement");
		mv.addObject("borrow", borrow);
		mv.addObject("rfid",rfid);
		return mv; 
	}
	@RequestMapping("/handleFrom.do")
	@ResponseBody
	public ModelAndView handleFrom(String borrowId) throws Exception {
		Borrow borrow = borrowService.findBorrowById(borrowId);
		ModelAndView mv = new ModelAndView("alert/handleFrom");
		mv.addObject("borrow", borrow);
		return mv; 
	}
	@RequestMapping("/findBorrowtable.do")
	@ResponseBody
	public List findBorrowtable(String borrowId) {
		List list = null;
		try {
			list = borrowService.findDetail(borrowId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping("/findBorrowEdit.do")
	@ResponseBody
	public List findBorrowEdit(String borrowId) {
		List list = null;
		try {
			list = borrowService.findTableDetail(borrowId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/findrfid.do")
	@ResponseBody
	public BorrowDetail findrfid(String rfid) throws Exception {
		BorrowDetail bor = borrowService.findrfid(rfid);

		return bor;
	}

	/**
	 * 
	 * @param 修改
	 * @return
	 */
	@RequestMapping("/updefindrfid.do")
	@ResponseBody
	public BorrowDetail updefindrfid(String rfid) throws Exception {
		BorrowDetail bor = borrowService.findrfid(rfid);

		return bor;
	}

	/**
	 * 显示借阅申请列表
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/queryBorrowInfo.do")
	@ResponseBody
	public Pager queryBorrowInfo(int rows, int page, String borrowIds) {
		Pager resultPage = null;
		String[] borrowIdArr = borrowIds.split(",");
		try {
			int start = (page - 1) * rows;
			resultPage = borrowService
					.queryBorrowInfo(start, rows, borrowIdArr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultPage;
	}
	
	/*档案交付----孙家武*/
	@RequestMapping("/updateArchivesInfoStatus.do")
	@ResponseBody
	public Result updateArchivesInfoStatus(String infguids, String temGuids,String borrborrowId,String tailguid)throws Exception{
		List<String> infids = new ArrayList<String>();
		List<String> temids = new ArrayList<String>();
		List<String> idTailguid = new ArrayList<String>();
		if (null != infguids && !"".equals(infguids)) {
			for(String id : infguids.split("~")){
				if(StringUtils.isEmpty(id)){
					continue;
				}
				infids.add(id);
			}
		}
		
		if(null != temGuids && !"".equals(temGuids)){
			for(String id : temGuids.split("~")){
				if(StringUtils.isEmpty(id)){
					continue;
				}
				temids.add(id);
			}
		}
		if(null != tailguid && !"".equals(tailguid)){
			for(String taiId : tailguid.split("~")){
				if(StringUtils.isEmpty(taiId)){
					continue;
				}
				idTailguid.add(taiId);
			}
		}
		for (String s : infids) {
			arcHistoryService.insertArcHistory(Integer.parseInt(s), "档案交付成功", null);
		}
		return borrowService.updateArchivesInfoStatus(temids, borrborrowId, idTailguid);
	
	}
	
	/**
	 * 获取打印页面信息
	 * 
	 * @param borrowId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getPrintInfo.do")
	public ModelAndView getPrintInfo(String borrowId,String userName){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Borrow borrow=null;
		try {
			 borrow = borrowService.findBorrowById(borrowId);
			List datalist = borrowService.findDetail(borrowId);
			List comments = borrowComments.findProcessCommentsByBorrowId(borrowId);
			dataMap.put("datalist", datalist);
			dataMap.put("borrow", borrow);
			dataMap.put("comments", comments);
			//dataMap.put("userName", userName);
		} catch (Exception e) {
			dataMap = new HashMap();
			dataMap.put("datalist", new ArrayList());
			//dataMap.put("userName", userName);
			dataMap.put("borrow", borrow);
		}
		return new ModelAndView("print/borrowFormPrint",dataMap);
	}
	/**
	 * 续借页面
	 * @return
	 */
	@RequestMapping("/findXjMessage.do")
	@ResponseBody
	public ModelAndView findXjMessage(String borrowId){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("borrowId", borrowId);
		return new ModelAndView("alert/borrowRenew",dataMap);
	}
	/**
	 * 根据借阅单ID查询续借信息
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/findBorrowRenew.do")
	@ResponseBody
	public List<Borrow> findBorrowRenew(String parentId){
		
		return borrowService.findBorrowRenew(parentId);
	}

}
