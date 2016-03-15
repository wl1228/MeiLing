package com.mingjiang.android.equipmonitor.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mingjiang.android.equipmonitor.EquipApp;
import com.mingjiang.android.equipmonitor.R;
import com.mingjiang.android.equipmonitor.fragment.PerfusionMachineFragment;
import com.mingjiang.android.equipmonitor.fragment.WeldmentFragment;
import com.prosysopc.ua.client.MonitoredDataItem;

import org.opcfoundation.ua.builtintypes.DataValue;

import app.android.mingjiang.com.opcuaapi.single.SingleDataInterface;

/**
 * 设备监控主界面。
 * Created by wangzs on 2015/12/18.
 */
public class EquipMonitorMainActivity extends Activity implements SingleDataInterface {

    private static final int WELD_FRAGMENT_LOCATION = 0;
    private static final int PERFUSION_FRAGMENT_LOCATION = 1;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private WeldmentFragment weldmentFragment = null;
    private PerfusionMachineFragment perfusionMachineFragment = null;

    private TextView perfusionMachineView;
    private TextView weldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipmonitor);

        //启动异步任务
        //SingleConnectAsyncTask task = new SingleConnectAsyncTask(this);
        //task.execute(Config.serverURL, Config.nodeId + "", Config.nodeName);
        fragmentManager = getFragmentManager();
        initView();
    }

    private void initView(){
        perfusionMachineView = (TextView)findViewById(R.id.perfusion_machine);
        weldView = (TextView)findViewById(R.id.weld);

        perfusionMachineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                //先隐藏掉所有Fragment
                hideAllFragments();
                if(perfusionMachineFragment == null){
                    perfusionMachineFragment = new PerfusionMachineFragment();
                    fragmentTransaction.add(R.id.showFrgment,perfusionMachineFragment);
                    fragmentTransaction.show(perfusionMachineFragment);
                }else{
                    fragmentTransaction.show(perfusionMachineFragment);
                }
                fragmentTransaction.commit();
            }
        });

        weldView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                //先隐藏掉所有Fragment
                hideAllFragments();
                if(weldmentFragment == null){
                    weldmentFragment = new WeldmentFragment();
                    fragmentTransaction.add(R.id.showFrgment,weldmentFragment);
                    fragmentTransaction.show(weldmentFragment);
                }else{
                    fragmentTransaction.show(weldmentFragment);
                }
                fragmentTransaction.commit();
            }
        });

        fragmentTransaction = fragmentManager.beginTransaction();
        //先隐藏掉所有Fragment
        hideAllFragments();
        if(weldmentFragment == null){
            weldmentFragment = new WeldmentFragment();
            fragmentTransaction.add(R.id.showFrgment,weldmentFragment);
            fragmentTransaction.show(weldmentFragment);
        }else{
            fragmentTransaction.show(weldmentFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_equipmonitor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        fragmentTransaction = fragmentManager.beginTransaction();
        //先隐藏掉所有Fragment
        hideAllFragments();
        int order = item.getOrder();
        //再选择要显示的Fragment
        switch (order) {
            case WELD_FRAGMENT_LOCATION:
                if(weldmentFragment == null){
                    weldmentFragment = new WeldmentFragment();
                    fragmentTransaction.add(R.id.showFrgment,weldmentFragment);
                }else{
                    fragmentTransaction.show(weldmentFragment);
                }
                break;
            case PERFUSION_FRAGMENT_LOCATION:
                if(perfusionMachineFragment == null){
                    perfusionMachineFragment = new PerfusionMachineFragment();
                    fragmentTransaction.add(R.id.showFrgment,perfusionMachineFragment);
                }else{
                    fragmentTransaction.show(perfusionMachineFragment);
                }
                break;
        }
        fragmentTransaction.commit();
        return super.onOptionsItemSelected(item);
    }

    /**
     * 隐藏所有fragment。
     */
    private void hideAllFragments() {
        if (weldmentFragment != null) {
            fragmentTransaction.hide(weldmentFragment);
        }
        if (perfusionMachineFragment != null) {
            fragmentTransaction.hide(perfusionMachineFragment);
        }
    }

    /**
     * 数据更新调用方法。
     * @param monitoredDataItem
     * @param dataValue
     * @param dataValue1
     */
    @Override
    public void dealDataValue(MonitoredDataItem monitoredDataItem, DataValue dataValue, DataValue dataValue1) {
        String values = dataValue.getValue().toString();
        String[] valueArr = values.split(",");

        Float powerValue = Float.valueOf(valueArr[0]);
        Float timeValue = Float.valueOf(valueArr[1]);
        Float thickValue = Float.valueOf(valueArr[2]);

        EquipApp.weldPower.currentValue = powerValue;
        EquipApp.weldPower.addValue(powerValue);

        EquipApp.weldThick.currentValue = thickValue;
        EquipApp.weldThick.addValue(thickValue);

        EquipApp.weldTime.currentValue = timeValue;
        EquipApp.weldTime.addValue(timeValue);
    }
}
