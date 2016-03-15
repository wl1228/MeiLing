package app.android.mingjiang.com.matrtials.entity;

import com.google.gson.Gson;

/**
 * 备注：中间库物料查询值。
 * 作者：wangzs on 2016/2/20 09:10
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class MidMaterialValue {

    public static final String SAFETY_STOCK_NAME = "安全库存数";
    public static final String NUMBER_NAME = "库存数";
    public static final String MATERIAL_ID_NAME = "物料编码";
    public static final String SAFETY_NAME = "是否为安全库存";
    public static final String MATERIAL_NAME_NAME = "物料名称";
    public static final String SHORTAGE_NAME = "库存缺量";
    public static final String SEAT_ID_NAME = "库位编号";
    public static final String ID_NAME = "ID";


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
