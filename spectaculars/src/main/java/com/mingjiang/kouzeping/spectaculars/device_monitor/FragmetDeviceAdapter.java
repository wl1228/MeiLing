package com.mingjiang.kouzeping.spectaculars.device_monitor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjiang.android.equipmonitor.activity.EquipMonitorMainActivity;
import com.mingjiang.kouzeping.spectaculars.R;

import java.util.List;


/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class FragmetDeviceAdapter extends BaseAdapter {
    Context mContext;
    List<DeviceData> mList;

    public FragmetDeviceAdapter(Context mContext, List<DeviceData> mList) {
        this.mContext = mContext;
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
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.fragment1_device_item,null);
            viewHolder.textView1=(TextView)convertView.findViewById(R.id.fragment1_item_textview1);
            viewHolder.textView2=(TextView)convertView.findViewById(R.id.fragment1_item_textview2);
            viewHolder.button=(Button)convertView.findViewById(R.id.fragment1_item_buttton);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.textView1.setText(mList.get(position).getName());
        viewHolder.textView2.setText(mList.get(position).getCode());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,EquipMonitorMainActivity.class);
                mContext.startActivity(intent);
                Toast.makeText(mContext, "跳转到详细界面", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
    private class ViewHolder{
        TextView textView1,textView2;
        Button button;
    }
}
