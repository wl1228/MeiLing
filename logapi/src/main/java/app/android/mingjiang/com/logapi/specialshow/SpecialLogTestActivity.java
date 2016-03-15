package app.android.mingjiang.com.logapi.specialshow;

import android.app.Activity;
import android.os.Bundle;

import app.android.mingjiang.com.logapi.R;
import app.android.mingjiang.com.logapi.specialshow.api.Logger;

/**
 * 备注：特殊显示日志测试。
 * 作者：wangzs on 2016/1/11 16:43
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class SpecialLogTestActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_log);
        Logger.d("dsa", "sadfsfds");
        Logger.e("dsa", "sadfsfds");
        Logger.i("adsfsd","asdfs");
        Logger.v("asdfsa","adsfdsf");
        Logger.w("dadsdf","dsafdsfsa");
    }
}
