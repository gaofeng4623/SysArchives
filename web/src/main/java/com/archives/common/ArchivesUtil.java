package com.archives.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.archives.exchange.service.ArcHandlerService;
import com.archives.exchange.service.DocumentQueryService;
import com.archives.exchange.service.OrgHandlerService;
import com.system.base.pojo.MultipleDataSource;

@Component
@Aspect
public class ArchivesUtil {
	

	//记录档案借出交付下架轨迹
/*	@SuppressWarnings("unchecked")
	@After(value ="execution(* com.archives.serviceimpl.ArchivesInfoServiceImpl.updateArchivesInfoStatus(..))")
		public void archivesUpdateArchivesInfoStatus(JoinPoint proceedingJoinPoint) throws Throwable{
		Object[] paramArray=proceedingJoinPoint.getArgs();
		List<String> guids = (List<String>)paramArray[0];
		for(String guid:guids){
			Itemstatus itemstatus = itemstatusDao.selectByPrimaryKey(Integer.valueOf(guid));
			arcHistoryService.insertArcHistory(itemstatus.getInfoid(), "借出交付下架","");
		}
	}*/
	
	//事实再一次证明不能二次代理
	@Before(value = "execution(* com.archives..*serviceimpl..*.*(..))")  
	public void getDataSource(JoinPoint jp) {
		if (jp.getTarget() instanceof OrgHandlerService 
				|| jp.getTarget() instanceof ArcHandlerService 
				|| jp.getTarget() instanceof DocumentQueryService) {
            MultipleDataSource.setDataSourceKey("drSybase");
        } else {
        	MultipleDataSource.setDataSourceKey("gtMysql");
        }
	}
}
