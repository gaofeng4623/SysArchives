package com.archives.exchange.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.archives.exchange.service.ArcHandlerService;
import com.archives.exchange.service.DocHandlerService;
import com.archives.exchange.service.OrgHandlerService;
import com.system.base.pojo.Result;

@Controller
@RequestMapping("/orgInit")
public class OrgInitController {
	@Resource
	private OrgHandlerService orgHandler;
	@Resource
	private ArcHandlerService arcHandler;
	@Resource
	private DocHandlerService docHandler;
	
	@RequestMapping("/initDept")
	@ResponseBody
	public Result initDepartment() {
		try {
			int count = orgHandler.synDepartments();
			return new Result(1, "部门初始化成功,同步了" + count + "条数据!");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(0, "部门初始化失败,请查看错误日志!");
		}
	}
	
	@RequestMapping("/initEmp")
	@ResponseBody
	public Result initEmployee() {
		try {
			int count = orgHandler.synEmployees();
			return new Result(1, "人员初始化成功,同步了" + count + "条数据!");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(0, "人员初始化失败,请查看错误日志!");
		}
	}
	
	@RequestMapping("/initArc")
	@ResponseBody
	public Result initArc() {
		try {
			int count = arcHandler.synArchives();
			return new Result(1, "档案同步成功,同步了" + count + "条数据!");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(0, "档案同步失败,请查看错误日志!");
		}
	}
	
	@RequestMapping("/initDoc")
	@ResponseBody
	public Result initDoc() {
		try {
			int count = docHandler.synDocument();
			return new Result(1, "文书档案同步成功,同步了" + count + "条数据!");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(0, "文书档案同步失败,请查看错误日志!");
		}
	}
	
}
