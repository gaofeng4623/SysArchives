package com.archives.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.BorrowDetail;
import com.archives.pojo.Docborrowdetail;
import com.archives.service.ArchivesInfoService;
import com.archives.service.SysDeskService;

@Controller
@RequestMapping("/sysDesk")
public class SysDeskController {
	@Resource
	SysDeskService sysDeskService;
	@Resource
	ArchivesInfoService archivesInfoService;

	/**
	 * 初始化功能菜单树
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/initDeskInfo.do")
	@ResponseBody
	public Map<String, Object> initDeskInfo() {
		Map<String, Object> dataMap = new HashMap();
		int archiveCount = sysDeskService.queryArchiveCount();
		int borrowArchiveCount = sysDeskService.queryborrowArchiveCount();
		int collectionCount = sysDeskService.queryCollectionCount();
		int borrowSheetCount = sysDeskService.queryBorrowSheetCount();
		int borrowNotDeliveredCount = sysDeskService
				.queryBorrowNotDeliveredCount();
		int gzryCount = sysDeskService.queryBorrowGzryCount();// 工作人员
		// 统计某一借阅人借阅数量
		// int userborrowArchiveCount =
		// sysDeskService.queryUserBorrowArchiveCount();
		// 统计某一借阅人归还数量
		int userReturnArchiveCount = sysDeskService
				.queryUserReturnArchiveCount();
		// 统计待交付给某一借阅人数量
		int userBorrowNotDeliveredCount = sysDeskService
				.queryUserBorrowNotDeliveredCount();
		// 统计借阅实借阅数量
		int borrowRoomBorrowArchiveCount = sysDeskService
				.queryBorrowRoomBorrowArchiveCount();
		// 统计借阅实归还数量
		int borrowRoomReturnArchiveCount = sysDeskService
				.queryBorrowRoomReturnArchiveCount();
		// 统计待交付给借阅实数量
		int borrowRoomBorrowNotDeliveredCount = sysDeskService
				.queryBorrowRoomBorrowNotDeliveredCount();
		int destroyArchives = sysDeskService.destroyArchives();
		dataMap.put("gzryCount", gzryCount);
		dataMap.put("destroyCount", destroyArchives);
		dataMap.put("archiveCount", archiveCount);
		dataMap.put("borrowArchiveCount", borrowArchiveCount);
		dataMap.put("collectionCount", collectionCount);
		dataMap.put("borrowSheetCount", borrowSheetCount);
		dataMap.put("borrowNotDeliveredCount", borrowNotDeliveredCount);
		dataMap.put("uptodoCount", archivesInfoService.findUpArchivesForTodo()
				.getMessage());
		// dataMap.put("userborrowArchiveCount",userborrowArchiveCount);
		dataMap.put("userReturnArchiveCount", userReturnArchiveCount);
		dataMap.put("userBorrowNotDeliveredCount", userBorrowNotDeliveredCount);
		dataMap.put("borrowRoomBorrowArchiveCount",
				borrowRoomBorrowArchiveCount);
		dataMap.put("borrowRoomReturnArchiveCount",
				borrowRoomReturnArchiveCount);
		dataMap.put("borrowRoomBorrowNotDeliveredCount",
				borrowRoomBorrowNotDeliveredCount);
		return dataMap;
	}

	/**
	 * 查询个月统计量
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/anuaryCount.do")
	@ResponseBody
	public Map<String, Object> anuaryCount() {
		Map<String, Object> dataMap = new HashMap();
		// 查询各月归档量
		List oneList = this.oneAnuaryCount("1号库房");
		List twoList = this.oneAnuaryCount("2号库房");
		List threeList = this.oneAnuaryCount("3号库房");
		dataMap.put("oneList", oneList);
		dataMap.put("twoList", twoList);
		dataMap.put("threeList", threeList);
		return dataMap;
	}

	/**
	 * 查询个月借阅量
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/borrowCount.do")
	@ResponseBody
	public Map<String, Object> borrowCount() {
		Map<String, Object> dataMap = new HashMap();
		// 查询各月归档量
		int January = 0;// 1月
		int February = 0;// 2月
		int March = 0;// 3月
		int April = 0;// 4月
		int May = 0;// 5月
		int June = 0;// 6月
		int July = 0;// 7月
		int August = 0;// 8月
		int September = 0;// 9月
		int October = 0;// 10月
		int November = 0;// 11月
		int December = 0;// 12月
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy");
		String year = format.format(date);
		List<BorrowDetail> OneJanuaryCount = sysDeskService.borrowCount(year);
		for (int i = 0; i < OneJanuaryCount.size(); i++) {
			BorrowDetail archivesInfo = OneJanuaryCount.get(i);
			String[] strs = archivesInfo.getBorrowCount().split("[-]");
			if (strs[1].equals("01")) {
				January = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("02")) {
				February = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("03")) {
				March = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("04")) {
				April = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("05")) {
				May = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("06")) {
				June = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("07")) {
				July = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("08")) {
				August = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("09")) {
				September = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("10")) {
				October = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("11")) {
				November = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("12")) {
				December = Integer.parseInt(archivesInfo.getGuidCount());
			}
		}
		List borrwoList = new ArrayList();
		borrwoList.add(January);
		borrwoList.add(February);
		borrwoList.add(March);
		borrwoList.add(April);
		borrwoList.add(May);
		borrwoList.add(June);
		borrwoList.add(July);
		borrwoList.add(August);
		borrwoList.add(September);
		borrwoList.add(October);
		borrwoList.add(November);
		borrwoList.add(December);
		dataMap.put("list", borrwoList);
		return dataMap;
	}

	public List oneAnuaryCount(String storage) {
		Map<String, Object> dataMap = new HashMap();
		// 查询各月归档量
		int January = 0;// 1月
		int February = 0;// 2月
		int March = 0;// 3月
		int April = 0;// 4月
		int May = 0;// 5月
		int June = 0;// 6月
		int July = 0;// 7月
		int August = 0;// 8月
		int September = 0;// 9月
		int October = 0;// 10月
		int November = 0;// 11月
		int December = 0;// 12月
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy");
		String year = format.format(date);
		List<ArchivesInfo> OneJanuaryCount = sysDeskService.eachMonthCount(storage, year);

		for (int i = 0; i < OneJanuaryCount.size(); i++) {
			ArchivesInfo archivesInfo = OneJanuaryCount.get(i);
			String[] strs = archivesInfo.getPlacecount().split("[-]");
			if (strs[1].equals("01")) {
				January = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("02")) {
				February = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("03")) {
				March = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("04")) {
				April = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("05")) {
				May = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("06")) {
				June = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("07")) {
				July = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("08")) {
				August = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("09")) {
				September = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("10")) {
				October = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("11")) {
				November = Integer.parseInt(archivesInfo.getGuidCount());
			}
			if (strs[1].equals("12")) {
				December = Integer.parseInt(archivesInfo.getGuidCount());
			}
		}
		List list = new ArrayList();
		list.add(January);
		list.add(February);
		list.add(March);
		list.add(April);
		list.add(May);
		list.add(June);
		list.add(July);
		list.add(August);
		list.add(September);
		list.add(October);
		list.add(November);
		list.add(December);
		dataMap.put("list", list);
		return list;
	}

	/**
	 * 查询案件性质统计量
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/caseProertyCount.do")
	@ResponseBody
	public Map<String, Object> caseProertyCount() {
		Map<String, Object> dataMap = new HashMap();
		int ms = 0;
		int xs = 0;
		int gjpc = 0;
		int xz = 0;
		int zx = 0;
		int fscbq = 0;
		// 查询各月归档量
		List<ArchivesInfo> list = sysDeskService.caseProertyCount();
		for (int i = 0; i < list.size(); i++) {
			ArchivesInfo archivesInfo = list.get(i);
			// 民事
			if (archivesInfo.getMark().equals("2")) {
				ms = Integer.parseInt(archivesInfo.getCaseProertyCount());
			}
			// 刑事
			if (archivesInfo.getMark().equals("1")) {
				xs = Integer.parseInt(archivesInfo.getCaseProertyCount());
			}
			// 国家赔偿与司法救助
			if (archivesInfo.getMark().equals("5")) {
				gjpc = Integer.parseInt(archivesInfo.getCaseProertyCount());
			}
			if (archivesInfo.getMark().equals("3")) {
				xz = Integer.parseInt(archivesInfo.getCaseProertyCount());
			}

			if (archivesInfo.getMark().equals("4")) {
				zx = Integer.parseInt(archivesInfo.getCaseProertyCount());
			}
			if (archivesInfo.getMark().equals("6")) {
				fscbq = Integer.parseInt(archivesInfo.getCaseProertyCount());
			}
		}

		dataMap.put("ms", ms);
		dataMap.put("xs", xs);
		dataMap.put("gjpc", gjpc);
		dataMap.put("xz", xz);
		dataMap.put("zx", zx);
		dataMap.put("fscbq", fscbq);
		return dataMap;
	}

	// 统计剩余库房档案和已用库房档案
	/**
	 * 查询案件性质统计量
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/libraryCount.do")
	@ResponseBody
	public Map<String, Object> libraryCount(int two, int three, int five) {
		Map<String, Object> dataMap = new HashMap();
		// 查询各月归档量
		int twoLibrary = sysDeskService.twoLibraryCount();
		int threeLibrary = sysDeskService.threeLibraryCount();
		int fiveLibrary = sysDeskService.fiveLibraryCount();
		List list = new ArrayList();
		list.add(twoLibrary);
		list.add(threeLibrary);
		list.add(fiveLibrary);
		dataMap.put("spend", list);
		int twoSurplus = two - twoLibrary;
		int threeSurplus = two - threeLibrary;
		int fiveSurplus = two - fiveLibrary;
		List listSurplus = new ArrayList();
		listSurplus.add(twoSurplus);
		listSurplus.add(threeSurplus);
		listSurplus.add(fiveSurplus);
		dataMap.put("surplus", listSurplus);
		return dataMap;
	}

	/**
	 * 文书档案首页统计
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/docDeskInfo.do")
	@ResponseBody
	public Map<String, Object> doucmentCount() {
		Map<String, Object> dataMap = new HashMap();
		int docCount = sysDeskService.docCount();// 库存
		int BorrowRoomBorrowCount = sysDeskService.queryBorrowRoomBorrowCount();
		int querBorrowSheetCount = sysDeskService.querBorrowSheetCount();
		int destroyCount = sysDeskService.destroyCount();
		dataMap.put("docCount", docCount);
		dataMap.put("BorrowRoomBorrowCount", BorrowRoomBorrowCount);
		dataMap.put("querBorrowSheetCount", querBorrowSheetCount);
		dataMap.put("destroyCount", destroyCount);
		return dataMap;
	}

	/**
	 * 查询个月统计量
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/docAnuaryCount.do")
	@ResponseBody
	public Map<String, Object> docAnuaryCount() {
		Map<String, Object> dataMap = new HashMap();
		// 查询各月归档量
		List twoList = this.oneAnuaryCount("2号库房");
		// 查询各月归档量
		int January = 0;// 1月
		int February = 0;// 2月
		int March = 0;// 3月
		int April = 0;// 4月
		int May = 0;// 5月
		int June = 0;// 6月
		int July = 0;// 7月
		int August = 0;// 8月
		int September = 0;// 9月
		int October = 0;// 10月
		int November = 0;// 11月
		int December = 0;// 12月
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy");
		String year = format.format(date);
		List<Docborrowdetail> OneJanuaryCount = sysDeskService
				.docBorrowCount(year);
		for (int i = 0; i < OneJanuaryCount.size(); i++) {
			Docborrowdetail doc = OneJanuaryCount.get(i);
			String[] strs = doc.getBorrowCount().split("[-]");
			if (strs[1].equals("01")) {
				January = Integer.parseInt(doc.getGuidCount());
			}
			if (strs[1].equals("02")) {
				February = Integer.parseInt(doc.getGuidCount());
			}
			if (strs[1].equals("03")) {
				March = Integer.parseInt(doc.getGuidCount());
			}
			if (strs[1].equals("04")) {
				April = Integer.parseInt(doc.getGuidCount());
			}
			if (strs[1].equals("05")) {
				May = Integer.parseInt(doc.getGuidCount());
			}
			if (strs[1].equals("06")) {
				June = Integer.parseInt(doc.getGuidCount());
			}
			if (strs[1].equals("07")) {
				July = Integer.parseInt(doc.getGuidCount());
			}
			if (strs[1].equals("08")) {
				August = Integer.parseInt(doc.getGuidCount());
			}
			if (strs[1].equals("09")) {
				September = Integer.parseInt(doc.getGuidCount());
			}
			if (strs[1].equals("10")) {
				October = Integer.parseInt(doc.getGuidCount());
			}
			if (strs[1].equals("11")) {
				November = Integer.parseInt(doc.getGuidCount());
			}
			if (strs[1].equals("12")) {
				December = Integer.parseInt(doc.getGuidCount());
			}
		}
		List borrwoList = new ArrayList();
		borrwoList.add(January);
		borrwoList.add(February);
		borrwoList.add(March);
		borrwoList.add(April);
		borrwoList.add(May);
		borrwoList.add(June);
		borrwoList.add(July);
		borrwoList.add(August);
		borrwoList.add(September);
		borrwoList.add(October);
		borrwoList.add(November);
		borrwoList.add(December);
		dataMap.put("list", borrwoList);

		return dataMap;
	}
}
