package com.mingjiang.kouzeping.spectaculars;
/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */

import android.annotation.SuppressLint;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.mingjiang.kouzeping.spectaculars.badness_monitor.MyFragmentBadness;
import com.mingjiang.kouzeping.spectaculars.device_monitor.MyFragmentDevice;
import com.mingjiang.kouzeping.spectaculars.indent_monitor.MyFragmentIndent;
import com.mingjiang.kouzeping.spectaculars.material_monitor.MyFragmentMaterial;
import com.mingjiang.kouzeping.spectaculars.product_monitor.MyFragmentProduct;
import com.mingjiang.kouzeping.spectaculars.utils.SharedPrefsUtil;

import java.util.ArrayList;

public class SpectacularsActivity extends FragmentActivity {

    private HorizontalScrollView scrollView; //上方导航栏
    private ViewPager pager;
    private TextView indicator;              //对应页下方会有一个绿色条指示
    private RadioGroup rg;
    private ArrayList<Fragment> fragList = new ArrayList<>();//保存每页对应的Fragment
    private String[] mRefreshTimeChose = new String[]{"10秒", "20秒", "30秒", "1分钟", "2分钟", "5分钟"};//刷新、轮播时间选择
    private String[] mTimeChose = new String[]{"2秒", "3秒", "5秒", "10秒", "30秒", "1分钟"};//刷新、轮播时间选择
    ImageButton mImageButton;               //选择是否轮播
    volatile int mRefreshtTime = 10000;       //刷新间隔，volatile属性是时刻获取变量最新值，不直接调用寄存器
    volatile int mCarouselTime = 2000;        //轮播间隔
    private volatile boolean isLoop = true; //是否轮播
    private volatile boolean isRun = true;  //是否结束线程
    private Spinner spinner1, spinner2;
    TransitionDrawable transitionDrawable1; //是否轮播的切换动画

