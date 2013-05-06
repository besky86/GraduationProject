/**
 * ImageCallback.java
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
 * ImageCallback.java
 * 
 * 2013-5-6
 */

package com.weibo.sdk.android.util;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * ClassName:ImageCallback
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-6
 */
/**
 * ImageCallback 回调接口
 * 2013-5-6 下午5:48:49
 * 
 * @version 1.0.0
 * 
 */
public interface ImageCallback {

	/**
	 * 
	 * imageSet图片资源设置
	 * @param drawable
	 * @param imageView 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void imageSet(Drawable drawable, ImageView imageView);
}

