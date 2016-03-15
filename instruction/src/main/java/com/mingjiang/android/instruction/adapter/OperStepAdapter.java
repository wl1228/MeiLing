package com.mingjiang.android.instruction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjiang.android.instruction.dialog.ShowBigPicDialog;
import com.mingjiang.android.instruction.entity.ShowStep;
import com.mingjiang.android.instruction.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作步骤。
 * Created by wangzs on 2015/12/10.
 */
public class OperStepAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater mInflater = null;
    private List<ShowStep> showSteps = new ArrayList<ShowStep>();
    private String basePath = "";

    public OperStepAdapter(Context context,List<ShowStep> showSteps){
        this.context = context;
        this.showSteps = showSteps;
        mInflater = LayoutInflater.from(context);
        this.basePath = basePath;
    }

    @Override
    public int getCount() {
        return this.showSteps.size();
    }

    @Override
    public Object getItem(int position) {
        return this.showSteps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.grid_step_oper_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       ShowStep showStep =  this.showSteps.get(position);
        if(showStep != null)
        {
            holder.title.setText(showStep.name);
            holder.image.setImageBitmap(showStep.bitmap);
            holder.detail.setText(showStep.description);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShowBigPicDialog(context,position,showSteps);
                }
            });
        }

        return convertView;
    }

    static class ViewHolder
    {
        public TextView title;
        public ImageView image;
        public TextView detail;

        public ViewHolder(View view) {
            title = (TextView)view.findViewById(R.id.title);
            image = (ImageView)view.findViewById(R.id.image);
            detail = (TextView)view.findViewById(R.id.detail);
        }
    }
}
