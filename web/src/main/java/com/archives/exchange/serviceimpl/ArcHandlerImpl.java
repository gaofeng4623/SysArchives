package com.archives.exchange.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archives.exchange.service.ArcHandler;
import com.archives.exchange.service.ArcHandlerService;

@Service("arcSchedule")
public class ArcHandlerImpl implements ArcHandler {
	@Resource
	private ArcHandlerService arcHandler;

	@Override
	public int synArc() {
		int count = 0;
		try {
			count = arcHandler.synArchives();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
