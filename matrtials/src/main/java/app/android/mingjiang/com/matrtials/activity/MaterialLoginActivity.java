package app.android.mingjiang.com.matrtials.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.util.ComServiceUtils;
import com.mingjiang.android.base.util.SuperToastApi;

import app.android.mingjiang.com.matrtials.MaterialApp;
import app.android.mingjiang.com.matrtials.R;
import app.android.mingjiang.com.matrtials.client.NetService;
import app.android.mingjiang.com.matrtials.entity.SessionObj;
import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * 备注：登录界面。
 * 作者：wangzs on 2016/2/23 15:56
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class MaterialLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_material_login);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //校验用户是否存在，用户重新登录
        if(MaterialApp.getApp().sessionObj != null){//返回键操作时
            MaterialApp.getApp().sessionObj = null;
            MaterialApp.getApp().materialMap = null;
            this.finish();
        }else{//启动扫描用户工牌服务。
            ComServiceUtils.startService(this, ComEvent.ACTION_MATERIAL_USER_SCAN);
        }
    }

    public void onEventMainThread(ComEvent event) {

        if(ComEvent.ACTION_MATERIAL_USER_SCAN == event.getActionType()){
           String message = event.getMessage();
            getSessionId(message);
        }else if(ComEvent.ACTION_GET_SESSION == event.getActionType()){
            SessionObj sessionObj = (SessionObj)event.getObjectMsg();
            MaterialApp.getApp().sessionObj = sessionObj;
            //执行页面跳转
            Intent intent = new Intent(this,MatrialsMainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 获取SessionID。
     */
    private void getSessionId(String userCode){
        MaterialApp.getApp().getNetService(this).login(userCode, NetService.CONTEXT_VALUE).subscribe(new Action1<SessionObj>() {
            @Override
            public void call(SessionObj result) {
                EventBus.getDefault().post(new ComEvent(result,ComEvent.ACTION_GET_SESSION));
            }

        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                SuperToastApi.showInitSuperToast(MaterialLoginActivity.this, "一键入库操作失败！");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        ComServiceUtils.stopService(this);
    }
}
