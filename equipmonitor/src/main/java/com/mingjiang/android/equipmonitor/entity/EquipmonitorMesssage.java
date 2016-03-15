package com.mingjiang.android.equipmonitor.entity;

/**
 * 设备检测管理信息。
 * Created by wangzs on 2015/12/22.
 */
public class EquipmonitorMesssage {

    /**
     * 产品号，订单号，工序，岗位，物料代码，物料名称，物料规格，供应商，装配工，检测工，质检结果，设备号，设备名称
     */
    public String productNumber;            //产品号
    public String orderNumber;              //订单号
    public String step;                      //工序
    public String post;                      //岗位
    public String materialCode;             //物料代码
    public String materialName;             //物料名称
    public String materialSpecification;   //物料规格
    public String supplier;                 //供应商
    public String fitters;                  //装配工
    public String inspectionStation;       //检测工
    public String QCResults;                //质检结果
    public String deviceNo;                 //设备号
    public String deviceName;               //设备名称
}
