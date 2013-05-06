/**
 * MyApplication.java
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
 * MyApplication.java
 * 
 * 2013-5-6
 */

package com.weibo.sdk.android.util;

import android.app.Application;
import android.content.Context;

/**
 * ClassName:MyApplication
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-6
 */
/**
 * MyApplication 2013-5-6 上午9:39:05
 * 
 * @version 1.0.0
 * 
 */
public class MyApplication extends Application {
	public static LazyImageLoader lazyImageLoader;
	public static Context context;

	public void onCreate() {
		super.onCreate();

		context = this.getApplicationContext();
		lazyImageLoader = new LazyImageLoader();
	}

}
