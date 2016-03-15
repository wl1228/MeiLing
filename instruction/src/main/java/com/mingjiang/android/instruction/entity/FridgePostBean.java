package com.mingjiang.android.instruction.entity;

import com.google.gson.Gson;

/**
 * 冰箱与工位信息上传到服务器的bean
 * Created by SXY on 2016/2/2.
 */
public class FridgePostBean {
    public String[] args;
    public FridgeBean kwargs;

    public FridgePostBean(String serial_number, String postCode) {

        this.args = new String[0];
        this.kwargs = new FridgeBean(serial_number, postCode);
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public FridgeBean getKwargs() {
        return kwargs;
    }

    public void setKwargs(FridgeBean kwargs) {
        this.kwargs = kwargs;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
