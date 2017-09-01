package com.archives.exchange.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.archives.exchange.dao.ArcHandlerDao;
import com.archives.exchange.service.ArcHandlerService;
import com.archives.pojo.ArchivesInfo;
import com.archives.service.ArchivesSynService;
import com.system.base.pojo.SysLog;
import com.system.dao.database.SysLogDao;
import com.system.util.common.Consts;

@Service("arcHandler")
public class ArcHandlerServiceImpl implements ArcHandlerService {
	@Resource
	private ArcHandlerDao arcHandlerDao;
	@Resource
	private ArchivesSynService archivesSynService;
	@Resource
	private SysLogDao sysLogDao; //日志
	
	private static final Logger logger = LoggerFactory
			.getLogger(ArcHandlerServiceImpl.class);

	@Override
	public int synArchives() throws Exception {
		int count = 0;
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String targetDate = sdf.format(cal.getTime());
			String now = sdf.format(new Date());
			/* 查询全部案件
			int total = arcHandlerDao.queryInfoCount();
			List<ArchivesInfo> archivesInfos = arcHandlerDao.queryInfo();*/
			//查询指定区间案件
			ArchivesInfo arc = null;
			int total = arcHandlerDao.queryIntervalInfoCount(targetDate, now);
			int addcount = arcHandlerDao.queryIntervalAddInfoCount(targetDate, now);
			int achivesCount = total - addcount;
			List<ArchivesInfo> addList = new ArrayList<ArchivesInfo>();
			List<ArchivesInfo> updateList = new ArrayList<ArchivesInfo>();
			List<ArchivesInfo> archivesInfos = arcHandlerDao.queryIntervalInfo(targetDate, now);
			logger.info("\r\n" + targetDate + " 至今  共产生" + total + "案件,其中新增案件" 
					+ addcount + "件,归档案件" + achivesCount + "件");
			for (ArchivesInfo info : archivesInfos) {
				arc = archivesSynService.selectByCaseNo(info.getCaseno());
				if (arc == null) {
					addList.add(info);
				} else if (arc.getUpdated() != null && arc.getUpdated() != 1) {
					updateList.add(info);
				}
				filter(info); //更新案件类型标识
				archivesSynService.synHistoryArchives(info, now); // 入库
				count++;
				logger.info("实时同步第" + count + "条数据");
			}
			logger.info("总共同步了" + count + "条数据");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String optime = format.format(new java.util.Date());
        	insertSelective(optime + " 总同步" + count + "案件,其中" 
        			+ targetDate + "至今新增案件" + addList.size() + "件," 
        			+ "归档案件" + updateList.size() + "件"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}
	
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println(sdf.format(cal.getTime()));
	}
	
	private void filter(ArchivesInfo info) {
		info.setMarkType(getMarkType(info.getAzbm()));
	}

