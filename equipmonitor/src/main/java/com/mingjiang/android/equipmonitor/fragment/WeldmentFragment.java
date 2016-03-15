package com.mingjiang.android.equipmonitor.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.mingjiang.android.equipmonitor.EquipApp;
import com.mingjiang.android.equipmonitor.R;
import com.mingjiang.android.equipmonitor.adapter.PowerValueAdapter;
import com.mingjiang.android.equipmonitor.adapter.ThickValueAdapter;
import com.mingjiang.android.equipmonitor.adapter.TimeValueAdapter;
import com.mingjiang.android.equipmonitor.entity.WeldPower;
import com.mingjiang.android.equipmonitor.entity.WeldThick;
import com.mingjiang.android.equipmonitor.entity.WeldTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import app.android.mingjiang.com.mpchartapi.BrokeLineChartUtil;
import app.android.mingjiang.com.mpchartapi.PieChartUtil;

/**
 * 超声波焊接机监控数据界面。
 * Created by wangzs on 2015/12/18.
 */
public class WeldmentFragment extends Fragment {

    private View mainView = null;
    private TextView weldThickView = null;
    private TextView weldTimeView = null;
    private TextView weldPowerView = null;
    private TextView deviceNameView = null;
    private TextView postView = null;
    private TextView preStepView = null;
    private TextView afStepView = null;
    private TextView equipStatusView = null;

    //返回上级界面
    private ImageView mImageview;

    //检测信息
    private TextView checkPlanView;
    private TextView checkTimeView;
    private TextView checkContentView;

    //显示点检
    private TextView showCheckView;

    private LineChart thickDataGraph;
    private ListView thickDataList;
    private LineChart timeDataGraph;
    private ListView timeDataList;
    private LineChart powerDataGraph;
    private ListView powerDataList;
    private PieChart timePieChartGrapth;
    private TextClock weldTimeClock;

    private ThickValueAdapter thickValueAdapter = null;
    private TimeValueAdapter timeValueAdapter = null;
    private PowerValueAdapter powerValueAdapter = null;

    private BrokeLineChartUtil thickMJChart = null;
    private BrokeLineChartUtil timeMJChart = null;
    private BrokeLineChartUtil powerMJChart = null;

    private static final String THICK_CONTENT = "焊接厚度";
    private static final String POWER_CONTENT = "焊接能量";
    private static final String TIME_CONTENT = "焊接时间";
    private static final String THICK_DESCRIPT = "焊接厚度变化曲线";
    private static final String POWER_DESCRIPT = "焊接能量变化曲线";
    private static final String TIME_DESCRIPT = "焊接时间变化曲线";

    private static final Float THICK_HEIGH = 0.93f;
    private static final Float THICK_RIGHT = 0.80f;
    private static final Float THICK_LOW = 0.67f;

    private static final Float POWER_HEIGH = 572f;
    private static final Float POWER_RIGHT = 372f;
    private static final Float POWER_LOW = 172f;

    private static final Float TIME_HEIGH = 0.64f;
    private static final Float TIME_RIGHT = 0.34f;
    private static final Float TIME_LOW = 0.04f;

