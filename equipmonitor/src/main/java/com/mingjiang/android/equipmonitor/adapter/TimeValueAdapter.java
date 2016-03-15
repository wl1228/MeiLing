package com.mingjiang.android.equipmonitor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangzs on 2015/12/21.
 */
public class TimeValueAdapter extends BaseAdapter {

    public List<Float> valueList = new LinkedList<Float>();
    public Context context = null;

    public TimeValueAdapter(List<Float> valueList, Context context){
        this.valueList = valueList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return valueList.size();
    }

    @Override
    public Object getItem(int position) {
        return valueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Float value = valueList.get(position);
        TextView valueView = new TextView(context);
        valueView.setText(value+"");
        valueView.setLeft(20);
        valueView.setTop(5);
        return valueView;
    }
}
