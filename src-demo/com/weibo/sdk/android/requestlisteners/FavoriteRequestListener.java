/**
 * FavoriteRequestListener.java
 * requestlisteners
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-5-9 		Lei
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */
/**
 * 系统项目名称
 * requestlisteners
 * FavoriteRequestListener.java
 * 
 * 2013-5-9
 */

package com.weibo.sdk.android.requestlisteners;

import java.io.IOException;

import android.app.Activity;

import com.weibo.sdk.android.demo.*;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

/**
 * ClassName:FavoriteRequestListener
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-9
 */
/**
 * FavoriteRequestListener 2013-5-9 下午4:19:26
 * 
 * @version 1.0.0
 * 
 */
public class FavoriteRequestListener implements RequestListener {

	Activity activity;
	String info;

	/**
	 * 创建一个新的实例 FavoriteRequestListener.
	 * 
	 */
	public FavoriteRequestListener() {

		super();
		// TODO Auto-generated constructor stub

	}
	/**
	 * 创建一个新的实例 FavoriteRequestListener.
	 * 
	 * @param activity
	 */
	public FavoriteRequestListener(Activity activity, String info) {
		super();
		this.activity = activity;
		this.info = info;
	}
	@Override
	public void onComplete(String response) {

		// TODO Auto-generated method stub
		Util.showToast(activity, info + "成功");
	}
	@Override
	public void onIOException(IOException e) {

		// TODO Auto-generated method stub
		Util.showToast(activity, "异常");

	}

	@Override
	public void onError(WeiboException e) {

		// TODO Auto-generated method stub
		Util.showToast(activity, "失败");

	}

}
