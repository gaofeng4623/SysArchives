package com.archives.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.archives.dao.DoorWarningDao;
import com.archives.pojo.DoorWarning;
@Controller
@RequestMapping("/file")
public class DoorCameraImageDownload {
	@Resource
	private DoorWarningDao doorWarningDao;
	private Log logger = LogFactory.getLog(DoorCameraImageDownload.class);
	
	
	@RequestMapping("/down.do")
	public String download(int guid,HttpServletResponse response) throws Exception{
		DoorWarning doorwarning=doorWarningDao.selectByPrimaryKey(guid);
		int b = 0;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setCharacterEncoding("utf-8");
			bos = new BufferedOutputStream(response.getOutputStream());
			bis = new BufferedInputStream(new FileInputStream(new File(doorwarning.getHandlerurl())));
			while ((b = bis.read()) != -1) {
				bos.write(b);
			}
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {bis.close();} catch (IOException e) {}
			}
			if (bos != null) {
				try {bos.close();} catch (IOException e) {}
			}
		}
		return null;
	}
	
}
