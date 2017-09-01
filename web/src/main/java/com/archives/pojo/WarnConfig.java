package com.archives.pojo;
/**
 * 借阅逾期预警
 * @author zfn
 *
 */
public class WarnConfig {
	
	private Integer guid;
	
	private Integer maxBorrowTime;//最大借阅天数
	
	private Integer firstWarning;//一次预警
	
	private Integer secondWarning;//二次预警
	
	private Integer thirdWarning;//三次预警
    private Integer status;
	public Integer getGuid() {
		return guid;
	}

	public void setGuid(Integer guid) {
		this.guid = guid;
	}

	public Integer getMaxBorrowTime() {
		return maxBorrowTime;
	}

	public void setMaxBorrowTime(Integer maxBorrowTime) {
		this.maxBorrowTime = maxBorrowTime;
	}

	public Integer getFirstWarning() {
		return firstWarning;
	}

	public void setFirstWarning(Integer firstWarning) {
		this.firstWarning = firstWarning;
	}

	public Integer getSecondWarning() {
		return secondWarning;
	}

	public void setSecondWarning(Integer secondWarning) {
		this.secondWarning = secondWarning;
	}

	public Integer getThirdWarning() {
		return thirdWarning;
	}

	public void setThirdWarning(Integer thirdWarning) {
		this.thirdWarning = thirdWarning;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
