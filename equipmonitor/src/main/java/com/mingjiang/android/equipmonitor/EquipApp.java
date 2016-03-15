package com.mingjiang.android.equipmonitor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.mingjiang.android.base.util.Config;
import com.mingjiang.android.equipmonitor.client.NetService;
import com.mingjiang.android.equipmonitor.entity.WeldPower;
import com.mingjiang.android.equipmonitor.entity.WeldThick;
import com.mingjiang.android.equipmonitor.entity.WeldTime;
import com.nrs.utils.tools.CrashHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.android.mingjiang.com.logapi.recordlog.log.manager.LogManager;
import retrofit.RestAdapter;

/**
 * Created by wangzs on 2015/12/18.
 */
public class EquipApp extends Application{

    private static EquipApp equipApp = null;

    private static NetService netService;

    public static List<Activity> allActivity = new ArrayList<Activity>();

    public static WeldPower weldPower = new WeldPower();//焊接能量

    public static WeldThick weldThick = new WeldThick();//焊接壁厚

    public static WeldTime weldTime = new WeldTime();//焊接时间

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static EquipApp getInstance(){
        if(equipApp == null){
            equipApp = new EquipApp();
        }
        return equipApp;
    }

    public static Map<Context,NetService> netServiceMap = new HashMap<Context,NetService>();

    public NetService getNetService(Context context) {
       NetService netService = netServiceMap.get(context);
        if (netService != null){
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
