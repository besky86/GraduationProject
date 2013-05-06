/**
 * SimpleImageLoader.java
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
 * SimpleImageLoader.java
 * 
 * 2013-5-6
 */

package com.weibo.sdk.android.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * ClassName:SimpleImageLoader
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-6
 */
/**
 * SimpleImageLoader 2013-5-6 上午9:52:21
 * 
 * @version 1.0.0
 * 
 */
public class SimpleImageLoader {

	public static void showImg(String url, ImageView view) {
		view.setTag(url);

		view.setImageBitmap(MyApplication.lazyImageLoader.get(url,
				getCallback(url, view)));
	}

	private static ImageLoaderCallback getCallback(String url,
			final ImageView view) {
		return new ImageLoaderCallback( ) {

			@Override
			public void refresh(String url, Bitmap bitmap) {

				// TODO Auto-generated method stub
				if (url.equals(view.getTag().toString())) {
					view.setImageBitmap(bitmap);
				}

			}
		};

	}
}
