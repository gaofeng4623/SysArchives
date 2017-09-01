package com.archives.web.controller;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.Itemstatus;
import com.archives.pojo.MapTotal;
import com.archives.service.ArchivesInfoService;
import com.archives.service.ItemstatusService;
import com.system.base.pojo.Department;
import com.system.base.pojo.Result;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/archivesInfo")
public class ArchivesInfoController {
//private Log logger = LogFactory.getLog(ArchivesInfoController.class);
	
	@Resource
	private ArchivesInfoService archivesInfoService;
	@Resource
	private ItemstatusService itemstatusService;

	@RequestMapping("/toAlertArchives.do")
	public ModelAndView toAlertArchives(){
		ModelAndView mv = new ModelAndView("alert/archivesInfo");
		return mv;
	}
	@RequestMapping("/toAlertArchivesForChange.do")
	public ModelAndView toAlertArchivesForChange(){
		ModelAndView mv = new ModelAndView("alert/archivesForChange");
		return mv;
	}

	// 查询所有数据
	@RequestMapping("/findArchivesInfo.do")
	@ResponseBody
	public Pager findArchivesInfo(ArchivesInfo archivesInfo, int rows, int page) throws Exception {
		Pager resultPage= null; 
		/*if(putonrecorddatesj!=null&&!putonrecorddatesj.equals("")){
			archivesInfo.setPutonrecorddate(putonrecorddatesj);
		}
		if(settledatesj!=null&&!settledatesj.equals("")){
			archivesInfo.setSettledate(settledatesj);
		}
		*/
	
		int start = (page -1) * rows;
		resultPage = archivesInfoService.findEmpByArchiveForPage(archivesInfo, start, rows);
		
		return resultPage;
	}
	
	// 查询所有数据
	@RequestMapping("/findArchivesForChange.do")
	@ResponseBody
	public Pager findArchivesForChange(ArchivesInfo archivesInfo, int rows, int page)throws Exception {
		Pager resultPage= null; 
		/*if(putonrecorddatesj!=null&&!putonrecorddatesj.equals("")){
			archivesInfo.setPutonrecorddate(putonrecorddatesj);
		}
		if(settledatesj!=null&&!settledatesj.equals("")){
			archivesInfo.setSettledate(settledatesj);
		}
		 */
		
		int start = (page -1) * rows;
		resultPage = archivesInfoService.findArchivesForChange(archivesInfo, start, rows);
		
		return resultPage;
	}
	// 查询所有数据
	@RequestMapping("/findArchivesInfoForShelves.do")
	@ResponseBody
	public Pager findArchivesInfoForShelves(ArchivesInfo archivesInfo, int rows, int page)throws Exception {
		Pager resultPage= null; 
		/*if(putonrecorddatesj!=null&&!putonrecorddatesj.equals("")){
			archivesInfo.setPutonrecorddate(putonrecorddatesj);
		}
		if(settledatesj!=null&&!settledatesj.equals("")){
			archivesInfo.setSettledate(settledatesj);
		}
		 */
		
		int start = (page -1) * rows;
		resultPage = archivesInfoService.findArchivesInfoForShelves(archivesInfo, start, rows);
		return resultPage;
	}
	
	
	// 根据guid查询所有数据
	@RequestMapping("/showArchiveDetail.do")
	@ResponseBody
	public ModelAndView showArchiveDetail(int itemGuid)throws Exception {
		ModelAndView mv = new ModelAndView("alert/archivesDetail");
		ArchivesInfo archivesInfo = archivesInfoService.findByItemGuid(itemGuid); //分卷的guid
		mv.addObject("arc", archivesInfo);
		return mv;
	}
	
	
	// 根据guid查询所有数据
	@RequestMapping("/findById.do")
	@ResponseBody
	public ModelAndView findById(Integer itemGuid)throws Exception {
		ModelAndView mv = new ModelAndView("alert/archivesInfoWin");
		ArchivesInfo archivesInfo = archivesInfoService.findByItemGuid(itemGuid);
		mv.addObject("arc", archivesInfo);
		return mv;
	}
	
	
	
