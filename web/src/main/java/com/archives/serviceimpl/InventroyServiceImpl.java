package com.archives.serviceimpl;

import java.net.URL;

import org.codehaus.xfire.client.Client;

import com.archives.dao.InventoryPlanCellDao;
import com.archives.dao.InventoryPlanDao;
import com.archives.pojo.InventoryPlan;
import com.archives.service.InventroyService;

public class InventroyServiceImpl implements InventroyService {

	private String serverAdress;
	private InventoryPlanDao inventoryPlanDao;
	private InventoryPlanCellDao inventoryPlanCellDao;
	
	@Override
	public Object[] invokeInventroy(String methodName, Object[] args)
			throws Exception {
		Client client = new Client(new URL(getServerAdress()));
		return client.invoke(methodName, args);
	}

	public String getServerAdress() {
		return serverAdress;
	}

	public void setServerAdress(String serverAdress) {
		this.serverAdress = serverAdress;
	}

	@Override
	public int refreshPlanCellStatus(int planId) {
		return inventoryPlanCellDao.refreshPlanCellStatus(planId);
	}
	
	@Override
	public int clearResultByPlanId(int planId) {
		return inventoryPlanCellDao.deleteResultByPlanId(planId);
	}

	public InventoryPlanCellDao getInventoryPlanCellDao() {
		return inventoryPlanCellDao;
	}

	public void setInventoryPlanCellDao(InventoryPlanCellDao inventoryPlanCellDao) {
		this.inventoryPlanCellDao = inventoryPlanCellDao;
	}
	
	public InventoryPlanDao getInventoryPlanDao() {
		return inventoryPlanDao;
	}

	public void setInventoryPlanDao(InventoryPlanDao inventoryPlanDao) {
		this.inventoryPlanDao = inventoryPlanDao;
	}

	@Override
	public int updateInventroyStatus(int planId, String status) {
		InventoryPlan plan = new InventoryPlan();
		plan.setGuid(planId);
		plan.setIsEnd(status);
		return inventoryPlanDao.updateByPrimaryKeySelective(plan);
	}
	
}
