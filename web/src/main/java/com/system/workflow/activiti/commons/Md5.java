package com.system.workflow.activiti.commons;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {

	public final static String getMd5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public final static String MD516(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result.toUpperCase();
	}

	public final static String MD532(String sourceStr) {
		return Md5.MD516(sourceStr)+"000000";
//		String result = "";
//		try {
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.update(sourceStr.getBytes());
//			byte b[] = md.digest();
//			int i;
//			StringBuffer buf = new StringBuffer("");
//			for (int offset = 0; offset < b.length; offset++) {
//				i = b[offset];
//				if (i < 0)
//					i += 256;
//				if (i < 16)
//					buf.append("0");
//				buf.append(Integer.toHexString(i));
//			}
//			result = buf.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return result+"00";
	}

	public final static String MD564(String sourceStr) {
		String newstr = "";
		/*try {
			// 确定计算方法  
			//MessageDigest md5 = MessageDigest.getInstance("MD5");  
			//BASE64Encoder base64en = new BASE64Encoder();  
			// 加密后的字符串  
			//newstr = base64en.encode(md5.digest(sourceStr.getBytes("utf-8")));  
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		return newstr;
	}
}
