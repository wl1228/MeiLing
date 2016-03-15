package com.mingjiang.android.base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络校验。
 * Created by wangzs on 2015/12/14.
 */
public class NetCheckUtils {

    private Context context = null;

    public NetCheckUtils(Context context){
        this.context = context;
    }

    //判断网络是否可用
    public static boolean isNetworkAvaiable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo(); // 获取代表联网状态的NetWorkInfo对象
        return (info != null && info.isConnected());
    }

    //判断WIFI是否可用
    public static boolean isWifiActive(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
        if(info!=null){
            for(int i=0;i<info.length;i++){
                //
                if(info[i].getTypeName().equals(ConnectivityManager.TYPE_WIFI)&& info[i].isConnected())
                    return true;
            }
        }
        return false;
    }
}
