package com.archives.web.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.ArchivesInfo;
import com.archives.service.ArchivesCollectionService;
import com.system.base.pojo.Department;
import com.system.workflow.activiti.commons.Pager;
/**
 * 档案催收
 * @author zfn
 *
 */
@Controller
@RequestMapping("/archivesCollection")
public class ArchivesCollectionController {
	@Resource
	private ArchivesCollectionService archivesCollectionService;
	
	/**
	 * 查询查询结案列表
	 * @return
	 */
	@RequestMapping("/findCollection.do")
	@ResponseBody
	public Pager findCollection(String middleTime,String undertakeDep,int rows, int page){
		Pager resultPage= null; 
		try {
			int start = (page -1) * rows;
			resultPage = archivesCollectionService.findCollection(middleTime,undertakeDep,start, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultPage;
	}
	/**
	 * 查询归档率
	 * @return
	 */
	@RequestMapping("/filingReate.do")
	@ResponseBody
	public List<ArchivesInfo> filingReate(){

		List listcount=new ArrayList();
		try {
			List<ArchivesInfo> list= archivesCollectionService.filingReate();
			List<ArchivesInfo> re= archivesCollectionService.filingReateCount();
			 
			if(re.size()>0){
				for(int i=0;i<re.size();i++){
					ArchivesInfo info=re.get(i);
					ArchivesInfo in=list.get(i);
					if(info.getPutonrecorddep().equals(in.getPutonrecorddep())){
						double cout=in.getPutOnRecordDepCount()/info.getPutOnRecordDepCount()*100;
						ArchivesInfo mm=new ArchivesInfo();
						mm.setPutonrecorddep(info.getPutonrecorddep());
						BigDecimal   b   =   new   BigDecimal(cout);
						double   archDouble   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
						mm.setArchiveCount(archDouble);//率
						mm.setVolumeCount(in.getPutOnRecordDepCount());
						mm.setArchiveVolumeCount(info.getPutOnRecordDepCount());
						//listcount.add(cout+"%");
						//listcount.add(info.getPutonrecorddep());
						listcount.add(mm);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listcount;
	}
	public List<Map<String,Object>>  listInfo(String middleTime,String undertakeDep){
	List	list=archivesCollectionService.listInfo(middleTime,undertakeDep);
		return list;
	}
	 public void doPost(HttpServletRequest request, HttpServletResponse response)  
	              throws ServletException, IOException {  
		 excle(request, response);  
	      } 
	 @RequestMapping("/excle.do")
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public void excle(HttpServletRequest request, HttpServletResponse response)  
	              throws ServletException, IOException { 
		String middleTime= request.getParameter("middleTime");
		String undertakeDep=request.getParameter("undertakeDep");
		if(undertakeDep!=null && !"null".equals(undertakeDep)){
			  undertakeDep=URLDecoder.decode(URLDecoder.decode(request.getParameter("undertakeDep"), "utf-8"));
		}else{
			  undertakeDep=request.getParameter("undertakeDep");
		}
	         response.setContentType("octets/stream");  
	  //      response.addHeader("Content-Disposition", "attachment;filename=test.xls");  
	          String excelName = "催收信息";  
	          //转码防止乱码  
	          response.addHeader("Content-Disposition", "attachment;filename="+new String( excelName.getBytes("gb2312"), "ISO8859-1" )+".xls");  
	          String[] headers = new String[]{"案号","年度","案字","案名","立案部门","结案日期"};  
	          try {  
	              OutputStream out = response.getOutputStream();  
	              archivesCollectionService.exportExcel(excelName,headers, listInfo(middleTime,undertakeDep), out,"yyyy-MM-dd");  
	             out.close();  
	         } catch (FileNotFoundException e) {  
	                 e.printStackTrace();  
	         } catch (IOException e) {  
	                  e.printStackTrace();  
	         }  
	     } 
	 
	/*(*
	 * 打印不分页查询
	 */
	@RequestMapping("/findCollectionNopage.do")
	public ModelAndView findCollectionNopage(String middleTime,String undertakeDep){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<ArchivesInfo> dataList=null;
		try {
			if(undertakeDep!=null&&!"null".equals(undertakeDep)){
				dataList = archivesCollectionService.findCollectionNopage(middleTime,URLDecoder.decode(URLDecoder.decode(undertakeDep, "utf-8")));
			}else{
				dataList = archivesCollectionService.findCollectionNopage(middleTime,undertakeDep);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataMap.put("dataList", dataList);
		return new ModelAndView("print/collectionPrint",dataMap);
	}
	
	@RequestMapping("/deptTree.do")
	@ResponseBody
	public List<Department> deptTree(){
		List<Department> depts = archivesCollectionService.findAllDepartmentsforTree();
		return depts;
	}

}
