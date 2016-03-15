package com.mingjiang.kouzeping.spectaculars.material_monitor;

import com.google.gson.Gson;

/**
 * Created by kouzeping on 2016/2/22.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MidMaterialValue {

    public String material_id;      //物料编码
    public String material_name;   //物料名称
    public String seat_id;          //库位编号
    public Boolean safety;          //是否为安全库存
    public String safety_stock;     //安全库存数
    public String number;           //库存数
    public String shortage;         //库存缺量
    public String id;               //ID

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
