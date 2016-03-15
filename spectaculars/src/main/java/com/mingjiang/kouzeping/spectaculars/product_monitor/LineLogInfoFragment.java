package com.mingjiang.kouzeping.spectaculars.product_monitor;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.mingjiang.kouzeping.spectaculars.R;
import com.mingjiang.kouzeping.spectaculars.event.Event;
import com.mingjiang.kouzeping.spectaculars.utils.SpectacularsApplication;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.greenrobot.event.EventBus;
import rx.functions.Action1;
/**
 * Created by kouzeping on 2016/2/25.
 * email：kouzeping@shmingjiang.org.cn
 */
public class LineLogInfoFragment extends DialogFragment {
    String lineCode;
    String lineName;
    Context mContext;
    ListView mListview;
    TextView mTextView;
    LinearLayout mLinerLayout;
    List<LineLogInfoItem> mList;
    MyLineLogInfoAdapter mAdapter;
    public LineLogInfoFragment(String lineCode,String lineName) {
        this.lineCode = lineCode;
        this.lineName = lineName;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        getDialog().setTitle(lineName + "产线停线信息");
        mContext=inflater.getContext();
        View view = inflater.inflate(R.layout.fragment3_product_lineloginfo, container);
        mListview=(ListView)view.findViewById(R.id.fragment3_lineloginfo_listview);
        mTextView=(TextView)view.findViewById(R.id.fragment3_linerloginfo_textview_none);
        mLinerLayout=(LinearLayout)view.findViewById(R.id.fragment3_linerloginfo_linearlayout);
        getLineLogInfo();
        return view;
    }
    private void getLineLogInfo() {
        mTextView.setVisibility(View.VISIBLE);
        mLinerLayout.setVisibility(View.GONE);
        mTextView.setText("正在获取停线信息...");
        Map<String,String> map=new HashMap<>();
        map.put("id",lineCode);
        map.put("name",lineName);
        SpectacularsApplication.getNetService(mContext).queryLinLogInfo(map).subscribe(new Action1<List<LineLogInfoItem>>() {
            @Override
            public void call(List<LineLogInfoItem> lineLog) {
                EventBus.getDefault().post(new Event(lineLog, Event.LINELOG_INFO_ITEM));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                EventBus.getDefault().post(new Event("获取"+lineName+"产线停线信息失败", Event.LINELOG_INFO_ITEM_MISS));
            }
        });
    }
    public void onEventMainThread(Event event) {
        if (Event.LINELOG_INFO_ITEM == event.getActionType()) {
            List<LineLogInfoItem> lineLogInfoItems=(List<LineLogInfoItem>)event.getObject();
            if (lineLogInfoItems!=null){
                if (lineLogInfoItems.size()>0){
                    mTextView.setVisibility(View.GONE);
                    mLinerLayout.setVisibility(View.VISIBLE);
                    this.mList=lineLogInfoItems;
                    if (mAdapter==null){
                        mAdapter=new MyLineLogInfoAdapter(mList,mContext);
                        mListview.setAdapter(mAdapter);
                    }else{
                        mAdapter.setmList(lineLogInfoItems);
                        mAdapter.notifyDataSetChanged();
                    }
                }else {
                    mTextView.setVisibility(View.VISIBLE);
                    mTextView.setText("没有停线记录");
                    mLinerLayout.setVisibility(View.GONE);
                }
            }else {
                mTextView.setVisibility(View.VISIBLE);
                mTextView.setText("没有停线记录");
                mLinerLayout.setVisibility(View.GONE);
            }
        }else if (Event.LINELOG_INFO_ITEM_MISS==event.getActionType()){
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(event.getMessage());
            mLinerLayout.setVisibility(View.GONE);
        }
    }
    @Override
    public void dismiss() {
        super.dismiss();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        //设置自定义的title  layout
        //getDialog().getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

    }
}
