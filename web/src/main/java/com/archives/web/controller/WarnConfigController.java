package com.archives.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.archives.pojo.Borrow;
import com.archives.pojo.WarnConfig;
import com.archives.service.WarnConfigService;
import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.util.common.Consts;
/**
 * 逾期预警配置
 * @author zfn
 *
 */
@Controller
@RequestMapping("/warnConfig")
public class WarnConfigController {
	@Resource
	private WarnConfigService warnConfigService;
	@Autowired
	private HttpServletRequest servletRequest;
	
	@RequestMapping("/findWarnConfig.do")
	@ResponseBody
	public Result findWarnConfig(){
		Result result = new Result();
		try {
			WarnConfig warnConfig = warnConfigService.findWarnConfig();
			result.setData(warnConfig);
			result.setStatus(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	@RequestMapping("/findDocWarnConfig.do")
	@ResponseBody
	public Result findDocWarnConfig(){
		Result result = new Result();
		try {
			WarnConfig warnConfig = warnConfigService.findDocWarnConfig();
			result.setData(warnConfig);
			result.setStatus(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	@RequestMapping("/saveOrUpdate.do")
	@ResponseBody
	public Result saveOrUpdate(WarnConfig warnConfig){
		Result result = new Result();
		try {
			if(warnConfig.getGuid() != null){
				warnConfigService.updateWarnConfig(warnConfig);
			}else{
				warnConfigService.saveWarnConfig(warnConfig);
			}
			result.setStatus(0);
			result.setMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping("/findBorrowWarn.do")
	@ResponseBody
	public Result findBorrowWarn(){
		Result result = new Result();
		Employee emp = (Employee) servletRequest.getSession().getAttribute(Consts.userkey);
		 List<Borrow> list = warnConfigService.findBorrowWarn(emp);
		 WarnConfig warnConfig = warnConfigService.findWarnConfig();
		 String message = "";
		 for (Borrow borrow : list) {
			 int cha = borrow.getBorrowDays()-warnConfig.getMaxBorrowTime();
			 if(cha >= warnConfig.getFirstWarning() && cha < warnConfig.getSecondWarning()){
				 message+="借阅逾期一级预警：借阅单号("+borrow.getFormNo()+")";
			 }else if(cha >=warnConfig.getSecondWarning() && cha < warnConfig.getThirdWarning()){
				 message+="借阅逾期二级预警：借阅单号("+borrow.getFormNo()+")";
			 }else if(cha >= warnConfig.getThirdWarning()){
				 message+="借阅逾期三级预警：借阅单号("+borrow.getFormNo()+")";
			 }
		}
		 result.setMessage(message);
		 result.setStatus(0);
		return result;
	}

}
