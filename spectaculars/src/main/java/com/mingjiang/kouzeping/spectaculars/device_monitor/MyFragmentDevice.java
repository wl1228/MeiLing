package com.mingjiang.kouzeping.spectaculars.device_monitor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.mingjiang.kouzeping.spectaculars.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kouzeping on 2016/2/15.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MyFragmentDevice extends Fragment {
    public MyFragmentDevice() {
        // TODO Auto-generated constructor stub
    }

    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_device, null);
        GridView mGridView= (GridView) view.findViewById(R.id.fragment1_gridview);
        List<DeviceData> list=new ArrayList<>();
        for (int i=0;i<30;i++){
            DeviceData data=new DeviceData();
            data.setName("设备名：超声波焊接机"+i);
            data.setCode("设备号："+i);
            list.add(data);
        }
        mGridView.setAdapter(new FragmetDeviceAdapter(getActivity(),list));
        return view;
    }
}
