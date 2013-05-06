/**
 * 
 */
package com.weibo.sdk.android.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 2013-04-24
 * @author lei
 * 
 */
public class NetUtil {
	public static Drawable getImage(String url) {
		if (url == null) {
			return null;
		}
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url)
					.openConnection();
			Drawable icon = Drawable
					.createFromStream(connection.getInputStream(), url
							.toString().split("\\/")[3]);

			return icon;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	
}
