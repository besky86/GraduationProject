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
import android.util.Log;

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
public class NullRequestListener implements RequestListener {

	Activity activity;

	/**
	 * 
	 * 创建一个新的实例 CommentRequestListener.
	 * 
	 */
	public NullRequestListener() {

		super();
		// TODO Auto-generated constructor stub

	}
	/**
	 * 
	 * 创建一个新的实例 CommentRequestListener.
	 * 
	 * @param activity
	 */
	public NullRequestListener(Activity activity) {
		super();
		this.activity = activity;
	}
	@Override
	public void onComplete(String response) {

		// TODO Auto-generated method stub
		// Delete by Lei@2013/05/09 DEL START
		// Util.showToast(activity, "评论成功");
		// Delete by Lei@2013/05/09 DEL END
		Log.e("error", response);
		
	}
	@Override
	public void onIOException(IOException e) {

		// TODO Auto-generated method stub
		Util.showToast(activity, "操作异常");

	}

	@Override
	public void onError(WeiboException e) {

		// TODO Auto-generated method stub
		Log.e("error", e.getMessage());
		 Util.showToast(MainTabActivity.activity, "操作失败");

	}

}
