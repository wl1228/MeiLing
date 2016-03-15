package app.android.mingjiang.com.matrtials.entity;

/**
 * 备注：一键入库返回值操作。
 * 作者：wangzs on 2016/2/23 16:24
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class RetValue {

    public static final String SUCCESS = "success";
    public static final String DEFAULT = "default";

    public static final String PARAM_ERROR = "Parameters Error";
    public static final String MATERIAL_ERROR = "Material Error";
    public static final String ORDER_ERROR = "Material Error";
    public static final String SEAT_ERROR = "Seat Error";
    public static final String CODE_ERROR = "Code Error";

    public static final String PARAM_ERROR_NAME = "参数错误!";
    public static final String MATERIAL_ERROR_NAME = "物料编码错误!";
    public static final String ORDER_ERROR_NAME = "订单编码错误!";
    public static final String SEAT_ERROR_NAME = "库位编码错误!";
    public static final String CODE_ERROR_NAME = "入库单编码错误!";

    public String success = "";
    public String error = "";

}
