package com.system.base.pojo;

public class SysDesk {
	// 当前库存
	private Integer archiveCount;
	// 借阅数量
	private Integer borrowArchiveCount;
	// 已办数量
	private Integer transferArchiveCount;
	// 待办数量
	private Integer collectArchiveCount;
	
	public Integer getArchiveCount() {
		return archiveCount;
	}
	public void setArchiveCount(Integer archiveCount) {
		this.archiveCount = archiveCount;
	}
	public Integer getBorrowArchiveCount() {
		return borrowArchiveCount;
	}
	public void setBorrowArchiveCount(Integer borrowArchiveCount) {
		this.borrowArchiveCount = borrowArchiveCount;
	}
	public Integer getTransferArchiveCount() {
		return transferArchiveCount;
	}
	public void setTransferArchiveCount(Integer transferArchiveCount) {
		this.transferArchiveCount = transferArchiveCount;
	}
	public Integer getCollectArchiveCount() {
		return collectArchiveCount;
	}
	public void setCollectArchiveCount(Integer collectArchiveCount) {
		this.collectArchiveCount = collectArchiveCount;
	}
}