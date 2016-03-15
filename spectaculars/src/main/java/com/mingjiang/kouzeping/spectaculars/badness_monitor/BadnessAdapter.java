package com.mingjiang.kouzeping.spectaculars.badness_monitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.mingjiang.kouzeping.spectaculars.R;
import com.mingjiang.kouzeping.spectaculars.device_monitor.DeviceData;

import java.util.ArrayList;
import java.util.List;

import app.android.mingjiang.com.mpchartapi.PieChartUtil;


/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class BadnessAdapter extends BaseAdapter {
    Context mContext;
    List<BadnessItem> mList;

    public BadnessAdapter( List<BadnessItem> mList,Context mContext) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setmList(List<BadnessItem> mList) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment5_badness_item, null);
            viewHolder.textViewType = (TextView) convertView.findViewById(R.id.fragment5_badness_producttype);
            viewHolder.textViewTotal = (TextView) convertView.findViewById(R.id.fragment5_badness_alltotal);
            viewHolder.textViewFpy = (TextView) convertView.findViewById(R.id.fragment5_badness_fpy);
            viewHolder.pieChart = (PieChart) convertView.findViewById(R.id.fragment5_badness_chart);

            viewHolder.textViewBadName1 = (TextView) convertView.findViewById(R.id.fragment5_badness_badname1);
            viewHolder.textViewBadValve1 = (TextView) convertView.findViewById(R.id.fragment5_badness_badvalue1);
            viewHolder.textViewTotalScale1 = (TextView) convertView.findViewById(R.id.fragment5_badness_totalscale1);
            viewHolder.textViewBadScale1 = (TextView) convertView.findViewById(R.id.fragment5_badness_badscale1);

            viewHolder.textViewBadName2 = (TextView) convertView.findViewById(R.id.fragment5_badness_badname2);
            viewHolder.textViewBadValve2 = (TextView) convertView.findViewById(R.id.fragment5_badness_badvalue2);
            viewHolder.textViewTotalScale2 = (TextView) convertView.findViewById(R.id.fragment5_badness_totalscale2);
            viewHolder.textViewBadScale2 = (TextView) convertView.findViewById(R.id.fragment5_badness_badscale2);

            viewHolder.textViewBadName3 = (TextView) convertView.findViewById(R.id.fragment5_badness_badname3);
            viewHolder.textViewBadValve3 = (TextView) convertView.findViewById(R.id.fragment5_badness_badvalue3);
            viewHolder.textViewTotalScale3 = (TextView) convertView.findViewById(R.id.fragment5_badness_totalscale3);
            viewHolder.textViewBadScale3 = (TextView) convertView.findViewById(R.id.fragment5_badness_badscale3);

            viewHolder.textViewBadName4 = (TextView) convertView.findViewById(R.id.fragment5_badness_badname4);
            viewHolder.textViewBadValve4 = (TextView) convertView.findViewById(R.id.fragment5_badness_badvalue4);
            viewHolder.textViewTotalScale4 = (TextView) convertView.findViewById(R.id.fragment5_badness_totalscale4);
            viewHolder.textViewBadScale4 = (TextView) convertView.findViewById(R.id.fragment5_badness_badscale4);

            viewHolder.textViewBadName5 = (TextView) convertView.findViewById(R.id.fragment5_badness_badname5);
            viewHolder.textViewBadValve5 = (TextView) convertView.findViewById(R.id.fragment5_badness_badvalue5);
            viewHolder.textViewTotalScale5 = (TextView) convertView.findViewById(R.id.fragment5_badness_totalscale5);
            viewHolder.textViewBadScale5 = (TextView) convertView.findViewById(R.id.fragment5_badness_badscale5);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BadnessItem item = mList.get(position);

        viewHolder.textViewType.setText(item.getTypename());
        viewHolder.textViewTotal.setText(item.getAlltotal() + "件");
        viewHolder.textViewFpy.setText(item.getFpy());

        viewHolder.textViewBadName1.setText(item.getBadname1());
        viewHolder.textViewBadValve1.setText(item.getBadvalue1() + "件");
        viewHolder.textViewTotalScale1.setText((item.getBadvalue1() * 100 / item.getAlltotal()) + "%");
        viewHolder.textViewBadScale1.setText((item.getBadvalue1() * 100 / item.getBadtotal()) + "%");

        viewHolder.textViewBadName2.setText(item.getBadname2());
        viewHolder.textViewBadValve2.setText(item.getBadvalue2() + "件");
        viewHolder.textViewTotalScale2.setText((item.getBadvalue2() * 100 / item.getAlltotal()) + "%");
        viewHolder.textViewBadScale2.setText((item.getBadvalue2() * 100 / item.getBadtotal()) + "%");

        viewHolder.textViewBadName3.setText(item.getBadname3());
        viewHolder.textViewBadValve3.setText(item.getBadvalue3() + "件");
        viewHolder.textViewTotalScale3.setText((item.getBadvalue3() * 100 / item.getAlltotal()) + "%");
        viewHolder.textViewBadScale3.setText((item.getBadvalue3() * 100 / item.getBadtotal()) + "%");

        viewHolder.textViewBadName4.setText(item.getBadname4());
        viewHolder.textViewBadValve4.setText(item.getBadvalue4() + "件");
        viewHolder.textViewTotalScale4.setText((item.getBadvalue4() * 100 / item.getAlltotal()) + "%");
        viewHolder.textViewBadScale4.setText((item.getBadvalue4() * 100 / item.getBadtotal()) + "%");

        viewHolder.textViewBadName5.setText(item.getBadname5());
        viewHolder.textViewBadValve5.setText(item.getBadvalue5() + "件");
        viewHolder.textViewTotalScale5.setText((item.getBadvalue5() * 100 / item.getAlltotal()) + "%");
        viewHolder.textViewBadScale5.setText((item.getBadvalue5() * 100 / item.getBadtotal()) + "%");
//
        List<Float> pieYValue = new ArrayList<>();
        pieYValue.add(0f + item.getBadvalue1());
        pieYValue.add(0f + item.getBadvalue2());
        pieYValue.add(0f + item.getBadvalue3());
        pieYValue.add(0f + item.getBadvalue4());
        pieYValue.add(0f + item.getBadvalue5());
        float other = 0f + item.getBadtotal() - item.getBadvalue1() - item.getBadvalue2() - item.getBadvalue3() - item.getBadvalue4() - item.getBadvalue5();
        if (other < 0) {
            other = 0f;
        }
        pieYValue.add(other);
        List<String> pieXValue = new ArrayList<>();
        pieXValue.add(item.getBadname1());
        pieXValue.add(item.getBadname2());
        pieXValue.add(item.getBadname3());
        pieXValue.add(item.getBadname4());
        pieXValue.add(item.getBadname5());
        pieXValue.add("其他不良");
        PieChartUtil.createPieChart(viewHolder.pieChart, "不良比例", pieYValue, pieXValue);

        PieChartUtil.createPieChart(viewHolder.pieChart, "不良比例", pieYValue, pieXValue);

        return convertView;
    }

    private class ViewHolder {
        TextView textViewType, textViewTotal, textViewFpy,
                textViewBadName1, textViewBadValve1, textViewTotalScale1, textViewBadScale1,
                textViewBadName2, textViewBadValve2, textViewTotalScale2, textViewBadScale2,
                textViewBadName3, textViewBadValve3, textViewTotalScale3, textViewBadScale3,
                textViewBadName4, textViewBadValve4, textViewTotalScale4, textViewBadScale4,
                textViewBadName5, textViewBadValve5, textViewTotalScale5, textViewBadScale5;
        private PieChart pieChart;
    }
}
