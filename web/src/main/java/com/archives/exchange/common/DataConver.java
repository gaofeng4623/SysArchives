package com.archives.exchange.common;

import com.archives.common.Adapter;

public class DataConver implements Adapter{

	@Override
	public boolean match(String head) {
		return head.indexOf("民") > -1;
	}


}
