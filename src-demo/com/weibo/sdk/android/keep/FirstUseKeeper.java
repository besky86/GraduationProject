package com.weibo.sdk.android.keep;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class FirstUseKeeper {

    private static final String PREFERENCES_NAME = "firstuse";

    // 判断是否为第一次使用
    public static boolean isFirstUse(Context context) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);

        return pref.getBoolean("isFirstUse", true);
    }

    public static void keepAccessToken(Context context, boolean isFirstUse) {

        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putBoolean("isFirstUse", isFirstUse);
        // added by Lei@2013/04/12 UPD END
        editor.commit();
    }
}
