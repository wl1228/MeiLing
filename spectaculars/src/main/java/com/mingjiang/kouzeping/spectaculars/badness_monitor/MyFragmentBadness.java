package com.mingjiang.kouzeping.spectaculars.badness_monitor;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.mingjiang.kouzeping.spectaculars.R;
import com.mingjiang.kouzeping.spectaculars.event.Event;
import com.mingjiang.kouzeping.spectaculars.product_monitor.MyLineLogAdapter;
import com.mingjiang.kouzeping.spectaculars.utils.RefreshTimeUtils;
import com.mingjiang.kouzeping.spectaculars.utils.SpectacularsApplication;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * Created by kouzeping on 2016/2/15.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MyFragmentBadness extends Fragment {
    GridView mGridview;
    Context mContext;
    List<BadnessItem> mBadnessItems;
    BadnessAdapter mBadnessAdapter;
    private volatile boolean isRun = true;  //是否结束线程
    volatile int mRefreshtTime = 10000;       //刷新时间
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //getBadnessInfo();
            getTestData();
        }
    };

    public MyFragmentBadness() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment5_badness, null);
        iniViewForGridview(view);
//      getBadnessInfo();
        initViewForAuto();
        getTestData();
        return view;
    }

    private void iniViewForGridview(View view) {
        mGridview = (GridView) view.findViewById(R.id.fragment5_badness_gridview);
        mBadnessItems=new ArrayList<>();
        mBadnessAdapter=new BadnessAdapter(mBadnessItems,mContext);
        mGridview.setAdapter(mBadnessAdapter);
    }

    private void getBadnessInfo() {
        SpectacularsApplication.getNetService(mContext).queryBadness().subscribe(new Action1<List<BadnessItem>>() {
            @Override
            public void call(List<BadnessItem> badnessItems) {
                EventBus.getDefault().post(new Event(badnessItems, Event.BADNESS_INFO));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                EventBus.getDefault().post(new Event("获取产品不良信息失败", Event.BADNESS_INFO_MISS));
            }
        });
    }

    public void getTestData() {
        List<BadnessItem> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            BadnessItem data = new BadnessItem();
            data.setTypename("产品类型" + i);
            data.setBadname1("第一类不良");
            data.setBadvalue1(i + 5);
            data.setBadname2("第二类不良");
            data.setBadvalue2(i + 4);
            data.setBadname3("第三类不良");
            data.setBadvalue3(i + 3);
            data.setBadname4("第四类不良");
            data.setBadvalue4(i + 2);
            data.setBadname5("第五类不良");
            data.setBadvalue5(i + 1);
            data.setBadtotal((i + 5) * 5);
            data.setAlltotal((i + 5) * 8);
            list.add(data);
        }
        mGridview.setAdapter(new BadnessAdapter(list, getActivity()));
    }

    public void onEventMainThread(Event event) {
        if (Event.BADNESS_INFO == event.getActionType()) {
            List<BadnessItem> badnessItems = (List<BadnessItem>) event.getObject();
            if (badnessItems != null) {
                mBadnessItems = badnessItems;
                mBadnessAdapter.setmList(mBadnessItems);
                mBadnessAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(mContext, "没有停线记录", Toast.LENGTH_SHORT).show();
            }
        } else if (Event.BADNESS_INFO_MISS == event.getActionType()) {
            Toast.makeText(mContext, event.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        isRun = false;
    }

    private void initViewForAuto() {
        // 自动更新数据
        MyThread myThread = new MyThread();
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
}
