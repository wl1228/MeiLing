package com.mingjiang.android.onoffline;

import android.app.Application;
import android.content.Context;

import com.mingjiang.android.base.util.Config;
import com.mingjiang.android.onoffline.client.NetService;
import com.mingjiang.android.onoffline.entity.OnOffLineData;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;

/**
 * 备注：
 * 作者：wangzs on 2016/2/19 09:40
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class OnOffLineApp extends Application {
    private static OnOffLineApp app = null;

    private static NetService netService;

    public static String postCode = "";
    public static String sessionId = "";
    public static String fridgeCode = "";
    public static Boolean isFridgeScan = Boolean.FALSE;//电冰箱扫描是否成功

    //记录电冰箱上线/下线信息
    public static OnOffLineData onOffLineData = new OnOffLineData();

    public static OnOffLineApp getApp(){
        if(app == null){
            app = new OnOffLineApp();
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
}
