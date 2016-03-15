package com.mingjiang.android.instruction.entity;

import com.google.gson.Gson;

/**
 * Created by kouzeping on 2016/2/3.
 * email：kouzeping@shmingjiang.org.cn
 */
public class FridgeTraceBean {
    public String operation;
    public String post_code;  //岗位编号

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