	/*private String createDocumentString(ArchivesInfo info) {
		try {
			StringWriter stringWriter = new StringWriter();
			Element root = DocumentHelper.createElement("archives");
			Document document = DocumentHelper.createDocument(root);
			Element arcInfo = root.addElement("archive");
			arcInfo.addElement("caseNo").setText(parseNull(info.getCaseno()));
			arcInfo.addElement("gdbz").setText(parseNull(info.getGdbz()));  //增加归档标志
			arcInfo.addElement("markType").setText(getMarkType(info.getAzbm()));
			arcInfo.addElement("archivesNo").setText(parseNull(info.getArchivesno()));
			arcInfo.addElement("year").setText(parseNull(info.getYear()));
			arcInfo.addElement("caseProerty").setText(parseNull(info.getCaseproerty()));
			arcInfo.addElement("caseWord").setText(parseNull(info.getCaseword()));
			arcInfo.addElement("courtShortName").setText(parseNull(info.getCourtshortname()));
			arcInfo.addElement("number").setText(parseNull(info.getNumber()));
			arcInfo.addElement("caseCategory").setText(parseNull(info.getCasecategory()));
			arcInfo.addElement("judgeProcedure").setText(parseNull(info.getJudgeprocedure()));
			arcInfo.addElement("caseName").setText(parseNull(info.getCasename()));
			arcInfo.addElement("shortDetailsCase").setText(parseNull(info.getShortdetailscase()));
			arcInfo.addElement("parties").setText(parseNull(info.getParties()));
			arcInfo.addElement("putOnRecordDate").setText(parseNull(info.getPutonrecorddate()));
			arcInfo.addElement("putOnRecordDep").setText(parseNull(info.getPutonrecorddep()));
			arcInfo.addElement("approvePer").setText(parseNull(info.getApproveper()));
			arcInfo.addElement("undertakeDep").setText(parseNull(info.getUndertakedep()));
			arcInfo.addElement("undertakePer").setText(parseNull(info.getUndertakeper()));
			arcInfo.addElement("courtClerk").setText(parseNull(info.getCourtclerk()));
			arcInfo.addElement("chiefJudge").setText(parseNull(info.getChiefjudge()));
			arcInfo.addElement("collegiateBench").setText(parseNull(info.getCollegiatebench()));
			arcInfo.addElement("formalDocument").setText(parseNull(info.getFormaldocument()));
			arcInfo.addElement("counterpart").setText(parseNull(info.getCounterpart()));
			arcInfo.addElement("otherDoucment").setText(parseNull(info.getOtherdoucment()));
			arcInfo.addElement("formalDocPageNum").setText(parseNull(info.getFormaldocpagenum()));
			arcInfo.addElement("counterpartPageNum").setText(parseNull(info.getCounterpartpagenum()));
			arcInfo.addElement("mergeCase").setText(parseNull(info.getMergecase()));
			arcInfo.addElement("settleManner").setText(parseNull(info.getSettlemanner()));
			arcInfo.addElement("settleDate").setText(parseNull(info.getSettledate()));
			arcInfo.addElement("settleReason").setText(parseNull(info.getSettlereason()));
			arcInfo.addElement("originalCourt").setText(parseNull(info.getOriginalcourt()));
			arcInfo.addElement("firstResult").setText(parseNull(info.getFirstresult()));
			arcInfo.addElement("secondResult").setText(parseNull(info.getSecondresult()));
			arcInfo.addElement("lastResult").setText(parseNull(info.getLastresult()));
			arcInfo.addElement("fullDocNo").setText(parseNull(info.getFulldocno()));
			arcInfo.addElement("catalogNo").setText(parseNull(info.getCatalogno()));
			arcInfo.addElement("evidenceBag").setText(parseNull(info.getEvidencebag()));
			arcInfo.addElement("degreeOfSecrets").setText(parseNull(info.getDegreeofsecrets()));
			arcInfo.addElement("storageLife").setText(parseNull(info.getStoragelife()));
			arcInfo.addElement("applyPer").setText(parseNull(info.getApplyper()));
			arcInfo.addElement("receivePer").setText(parseNull(info.getReceiveper()));
			arcInfo.addElement("placeOnPer").setText(parseNull(info.getPlaceonper()));
			arcInfo.addElement("placeOnDate").setText(parseNull(info.getPlaceondate()));
			arcInfo.addElement("registerPer").setText(parseNull(info.getRegisterper()));
			arcInfo.addElement("registerTime").setText(parseNull(info.getRegistertime()));
			arcInfo.addElement("remark").setText(parseNull(info.getRemark()));
			OutputFormat xmlFormat = new OutputFormat();
			xmlFormat.setEncoding("UTF-8");
			xmlFormat.setNewlines(true);
			xmlFormat.setIndent(true);
			xmlFormat.setIndent("    ");
			XMLWriter xmlWriter = new XMLWriter(stringWriter, xmlFormat);
			xmlWriter.write(document);
			xmlWriter.close();
			return stringWriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

	private String getMarkType(String azbm) {
		String result = "";
		if (azbm == null || "".equals(azbm.trim())) {
			result = "OTHER";
		} else {
			String sign = azbm.substring(0, 1);
			switch (sign) {
				case "1" : result = "XS"; break; //刑事
				case "2" : result = "MS"; break; //民事
				case "3" : result = "XZ"; break; //行政
				case "4" : result = "ZX"; break; //执行
				case "5" : result = "GJPCYSFJZ"; break; //国家赔偿与司法救助
				case "6" : result = "FSBQ"; break; //非诉保全
				case "7" : result = "GJSFXZ"; break; //国际司法协助
				case "8" : result = "QJSFXZ"; break; //区际司法协助
				case "9" : result = "SFZC"; break; //司法制裁
				case "A" : result = "XF"; break; //信访
				default : result = "OTHER"; //其他类别
			}
		}
		return result;
	}
	
	private void insertSelective(String event) {
		SysLog syslog = new SysLog();
		syslog.setOperationType(String.valueOf(Consts.operationType[3]));
		syslog.setUpdateTime(new Date());
		syslog.setPerson("系统定时任务");
		syslog.setIp("localhost");
		syslog.setEvent(event);
		this.sysLogDao.insertSelective(syslog);
	}

}
