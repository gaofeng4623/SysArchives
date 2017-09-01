package com.system.workflow.activiti.commons;

import java.util.List;


public class Pager {
	
	/**
	 * 分页总数
	 */
	private int total;
	
	/**
	 * 分页数据
	 */
	private List<?> rows;

	
	public Pager() {
		super();
	}

	public Pager(int total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	/* (Not Javadoc) 
	* <p>Description: </p> 
	* @return 
	* @see java.lang.Object#toString() 
	*/
	@Override
	public String toString() {
		return "Pager [total=" + total + ", rows=" + rows + "]";
	}

}
