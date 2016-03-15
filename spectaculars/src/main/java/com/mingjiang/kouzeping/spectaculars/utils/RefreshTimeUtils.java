package com.mingjiang.kouzeping.spectaculars.utils;

import android.content.Context;

/**
 * Created by kouzeping on 2016/2/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class RefreshTimeUtils {
    public static int getRefreshTime(Context context){
        int refreshTiem=10*1000;
        int position=SharedPrefsUtil.getInt(context, SharedPrefsUtil.REFRESH_TIME);
        switch (position) {
            case 0:refreshTiem=10*1000;break;
            case 1:refreshTiem=20*1000;break;
            case 2:refreshTiem=30*1000;break;
            case 3:refreshTiem=60*1000;break;
            case 4:refreshTiem=120*1000;break;
            case 5:refreshTiem=300*1000;break;
        }
        return refreshTiem;
    }
}
