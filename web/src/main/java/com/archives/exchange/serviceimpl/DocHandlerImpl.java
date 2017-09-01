package com.archives.exchange.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.exchange.service.DocHandler;
import com.archives.exchange.service.DocHandlerService;

@Service("docSchedule")
public class DocHandlerImpl implements DocHandler {
	@Resource
	private DocHandlerService docHandler;

	@Override
	public int synDoc() {
		int count = 0;
		try {
			count = docHandler.synDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
