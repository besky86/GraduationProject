/**
 * AsynImageLoader.java
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
 * AsynImageLoader.java
 * 
 * 2013-5-6
 */

package com.weibo.sdk.android.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * ClassName:AsynImageLoader
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-6
 */
/**
 * AsynImageLoader 2013-5-6 下午5:44:11
 * 
 * @version 1.0.0
 * 
 */
public class AsynImageLoader {

	// 图片数据缓存 key = url , value = 图片资源对象
	private static HashMap<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();

	public AsynImageLoader() {
		if (imageCache == null) {
			imageCache = new HashMap<String, SoftReference<Drawable>>();

		}
	}

	/**
	 * 
	 * loadDrawable 异步下载图片
	 * 
	 * @param url
	 *            图片地址
	 * @param imageView
	 *            相应组件
	 * @param callback
	 *            回调接口
	 * @return Drawable
	 * @exception
	 * @since 1.0.0
	 */
	public static Drawable loadDrawable(final String url,
			final ImageView imageView, final ImageCallback callback) {
		// 判断是否已经下载过
		if (imageCache.containsKey(url)) {
			SoftReference<Drawable> softRef = imageCache.get(url);
			Drawable drawable = softRef.get();
			if (drawable != null) {
				return drawable;
			}

		}

		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				// 图片资源设置
				callback.imageSet((Drawable) msg.obj, imageView);
			}
		};

		// 下载图片
		new Thread() {
			public void run() {
				Drawable drawable = Tools.getDrawablFromUrl(url);
				// 存储到缓存，避免重复下载
				imageCache.put(url, new SoftReference<Drawable>(drawable));

				Message msg = handler.obtainMessage();
				msg.obj = drawable;
				handler.sendMessage(msg);
			}
		}.start();

		return null;
	}

}
