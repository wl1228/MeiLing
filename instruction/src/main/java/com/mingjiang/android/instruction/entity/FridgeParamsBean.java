package com.mingjiang.android.instruction.entity;

import com.google.gson.Gson;

/**
 * 由于json格式需要，该类用于嵌套FridgePostBean来发送json数据
 * Created by kouzeping on 2016/2/3.
 * email：kouzeping@shmingjiang.org.cn
 */
public class FridgeParamsBean {
    public FridgePostBean params;

    public FridgePostBean getParams() {
        return params;
    }

    public void setParams(FridgePostBean params) {
        this.params = params;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
