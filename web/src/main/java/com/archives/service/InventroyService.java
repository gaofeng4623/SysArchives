package com.archives.service;

public interface InventroyService {
	
	/*请求盘点服务*/
	public Object[] invokeInventroy(String methodName, Object[] args) throws Exception;
	
	public int refreshPlanCellStatus(int planId);
	
	public int clearResultByPlanId(int planId);
	
	public int updateInventroyStatus(int planId, String status);
}
