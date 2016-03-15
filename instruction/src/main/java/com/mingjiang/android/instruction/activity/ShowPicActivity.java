package com.mingjiang.android.instruction.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.mingjiang.android.instruction.R;

import java.io.InputStream;

/**
 * 说明：测试使用本地图片展现。
 * 作者：wangzs on 2015/12/25 11:09
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class ShowPicActivity extends Activity {

    ImageView imageView = null;
    ProgressDialog dialog = null;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);

        imageView = (ImageView)findViewById(R.id.show_image);

        try {
            InputStream in=getAssets().open("img/postOperInstuction.jpg");
            Bitmap bmp= BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openPicFile(){
        new Thread(){
            @Override
            public void run() {
                super.run();
            }
        }.start();
    }


}
