package com.mingjiang.android.equipmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingjiang.android.equipmonitor.R;

/**
 * 灌注机监控数据界面。
 * Created by wangzs on 2015/12/18.
 */
public class PerfusionMachineFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfusion_machine, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    //数据处理
    @Override
    public void onResume() {
        super.onResume();

    }

    //初始化界面组件
    private void initView(){

    }
}
