package org.mj.com.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class SharedPrefsUtil {
    public static String POST_CODE = "post_code";//可以将岗位编号进行保存操作
    public static String CHECK_POST_CODE = "check_post_code";//可以将检验岗位编号进行保存操作
    public static String CHECK_CLASSES_CODE = "user_code";//可将用户编码进行保存


    public static SharedPreferences getDefaultSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setValue(Context context, String key, String value) {
        getDefaultSharedPrefs(context).edit().putString(key, value).commit();
    }
    public static void setValue(Context context, String key, int value) {
        getDefaultSharedPrefs(context).edit().putInt(key, value).commit();
    }
    public static void setValue(Context context, String key, boolean value) {
        getDefaultSharedPrefs(context).edit().putBoolean(key, value).commit();
    }
    public static void setValue(Context context, String key, float value) {
        getDefaultSharedPrefs(context).edit().putFloat(key, value).commit();
    }
    public static void setValue(Context context, String key, long value) {
        getDefaultSharedPrefs(context).edit().putLong(key, value).commit();
    }

    public static String getString(Context context, String key) {
        return getDefaultSharedPrefs(context).getString(key, "");
    }


    public static boolean getBoolean(Context context, String key) {
        return getDefaultSharedPrefs(context).getBoolean(key, false);
    }
    public static int getInt(Context context, String key) {
        return getDefaultSharedPrefs(context).getInt(key, 0);
    }
    public static long getLong(Context context, String key) {
        return getDefaultSharedPrefs(context).getLong(key, 0);
    }
    public static float getFloat(Context context, String key) {
        return getDefaultSharedPrefs(context).getFloat(key, 0f);
    }

    public static boolean contains(Context context, String key) {
        return getDefaultSharedPrefs(context).contains(key);
    }

}
