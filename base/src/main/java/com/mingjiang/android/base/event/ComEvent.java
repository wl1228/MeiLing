package com.mingjiang.android.base.event;

/**
 * 备注：用于EventBus数据处理。
 * 作者：wangzs on 2016/2/19 10:05
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class ComEvent {

    /*************************************串口读取数据服务******************************************/
    public static final int ACTION_POST_SCAN = 100;                     //岗位扫描
    public static final int ACTION_USER_SCAN = 101;                     //用户扫描
    public static final int ACTION_ONOFF_FRIDGE_SCAN = 102;            //上下线-冰箱扫描
    public static final int ACTION_MATERIAL_USER_SCAN = 103;           //物料管理-用户登录
    public static final int ACTION_INSTRUCTION_FRIDGE_SCAN = 104;     //岗位指导书-冰箱扫描
    /*************************************串口读取数据服务******************************************/


    /************************************岗位用户扫描（scan）***************************************/
    public static final int ACTION_CHECK_USER = 10;                     //校验用户
    public static final int ACTION_CHECK_USER_ERROR = 11;              //校验用户出错
    /************************************岗位用户扫描（scan）***************************************/


    /**************************************岗位指导书***********************************************/
    public static final int ACTION_LOAD_SERVER = 20;                   //加载服务数据（JSON）
    public static final int ACTION_LOAD_SERVER_ERROR = 21;            //加载服务数据出错
    public static final int ACTION_LOAD_IMAGE = 22;                    //加载操作步骤图片
    public static final int ACTION_LOAD_IMAGE_ERROR = 23;             //加载操作步骤图片出错
    public static final int ACTION_LOAD_WHELL_HIDE = 24;              //加载图片进度框消失
    /**************************************岗位指导书***********************************************/


    /**************************************物料管理*************************************************/
    public static final int ACTION_MIDDLE_LIBARY_QUERY = 60;          //中间库
    public static final int ACTION_AROUND_LIBARY_QUERY = 61;          //线边库
    public static final int ACTION_GET_SESSION = 62;                   //获取Session数据
    public static final int ACTION_ADDLIB_TIP = 63;                    //一键入库
    public static final int ACTION_ADDLIB_SCAN_CODE = 64;             //扫描出库单获取物料信息
    public static final int ACTION_ADDLIB_GET_MATERIALS = 65;        //获取物料信息
    /**************************************物料管理*************************************************/


    /**************************************上下线***************************************************/
    public static final int ACTION_ONOFF_SUCCESS = 70;                //上下线数据提交成功
    public static final int ACTION_ONOFF_FAILUE = 71;                 //上下线数据提交失败
    public static final int ACTION_ONOFF_ERROR_SERIAL_NUM = 72;     //生产流水号错误
    /**************************************上下线***************************************************/



    /**************************************打印二维码***********************************************/
    public final static int ACTION_PRINT = 81;                       //打印操作
    public final static int ACTION_GET_CODE = 82;                   //获取编码
    public final static int ACTION_WAIT = 83;                       //等待
    /**************************************打印二维码***********************************************/


    /**************************************设备监控*************************************************/

    /**************************************设备监控*************************************************/


    /**************************************生产监控*************************************************/

    /**************************************生产监控*************************************************/

    /**************************************质量检验*************************************************/

    /**************************************质量检验*************************************************/

    protected String message;       //传递字符串数据
    protected int actionType;       //事件类型
    private Object objectMsg = null;//传递对象数据

    public ComEvent(Object objectMsg, int actionType){
        this.objectMsg = objectMsg;
        this.actionType = actionType;
    }

    public ComEvent(String message, int actionType) {
        this.message = message;
        this.actionType = actionType;
    }

    public int getActionType() {
        return actionType;
    }

    public String getMessage() {
        return message;
    }

    public Object getObjectMsg(){
        return objectMsg;
    }
}
