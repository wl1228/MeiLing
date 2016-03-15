package com.mingjiang.kouzeping.spectaculars.product_monitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjiang.kouzeping.spectaculars.R;

import java.util.List;

/**
 * Created by kouzeping on 2016/2/25.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MyLineLogInfoAdapter extends BaseAdapter {
    List<LineLogInfoItem> mList;
    Context mContext;

    public MyLineLogInfoAdapter(List<LineLogInfoItem> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setmList(List<LineLogInfoItem> mList) {
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
        ViewHoder viewHoder=null;
        if (convertView==null){
            viewHoder=new ViewHoder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.fragment3_lineloginfo_item,null);
            viewHoder.textView1= (TextView) convertView.findViewById(R.id.fragment3_lineloginfo_starttime);
            viewHoder.textView2= (TextView) convertView.findViewById(R.id.fragment3_lineloginfo_endtime);
            viewHoder.textView3= (TextView) convertView.findViewById(R.id.fragment3_lineloginfo_usercode);
            viewHoder.textView4= (TextView) convertView.findViewById(R.id.fragment3_lineloginfo_username);
            viewHoder.textView5= (TextView) convertView.findViewById(R.id.fragment3_lineloginfo_note);
            convertView.setTag(viewHoder);
        }else {
            viewHoder= (ViewHoder) convertView.getTag();
        }
        if (position%2==0){
            convertView.setBackgroundResource(R.color.gray);
        }else {
            convertView.setBackgroundResource(R.color.white);
        }
        LineLogInfoItem item=mList.get(position);
        viewHoder.textView1.setText(item.getOperDate());
        viewHoder.textView2.setText(item.getEndDatetime());
        viewHoder.textView3.setText(item.getUser_id());
        viewHoder.textView4.setText(item.getUser_name());
        viewHoder.textView5.setText(item.getNotes());
        return convertView;
    }
    private class ViewHoder{
        TextView textView1,textView2,textView3,textView4,textView5;
    }
}
