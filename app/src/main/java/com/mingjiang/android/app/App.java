package com.mingjiang.android.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import app.android.mingjiang.com.logapi.recordlog.log.manager.LogManager;

/**
 * Created by wangzs on 2015/12/16.
 */
public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //日志注册
        LogManager.getManager(getApplicationContext()).registerCrashHandler();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //日志取消注册
        LogManager.getManager(getApplicationContext()).unregisterCrashHandler();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
