package com.weibo.sdk.android.keep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "weibo.db";
	private static final String CREATE_USERINFO_TABLE = "CREATE TABLE IF NOT EXISTS UserInfo("
			+ "_id INTEGER PRIMARY KEY,"
			+ "uid TEXT,"
			+ "userName TEXT,"
			+ "token TEXT," + "expiresTime LONG," + "userIcon BLOB);";

	private static final String USERINFO_DELETE_DDL = "DROP TABLE IF EXISTS UserInfo";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_USERINFO_TABLE);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(USERINFO_DELETE_DDL);
		db.execSQL(CREATE_USERINFO_TABLE);
	}

}
