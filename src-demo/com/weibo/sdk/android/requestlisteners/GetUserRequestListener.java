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
import android.os.Handler;
import android.os.Message;

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
public class GetUserRequestListener implements RequestListener {

	Activity activity;
	Handler h;

	/**
	 * 
	 * 创建一个新的实例 CommentRequestListener.
	 * 
	 */
	public GetUserRequestListener() {

		super();
		// TODO Auto-generated constructor stub

	}
	/**
	 * 
	 * 创建一个新的实例 CommentRequestListener.
	 * 
	 * @param activity
	 */
	public GetUserRequestListener(Activity activity, Handler h) {
		super();
		this.activity = activity;
		this.h = h;
	}
	@Override
	public void onComplete(String response) {

		Message msg = new Message();
		msg.getData().putString("user", response);
		h.handleMessage(msg);

	}
	@Override
	public void onIOException(IOException e) {

		// TODO Auto-generated method stub
		Util.showToast(activity, "操作异常");

	}

	@Override
	public void onError(WeiboException e) {

		// TODO Auto-generated method stub
		Util.showToast(activity, "操作失败");

	}

}
