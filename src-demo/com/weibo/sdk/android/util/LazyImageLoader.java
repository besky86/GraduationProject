/**
 * LazyImageLoader.java
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
 * LazyImageLoader.java
 * 
 * 2013-5-5
 */

package com.weibo.sdk.android.util;

import java.lang.Thread.State;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * ClassName:LazyImageLoader
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-5
 */
/**
 * LazyImageLoader 2013-5-5 下午9:37:59
 * 
 * @version 1.0.0
 * 
 */
public class LazyImageLoader {
	private static final int MESSAGE_ID = 1;
	public static final String EXTRA_IMG_URL = "image_url";
	public static final String EXTRA_IMAGE = "image";
	private CallBackManager callBackManager;

	private ImageManager imgManager = new ImageManager();

	private BlockingQueue<String> urlQueue = new ArrayBlockingQueue<String>(50);

	private DownloadImageThread downloadImageThread = new DownloadImageThread();

	public Bitmap get(String url, ImageLoaderCallback callback) {
		Bitmap bitmap = null;
		if (imgManager.contains(url)) {

			bitmap = imgManager.getFromCache(url);
			return bitmap;
		}
		else {

			callBackManager.put(url, callback);
			startDownloadThread(url);
		}

	}

	/**
	 * startDownloadThread(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void startDownloadThread(String url) {

		// TODO Auto-generated method stub
		putUrltoQueue(url);

		State state = downloadImageThread.getState();

		if (state == State.NEW) {
			downloadImageThread.start();
		}
		else
			if (state == State.TERMINATED) {
				downloadImageThread = new DownloadImageThread();
				downloadImageThread.start();
			}

	}

	private void putUrltoQueue(String url) {
		if (!urlQueue.contains(url)) {
			urlQueue.put(url);
		}
	}

	Handler handler = new Handler() {

		public void handMessage(Message msg) {

			switch (msg.what) {
				case MESSAGE_ID :
					Bundle bundle = msg.getData();
					String url = bundle.getString(EXTRA_IMG_URL);
					Bitmap bitmap = bundle.getParcelable(EXTRA_IMAGE);

					callBackManager.callback(url, bitmap);
			}
		}
	};
	private class DownloadImageThread extends Thread {

		private boolean isRun = true;
		public void shutDown(){
			isRun = false;
		}
		

		public void run() {
			try {
				while (isRun) {
					String url = urlQueue.poll();
					if (null == url) {
						break;
					}

					Bitmap bitmap = imgManager.safeGet(url);
					Message msg = handler.obtainMessage(MESSAGE_ID);
					Bundle bundle = msg.getData();
					bundle.putSerializable(EXTRA_IMG_URL, url);
					bundle.putParcelable(EXTRA_IMAGE, bitmap);

					handler.sendMessage(msg);

				}
			}
			catch (Exception e) {

			}
			finally{
				isRun = false;
			}
		}
	}
}
