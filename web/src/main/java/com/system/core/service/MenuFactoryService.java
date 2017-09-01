package com.system.core.service;

import javax.servlet.ServletRequest;

public interface MenuFactoryService {
	public String createMenuString(ServletRequest request, String optype);
}
