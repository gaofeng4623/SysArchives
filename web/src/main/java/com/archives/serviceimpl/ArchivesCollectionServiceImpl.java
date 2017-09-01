package com.archives.serviceimpl;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archives.dao.ArchivesCollectionDao;
import com.archives.pojo.ArchivesInfo;
import com.archives.pojo.Borrow;
import com.archives.service.ArchivesCollectionService;
import com.system.base.pojo.Department;
import com.system.dao.database.DepartmentDao;
import com.system.workflow.activiti.commons.Pager;
@Service
public class ArchivesCollectionServiceImpl implements ArchivesCollectionService{
	@Autowired
	private ArchivesCollectionDao archivesCollectionDao;
	@Autowired
	private DepartmentDao departmentDao;
	@Override
	public Pager findCollection(String middleTime, String undertakeDep, int start, int rows) {
		Map<String,Object> m = new HashMap();
		m.put("start", start);
		m.put("rows", rows);
		m.put("middleTime", middleTime);
		m.put("undertakeDep", undertakeDep);
		List<Borrow> list = archivesCollectionDao.findCollection(m);
		int total = archivesCollectionDao.findCollectionCountList(m);
		return new Pager(total, list);
	}

	@Override
	public List<ArchivesInfo> findCollectionNopage(String middleTime,String undertakeDep) {
		return archivesCollectionDao.findCollectionNopage(middleTime,undertakeDep);
	}

	@Override
	public List<Department> findAllDepartmentsforTree() {
		List<Department> depts = departmentDao.selectAllDepratments();
		List<Department>  nodeList= new ArrayList<Department>();
		Department root = new Department();
		root.setDepartmentid("0");
		root.setSuperiorid("-1");
		root.setDepartmentname("省高级人民法院");
		depts.add(root);
		for (Department department : depts) {
			department.setId(department.getDepartmentid());
			department.setText(department.getDepartmentname());
		}
		for (Department department : depts) {
			boolean mark = false;
			for (Department department2 : depts) {
				if(department2.getDepartmentid().equals(department.getSuperiorid())){
					mark = true;
					if(null == department2.getChildren()){
						department2.setChildren(new ArrayList<Department>());
					}
					department2.getChildren().add(department);
					break;
				}
			}
			
			if(!mark){
				nodeList.add(department);
			}
		}
		
		return nodeList;
	}

	@Override
	public List<ArchivesInfo> listInfo(String middleTime,String undertakeDep) {
		List list = archivesCollectionDao.listInfo(middleTime,undertakeDep);

		return list;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	 public void exportExcel(String title,String[] headers,List mapList,OutputStream out,String pattern){  
		//声明一个工作簿  
		         HSSFWorkbook workbook = new HSSFWorkbook();  
		        //生成一个表格  
		          HSSFSheet sheet = workbook.createSheet(title);  
		         //设置表格默认列宽度为15个字符  
		        sheet.setDefaultColumnWidth(20);  
		         //生成一个样式，用来设置标题样式  
		          HSSFCellStyle style = workbook.createCellStyle();  
		         //设置这些样式  
		         style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
		          style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
		          style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
		          style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
		          style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
		         style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
		          style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		         //生成一个字体  
		          HSSFFont font = workbook.createFont();  
		         font.setColor(HSSFColor.VIOLET.index);  
		          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
		          //把字体应用到当前的样式  
		          style.setFont(font);  
		         // 生成并设置另一个样式,用于设置内容样式  
		          HSSFCellStyle style2 = workbook.createCellStyle();  
		          style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
		         style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
		         style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
		         style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
		         style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
		          style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
		          style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		          style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
		         // 生成另一个字体  
		          HSSFFont font2 = workbook.createFont();  
		          font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
		          // 把字体应用到当前的样式  
		          style2.setFont(font2);  
		          //产生表格标题行  
		          HSSFRow row = sheet.createRow(0);  
		          for(int i = 0; i<headers.length;i++){  
		              HSSFCell cell = row.createCell(i);  
		              cell.setCellStyle(style);  
		              HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
		             cell.setCellValue(text);  
		          }  
		          for (int i=0;i<mapList.size();i++) {  
		        	  ArchivesInfo list=(ArchivesInfo) mapList.get(i);
		        	               row = sheet.createRow(i+1);  
		        	               int j = 0;  
		        	               Object value = null;  
		        	              value=list.getCaseno(); 
		        	              if(list.getCaseno()!=null){
		        	            	  row.createCell(j++).setCellValue(list.getCaseno().toString());  
		        	              }else{
		        	            	  row.createCell(j++).setCellValue("");  
		        	              }
		        	              if(list.getYear()!=null){
		        	            	  row.createCell(j++).setCellValue(list.getYear().toString()); 
		        	              }else{
		        	            	  row.createCell(j++).setCellValue("");  
		        	              }
		        	              if(list.getCaseword()!=null){
		        	            	  row.createCell(j++).setCellValue(list.getCaseword().toString()); 
		        	              }else{
		        	            	  row.createCell(j++).setCellValue("");  
		        	              }
		        	              if(list.getCasename()!=null){
		        	            	  row.createCell(j++).setCellValue(list.getCasename().toString()); 
		        	              }else{
		        	            	  row.createCell(j++).setCellValue("");   
		        	              }
		        	              if(list.getPutonrecorddep()!=null){
		        	            	  row.createCell(j++).setCellValue(list.getPutonrecorddep().toString());
		        	              }else{
		        	            	  row.createCell(j++).setCellValue(""); 
		        	              }
		        	              if(list.getSettledate()!=null){
		        	            	 String setDate=list.getSettledate().toString();
		        	            	 
		        	            	  row.createCell(j++).setCellValue(setDate.substring(0, 10)); 
		        	              }else{
		        	            	  row.createCell(j++).setCellValue(""); 
		        	              }
		        	            /*  row.createCell(j++).setCellValue(list.getYear().toString());  
		        	              row.createCell(j++).setCellValue(list.getYear().toString());  
		        	              row.createCell(j++).setCellValue(list.getCaseword().toString());  
		        	              row.createCell(j++).setCellValue(list.getCasename().toString());  
		        	              row.createCell(j++).setCellValue(list.getPutonrecorddep().toString());  
		        	              row.createCell(j++).setCellValue(list.getSettledate().toString());  */
		        	           }  
		        	           try {  
		        	               workbook.write(out);  
		        	           } catch (IOException e) {  
		        	               e.printStackTrace();  
		        	           }  
		        	       


	 }

	@Override
	public List<ArchivesInfo> filingReate() {
		Map<String,Object> m = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date yea = new Date();
		String year = sdf.format(yea);
		m.put("year", year);
		List<ArchivesInfo> list = archivesCollectionDao.filingReate(m);
		return list;
	}

	@Override
	public List<ArchivesInfo> filingReateCount() {
		Map<String,Object> m = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date yea = new Date();
		String year = sdf.format(yea);
		m.put("year", year);
		List<ArchivesInfo> list = archivesCollectionDao.filingReateCount(m);
		return list;
	}

}
