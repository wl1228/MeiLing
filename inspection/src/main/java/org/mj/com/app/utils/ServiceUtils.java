package org.mj.com.app.utils;

import android.content.Context;
import android.content.Intent;

import org.mj.com.app.service.ComService;


/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class ServiceUtils {

    public static final String ACTIVITY_CODE = "activity_code";
    /**
     * 启动服务。
     * @param context
     */
    public static void startService(Context context,String activityCode){
        Intent intent = new Intent(context, ComService.class);
        intent.putExtra(ACTIVITY_CODE,activityCode);
        context.startService(intent);
    }

    /**
     * 关闭服务。
     * @param context
     */
    public static void stopService(Context context){
        Intent intent = new Intent(context,ComService.class);
        context.stopService(intent);
    }
}
