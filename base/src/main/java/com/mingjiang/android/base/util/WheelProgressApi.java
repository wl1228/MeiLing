package com.mingjiang.android.base.util;

import android.content.Context;

import app.android.mingjiang.com.progresswheel.LoadingDialog;

/**
 * 环线进度框接口。
 * 作者：wangzs on 2016/1/15 13:39
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class WheelProgressApi {

    private WheelProgressApi(){
        throw new UnsupportedOperationException("不能实例化");
    }

    public static void showProgress(Context context,String showMessage,boolean cancelable){
        LoadingDialog.showDialog(context, showMessage, cancelable);
    }

    public static void closeProgress(Context context){
        LoadingDialog.closeCommentDialog(context);
    }
}
