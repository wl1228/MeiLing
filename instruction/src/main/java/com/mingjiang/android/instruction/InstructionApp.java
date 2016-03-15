package com.mingjiang.android.instruction;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.mingjiang.android.base.util.Config;
import com.mingjiang.android.instruction.client.NetService;
import com.mingjiang.android.instruction.entity.OperStep;
import com.mingjiang.android.instruction.entity.PostOperInstruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RestAdapter;

/**
 * Created by wangzs on 2015/12/16.
 */
public class InstructionApp extends Application{
    private static InstructionApp app;

    private static NetService netService;

    public static List<Activity> allActivity = new ArrayList<Activity>();

    public static PostOperInstruction localPostOperInstruction = null; //本地读取
    public static PostOperInstruction currentPostOperInstruction = null;//服务器端传递
    public static List<OperStep> needDownloadList = new ArrayList<OperStep>();//需要下载图片的OperStep
    public static int downloadCount = 0;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static InstructionApp getApp(){
        if(app == null){
            app = new InstructionApp();
        }
        return app;
    }

    public static Map<Context,NetService> netServiceMap = new HashMap<Context,NetService>();

    public NetService getNetService(Context context) {
        netService = netServiceMap.get(context);
        if(netService != null){
            return netService;
        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Config.getBaseUrl(context))
                .setLogLevel(RestAdapter.LogLevel.NONE)//如果设置RestAdapter.LogLevel.FULL则会出现大图片下载不下来问题。
                .build();
        netService = restAdapter.create(NetService.class);
        netServiceMap.put(context,netService);
        return netService;
    }

    /**
     * 添加activity。
     * @param activity
     */
    public static void addActivity(Activity activity){
        if(!allActivity.contains(activity)){
            allActivity.add(activity);
        }
    }

    /**
     * 退出应用程序。
     */
    public static void exitApplication(){
       for(Activity activity : allActivity){
           activity.finish();
       }
        System.exit(0);
    }
}
