package com.weibo.sdk.android.keep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weibo.db";
    private Context mContext;
    private static final String CREATE_USER_TABLE = "CREATE  TABLE USER ("
            + "_ID INTEGER PRIMARY KEY, " + "uid  TEXT," + "token TEXT,"
            + "expiresTime  DOUBLE);";

    private static final String USER_DELETE_DDL = "DROP TABLE IF EXISTS USER";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(USER_DELETE_DDL);
        db.execSQL(CREATE_USER_TABLE);
    }

}
