/**
 * Tools.java
 * com.weibo.sdk.android.util
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-5-6 		Lei
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */
/**
 * 系统项目名称
 * com.weibo.sdk.android.util
 * Tools.java
 * 
 * 2013-5-6
 */

package com.weibo.sdk.android.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.drawable.Drawable;

/**
 * ClassName:Tools
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-6
 */
/**
 * Tools 2013-5-6 下午5:59:07
 * 
 * @version 1.0.0
 * 
 */
public class Tools {

	public static Drawable getDrawablFromUrl(String url) {
		try {
			URLConnection urls = new URL(url).openConnection();
			return Drawable.createFromStream(urls.getInputStream(), "image");

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