	@RequestMapping("/editArchiveForm.do")
	@ResponseBody
	public ModelAndView editArchiveForm(Integer itemGuid, String status) throws Exception{
		ModelAndView mv = new ModelAndView("alert/editArchiveForm");
		ArchivesInfo archive = archivesInfoService.findByItemGuid(itemGuid);
		List<Itemstatus> varList = itemstatusService.findByArchiveId(archive.getGuid());
		mv.addObject("varList", varList);
		mv.addObject("archive", archive);
		mv.addObject("mkStatus", status);
		return mv;
	}
	
	// 查询所有数据
	@RequestMapping("/findBorrowArchivesInfo.do")
	@ResponseBody
	public Pager findBorrowArchivesInfo(ArchivesInfo archivesInfo, int rows, int page)throws Exception {
		Pager resultPage= null; 
		int start = (page -1) * rows;
		resultPage = archivesInfoService.findBorrowByArchiveForPage(archivesInfo, start, rows);
		return resultPage;
	}
	
	//查询待上架数量
	@RequestMapping("/findUpArchivesForTodo.do")
	@ResponseBody
	public Result findUpArchivesForTodo() {
		return archivesInfoService.findUpArchivesForTodo();
	}
	@RequestMapping("/destroyAllByIds.do")
	@ResponseBody
	public Result destroyAllByIds(String destroyAllByIds){
		List<String> idList = new ArrayList<String>();

		if(null != destroyAllByIds && !"".equals(destroyAllByIds)){
			for(String ctrlId : destroyAllByIds.split(",")){
				if(StringUtils.isEmpty(ctrlId)){
					continue;
				}
				idList.add(ctrlId);
			}
		}
		
		return archivesInfoService.destroyAllByIds(idList);
	}
	// 保存档案信息
			@RequestMapping("/saveArchivesInfo.do")
			@ResponseBody
			public Result saveArchivesInfo(ArchivesInfo info) {
				info.setStatus("0");
				return archivesInfoService.saveArchivesInfo(info);
			}
			@RequestMapping("/addArchivesInfo.do")
			@ResponseBody
			public ModelAndView addArchivesInfo() {
				ModelAndView mv = new ModelAndView("alert/addArchivesInfo");
				return mv;
			}
			//跳转到修改页面
			@RequestMapping("/editArchivesInfo.do")
			@ResponseBody
			public ModelAndView editArchivesInfo(Integer itemGuid) throws Exception {
				ModelAndView mv = new ModelAndView("alert/editArchivesInfo");
				ArchivesInfo archivesInfo = archivesInfoService.findByItemGuid(itemGuid);
				if(null!= archivesInfo.getPutonrecorddate() && !"".equals(archivesInfo.getPutonrecorddate())){
					archivesInfo.setPutonrecorddate(archivesInfo.getPutonrecorddate().substring(0, 10));
				}
				if(null!= archivesInfo.getSettledate() && !"".equals(archivesInfo.getSettledate())){
					archivesInfo.setSettledate(archivesInfo.getSettledate().substring(0, 10));
				}
				if(null!= archivesInfo.getPlaceondate() && !"".equals(archivesInfo.getPlaceondate())){
					archivesInfo.setPlaceondate(archivesInfo.getPlaceondate().substring(0, 10));
				}
				if(null!= archivesInfo.getRegistertime() && !"".equals(archivesInfo.getRegistertime())){
					archivesInfo.setRegistertime(archivesInfo.getRegistertime().substring(0, 10));
				}
				return mv.addObject("arc", archivesInfo);
			}
	       //修改
			@RequestMapping("/updArchivesInfo.do")
			@ResponseBody
			public Result updArchivesInfo(ArchivesInfo info) {
				return archivesInfoService.updateArchivesInfo(info);
			}
			@RequestMapping("/delArchivesInfo.do")
			@ResponseBody
			public Result delArchivesInfo(String ids) {
				List<Integer> idList = new ArrayList<Integer>();
				if (null != ids && !"".equals(ids)) {
					for (String ctrlId : ids.split(",")) {
						if (StringUtils.isEmpty(ctrlId)) {
							continue;
						}
						idList.add(Integer.parseInt(ctrlId));
					}
				}

				return archivesInfoService.delArchivesInfo(idList);
			}
			@RequestMapping("/findTotal.do")
			@ResponseBody
			public List<MapTotal> findTotal() {
				List<MapTotal> total = archivesInfoService.findTotal();
				return total;
			}
}
