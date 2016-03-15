package com.mingjiang.kouzeping.spectaculars.utils;

import android.app.Application;
import android.content.Context;

import com.mingjiang.android.base.util.Config;
import com.mingjiang.kouzeping.spectaculars.SpectacularsActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class SpectacularsApplication extends Application {

    private static SpectacularsApplication application;
    private static NetService netService;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        application=SpectacularsApplication.this;
    }

    public static Map<Context,NetService> netServiceMap = new HashMap<Context,NetService>();

    public static NetService getNetService(Context context) {
        netService =  netServiceMap.get(context);
        if ( netService!= null )
            return netService;

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Config.getBaseUrl(context))
                .setLogLevel(RestAdapter.LogLevel.NONE)//如果设置RestAdapter.LogLevel.FULL则会出现大图片下载不下来问题。
                .build();
        netService = restAdapter.create(NetService.class);
        netServiceMap.put(context,netService);
        return netService;
    }
    public static SpectacularsApplication getApplication(){
        return application;
    }
    public  Context getContext(){
        context=application.getApplicationContext();
        return context;
    }

}
