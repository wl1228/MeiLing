package com.mingjiang.kouzeping.spectaculars.material_monitor;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjiang.kouzeping.spectaculars.R;
import com.mingjiang.kouzeping.spectaculars.event.Event;
import com.mingjiang.kouzeping.spectaculars.utils.RefreshTimeUtils;
import com.mingjiang.kouzeping.spectaculars.utils.SharedPrefsUtil;
import com.mingjiang.kouzeping.spectaculars.utils.SpectacularsApplication;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * Created by kouzeping on 2016/2/22.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MyFragmentMaterial extends Fragment {
    List<EditText> mList = new ArrayList<>();
    TextView mTextview1,mTextviewCurrentPage;
    Button mButtonPageUp,mButtonPageDown, mButton2, mButton3;
    //material_id ：物料编码，material_name ：物料名称，seat_id：库位编号reservoir_area：库区编号page_size:返回记录（分页，默认10条）page_number：页码（分页）
    String[] mFiltrateItem = new String[]{"物料编码", "物料名称", "库位编号", "库区编号", "页面条数", "页码"};
    ListView mListView;
    List<MidMaterialValue> mMidMaterList;
    Context mContext;
    private MiddleLibAdapter adapter = null;
    LinearLayout mLinearLayout;
    int pageSize=20;
    long totalSize=0;
    int pagenum=1;
    private volatile boolean isRun = true;  //是否结束线程
    volatile int mRefreshtTime=10000;       //刷新时间
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dealQueryData();
        }
    };
    public MyFragmentMaterial() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4_material, null);
        EventBus.getDefault().register(this);
        mContext = getActivity();
        initViewForFind(view);
        ininViewForData();
        initViewForEvent();
        //开始时默认获取一次数据
        dealQueryData();
        //开启自动更新
        initViewForAuto();

        return view;
    }

    private void ininViewForData() {
        mMidMaterList=new ArrayList<>();
        adapter = new MiddleLibAdapter(getActivity(), mMidMaterList);
        mListView.setAdapter(adapter);
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
                for (int i = 0; i < mList.size(); i++) {
                    String data = mList.get(i).getText().toString();

                    if (data != null && data != "" && data.length() > 0) {
                        filtrate.append(mFiltrateItem[i] + ":" + data + "  ");
                    }
                }
                if (filtrate.toString().length() > 1) {
                    mTextview1.setText(filtrate.toString());
                } else {
                    mTextview1.setText("无筛选条件（默认显示全部物料）");
                }
                //执行数据查询
                dealQueryData();
            }


        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mList.size(); i++) {
                    EditText editText = mList.get(i);
                    editText.setText("");
                }
                mTextview1.setText("无筛选条件(默认显示全部物料)");
                //将页面条数，页码变回默认值
                pageSize = 20;
                pagenum = 1;
            }
        });
        mButtonPageUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagenum <= 1) {
                    Toast.makeText(mContext, "已经是第一页", Toast.LENGTH_SHORT).show();
                } else {
                    pagenum--;
                    dealQueryData();
                }
            }
        });
        mButtonPageDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagenum >= (totalSize / pageSize + 1)) {
                    Toast.makeText(mContext, "已经是最后一页", Toast.LENGTH_SHORT).show();
                } else {
                    pagenum++;
                    dealQueryData();
                }
            }
        });
    }
    private void initViewForFind(View view) {
        mLinearLayout = (LinearLayout) view.findViewById(R.id.fragment4_linearlayout);
        mTextview1 = (TextView) view.findViewById(R.id.fragment4_textveiw1);
        mTextviewCurrentPage=(TextView)view.findViewById(R.id.fragment4_textview_currentpage);

        mButton2 = (Button) view.findViewById(R.id.fragment4_button2);
        mButton3 = (Button) view.findViewById(R.id.fragment4_button3);
        mButtonPageUp=(Button)view.findViewById(R.id.fragment4_button_pageup);
        mButtonPageDown=(Button)view.findViewById(R.id.fragment4_button_pagedown);

        mListView = (ListView) view.findViewById(R.id.fragment4_listview);
        mList.clear();
        mList.add((EditText) view.findViewById(R.id.fragment4_edittext1));
        mList.add((EditText) view.findViewById(R.id.fragment4_edittext2));
        mList.add((EditText) view.findViewById(R.id.fragment4_edittext3));
        mList.add((EditText) view.findViewById(R.id.fragment4_edittext4));
        mList.add((EditText) view.findViewById(R.id.fragment4_edittext5));
        mList.add((EditText) view.findViewById(R.id.fragment4_edittext6));
    }
    //组织查询数据。
    private void dealQueryData() {
        Toast.makeText(mContext, "正在获取数据", Toast.LENGTH_SHORT).show();
        MidMaterialQuery midMaterialQuery = new MidMaterialQuery();
        midMaterialQuery.material_id = mList.get(0).getText().toString();
        midMaterialQuery.material_name = mList.get(1).getText().toString();
        midMaterialQuery.seat_id = mList.get(2).getText().toString();
        midMaterialQuery.reservoir_area = mList.get(3).getText().toString();
        midMaterialQuery.page_size = mList.get(4).getText().toString();
        //如果筛选没有输入页面条数，调用缓存的页面条数
        if ( mList.get(4).getText().toString()!=null&&!"".equals(mList.get(4).getText().toString())){
            midMaterialQuery.page_size = mList.get(4).getText().toString();
            pageSize=Integer.parseInt(mList.get(4).getText().toString());
        }else {
            midMaterialQuery.page_size=""+pageSize;
        }
        //如果筛选没有输入页码，调用缓存的页码
        if ( mList.get(5).getText().toString()!=null&&!"".equals(mList.get(5).getText().toString())){
            midMaterialQuery.page_number = mList.get(5).getText().toString();
            pagenum=Integer.parseInt(mList.get(5).getText().toString());
        }else {
            midMaterialQuery.page_number=""+pagenum;
        }
        dealQueary(midMaterialQuery);
    }

    //执行查询操作
    private void dealQueary(MidMaterialQuery midMaterialQuery) {
        SpectacularsApplication.getNetService(mContext).queryMidMaterial(midMaterialQuery.getQueryMap()).subscribe(new Action1<MidMaterialResult>() {
            @Override
            public void call(MidMaterialResult midMaterialResult) {
                dealQueryDataShow(midMaterialResult);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                EventBus.getDefault().post(new Event("获取数据失败", Event.MATERIAL_INFO_MISS));
                pageSize=20;pagenum=1;
            }
        });
    }

    //处理数据显示
    private void dealQueryDataShow(MidMaterialResult midMaterialResult) {
        if (midMaterialResult.result_size!=null&&!"false".equals(midMaterialResult.result_size)&&!"".equals(midMaterialResult.result_size)){
            totalSize = Long.parseLong(midMaterialResult.result_size);
        }
        EventBus.getDefault().post(new Event(midMaterialResult.result, Event.MATERIAL_INFO));
    }
    public void onEventMainThread(Event event) {
        if (Event.MATERIAL_INFO == event.getActionType()) {
            List<MidMaterialValue> list = event.getMaterialList();
            if (list==null||list.size()==0){
                Toast.makeText(mContext, "没有匹配的数据", Toast.LENGTH_SHORT).show();
                pageSize=20;pagenum=1;
            }else {
                Toast.makeText(mContext, "共获得"+totalSize+"条数据,本页显示"+list.size()+"条.", Toast.LENGTH_SHORT).show();
                mMidMaterList=list;
                adapter.setMidMaterialValueList(mMidMaterList);
                adapter.notifyDataSetChanged();
                //更新页码
                int totalpage= (int) (totalSize%pageSize==0?(totalSize/pageSize):(totalSize / pageSize+1));
                mTextviewCurrentPage.setText("第" + pagenum + "/" +totalpage+ "页");
            }


        }else if (Event.MATERIAL_INFO_MISS==event.getActionType()){
            Toast.makeText(mContext, event.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
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
