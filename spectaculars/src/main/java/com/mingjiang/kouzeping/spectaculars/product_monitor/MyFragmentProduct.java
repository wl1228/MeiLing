package com.mingjiang.kouzeping.spectaculars.product_monitor;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.mingjiang.android.base.util.Config;
import com.mingjiang.kouzeping.spectaculars.R;
import com.mingjiang.kouzeping.spectaculars.badness_monitor.BadnessItem;
import com.mingjiang.kouzeping.spectaculars.event.Event;
import com.mingjiang.kouzeping.spectaculars.material_monitor.MidMaterialValue;
import com.mingjiang.kouzeping.spectaculars.utils.Constant;
import com.mingjiang.kouzeping.spectaculars.utils.RefreshTimeUtils;
import com.mingjiang.kouzeping.spectaculars.utils.SharedPrefsUtil;
import com.mingjiang.kouzeping.spectaculars.utils.SpectacularsApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.android.mingjiang.com.mpchartapi.BarChartUtil;
import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * Created by kouzeping on 2016/2/15.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MyFragmentProduct extends Fragment {

    private static final String DATE_CONTENT = "日产量";
    private static final String WEEK_CONTENT = "周产量";
    private static final String MONTH_CONTENT = "月产量";
    private MJLineChart dateMJChart = null;
    private MJLineChart weekMJChart = null;
    private MJLineChart monthMJChart = null;
    private MyThread myThread;
    private volatile boolean isRun = true;  //是否结束线程
    Context mContext;
    List<PlanItem> mPlanList = new ArrayList<>();
    ListView mListView, mListView2;
    BarChart mLineChart11, mLineChart21, mLineChart31;
    MyLineLogAdapter myLineLogAdapter;
    MyPlanAdapeter mMyPlanAdapeter;
    private ArrayList<String> xDateList = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
    private ArrayList<String> xWeekList = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
    private ArrayList<String> xMonthList = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
    private static List<Float> dateList = new ArrayList<>(Arrays.asList(10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f));
    private static List<Float> weekList = new ArrayList<>(Arrays.asList(10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f));
    private static List<Float> monthList = new ArrayList<>(Arrays.asList(10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f));

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getPlanData();
            getYieldData();
            getLineLog();
        }
    };
    volatile int mRefreshtTime = 10000;
    List<LineLogItem> mLineLogItems;  //停线记录的数据

    public MyFragmentProduct() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3_product, null);
        EventBus.getDefault().register(this);
        mContext = getActivity();
        initViewForFind(view);
        initViewForData();
        initViewForAuto();
        getPlanData();//自动获取有时不及时，所以加载页面时就获取一次
        getYieldData();
        getLineLog();
        initViewForEvent();
        return view;
    }

    private void initViewForData() {
        mPlanList = new ArrayList<>();
        mMyPlanAdapeter = new MyPlanAdapeter(mPlanList, mContext);
        mListView.setAdapter(mMyPlanAdapeter);

        mLineLogItems = new ArrayList<>();
        myLineLogAdapter = new MyLineLogAdapter(mLineLogItems, mContext);
        mListView2.setAdapter(myLineLogAdapter);
    }

    private void initViewForEvent() {
        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LineLogInfoFragment lineLogInfoFragment = new LineLogInfoFragment(mLineLogItems.get(position).getId(), mLineLogItems.get(position).getName());
                lineLogInfoFragment.setCancelable(true);
                lineLogInfoFragment.show(getChildFragmentManager(), mLineLogItems.get(position).getName() + "产线停线信息");
            }
        });
    }

    private void getLineLog() {
        SpectacularsApplication.getNetService(mContext).queryLineLog().subscribe(new Action1<List<LineLogItem>>() {
            @Override
            public void call(List<LineLogItem> lineLog) {
                EventBus.getDefault().post(new Event(lineLog, Event.LINELOG_INFO));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                EventBus.getDefault().post(new Event("获取停线记录失败", Event.LINELOG_INFO_MISS));
            }
        });
    }

    private void getPlanData() {
        SpectacularsApplication.getNetService(mContext).queryPlanData().subscribe(new Action1<List<PlanItem>>() {
            @Override
            public void call(List<PlanItem> planItems) {
                EventBus.getDefault().post(new Event(planItems, Event.PLANDATA));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                EventBus.getDefault().post(new Event(new ArrayList<PlanItem>(), Event.PLANDATA));
//                EventBus.getDefault().post(new Event("获取日计划信息失败", Event.PLANDATA_MISS));
            }
        });
    }

    private void getYieldData() {
        String[] group_by = new String[]{"day", "week", "month"};
        for (int i = 0; i < 3; i++) {
            final int finalI = i;
            Map<String, String> map = new HashMap<>();
            map.put("group_by", group_by[finalI]);
            SpectacularsApplication.getNetService(mContext).queryYieldData(map).subscribe(new Action1<YieldItem>() {
                @Override
                public void call(YieldItem yieldItem) {
                    switch (finalI) {
                        case 0:
                            xDateList = yieldItem.getColumn();
                            dateList=yieldItem.getData();
                            EventBus.getDefault().post(new Event(yieldItem, Event.YIELD_DATE));
                            break;
                        case 1:
                            xWeekList = yieldItem.getColumn();
                            weekList=yieldItem.getData();
                            EventBus.getDefault().post(new Event(yieldItem, Event.YIELD_WEEK));
                            break;
                        case 2:
                            xMonthList = yieldItem.getColumn();
                            monthList=yieldItem.getData();
                            EventBus.getDefault().post(new Event(yieldItem, Event.YIELD_MONTH));
                            break;
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    EventBus.getDefault().post(new Event("获取产量信息失败", Event.YIELD_MISS));
                }
            });
        }
    }
    private void UpdateYield(int flag) {
        switch (flag) {
            case 0:
                if (dateMJChart == null) {
                    dateMJChart = new MJLineChart();
                    BarChartUtil.createBarChart(mLineChart11, DATE_CONTENT, xDateList, dateList);
                } else {
                    BarChartUtil.updateData(mLineChart11, xDateList, DATE_CONTENT, dateList);
                }
                break;
            case 1:
                if (weekMJChart == null) {
                    weekMJChart = new MJLineChart();
                    BarChartUtil.createBarChart(mLineChart21, WEEK_CONTENT, xWeekList, weekList);
                } else {
                    BarChartUtil.updateData(mLineChart21, xWeekList, WEEK_CONTENT, weekList);
                }
                break;
            case 2:
                if (monthMJChart == null) {
                    monthMJChart = new MJLineChart();
                    BarChartUtil.createBarChart(mLineChart31, MONTH_CONTENT, xMonthList, monthList);
                } else {
                    BarChartUtil.updateData(mLineChart31, xMonthList, MONTH_CONTENT, monthList);
                }
                break;
        }
    }

    private void initViewForFind(View view) {
        mListView = (ListView) view.findViewById(R.id.fragment3_listview);
        mListView2 = (ListView) view.findViewById(R.id.fragment3_listview2);
        mLineChart11 = (BarChart) view.findViewById(R.id.fragment3_barchart1);
        mLineChart21 = (BarChart) view.findViewById(R.id.fragment3_barchart2);
        mLineChart31 = (BarChart) view.findViewById(R.id.fragment3_barchart3);
    }

    private void initViewForAuto() {
        // 自动更新数据
        myThread = new MyThread();
        myThread.start();
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (isRun) {
                mRefreshtTime = RefreshTimeUtils.getRefreshTime(mContext);
                SystemClock.sleep(mRefreshtTime);
                handler.sendEmptyMessage(0);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRun = false;
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(Event event) {
        int actionType = event.getActionType();
        switch (actionType) {
            case Event.LINELOG_INFO:
                List<LineLogItem> lineLogItems = (List<LineLogItem>) event.getObject();
                if (lineLogItems != null) {
                    mLineLogItems = lineLogItems;
                    myLineLogAdapter.setmList(mLineLogItems);
                    myLineLogAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "没有停线记录", Toast.LENGTH_SHORT).show();
                }
                break;
            case Event.LINELOG_INFO_MISS:
                Toast.makeText(mContext, event.getMessage(), Toast.LENGTH_SHORT).show();
                break;
            case Event.PLANDATA:
//               mPlanList = (List<PlanItem>) event.getObject();
                mPlanList.clear();
                for (int i = 0; i < 10; i++) {
                    PlanItem planItem = new PlanItem();
                    planItem.setId("ID" + i);
                    planItem.setOrder_id("订单ID" + i);
                    planItem.setPlan_quantity(i + 5);
                    planItem.setSection_gd0010(i + 2);
                    planItem.setSection_gd0020(i + 1);
                    planItem.setStore_quantity(i);
                    mPlanList.add(planItem);
                }
                if (mPlanList != null) {
                    mMyPlanAdapeter.setmList(mPlanList);
                    mMyPlanAdapeter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "没有日计划信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case Event.PLANDATA_MISS:
                Toast.makeText(mContext, event.getMessage(), Toast.LENGTH_SHORT).show();
                break;
            case Event.YIELD_DATE:      //更新日产量
                UpdateYield(0);
                break;
            case Event.YIELD_WEEK:      //更新月产量
                UpdateYield(1);
                break;
            case Event.YIELD_MONTH:     //更新周产量
                UpdateYield(2);
                break;
            case Event.YIELD_MISS:      //获取产量失败
                Toast.makeText(mContext, event.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
