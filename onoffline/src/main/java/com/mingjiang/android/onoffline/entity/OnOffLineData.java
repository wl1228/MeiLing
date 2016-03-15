package com.mingjiang.android.onoffline.entity;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 备注：
 * 作者：wangzs on 2016/2/19 14:05
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class OnOffLineData {

    //岗位类型
    public static final String ON_LINE = "online";
    public static final String OFF_LINE = "offline";

    //提交返回值
    public static final String SERIAL_NUMBER_ERROR = "serial number error";
    public static final String RESPONSE_SUCCESS = "success";

    //字段属性
    public static final String SERIAL_NUMBER = "serial_number";
    public static final String OPERATION = "operation";
    public static final String POST_CODE = "post_code";
    public static final String REASON = "reason";

    public String serial_number;   //生产流水号，//冰箱二维码
    public String operation;        //岗位类型(onlinr:上线，offline：下线)
    public String post_code;        //岗位编码//扫描岗位二维码
    public String reason ;          //下线原因	//填写下线原因

    public OnOffLineData(){

    }

    public Map<String,String> setData(){
        Map<String,String> retMap = new HashMap<String,String>();
        retMap.put(SERIAL_NUMBER,serial_number);
        retMap.put(OPERATION,operation);
        retMap.put(POST_CODE,post_code);
        retMap.put(REASON,reason);
        return retMap;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
