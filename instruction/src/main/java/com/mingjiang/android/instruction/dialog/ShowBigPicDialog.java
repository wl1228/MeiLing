package com.mingjiang.android.instruction.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjiang.android.instruction.R;
import com.mingjiang.android.instruction.entity.ShowStep;

import java.util.ArrayList;
import java.util.List;


/**
 * 大屏显示图片信息。
 */
public class ShowBigPicDialog implements ViewPager.OnPageChangeListener {

    private LinearLayout layout = null;
    private ViewPager viewPager = null;
    private TextView titleView= null;
    private TextView contentView = null;
    private  AlertDialog dialog = null;
    private ImageView[] imageViews;
    private Button beforeBtn = null;
    private Button nextBtn = null;
    private int curPosition = 0;
    private List<ShowStep> listShowStep = new ArrayList<ShowStep>();
    private Context context = null;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        titleView.setText(listShowStep.get(position % imageViews.length).name);
        contentView.setText(listShowStep.get(position % imageViews.length).description);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public ShowBigPicDialog(Context context, int position, List<ShowStep> listShowStem) {
        this.context = context;
        this.curPosition = position;
        this.listShowStep = listShowStem;
        init();
    }

    //初始化
    private void init(){

        LayoutInflater inflaterDl = LayoutInflater.from(context);
        layout = (LinearLayout)inflaterDl.inflate(R.layout.picture_item_layout, null);
        viewPager = (ViewPager)layout.findViewById(R.id.viewPager);
        titleView = (TextView)layout.findViewById(R.id.title);
        contentView = (TextView)layout.findViewById(R.id.detail);
        beforeBtn = (Button)layout.findViewById(R.id.before);
        nextBtn = (Button)layout.findViewById(R.id.next);

        beforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curPosition = viewPager.getCurrentItem()-1;
                if (curPosition < 0) {
                    curPosition = 0;
                    Toast.makeText(context, "已是最前一张", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("curPosition", String.valueOf(curPosition));
                    viewPager.setCurrentItem(curPosition);
//                    onPageSelected(curPosition);
                }
                Log.e("curPosition_end", String.valueOf(curPosition));

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("curPosition_start", String.valueOf(curPosition));
                curPosition = viewPager.getCurrentItem() + 1;
//                onPageSelected(curPosition);
                Log.e("curPosition", String.valueOf(curPosition));
                viewPager.setCurrentItem(curPosition);
                Log.e("curPosition_start", String.valueOf(curPosition));

            }
        });

        //对话框
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);

        //初始化图片信息
       int size = listShowStep.size();
        imageViews = new ImageView[size];
        for(int i=0; i<imageViews.length; i++){
            ImageView imageView = new ImageView(context);
            imageViews[i] = imageView;
            imageView.setImageBitmap(listShowStep.get(i).bitmap);
        }



        //设置Adapter
        viewPager.setAdapter(new MyAdapter());
        //设置监听，主要是设置点点的背景
        viewPager.setOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        viewPager.setCurrentItem((imageViews.length) * 100 + curPosition);

        titleView.setText(listShowStep.get(curPosition).name);
        contentView.setText(listShowStep.get(curPosition).description);

    }


    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews[position % imageViews.length];
            ViewPager viewPager = (ViewPager)container;
            //if(viewPager.getChildCount() == imageViews.length){
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException.
                ViewParent vp =imageView.getParent();
                if (vp!=null){
                    ViewGroup parent = (ViewGroup)vp;
                    parent.removeView(imageView);
                }
           // }
            viewPager.addView(imageView,0);
            return imageView;
        }


        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            //((ViewPager)container).removeView(imageViews[position % imageViews.length]);
        }
    }
}
