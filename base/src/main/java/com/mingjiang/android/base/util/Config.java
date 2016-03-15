package com.mingjiang.android.base.util;

import android.content.Context;

/**
 * 备注：配置文件。
 * 作者：wangzs on 2016/2/20 10:43
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class Config {

    public final static String BASE_URL = "http://192.168.0.143:9007/";
    public final static String BASE_URL_NAME = "BASE_URL";
    public final static String HTTP = "http://";
    private static String baseUrl = BASE_URL;

    public static void setBaseUrl(Context context,String url){
        if(!url.startsWith(HTTP)) {
            baseUrl = HTTP + url;
        }else{
            baseUrl = url;
        }
        SharedPrefsUtil.setValue(context,BASE_URL_NAME,baseUrl);
    }
    public static String getBaseUrl(Context context){
        baseUrl = SharedPrefsUtil.getString(context,BASE_URL_NAME);
        if(null == baseUrl || "".equals(baseUrl)){
            baseUrl = BASE_URL;
        }
        return baseUrl;
    }
}
