/**
 * CallBackManager.java
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
 * CallBackManager.java
 * 
 * 2013-5-5
 */

package com.weibo.sdk.android.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Bitmap;

/**
 * ClassName:CallBackManager
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-5
 */
/**
 * CallBackManager 2013-5-5 下午9:38:45
 * 
 * @version 1.0.0
 * 
 */
public class CallBackManager {

	private ConcurrentHashMap<String, List<ImageLoaderCallback>> callbackMap;

	/**
	 * 创建一个新的实例 CallBackManager.
	 * 
	 */
	public CallBackManager() {

		// TODO Auto-generated constructor stub
		callbackMap = new ConcurrentHashMap<String, List<ImageLoaderCallback>>();

	}

	public void put(String url,ImageLoaderCallback callback) {
		if(!callbackMap.contains(url)) {
			callbackMap.put(url, new ArrayList<ImageLoaderCallback>( ));
			
		}
		callbackMap.get(url).add(callback);
	}
	public void callback(String url, Bitmap bitmap) {

		List<ImageLoaderCallback> callbacks = callbackMap.get(url);
		if (null == callbacks)
			return;

		for (ImageLoaderCallback callback : callbacks) {
			if (null != callback) {
				callback.refresh(url, bitmap);
			}

		}

		callbacks.clear();
		callbackMap.remove(url);

	}

}
