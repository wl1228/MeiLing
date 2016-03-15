package com.mingjiang.kouzeping.spectaculars.material_monitor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kouzeping on 2016/2/22.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MidMaterialResult {

    public String result_size = ""; //返回记录数
    public List<MidMaterialValue> result = new ArrayList<>();//返回查询记录
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
