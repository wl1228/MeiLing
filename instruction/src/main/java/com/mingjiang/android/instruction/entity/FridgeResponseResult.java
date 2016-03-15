package com.mingjiang.android.instruction.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by SXY on 2016/2/2.
 */
public class FridgeResponseResult {
    public List<String> warning;

    public List<String> getWarning() {
        return warning;
    }

    public void setWarning(List<String> warning) {
        this.warning = warning;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
