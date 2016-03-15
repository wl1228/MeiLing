package com.mingjiang.kouzeping.spectaculars.product_monitor;

import java.util.ArrayList;

/**
 * Created by kouzeping on 2016/2/3.
 * email：kouzeping@shmingjiang.org.cn
 */
public class YieldItem {
    ArrayList<String> column;
    ArrayList<Float> data;

    public ArrayList<String> getColumn() {
        return column;
    }

    public void setColumn(ArrayList<String> column) {
        this.column = column;
    }

    public ArrayList<Float> getData() {
        return data;
    }

    public void setData(ArrayList<Float> data) {
        this.data = data;
    }
}
