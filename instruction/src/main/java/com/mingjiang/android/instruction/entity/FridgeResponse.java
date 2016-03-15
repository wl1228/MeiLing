package com.mingjiang.android.instruction.entity;

import com.google.gson.Gson;

/**
 * 向服务器发送冰箱岗位信息后，返回的
 * Created by SXY on 2016/2/2.
 */
public class FridgeResponse {
    public String jsonrpc;
    public int id;
    public FridgeResponseResult result;

    public FridgeResponse() {
    }


    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FridgeResponseResult getResult() {
        return result;
    }

    public void setResult(FridgeResponseResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
