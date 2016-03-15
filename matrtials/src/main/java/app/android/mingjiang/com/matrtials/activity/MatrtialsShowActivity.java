package app.android.mingjiang.com.matrtials.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import app.android.mingjiang.com.matrtials.R;
import app.android.mingjiang.com.matrtials.adapter.MaterialAdapter;
import app.android.mingjiang.com.matrtials.entity.Material;
import app.android.mingjiang.com.matrtials.fragment.GetMoreFragment;

public class MatrtialsShowActivity extends Activity{
    public static final int USE = 1;
    private Button auto_use;
    private Button get;
    private Button add1;
    private Button add5;
    private Button sub1;
    private Button sub5;
    private Button useMaterial;
    private TextView autoTimeTextView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MaterialAdapter mAdapter;
    private ArrayList<Material> materials = new ArrayList<>();
    private TimerTask timerTask;
    private Timer timer;
    private int autoTime = 5;
    private boolean isAutoUse = false;


    private static final Handler handler = new Handler();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrtials_show);

        get = (Button) findViewById(R.id.get);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        materials.add(new Material("#MKH111001", "大号螺丝", 10, 10, 1));
        materials.add(new Material("#MKH111002", "气垫", 15, 15, 2));
        materials.add(new Material("#MKH111003", "塑料管", 1, 20, 1));
        materials.add(new Material("#MKH111004", "胶布", 5, 35, 3));
        mAdapter = new MaterialAdapter(materials);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        //用一次料
        useMaterial = (Button) findViewById(R.id.change);
        useMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                use();
            }
        });

        //打开获取物料界面
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetMoreDialog(view);
            }
        });

        //当物料不足时点击可打开获取物料界面
        mAdapter.setOnItemClickListener(new MaterialAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Material data) {
                if (!data.isSafe()) {
                    GetMoreDialog(view);
                }
            }
        });

        //自动用料开启和关闭
        auto_use = (Button) findViewById(R.id.auto_use);
        auto_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAutoUse) {
                    stopAutoUse();
                } else {
                    autoUse();
                }
            }
        });

        //设置自动用料时间间隔
        autoTimeTextView = (TextView) findViewById(R.id.auto_time);
        add1 = (Button) findViewById(R.id.auto_time_add1);
        add5 = (Button) findViewById(R.id.auto_time_add5);
        sub1 = (Button) findViewById(R.id.auto_time_sub1);
        sub5 = (Button) findViewById(R.id.auto_time_sub5);
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoTime++;
                autoTimeTextView.setText(autoTime + "秒");
                stopAutoUse();
            }
        });
        add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoTime += 5;
                autoTimeTextView.setText(autoTime + "秒");
                stopAutoUse();
            }
        });
        sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoTime > 1) {
                    autoTime--;
                }
                autoTimeTextView.setText(autoTime + "秒");
                stopAutoUse();
            }
        });
        sub5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoTime > 5) {
                    autoTime -= 5;
                } else {
                    autoTime = 1;
                }
                autoTimeTextView.setText(autoTime + "秒");
                stopAutoUse();
            }
        });

        startActivity(new Intent(this,MatrialsMainActivity.class));
    }

    //打开获取物料界面
    public void GetMoreDialog(View view) {
        stopAutoUse();
        GetMoreFragment getMoreFragment = new GetMoreFragment();
        getMoreFragment.setCancelable(false);
        getMoreFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        getMoreFragment.show(getFragmentManager(), "GetMoreFragment");
    }
    //获得物料，给Fragment调用的
    public ArrayList<Material> getMaterials() {
        return materials;
    }
    //通知物料变化，给Fragment调用的
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }
    //使用一次物料的函数，返回结果，物料不足则为false
    private boolean use() {
        boolean enough = true;
        //扫描物料数量，看是否够一次使用
        for (Material m : materials) {
            if (!m.isEnough()) {
                enough = false;
            }
        }
        if (enough) {
            for (Material m : materials) {
                m.change();
            }
        } else {
            Toast.makeText(this, "物料不足！", Toast.LENGTH_SHORT).show();
        }
        mAdapter.notifyDataSetChanged();
        return enough;
    }
    //开启自动用料
    public void autoUse() {
        auto_use.setText(R.string.stop_auto_use);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //通过handler发送消息通知主线程
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!use()) {
                            stopAutoUse();
                        }
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, autoTime * 1000, autoTime * 1000);
        isAutoUse = true;
    }

    //停止自动用料
    public void stopAutoUse() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        isAutoUse = false;
        auto_use.setText(R.string.start_auto_use);
    }
}