    private ArrayList<String> xList = new ArrayList<String>(Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"));
    private Random random = new Random();

    //动态更新绘图数据。
    private static final String KEY = "KEY";
    private static final String POWER = "power";
    private static final String TIME = "time";
    private static final String THICK = "thick";
    private static final String VALUE = "value";

    private List<String> timeScaleNameList = new ArrayList<>();
    private List<Float> timeScaleValueList = new ArrayList<>();

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle buldler = msg.getData();
            float value = buldler.getFloat(VALUE);
           String key =  buldler.getString(KEY);
            if(key.equals(POWER)){
                int intValue = (int)value;
                Float floatValue = (float)intValue;
                powerMJChart.addEntry("1", floatValue);
                weldPowerView.setText(intValue+"");
            }else if(key.equals(TIME)){
                float fValue = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                timeMJChart.addEntry("1", fValue);
                weldTimeView.setText(fValue + "");
            }else if(key.equals(THICK)){
               float fValue = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                thickMJChart.addEntry("1", fValue);
                weldThickView.setText(fValue+"");
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_weldment, container, false);
        return mainView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView(){

        weldThickView = (TextView)mainView.findViewById(R.id.weld_thick);
        weldTimeView = (TextView)mainView.findViewById(R.id.weld_time);
        weldPowerView = (TextView)mainView.findViewById(R.id.weld_power);

        //显示名字信息
        deviceNameView = (TextView)mainView.findViewById(R.id.device_name);
        postView = (TextView)mainView.findViewById(R.id.post);
        preStepView = (TextView)mainView.findViewById(R.id.pre_step);
        afStepView = (TextView)mainView.findViewById(R.id.af_step);
        equipStatusView = (TextView)mainView.findViewById(R.id.equip_status);

        //显示检测信息
        checkPlanView = (TextView)mainView.findViewById(R.id.check_plan);
        checkTimeView = (TextView)mainView.findViewById(R.id.check_time);
        checkContentView = (TextView)mainView.findViewById(R.id.check_content);

        //显示点检
        showCheckView = (TextView)mainView.findViewById(R.id.show_check);
        showCheckView.setOnClickListener(new ShowCheckListener());
        thickDataGraph = (LineChart)mainView.findViewById(R.id.thickDataGraph);
        thickDataList = (ListView)mainView.findViewById(R.id.thickDataList);

        timeDataGraph = (LineChart)mainView.findViewById(R.id.timeDataGraph);
        timeDataList = (ListView)mainView.findViewById(R.id.timeDataList);

        powerDataGraph = (LineChart)mainView.findViewById(R.id.powerDataGraph);
        powerDataList = (ListView)mainView.findViewById(R.id.powerDataList);

        timePieChartGrapth = (PieChart)mainView.findViewById(R.id.timeScaleGraph);

        weldTimeClock = (TextClock)mainView.findViewById(R.id.weldClock);

        mImageview=(ImageView)mainView.findViewById(R.id.weld_imageveiw);
        mImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        weldTimeClock.setFormat24Hour("yyyy-MM-dd hh:mm:ss, EEEE");

        List<Float> thickList = new ArrayList<Float>();
        thickList.add(THICK_RIGHT);
        List<Float> timeList = new ArrayList<Float>();
        timeList.add(TIME_RIGHT);
        List<Float> powerList = new ArrayList<Float>();
        powerList.add(POWER_RIGHT);

        thickMJChart = new BrokeLineChartUtil(thickDataGraph,THICK_DESCRIPT,THICK_CONTENT,xList,thickList);
        timeMJChart = new BrokeLineChartUtil(timeDataGraph,TIME_DESCRIPT,TIME_CONTENT,xList,timeList);
        powerMJChart = new BrokeLineChartUtil(powerDataGraph,POWER_DESCRIPT,POWER_CONTENT,xList,powerList);
        testTimeScalePie();
    }

    //显示点检信息
    class ShowCheckListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            PointCheckFragment pointCheckFragment = new PointCheckFragment();
            pointCheckFragment.setCancelable(true);
            pointCheckFragment.show(getFragmentManager(), "超声波焊接机点检信息");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dataToGraph();
    }

    /**
     * 根据数据绘制二维图。
     */
    private void dataToGraph(){
        initThickView();
        initTimeView();
        intPowerView();
    }

    //初始化壁厚界面。
    private void initThickView(){

        //展现listview数据
        WeldThick weldThick = EquipApp.weldThick;
        thickValueAdapter = new ThickValueAdapter(weldThick.valueList,this.getActivity());
        thickDataList.setAdapter(thickValueAdapter);
        thickValueAdapter.notifyDataSetChanged();

        //绘制二维图
        testDataThick();
        //LineChartUtil.getInstance().initMChart(thickDataGraph,weldThick.minValue,weldThick.maxValue,weldThick.bestValue, weldThick.valueList);
    }

