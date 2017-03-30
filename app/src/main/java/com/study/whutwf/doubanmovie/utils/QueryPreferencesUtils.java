package com.study.whutwf.doubanmovie.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by whutwf on 17-3-30.
 */

public class QueryPreferencesUtils {

    public static void setStoredPreference(Context context, String key, String string) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(key, string)
                .apply();
    }

    public static String getStoredPreference(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, null);
    }

    //设置记录是否存储
    public static void setSignStoredPreference(Context context, String key, boolean sign) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(key, sign)
                .apply();
    }

    public static boolean getSignStoredPreference(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(key, false);
    }
}
