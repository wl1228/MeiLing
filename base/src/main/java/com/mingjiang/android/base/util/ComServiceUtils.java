package com.mingjiang.android.base.util;

import android.content.Context;
import android.content.Intent;

import com.mingjiang.android.base.service.ComService;


/**
 * 备注：启动和关闭串口服务。
 * 作者：wangzs on 2016/2/19 10:05
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class ComServiceUtils {

    /**
     * 启动串口服务。
     */
    public static void startService(Context context,int activityCode){
        Intent intent = new Intent(context, ComService.class);
        intent.putExtra(Constants.ACTIVITY_CODE,activityCode);
        context.startService(intent);
    }

    /**
     * 关闭串口服务。
     */
    public static void stopService(Context context){
        Intent intent = new Intent(context,ComService.class);
        context.stopService(intent);
    }
}