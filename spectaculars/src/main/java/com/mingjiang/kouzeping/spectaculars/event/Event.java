package com.mingjiang.kouzeping.spectaculars.event;

import com.mingjiang.kouzeping.spectaculars.material_monitor.MidMaterialValue;

import java.util.List;
/**
 * Created by kouzeping on 2016/2/22.
 * email：kouzeping@shmingjiang.org.cn
 */
public class Event {

    public static final int MATERIAL_INFO = 0;           //获取物料信息
    public static final int MATERIAL_INFO_MISS = 1;           //获取物料信息失败
    public static final int LINELOG_INFO = 2;             //获取停线记录
    public static final int LINELOG_INFO_MISS = 3;          //获取停线记录失败
    public static final int LINELOG_INFO_ITEM=4;           //获取停线记录详细信息
    public static final int LINELOG_INFO_ITEM_MISS=5;       //获取停线记录详细信息失败
    public static final int BADNESS_INFO=6;                 //获取五大不良信息
    public static final int BADNESS_INFO_MISS=7;            //获取五大不良信息失败
    public static final int PLANDATA=8;                 //获取五大不良信息
    public static final int PLANDATA_MISS=9;            //获取五大不良信息失败
    public static final int YIELD_DATE=10;             //日产量
    public static final int YIELD_WEEK=11;            //周产量
    public static final int YIELD_MONTH=12;            //月产量
    public static final int YIELD_MISS=13;            //获取产量失败
    public static final int INDENT_INFO=14;            //月产量
    public static final int INDENT_INFO_MISS=15;            //获取产量失败
    protected String message;
    protected int actionType;
    private Object object;
    List<MidMaterialValue> materialList;

    public Object getObject() {
        return object;
    }
    public Event(Object objet, int actionType) {
        this.object=objet;
        this.actionType = actionType;
    }
    public Event(String message, int actionType) {
        this.message = message;
        this.actionType = actionType;
    }
    public Event(List<MidMaterialValue> materialList, int actionType) {
        this.materialList = materialList;
        this.actionType = actionType;
    }
    public List<MidMaterialValue> getMaterialList() {
        return materialList;
    }
    public int getActionType() {
        return actionType;
    }

    public String getMessage() {
        return message;
    }

}
