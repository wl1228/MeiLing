package app.android.mingjiang.com.matrtials.entity;

import com.google.gson.Gson;

/**
 * 备注：线边库物料查询值。
 * 作者：wangzs on 2016/2/20 09:11
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class AroundMaterialValue {

    public static final String SAFETY_STOCK_NAME = "安全库存数";
    public static final String AREA_ID_NAME = "下料点编号";
    public static final String NUMBER_NAME = "库存数";
    public static final String NATERIAL_ID_NAME = "物料编码";
    public static final String SAFETY_ANEM = "是否为安全库存";
    public static final String NAMTERIAL_NAME_NAME = "物料名称";
    public static final String SHORTAGE_NAME ="库存缺量";
    public static final String EXPENDS_NAME = "单位时间消耗量";
    public static final String ID_NAME = "ID";

    public String material_id;      //物料编码
    public String material_name;    //物料名称
    public String area_id;          //下料点编号
    public Boolean safety;          //是否为安全库存
    public String safety_stock;     //安全库存数
    public String number;           //库存数
    public String shortage;         //库存缺量
    public String expends;          //单位时间消耗量
    public String id;               //ID

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
