package app.android.mingjiang.com.matrtials.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import app.android.mingjiang.com.matrtials.R;
import app.android.mingjiang.com.matrtials.activity.MatrtialsShowActivity;
import app.android.mingjiang.com.matrtials.adapter.ListAdapter;
import app.android.mingjiang.com.matrtials.entity.Material;


public class GetMoreFragment extends DialogFragment {

    private ArrayList<Material> materials;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        materials = ((MatrtialsShowActivity) getActivity()).getMaterials();
        View view = inflater.inflate(R.layout.fragment_get_more, container);

        //货物是个listVIew列表
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        ListAdapter itemAdapter = new ListAdapter(view.getContext(), R.layout.list_item, materials);
        listView.setAdapter(itemAdapter);

        Button confirm = (Button) view.findViewById(R.id.confirm);
        Button cancel = (Button) view.findViewById(R.id.cancel);

        //确定取货
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //货物到达，目前不知道何时增加货物，现在视为请求就加货物
                for (Material m : materials) {
                    m.arrivalOfGoods(100);
                }
                ((MatrtialsShowActivity) getActivity()).notifyDataSetChanged();
                onStop();
            }
        });

        //取消取货
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStop();
            }
        });
        return view;
    }

    @Override
    public void onStop() {
        Log.e("materials", materials.toString());
        super.onStop();
        //设置需求值为零
        for (Material m :materials) {
            m.setNeed(0);
        }

    }
}
