package com.mingjiang.android.equipmonitor.entity;

/**
 * 实时焊接信息。
 * Created by wangzs on 2015/12/21.
 */
public class Weld {

    public static final String WELD_THICK = "焊接壁厚";
    public static final String WELD_TIME = "焊接时间";
    public static final String WELD_POWER = "焊接能量";
    public static final String COUNTER = "计数器";

    public Float weldThick;       //焊接壁厚
    public Float weldTime;        //焊接时间
    public Float weldPower;       //焊接能量
    public String counter;         //计数器

    //实时设置值
    public void setValue(WeldPower weldPower,WeldThick weldThick,WeldTime weldTime){
        weldPower.currentValue = this.weldPower;
        weldPower.addValue(this.weldPower);
        weldThick.currentValue = this.weldThick;
        weldThick.addValue(this.weldThick);
        weldTime.currentValue = this.weldTime;
        weldTime.addValue(this.weldTime);
    }
}
