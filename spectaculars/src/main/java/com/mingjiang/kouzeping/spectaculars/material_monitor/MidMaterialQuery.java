package com.mingjiang.kouzeping.spectaculars.material_monitor;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kouzeping on 2016/2/22.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MidMaterialQuery {

    public static final String MATERIAL_ID = "material_id";
    public static final String MATERIAL_NAME = "material_name";
    public static final String SEAT_ID = "seat_id";
    public static final String RESWEVOIR_AREA = "reservoir_area";
    public static final String PAGE_SIZE = "page_size";
    public static final String PAGE_NUMBER = "page_number";

    public String material_id;      //物料编码
    public String material_name;    //物料名称
    public String seat_id;          //库位编号
    public String reservoir_area;   //库区编号
    public String page_size;        //返回记录
    public String page_number;      //页码


    public Map<String,String> getQueryMap(){
        Map<String,String> map=new HashMap<>();
        map.put(MATERIAL_ID,material_id);
        map.put(MATERIAL_NAME,material_name);
        map.put(SEAT_ID,seat_id);
        map.put(RESWEVOIR_AREA,reservoir_area);
        map.put(PAGE_SIZE,page_size);
        map.put(PAGE_NUMBER,page_number);
        return map;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
