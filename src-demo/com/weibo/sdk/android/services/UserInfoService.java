package com.weibo.sdk.android.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.weibo.sdk.android.entity.UserInfo;
import com.weibo.sdk.android.keep.DBHelper;

/**
 * 2013-04-24
 * 
 * @author lei
 * 
 */
public class UserInfoService {

	private DBHelper dbHelper;
	private final String[] columns = new String[]{UserInfo.ID,
			UserInfo.USER_ID, UserInfo.USER_NAME, UserInfo.USERICON,
			UserInfo.TOKEN, UserInfo.EXPIRESTIME

	};

	public UserInfoService(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void insertUserInfo(UserInfo user) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues(5);

		values.put(UserInfo.USER_ID, user.getUserId());
		values.put(UserInfo.USER_NAME, user.getUserName());
		values.put(UserInfo.EXPIRESTIME, user.getExpiresTime());
		values.put(UserInfo.TOKEN, user.getToken());
		Drawable icon = user.getUserIcon();
		BitmapDrawable tempDrawable = (BitmapDrawable) icon;
		Bitmap userIcon = tempDrawable.getBitmap();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		userIcon.compress(CompressFormat.PNG, 100, os);

		values.put(UserInfo.USERICON, os.toByteArray());

		db.insert(UserInfo.TB_NAME, null, values);
		db.close();
	}

	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserInfo getUserInfoByUserId(String userId) {

		UserInfo userInfo = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		Cursor cursor = db.query(UserInfo.TB_NAME, columns, UserInfo.USER_ID
				+ "=?", new String[]{userId}, null, null, null);

		if (null != cursor) {

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				userInfo = new UserInfo();
				userInfo.setId(cursor.getLong(cursor
						.getColumnIndex(UserInfo.ID)));
				userInfo.setUserName(cursor.getString(cursor
						.getColumnIndex(UserInfo.USER_NAME)));
				userInfo.setToken(cursor.getString(cursor
						.getColumnIndex(UserInfo.TOKEN)));
				userInfo.setExpiresTime(cursor.getLong(cursor
						.getColumnIndex(UserInfo.EXPIRESTIME)));
				userInfo.setUserId(cursor.getString(cursor
						.getColumnIndex(UserInfo.USER_ID)));

				byte[] byteIcon = cursor.getBlob(cursor
						.getColumnIndex(UserInfo.USERICON));

				if (null != byteIcon) {

					ByteArrayInputStream is = new ByteArrayInputStream(byteIcon);
					Drawable userIcon = Drawable.createFromStream(is, "image");

					userInfo.setUserIcon(userIcon);
				}

			}

		}
		cursor.close();
		db.close();
		return userInfo;
	}

	/**
	 * 更新用户Token信息
	 * 
	 * @param userInfo
	 */
	public void updateUserInfo(UserInfo userInfo) {
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues(2);

		values.put(UserInfo.TOKEN, userInfo.getToken());
		values.put(UserInfo.EXPIRESTIME, userInfo.getExpiresTime());

		db.update(UserInfo.TB_NAME, values, UserInfo.USER_ID + "=?",
				new String[]{userInfo.getUserId()});
		db.close();

	}

	public void updateUserInfo(String userId, String userName, Bitmap userIcon) {
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues(2);

		// values.put(UserInfo.TOKEN, userInfo.getToken());
		// values.put(UserInfo.EXPIRESTIME, userInfo.getExpiresTime());
		// db.update(UserInfo.TB_NAME, values, UserInfo.USER_ID + "=?",
		// new String[] { userInfo.getUserId() });
		db.close();

	}

	/**
	 * 查询所有用户信息
	 * 
	 * @return
	 */
	public List<UserInfo> findAllUsers() {
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();

		List<UserInfo> users = null;

		Cursor cursor = db.query(UserInfo.TB_NAME, columns, null, null, null,
				null, null);
		if (null != cursor && cursor.getCount() != 0) {
			users = new ArrayList<UserInfo>(cursor.getCount());
			UserInfo userInfo = null;
			while (cursor.moveToNext()) {
				userInfo = new UserInfo();
				userInfo.setId(cursor.getLong(cursor
						.getColumnIndex(UserInfo.ID)));
				userInfo.setUserName(cursor.getString(cursor
						.getColumnIndex(UserInfo.USER_NAME)));
				userInfo.setToken(cursor.getString(cursor
						.getColumnIndex(UserInfo.TOKEN)));
				userInfo.setExpiresTime(cursor.getLong(cursor
						.getColumnIndex(UserInfo.EXPIRESTIME)));
				userInfo.setUserId(cursor.getString(cursor
						.getColumnIndex(UserInfo.USER_ID)));
				byte[] byteIcon = cursor.getBlob(cursor
						.getColumnIndex(UserInfo.USERICON));
				if (null != byteIcon) {
					ByteArrayInputStream is = new ByteArrayInputStream(byteIcon);
					Drawable userIcon = Drawable.createFromStream(is, "image");
					BitmapDrawable tempDrawable = (BitmapDrawable) userIcon;
					userInfo.setUserIcon(userIcon);
				}// end if

				users.add(userInfo);

			}// end while
		}// end if

		cursor.close();
		db.close();
		return users;
	}
}
