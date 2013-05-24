/**
 * ImageManager.java
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
 * ImageManager.java
 * 
 * 2013-5-5
 */

package com.weibo.sdk.android.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * ClassName:ImageManager
 * Function: TODO 获取图片
 * Reason:	 TODO 图片获取
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-5
 */
/**
 * ImageManager 2013-5-5 下午9:30:36
 * 
 * @version 1.0.0
 * 
 */
public class ImageManager {

	Map<String, SoftReference<Bitmap>> imageCache;
	private Context context;

	public ImageManager(Context context) {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
		this.context = context;
	}

	public Bitmap getFromCache(String url) {

		Bitmap bitmap = null;
		bitmap = this.getFromMapCache(url);

		if (null == bitmap) {
			getFromFile(url);
		}

		return bitmap;

	}

	/**
	 * getFromFile 从文件中获取Bitmap
	 * 
	 * @param url
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private Bitmap getFromFile(String url) {

		// TODO Auto-generated method stub

		// String fileName = this.getMd5(url);
		String fileName = url;

		FileInputStream is = null;

		try {
			is = context.openFileInput(fileName);
			return BitmapFactory.decodeStream(is);
		}
		catch (FileNotFoundException e) {

			e.printStackTrace();
			return null;
		}
		finally {
			if (null != is) {
				try {
					is.close();
				}
				catch (Exception ex) {

				}
			}
		}

	}

	void Download() {

	}

	/**
	 * 
	 * getFromMapCache 从cache中获取图片
	 * 
	 * @param url
	 * @return Bitmap
	 * @exception
	 * @since 1.0.0
	 */
	public Bitmap getFromMapCache(String url) {
		Bitmap bitmap = null;

		SoftReference<Bitmap> ref = null;

		synchronized (this) {
			ref = imageCache.get(url);
		}

		if (null != ref) {
			bitmap = ref.get();

			if (null != bitmap) {

			}
		}
		return bitmap;
	}

	public Bitmap safeGet(String url) {
		Bitmap bitmap = this.getFromFile(url);
		if (null != bitmap) {
			synchronized (this) {
				imageCache.put(url, new SoftReference<Bitmap>(bitmap));
			}
			return bitmap;
		}

		return downloadImage(url);
	}

	public Bitmap downloadImage(String url) {
		if (url == null) {
			return null;
		}
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url)
					.openConnection();

			String fileName = writeToFile(url, connection.getInputStream());
			return BitmapFactory.decodeFile(fileName);

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// 缓存中是否含有图片
	public boolean contains(String url) {
		return imageCache.containsKey(url);

	}

	/**
	 * 
	 * writeToFile 将图片存入缓存文件
	 * 
	 * @param fileName
	 * @param is
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public String writeToFile(String fileName, InputStream is) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(context.openFileOutput(fileName,
					Context.MODE_PRIVATE));

			byte[] buffer = new byte[1024];

			int length;
			while ((length = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, length);
			}

		}
		catch (Exception e) {

		}
		finally {
			try {
				if (null != bis) {
					bis.close();
				}

				if (null != bos) {
					bos.flush();
					bos.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		return context.getFilesDir() + "/" + fileName;

	}

	private String getMd5(String url) {
		return MD5Util.md5(url);
	}

}
