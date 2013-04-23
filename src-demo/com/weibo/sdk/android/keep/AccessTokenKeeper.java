package com.weibo.sdk.android.keep;

import com.weibo.sdk.android.Oauth2AccessToken;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 该类用于保存Oauth2AccessToken到sharepreference，并提供读取功能
 * 
 * @author xiaowei6@staff.sina.com.cn
 * 
 */
public class AccessTokenKeeper {
    private static final String PREFERENCES_NAME = "com_weibo_sdk_android";

    /**
     * 保存accesstoken到SharedPreferences
     * 
     * @param context
     *            Activity 上下文环境
     * @param token
     *            Oauth2AccessToken
     */
    public static void keepAccessToken(Context context, Oauth2AccessToken token) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString("token", token.getToken());
        editor.putLong("expiresTime", token.getExpiresTime());
        // added by Lei@2013/04/12 UPD START
        // editor.putString("uid", token.getUid());
        editor.putString("uid", token.getUid());
        // added by Lei@2013/04/12 UPD END
        editor.commit();

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put("uid", token.getToken());
        args.put("token", token.getToken());
        args.put("expiresTime", token.getExpiresTime());
        db.insert("USER", null, args);
        db.close();

    }

    /**
     * 清空sharepreference
     * 
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();

        //Add or Update by Lei@2013/04/23 UPD START
        //DBHelper dbHelper = new DBHelper(context);
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        //db.delete("USER", null, null);
        //db.close();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("USER", null, null);
        db.close();
        //Add or Update by Lei@2013/04/23 UPD END

    }

    /**
     * 从SharedPreferences读取accessstoken
     * 
     * @param context
     * @return Oauth2AccessToken
     */
    public static Oauth2AccessToken readAccessToken(Context context) {
        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        token.setToken(pref.getString("token", ""));
        token.setExpiresTime(pref.getLong("expiresTime", 0));
        // added by Lei@2013/04/12 UPD START
        // token.setUid(pref.getString("uid", ""));
        token.setUid(pref.getString("uid", ""));
        // added by Lei@2013/04/12 UPD END

        return token;
    }
}