    //初始化焊接时间界面。
    private void initTimeView(){

        //展现listview数据
        WeldTime weldTime = EquipApp.weldTime;
        timeValueAdapter = new TimeValueAdapter(weldTime.valueList,this.getActivity());
        timeDataList.setAdapter(timeValueAdapter);
        timeValueAdapter.notifyDataSetChanged();

        //绘制二维图
        testDataTime();
        //LineChartUtil.getInstance().addDataSet(timeDataGraph,weldTime.valueList);
    }

    //初始化能量界面。
    private void intPowerView(){

        //展现listview数据
        WeldPower weldPower = EquipApp.weldPower;
        powerValueAdapter = new PowerValueAdapter(weldPower.valueList,this.getActivity());
        powerDataList.setAdapter(powerValueAdapter);
        powerValueAdapter.notifyDataSetChanged();

        //绘制二维图
        testDataPower();
        //LineChartUtil.getInstance().addDataSet(powerDataGraph, weldPower.valueList);
    }

    private void testDataPower(){
        powerMJChart.addXLimitLine(POWER_HEIGH,"焊接能量警戒值",Color.RED);
        powerMJChart.addXLimitLine(POWER_RIGHT, "焊接能量最优值", Color.BLACK);
        powerMJChart.addXLimitLine(POWER_LOW, "焊接能量警戒值", Color.RED);
        final Random radmom = new Random();
        new Thread()
        {
            @Override
            public void run() {

                for(int i=0;;i++) {
                    try {
                        this.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY, POWER);
                    Float value = POWER_RIGHT;
                    if(radmom.nextFloat() > 0.5f){
                        value = POWER_RIGHT + 200*radmom.nextFloat();
                    } else {
                        value = POWER_RIGHT - 200*radmom.nextFloat();
                    }
                    bundle.putFloat(VALUE,value);
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    private void testDataTime(){
        timeMJChart.addXLimitLine(TIME_HEIGH,"焊接时间警戒值",Color.RED);
        timeMJChart.addXLimitLine(TIME_RIGHT,"焊接时间最优值", Color.BLACK);
        timeMJChart.addXLimitLine(TIME_LOW,"焊接时间警戒值",Color.RED);
        final Random radmom = new Random();
        new Thread()
        {
            @Override
            public void run() {

                for(int i=0;;i++) {
                    try {
                        this.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY, TIME);
                    Float value = TIME_RIGHT;
                    if(radmom.nextFloat() > 0.5f){
                        value = TIME_RIGHT + 0.3f * radmom.nextFloat();
                    } else {
                        value = TIME_RIGHT - 0.3f * radmom.nextFloat();
                    }

                    bundle.putFloat(VALUE,value);
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    private void testDataThick(){
        thickMJChart.addXLimitLine(THICK_HEIGH,"焊接壁厚警戒值",Color.RED);
        thickMJChart.addXLimitLine(THICK_RIGHT,"焊接壁厚最优值", Color.BLACK);
        thickMJChart.addXLimitLine(THICK_LOW,"焊接壁厚警戒值",Color.RED);
        final Random radmom = new Random();
        new Thread()
        {
            @Override
            public void run() {

                for(int i=0;;i++) {
                    try {
                        this.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY, THICK);
                    Float value = THICK_RIGHT;
                    if(radmom.nextFloat() > 0.5f){
                        value = THICK_RIGHT + 0.13f * radmom.nextFloat();
                    } else {
                        value = THICK_RIGHT - 0.13f * radmom.nextFloat();
                    }

                    bundle.putFloat(VALUE,value);
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    private void testTimeScalePie()
    {
        timeScaleNameList.add("点检");
        timeScaleNameList.add("检修");
        timeScaleNameList.add("停机");
        timeScaleNameList.add("正常使用");

        timeScaleValueList.add(0.3f);
        timeScaleValueList.add(0.5f);
        timeScaleValueList.add(0f);
        timeScaleValueList.add(99.2f);
        PieChartUtil.createPieChart(timePieChartGrapth, "设备使用率", timeScaleValueList, timeScaleNameList);
    }
}
