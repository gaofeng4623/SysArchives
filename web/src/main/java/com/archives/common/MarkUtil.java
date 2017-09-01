package com.archives.common;

public class MarkUtil {
	
	/**
	 * 
	 * @param prefix
	 * @param guid 档案的主键
	 * @param itemid 分卷号
	 * @param itemType 分卷类型
	 * @return
	 */
	public static String generateMarkNo(String prefix, String guid, Integer itemid, Integer itemType){
		String formatArchNo = "";
		if(10 > guid.length()){
			formatArchNo = "0000000000" + guid;
			guid = formatArchNo.substring(formatArchNo.length()-10, formatArchNo.length());
		}else{
			guid = guid.substring(guid.length()-10, guid.length());
		}
		
		String suffix = "";
		String reelNo = "";
		itemType = itemType == null ? 0 : itemType;
		switch (itemType) {
			case 0 : suffix = ""; break; 
			case 1 : suffix = "D"; break; //D表示正卷
			case 2 : suffix = "F"; break; //F表示副卷
			default : suffix = ""; 
		}
		if (itemid == null || itemid == 0) {
			reelNo = "0001";
		} else {
			int length = itemid.toString().length();
			if (length < 4) {
				String str = "0000".substring(0, (4 - length)-1);
				reelNo = str + itemid;
			}
		}
		reelNo = suffix + reelNo;
		return prefix + guid + reelNo;
	}
	
	public static String numberToFormatString(int num, int count){
		String numStr = "0000"+num;
		return numStr.substring(numStr.length()-3, numStr.length());
	}
	

}
