package com.mingjiang.kouzeping.spectaculars.indent_monitor;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjiang.android.base.util.Config;
import com.mingjiang.kouzeping.spectaculars.R;
import com.mingjiang.kouzeping.spectaculars.badness_monitor.BadnessItem;
import com.mingjiang.kouzeping.spectaculars.event.Event;
import com.mingjiang.kouzeping.spectaculars.utils.Constant;
import com.mingjiang.kouzeping.spectaculars.utils.RefreshTimeUtils;
import com.mingjiang.kouzeping.spectaculars.utils.SharedPrefsUtil;
import com.mingjiang.kouzeping.spectaculars.utils.SpectacularsApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * Created by kouzeping on 2016/2/15.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MyFragmentIndent extends Fragment {
    List<EditText> mList = new ArrayList<>();
    List<IndentItem> mIndentList =new ArrayList<>();
    TextView mTextview1;
    DatePicker mDatePicker1, mDatePicker2;
    CheckBox mCheckBox1, mCheckBox2;
    RadioGroup mRadioGroup;
    RadioButton mRadioButton1,mRadioButton2;
    Button mButton1, mButton2, mButton3;
    String[] mFiltrateItem=new String[]{"工艺流程","产品ID","产品批次号","订单号","检测记录号","维修记录号"};
    String[] mQueryDataItem=new String[]{"gy_flow_name","product_id","batch_id","order_id","detection_id","maintain_id"};
    ListView mListView;
    MyIndentAdapeter mMyIndentAdapter;
    Map<String,String> mQueryMap=new HashMap<>();
    Context mContext;
    LinearLayout mLinearLayout;
    private volatile boolean isRun = true;  //是否结束线程
    volatile int mRefreshtTime=10000;       //刷新时间
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getIndentData();
        }
    };
    public MyFragmentIndent() {
        // TODO Auto-generated constructor stub
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment2_indent, null);
        mContext=getActivity();
        initViewForFind(view);
        initViewForData();
        initViewForEvent();
        getIndentData();//开始时默认获取一次数据
        initViewForAuto();//开启自动刷新
        return view;
    }
    private void initViewForData() {
        mIndentList=new ArrayList<>();
        mMyIndentAdapter=new MyIndentAdapeter(mIndentList, mContext);
        mListView.setAdapter(mMyIndentAdapter);
    }
    private void getIndentData() {
        SpectacularsApplication.getNetService(mContext).queryIndentInfo(mQueryMap).subscribe(new Action1<IndentInfo>() {
            @Override
            public void call(IndentInfo indentInfo) {
                EventBus.getDefault().post(new Event(indentInfo, Event.INDENT_INFO));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                EventBus.getDefault().post(new Event("获取订单信息失败", Event.INDENT_INFO_MISS));
            }
        });

    }
    private void initViewForEvent() {

        mTextview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLinearLayout.getVisibility() == View.GONE) {
                    mLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayout.setVisibility(View.GONE);
                }
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder filtrate = new StringBuilder();
                mQueryMap.clear();
                if (mRadioButton1.isChecked()) {
                    mQueryMap.put("select_time", "0");
                } else if (mRadioButton2.isChecked()) {
                    mQueryMap.put("select_time", "1");
                }
                if (mCheckBox1.isChecked()) {
                    filtrate.append("起始日期：" + mDatePicker1.getYear() + "-" + (mDatePicker1.getMonth() + 1) + "-" + mDatePicker1.getDayOfMonth() + "  ");
                    mQueryMap.put("begin_date", mDatePicker1.getYear() + "-" + (mDatePicker1.getMonth() + 1) + "-" + mDatePicker1.getDayOfMonth());
                }
                if (mCheckBox2.isChecked()) {
                    filtrate.append("结束日期：" + mDatePicker2.getYear() + "-" + (mDatePicker1.getMonth() + 1) + "-" + mDatePicker2.getDayOfMonth() + "  ");
                    mQueryMap.put("end_date", mDatePicker2.getYear() + "-" + (mDatePicker2.getMonth() + 1) + "-" + mDatePicker2.getDayOfMonth());
                }
                for (int i = 0; i < mList.size(); i++) {
                    String data = mList.get(i).getText().toString();

                    if (data != null && data != "" && data.length() > 0) {
                        filtrate.append(mFiltrateItem[i] + ":" + data + "  ");
                        mQueryMap.put(mQueryDataItem[i], data);
                    }
                }
                if (filtrate.toString().length() > 1) {
                    mTextview1.setText(filtrate.toString());
                } else {
                    mQueryMap.clear();
                    mTextview1.setText("无筛选条件（默认显示全部订单）");
                }
                getIndentData();
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckBox1.setChecked(false);
                mCheckBox2.setChecked(false);
                for (int i = 0; i < mList.size(); i++) {
                    EditText editText = mList.get(i);
                    editText.setText("");
                }
                mRadioButton1.setChecked(true);
                mQueryMap.clear();
                mTextview1.setText("无筛选条件（默认显示全部订单）");
            }
        });
    }

    private void initViewForFind(View view) {
        mLinearLayout = (LinearLayout) view.findViewById(R.id.fragment2_linearlayout);
        mTextview1 = (TextView) view.findViewById(R.id.fragment2_textveiw1);
        mDatePicker1 = (DatePicker) view.findViewById(R.id.fragment2_datepicker_starttime);
        mDatePicker2 = (DatePicker) view.findViewById(R.id.fragment2_datepicker_endtime);
        mCheckBox1 = (CheckBox) view.findViewById(R.id.fragment2_checkbox1);
        mCheckBox2 = (CheckBox) view.findViewById(R.id.fragment2_checkbox2);
        mButton2 = (Button) view.findViewById(R.id.fragment2_button2);
        mButton3 = (Button) view.findViewById(R.id.fragment2_button3);
        mListView=(ListView)view.findViewById(R.id.fragment2_listview);
        mRadioGroup=(RadioGroup)view.findViewById(R.id.fragment2_radiogroup);
        mRadioButton1=(RadioButton)view.findViewById(R.id.fragment2_radiobutton1);
        mRadioButton2=(RadioButton)view.findViewById(R.id.fragment2_radiobutton2);

        mList.clear();
        mList.add((EditText) view.findViewById(R.id.fragment2_edittext1));
        mList.add((EditText) view.findViewById(R.id.fragment2_edittext2));
        mList.add((EditText) view.findViewById(R.id.fragment2_edittext3));
        mList.add((EditText) view.findViewById(R.id.fragment2_edittext4));
        mList.add((EditText) view.findViewById(R.id.fragment2_edittext5));
        mList.add((EditText) view.findViewById(R.id.fragment2_edittext6));
        Time time = new Time("GMT+8");
        time.setToNow();
        System.out.println("-----"+time.year+time.month+time.monthDay);
        mDatePicker1.updateDate(time.year,time.month,time.monthDay);
        mDatePicker2.updateDate(time.year,time.month,time.monthDay);
    }
    public void onEventMainThread(Event event) {
        if (Event.INDENT_INFO == event.getActionType()) {
            List<IndentItem> indentItems=((IndentInfo)event.getObject()).getResult();
            if (indentItems != null) {
                mIndentList=indentItems;
                mMyIndentAdapter.setmList(mIndentList);
                mMyIndentAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(mContext, "没有停线记录", Toast.LENGTH_SHORT).show();
            }
        } else if (Event.INDENT_INFO_MISS == event.getActionType()) {
            Toast.makeText(mContext, event.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRun=false;
        EventBus.getDefault().unregister(this);
    }

    private void initViewForAuto() {
        // 自动更新数据
        MyThread myThread=new MyThread();
        myThread.start();
    }
    private class MyThread extends Thread{
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
