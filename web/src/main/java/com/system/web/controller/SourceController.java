package com.system.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.system.base.pojo.Result;
import com.system.base.pojo.Source;
import com.system.core.service.SourceService;
import com.system.util.common.GUID;
import com.system.util.common.HtmlUtils;
import com.system.workflow.activiti.commons.Pager;

/**
 * @info 系统资源相关功能
 * @author 高峰 2016-09-08
 */
@Controller
@RequestMapping("/source")
public class SourceController {
	@Resource
	private SourceService sourceService;

	/**
	 * 初始化资源树
	 */
	@RequestMapping("/findAllSource.do")
	@ResponseBody
	public List<Source> findAllSource() {
		return sourceService.findAllSource();
	}
	
	/**
	 * 获得角色资源树
	 */
	@RequestMapping("/initRoleTree.do")
	@ResponseBody
	public List<Source> initRoleTree(String roleId) {
		return sourceService.selectLimitedSourceByRoleId(roleId);
	}
	
	/**
	 * 查找资源列表
	 */
	@RequestMapping("/findSource.do")
	@ResponseBody
	public Pager findSource(int page, int rows, String sourceIds) {
		int totalSize = 0;
		int start = (page - 1) * rows;
		List<Source> sourceList = null;
		List<String> idList = new ArrayList<String>();
		if (null != sourceIds && !"".equals(sourceIds)) {
			for (String sourceId : sourceIds.split(",")) {
				if (StringUtils.isEmpty(sourceId)) {
					continue;
				}
				idList.add(sourceId);
			}
		}
		if (0 < idList.size()) {
			totalSize = sourceService.countSourceBySourceIds(idList);
			sourceList = sourceService.selectSourceBySourceIds(idList, start, rows);
		} else {
			totalSize = sourceService.countSourceBySourceIds(null);
			sourceList = sourceService.selectSourceBySourceIds(null, start, rows);
		}
		return new Pager(totalSize, sourceList);
	}
	
	/**
	 * 添加资源
	 */
	@RequestMapping("/addSrcInit.do")
	public ModelAndView addSourceInit(String sourceId, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("system/alert/addSource");
		Source source = new Source();
		source.setGuid(sourceId);
		source.setObjectEditor(HtmlUtils.createEditor(request, "content1", null));
		mv.addObject("superSrc", source);
		return mv;
	}
	
	/**
	 * 新增资源保存
	 */
	@RequestMapping("/saveSrcInfo.do")
	@ResponseBody
	public Result saveSourceInfo(Source source, HttpServletRequest request) throws Exception {
		source.setGuid(new GUID().toString());
		source.setScripts(HtmlUtils.htmlFilter(request, source.getScripts()));
		source.setContent(HtmlUtils.htmlFilter(request, source.getContent()));
		return sourceService.insertSource(source);
	}
	
	/**
	 * 修改资源配置
	 */
	@RequestMapping("/updateSrcInit.do")
	public ModelAndView updateSrcInit(String sourceId, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("system/alert/updSource");
		Source source = sourceService.selectByPrimaryKey(sourceId);
		source.setScripts(HtmlUtils.htmlRestore(request, source.getScripts()));
		source.setContent(HtmlUtils.htmlRestore(request, source.getContent()));
		source.setObjectEditor(HtmlUtils.createEditor(request, "content1", source.getClassName()));
		mv.addObject("selSrc", source);
		return mv;
	}

	/**
	 * 修改资源保存
	 */
	@RequestMapping("/updSrcInfo.do")
	@ResponseBody
	public Result updSrcInfo(Source source, HttpServletRequest request) throws Exception {
		source.setScripts(HtmlUtils.htmlFilter(request, source.getScripts()));
		source.setContent(HtmlUtils.htmlFilter(request, source.getContent()));
		return sourceService.updateBySelective(source);
	}
	
	/**
	 * 删除资源
	 */
	@RequestMapping("/delSrc.do")
	@ResponseBody
	public Result delSrc(String sourceId) throws Exception {
		return sourceService.deleteByPrimaryKey(sourceId);
	}
	
	/*保存角色关联的资源对象 */
	@RequestMapping("/saveRoleSrc.do")
	@ResponseBody
	public Result saveSourceSetting(String roleId, String[] idList) {
		Result res = new Result();
		try {
			sourceService.removeRoleSource(roleId);
			if (idList == null || idList.length == 0) {
				res.setStatus(0);
				res.setMessage("资源权限保存成功");
				return res;
			}
			sourceService.insertRoleSource(roleId, idList);
			res.setStatus(0);
			res.setMessage("资源权限保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(1);
			res.setMessage("资源权限保存失败");
		}
		return res;
	}
}
