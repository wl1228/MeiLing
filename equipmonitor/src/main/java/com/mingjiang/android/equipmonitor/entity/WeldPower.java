package com.mingjiang.android.equipmonitor.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * 焊接能量。
 * Created by wangzs on 2015/12/21.
 */
public class WeldPower {

    //实时显示数据条数
    public static final int SIZE = 50;
    //插入位置
    public static final int INSERT_POSITION = 0;

    //当前能量
    public Float currentValue;

    //最大值
    public Float maxValue;

    //最小值
    public Float minValue;

    //最适值
    public Float bestValue;

    //能量列表
    public List<Float> valueList = new LinkedList<Float>();

    //添加值
    public void addValue(Float value){

        valueList.add(INSERT_POSITION, value);

        if(valueList.size() == SIZE){
            valueList.remove(SIZE-1);
        }

    }
}
