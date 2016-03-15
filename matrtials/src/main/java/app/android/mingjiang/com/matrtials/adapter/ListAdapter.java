package app.android.mingjiang.com.matrtials.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.android.mingjiang.com.matrtials.R;
import app.android.mingjiang.com.matrtials.entity.Material;

/**
 * Created by SunYi on 2015/12/23/0023.
 */
public class ListAdapter extends ArrayAdapter {
    private ArrayList<Material> materials;
    private int resourceId;
    TextView quantity;

    public ListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        resourceId = resource;
        materials = (ArrayList<Material>) objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Material m = materials.get(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        } else {
            view = convertView;
        }

        TextView name = (TextView) view.findViewById(R.id.list_item_name);
        name.setText(m.getName());

        quantity = (TextView) view.findViewById(R.id.list_item_quantity);
        Button add = (Button) view.findViewById(R.id.list_item_add);
        Button sub = (Button) view.findViewById(R.id.list_item_sub);

        //设置初始的需求值，需要补货的物料默认设置为1
        m.autoNeed();
        quantity.setText(String.valueOf(m.getNeed()));

        //增加货物需求值
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m.addNeed();
                notifyDataSetChanged();
            }
        });

        //减少
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m.subNeed();
                notifyDataSetChanged();
            }
        });
        return view;

    }


}
