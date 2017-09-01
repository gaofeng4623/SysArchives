package com.archives.common;

/**
 * 档案状态枚举类型
 * @author 123
 *
 */
public enum ArchiveStatus {
	NOT_SHELVES("0", "未上架"), 
	IN_LIBRARY("1", "在库"), 
	DOWN_SHELVES("2","下架"),
	DISTORY("3","销毁"),
	READY_SHELIVES("4","待上架");

	/**
	 * 档案状态的值。
	 */
	private String value;
	/**
	 * 档案状态的中文描述。
	 */
	private String text;

	private ArchiveStatus(String status, String desc) {
		setValue(status);
		setText(desc);
	}

	/**
	 * 根据档案状态的值获取枚举对象。
	 * 
	 * @param status 档案状态的值
	 * @return 枚举对象
	 */
	public static ArchiveStatus getInstance(String status) {
		ArchiveStatus[] allStatus = ArchiveStatus.values();
		ArchiveStatus result = null;
		for (ArchiveStatus ws : allStatus) {
			if (ws.getValue().equalsIgnoreCase(status)) {
				result = ws;
				break;
			}
		}
		return result;
		// throw new IllegalArgumentException("status值非法，没有符合课程状态的枚举对象");
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