    MyThread myThread;                      //控制自动轮播的线程
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (pager.getCurrentItem() == 4) {
                pager.setCurrentItem(0);
            } else {
                pager.setCurrentItem(pager.getCurrentItem() + 1);
            }
        }
    };

	/*
     * RadioButton不加背景文字无法居中 RadioButton不加id设置默认选中项时选中状态切换会有问题
	 */

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectaculars);
        // 找到组件
        initRootViewForFind();
        // 设置组件事件监听
        initRootViewForEvent();
        // 设置viewpager
        initRootViewPager();
        //设置自动轮播
        initViewForAuto();
        //设置一些参数
        initViewForSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //读取上次的刷新设置
        mRefreshtTime = matchingRefreshTime(SharedPrefsUtil.getInt(SpectacularsActivity.this, SharedPrefsUtil.REFRESH_TIME));
        spinner1.setSelection(SharedPrefsUtil.getInt(SpectacularsActivity.this, SharedPrefsUtil.REFRESH_TIME));
        //读取上次的轮播设置
        mCarouselTime = matchingCarouselTime(SharedPrefsUtil.getInt(SpectacularsActivity.this, SharedPrefsUtil.CAROUSEL_TIME));
        spinner2.setSelection(SharedPrefsUtil.getInt(SpectacularsActivity.this, SharedPrefsUtil.CAROUSEL_TIME));
        //设置按钮的显示状态
        transitionDrawable1 = (TransitionDrawable) mImageButton.getDrawable();
        if (!SharedPrefsUtil.getBoolean(SpectacularsActivity.this, SharedPrefsUtil.IS_LOOP)) {
            transitionDrawable1.reverseTransition(1);
            isLoop = false;
            SharedPrefsUtil.setValue(SpectacularsActivity.this, SharedPrefsUtil.IS_LOOP, false);
        } else {
            isLoop = true;
        }
    }

    private void initViewForSpinner() {
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, R.layout.simple_spinner_item_mycreat, mRefreshTimeChose);
        spinner1.setAdapter(arrayAdapter1);
        //注册事件
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRefreshtTime = matchingRefreshTime(position);
                SharedPrefsUtil.setValue(SpectacularsActivity.this, SharedPrefsUtil.REFRESH_TIME, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, R.layout.simple_spinner_item_mycreat, mTimeChose);
        spinner2.setAdapter(arrayAdapter2);
        //注册事件
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCarouselTime = matchingCarouselTime(position);
                SharedPrefsUtil.setValue(SpectacularsActivity.this, SharedPrefsUtil.CAROUSEL_TIME, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initViewForAuto() {
        // 自动切换页面功能
        myThread = new MyThread();
        myThread.start();
    }

    private void initRootViewPager() {
        rg = (RadioGroup) findViewById(R.id.rg);
        fragList.add(new MyFragmentDevice());
        fragList.add(new MyFragmentIndent());
        fragList.add(new MyFragmentProduct());
        fragList.add(new MyFragmentMaterial());
        fragList.add(new MyFragmentBadness());
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
//        pager.setCurrentItem(Integer.MAX_VALUE / 2-Integer.MAX_VALUE/2%fragList.size());//默认在中间，使用户看不到边界
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // 选中的RadioButton播放动画
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.2f, 1, 1.2f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(2000);
                sAnim.setFillAfter(true);

                // 遍历所有的RadioButton
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioBtn = (RadioButton) group.getChildAt(i);
                    if (radioBtn.isChecked()) {
                        radioBtn.startAnimation(sAnim);
                    } else {
                        radioBtn.clearAnimation();
                    }
                }
                if (checkedId == R.id.rb1) {
                    pager.setCurrentItem(0);
                } else if (checkedId == R.id.rb2) {
                    pager.setCurrentItem(1);

                } else if (checkedId == R.id.rb3) {
                    pager.setCurrentItem(2);

                } else if (checkedId == R.id.rb4) {
                    pager.setCurrentItem(3);

                } else if (checkedId == R.id.rb5) {
                    pager.setCurrentItem(4);

                }
            }
        });
        // 设置pager缓存页数限制
        pager.setOffscreenPageLimit(5);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // 获取对应位置的RadioButton
                RadioButton radioBtn = (RadioButton) rg.getChildAt(arg0);
                // 设置对应位置的RadioButton为选中的状态
                radioBtn.setChecked(true);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
				/*
				 * arg0 : 可见项的第一项的编号 arg1 : 相对于屏幕左侧的偏移比（0无偏移 1偏移一个屏幕） arg2 :
				 * 平移的绝对的像素值
				 *
				 * px = 70 * (320 / 160)
				 *
				 * px = dp * (dpi / 160)
				 */
                // 获得控件的属性。。获取TextView在其父容器LinearLayout中的布局参数
                // int height = params.height;
                // int width = params.width;
                // int margin = params.leftMargin;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indicator
                        .getLayoutParams();
                // 设置下方标志的滚动
                int leftmargin = (int) ((arg0 + arg1) * params.width);
                params.leftMargin = leftmargin;
                indicator.setLayoutParams(params);

                // 设置整个卷轴的滚动
                // 获取手机的屏幕宽
                // DisplayMetrics outMetrics = new DisplayMetrics();
                // getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
                DisplayMetrics outMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
                // 计算滚动距离，使选定的radiobutton总处于屏幕特定位置
                int distance = (int) (leftmargin + params.width - outMetrics.widthPixels / 2);
                // 滚动，数值为负时不滚动
                scrollView.scrollTo(distance, 0);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void initRootViewForEvent() {
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 设置图片转换动画，并保存设置值
                if (isLoop) {
                    transitionDrawable1.reverseTransition(1);
                    Toast.makeText(SpectacularsActivity.this, "自动轮播关闭", Toast.LENGTH_SHORT).show();
                    isLoop = false;
                    SharedPrefsUtil.setValue(SpectacularsActivity.this, SharedPrefsUtil.IS_LOOP, false);
                } else {
                    transitionDrawable1.reverseTransition(1);
                    Toast.makeText(SpectacularsActivity.this, "自动轮播开启", Toast.LENGTH_SHORT).show();
                    isLoop = true;
                    SharedPrefsUtil.setValue(SpectacularsActivity.this, SharedPrefsUtil.IS_LOOP, true);
                }
            }
        });
    }

    private void initRootViewForFind() {
        scrollView = (HorizontalScrollView) findViewById(R.id.scroll);
        pager = (ViewPager) findViewById(R.id.pager);
        indicator = (TextView) findViewById(R.id.indicator);
        spinner1 = (Spinner) findViewById(R.id.activity_spectaculars_spnner1);
        spinner2 = (Spinner) findViewById(R.id.activity_spectaculars_spnner2);
        mImageButton = (ImageButton) findViewById(R.id.activity_spectaculars_imagebutton);
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return fragList.size();
        }
        @Override
        public Fragment getItem(int position) {
//            //对ViewPager页号求模取出View列表中要显示的项
//            position %= fragList.size();
//            if (position<0){
//                position = fragList.size()+position;
//            }
            Fragment view = fragList.get(position);
            return view;
        }
    }

    private int matchingCarouselTime(int position) {
        int time = 2000;
        switch (position) {
            case 0:
                time = 2 * 1000;
                break;
            case 1:
                time = 3 * 1000;
                break;
            case 2:
                time = 5 * 1000;
                break;
            case 3:
                time = 10 * 1000;
                break;
            case 4:
                time = 30 * 1000;
                break;
            case 5:
                time = 60 * 1000;
                break;
        }
        return time;
    }

    private int matchingRefreshTime(int position) {
        int time = 2000;
        switch (position) {
            case 0:
                time = 10 * 1000;
                break;
            case 1:
                time = 20 * 1000;
                break;
            case 2:
                time = 30 * 1000;
                break;
            case 3:
                time = 60 * 1000;
                break;
            case 4:
                time = 120 * 1000;
                break;
            case 5:
                time = 300 * 1000;
                break;
        }
        return time;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //当activity停止时停止轮播
        isLoop = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当activity销毁时停止线程
        isRun = false;
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            while (isRun) {
                SystemClock.sleep(mCarouselTime);
                if (isLoop) {
                    handler.sendEmptyMessage(0);
                }
            }
        }
    }
}

