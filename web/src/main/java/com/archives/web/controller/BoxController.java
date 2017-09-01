package com.archives.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.Box;
import com.archives.pojo.BoxStatus;
import com.archives.service.BoxService;
import com.archives.service.BoxStatusService;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/box")
public class BoxController {
	@Resource
	private BoxService boxService;
	@Resource
	private BoxStatusService boxStatusService;

	// 查询所有数据
	@RequestMapping("/findBox.do")
	@ResponseBody
	public Pager findDocument(Box box, int rows, int page) throws Exception {
		Pager resultPage = null;

		int start = (page - 1) * rows;
		resultPage = boxService.findBoxForPage(box, start, rows);

		return resultPage;
	}

	// 根据guid查询所有数据
	@SuppressWarnings("static-access")
	@RequestMapping("/findById.do")
	@ResponseBody
	public ModelAndView findById(int guid) throws Exception {
		ModelAndView mv = new ModelAndView("alert/document");
		Box document = boxService.findBoxById(guid);

		mv.addObject("arc", document);

		return mv;
	}

	// 根据guid查询所有数据
	@SuppressWarnings("static-access")
	@RequestMapping("/documentLibarary.do")
	@ResponseBody
	public ModelAndView documentLibarary(int guid) throws Exception {
		ModelAndView mv = new ModelAndView("alert/documentLibarary");
		Box document = boxService.findBoxById(guid);

		mv.addObject("arc", document);

		return mv;
	}

	@RequestMapping("/editBoxForm.do")
	@ResponseBody
	public ModelAndView editArchiveForm(Integer guid, String status)
			throws Exception {
		ModelAndView mv = new ModelAndView("alert/editBoxForm");
		Box archive = boxService.findBoxById(guid);
		List<BoxStatus> varList = boxStatusService.findByBoxNo(guid);
		/*
		 * for (BoxStatus itemstatus : varList) { itemstatus.setItemNo(1); }
		 */
		mv.addObject("varList", varList);
		mv.addObject("archive", archive);
		mv.addObject("mkStatus", status);
		return mv;
	}

	@RequestMapping("/addBox.do")
	@ResponseBody
	public ModelAndView addControlInit() {
		ModelAndView mv = new ModelAndView("alert/addBox");
		return mv;
	}

	// 保存盒子信息
	@RequestMapping("/saveBox.do")
	@ResponseBody
	public Result saveBox(Box box) {
		return boxService.saveBox(box);
	}

	@RequestMapping("/editBox.do")
	@ResponseBody
	public ModelAndView editBox(int id) throws Exception {
		ModelAndView mv = new ModelAndView("alert/updBox");
		Box ctrl = boxService.findBoxById(id);
		mv.addObject("ctrl", ctrl);
		return mv;
	}

	@RequestMapping("/updBox.do")
	@ResponseBody
	public Result updBox(Box box) {
		return boxService.updBox(box);
	}

	@RequestMapping("/delBox.do")
	@ResponseBody
	public Result delBox(String ids) {
		List<Integer> idList = new ArrayList<Integer>();

		if (null != ids && !"".equals(ids)) {
			for (String ctrlId : ids.split(",")) {
				if (StringUtils.isEmpty(ctrlId)) {
					continue;
				}
				idList.add(Integer.parseInt(ctrlId));
			}
		}

		return boxService.delBox(idList);
	}

	@RequestMapping("/findAllByIds.do")
	@ResponseBody
	public Result findAllByIds(String boxNumberList) {
		List<String> idList = new ArrayList<String>();

		if (null != boxNumberList && !"".equals(boxNumberList)) {
			for (String ctrlId : boxNumberList.split(",")) {
				if (StringUtils.isEmpty(ctrlId)) {
					continue;
				}
				idList.add(ctrlId);
			}
		}

		return boxService.findAllByIds(idList);
	}

	@RequestMapping("/toDocumentForChange.do")
	public ModelAndView toAlertArchivesForChange() {
		ModelAndView mv = new ModelAndView("alert/documentForChange");
		return mv;
	}

	@RequestMapping("/findFormView.do")
	public ModelAndView findFormView(int guid) throws Exception {
		ModelAndView mv = new ModelAndView("boxRequestShow");
		Box document = boxService.findBoxById(guid);
		mv.addObject("arc", document);

		return mv;
	}
	@RequestMapping("/findBoxShow.do")
	public ModelAndView findBoxFormView(int guid) throws Exception {
		ModelAndView mv = new ModelAndView("alert/boxShow");
		Box document = boxService.findBoxById(guid);
		mv.addObject("arc", document);

		return mv;
	}
	// 档案上架
		@RequestMapping("/findBoxForShelves.do")
		@ResponseBody
		public Pager findBoxForShelves(Box box, int rows, int page)throws Exception {
			Pager resultPage= null; 
			int start = (page -1) * rows;
			resultPage = boxService.findBoxForShelves(box, start, rows);
			return resultPage;
		}
}
