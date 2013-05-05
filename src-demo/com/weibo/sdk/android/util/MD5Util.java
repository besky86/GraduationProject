/**
 * MD5Util.java
 * com.weibo.sdk.android.util
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-5-5 		Lei
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */
/**
 * 系统项目名称
 * com.weibo.sdk.android.util
 * MD5Util.java
 * 
 * 2013-5-5
 */

package com.weibo.sdk.android.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName:MD5Util
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-5
 */
/**
 * MD5Util 2013-5-5 下午10:11:33
 * 
 * @version 1.0.0
 * 
 */
public class MD5Util {

	private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	public static String toHexString(byte[] b) {
		// String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			return toHexString(messageDigest);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

}
