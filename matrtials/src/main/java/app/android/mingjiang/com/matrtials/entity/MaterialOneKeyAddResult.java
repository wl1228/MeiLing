package app.android.mingjiang.com.matrtials.entity;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import app.android.mingjiang.com.matrtials.utils.NumberUtils;

/**
 * 备注：物料一键入库。
 * 作者：wangzs on 2016/2/23 09:29
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class MaterialOneKeyAddResult {

    public static final String OUT_CODE = "out_code";//出库单编号

    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String NUMBER = "number";
    public static final String SEAT_ID = "seat_id";

    public String code;                 //物料编码
    public String name;                 //物料名称
    public String number;               //数量
    public String seat_id;              //库位编码

    public MaterialOneKeyAddResult(){

    }

    //将实体数据转换成Map数据。
    public Map<String,String> setData(){

        Map<String,String> retMap = new HashMap<String,String>();

        retMap.put(CODE,this.code);
        retMap.put(NUMBER,this.number);
        retMap.put(SEAT_ID,this.seat_id);
        retMap.put(NAME,this.name);

        return retMap;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
