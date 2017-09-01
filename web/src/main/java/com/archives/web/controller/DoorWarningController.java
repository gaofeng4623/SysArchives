package com.archives.web.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.archives.pojo.DoorWarning;
import com.archives.service.DoorWarningService;
import com.system.base.pojo.Employee;
import com.system.base.pojo.Result;
import com.system.util.common.Consts;
import com.system.workflow.activiti.commons.Pager;

@Controller
@RequestMapping("/doorWarning")
public class DoorWarningController {

	@Resource
	private DoorWarningService doorWarningService;


	// 查询所有数据
	@RequestMapping("/finddoorWarning.do")
	@ResponseBody
	public Pager findDoorWarning(DoorWarning doorWarning, int rows, int page) throws Exception{
		Pager resultPage= null; 
		int start = (page -1) * rows;
		resultPage = doorWarningService.findEmpByDoorWarningForPage(doorWarning, start, rows);
		return resultPage;
	}
	
	@RequestMapping("/updDoorWarning.do")
	@ResponseBody
	public Result updDoorWarning(DoorWarning door, HttpSession session)throws Exception{
		Employee emp = (Employee) session.getAttribute(Consts.userkey);
		String user=emp.getEmployeeName();
		door.setHandler(user);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		door.setHandlerdate(df.format(new Date()));
		return doorWarningService.updateDoorSelective(door);
	
	}
	// 获取打印页面信息
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping("/getPrintInfo.do")
		public ModelAndView getPrintInfo(DoorWarning doorWarning){
			Map dataMap = new HashMap();
			/*List<String> idList = new ArrayList<String>();
			if(null != guidStr && !"".equals(guidStr)){
				for(String guid : guidStr.split(",")){
					if(StringUtils.isEmpty(guid)){
						continue;
					}
					idList.add(guid);
				}
			}*/
			try {
				List<DoorWarning> datalist = doorWarningService.findAllByIds(doorWarning);
	
				dataMap.put("datalist", datalist);
			} catch (Exception e) {
				dataMap = new HashMap();
				dataMap.put("datalist", new ArrayList());
			}
			return new ModelAndView("print/doorPrint",dataMap);
		}
}
