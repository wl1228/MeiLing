package com.mingjiang.kouzeping.spectaculars.product_monitor;

import android.content.Context;
import android.graphics.Color;
import android.media.tv.TvContract;
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
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MyPlanAdapeter extends BaseAdapter {
    List<PlanItem> mList;
    Context mContext;

    public MyPlanAdapeter(List<PlanItem> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setmList(List<PlanItem> mList) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.fragment3_product_item,null);
            viewHolder=new ViewHolder();
            viewHolder.textView1=(TextView)convertView.findViewById(R.id.fragment3_item_textview1);
            viewHolder.textView2=(TextView)convertView.findViewById(R.id.fragment3_item_textview2);
            viewHolder.textView3=(TextView)convertView.findViewById(R.id.fragment3_item_textview3);
            viewHolder.textView4=(TextView)convertView.findViewById(R.id.fragment3_item_textview4);
            viewHolder.textView5=(TextView)convertView.findViewById(R.id.fragment3_item_textview5);
            viewHolder.textView6=(TextView)convertView.findViewById(R.id.fragment3_item_textview6);
            viewHolder.progressBar=(ProgressBar)convertView.findViewById(R.id.fragment3_item_progressBar);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        if (position%2==0){
            convertView.setBackgroundResource(R.color.gray);
        }else {
            convertView.setBackgroundResource(R.color.white);
        }
        // [{"section_gd0020": 0, "section_gd0010": 2, "order_id": "11111111", "id": 6, "plan_quantity": 110, "store_quantity": 0}]
        PlanItem planDetail=mList.get(position);
        viewHolder.textView1.setText(planDetail.getOrder_id());
        viewHolder.textView2.setText(""+planDetail.getPlan_quantity());
        viewHolder.textView3.setText(""+planDetail.getSection_gd0010());
        viewHolder.textView4.setText(""+planDetail.getSection_gd0020());
        viewHolder.textView5.setText(""+planDetail.getStore_quantity());
        double percentage=planDetail.getStore_quantity()*100/(double)planDetail.getPlan_quantity();
        DecimalFormat df   = new DecimalFormat("######0.00");
        viewHolder.textView6.setText("" + df.format(percentage) + "%");
        viewHolder.progressBar.setMax(planDetail.getPlan_quantity());
        viewHolder.progressBar.setProgress(planDetail.getStore_quantity());
        viewHolder.progressBar.setSecondaryProgress(planDetail.getSection_gd0010());

        return convertView;
    }
    private class ViewHolder{
        TextView textView1,textView2,textView3,textView4,textView5,textView6;
        ProgressBar progressBar;
    }
}
