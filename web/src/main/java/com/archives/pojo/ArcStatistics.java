package com.archives.pojo;


public class ArcStatistics {
    private Integer placeOnCount;//每月归档数量

    private String placeOnMonth;//归档月份
    
    private Integer borrowCount;//每月借阅数量

    private String borrowMonth;//借阅月份

	public Integer getPlaceOnCount() {
		return placeOnCount;
	}

	public void setPlaceOnCount(Integer placeOnCount) {
		this.placeOnCount = placeOnCount;
	}

	public String getPlaceOnMonth() {
		return placeOnMonth;
	}

	public void setPlaceOnMonth(String placeOnMonth) {
		this.placeOnMonth = placeOnMonth;
	}

	public Integer getBorrowCount() {
		return borrowCount;
	}

	public void setBorrowCount(Integer borrowCount) {
		this.borrowCount = borrowCount;
	}

	public String getBorrowMonth() {
		return borrowMonth;
	}

	public void setBorrowMonth(String borrowMonth) {
		this.borrowMonth = borrowMonth;
	}
}