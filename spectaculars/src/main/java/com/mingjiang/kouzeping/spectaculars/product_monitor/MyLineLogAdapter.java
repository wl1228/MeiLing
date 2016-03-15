package com.mingjiang.kouzeping.spectaculars.product_monitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mingjiang.kouzeping.spectaculars.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by kouzeping on 2016/2/24.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MyLineLogAdapter extends BaseAdapter {
    List<LineLogItem> mList;
    Context mContext;

    public MyLineLogAdapter(List<LineLogItem> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setmList(List<LineLogItem> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.fragment3_product_item2,null);
            viewHolder=new ViewHolder();
            viewHolder.textView1=(TextView)convertView.findViewById(R.id.fragment3_item2_textview1);
            viewHolder.textView2=(TextView)convertView.findViewById(R.id.fragment3_item2_textview2);
            viewHolder.textView3=(TextView)convertView.findViewById(R.id.fragment3_item2_textview3);
            viewHolder.textView4=(TextView)convertView.findViewById(R.id.fragment3_item2_textview4);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        LineLogItem lineLogItem=mList.get(position);
        if (position%2==0){
            convertView.setBackgroundResource(R.color.gray);
        }else {
            convertView.setBackgroundResource(R.color.white);
        }
        viewHolder.textView1.setText(lineLogItem.getCode());
        viewHolder.textView2.setText(lineLogItem.getName());
        int hour=lineLogItem.getRuntime()/3600;
        int minute=(lineLogItem.getRuntime()%3600)/60;
        String runtime="";
        if (hour>0){
            runtime=runtime+hour+"小时";
        }
        if (minute>0){
            runtime=runtime+minute+"分钟";
        }
        viewHolder.textView3.setText(runtime);
        if ("0".equals(lineLogItem.getLineState())){
            viewHolder.textView4.setText("运行");
        }else if ("1".equals(lineLogItem.getLineState())){
            viewHolder.textView4.setText("停线");
            convertView.setBackgroundResource(R.color.red);
        }
        return convertView;
    }
    private class ViewHolder{
        TextView textView1,textView2,textView3,textView4;
    }
}
