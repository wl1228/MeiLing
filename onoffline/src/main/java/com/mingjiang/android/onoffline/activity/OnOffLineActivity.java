package com.mingjiang.android.onoffline.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.util.Constants;
import com.mingjiang.android.base.util.ComServiceUtils;
import com.mingjiang.android.base.util.SuperToastApi;
import com.mingjiang.android.onoffline.OnOffLineApp;
import com.mingjiang.android.onoffline.R;
import com.mingjiang.android.onoffline.entity.OnOffLineData;
import com.mingjiang.android.onoffline.utils.Constant;

import java.util.Map;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * 备注：上线/下线处理界面。
 * 作者：wangzs on 2016/2/19 09:39
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class OnOffLineActivity extends Activity{

    private TextView logoutText = null;
    private EditText serialNumberEdit = null;
    private Spinner operationSpinner = null;
    private EditText postCodeEdit = null;
    private EditText reasonEdit = null;
    private TextView resonNameText = null;
    private Button submitOnoffLineBtn = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onoff);
        EventBus.getDefault().register(this);
        getIntentExtra();
        initView();
        ComServiceUtils.startService(this, ComEvent.ACTION_ONOFF_FRIDGE_SCAN);
    }

    private void initView(){
        logoutText = (TextView)findViewById(R.id.logout);
        logoutText.setOnClickListener(new LogoutListener());
        serialNumberEdit = (EditText)findViewById(R.id.serial_number);
        serialNumberEdit.setFocusable(false);
        operationSpinner = (Spinner)findViewById(R.id.operation);
        postCodeEdit = (EditText)findViewById(R.id.post_code);
        postCodeEdit.setText(OnOffLineApp.postCode);
        postCodeEdit.setFocusable(false);
        reasonEdit = (EditText)findViewById(R.id.reason);
        reasonEdit.setVisibility(View.INVISIBLE);
        resonNameText = (TextView)findViewById(R.id.resonName);
        resonNameText.setVisibility(View.INVISIBLE);
        submitOnoffLineBtn = (Button)findViewById(R.id.submit_onoff_line);
        submitOnoffLineBtn.setOnClickListener(new SubmitOnOffListener());
        operationSpinner.setOnItemSelectedListener(new ItemSelectListener());
    }

    /**
     * 退出操作。
     */
    class LogoutListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            OnOffLineActivity.this.finish();
        }
    }

    class ItemSelectListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position == 0){
                reasonEdit.setVisibility(View.INVISIBLE);
                resonNameText.setVisibility(View.INVISIBLE);
            }else{
                reasonEdit.setVisibility(View.VISIBLE);
                resonNameText.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    //提交上/下线操作
    class SubmitOnOffListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            long itemId =operationSpinner.getSelectedItemId();
            String reson = reasonEdit.getText().toString();
            OnOffLineData onOffLineData = new OnOffLineData();

            if(itemId == 0){//上线
                reasonEdit.setVisibility(View.INVISIBLE);
                resonNameText.setVisibility(View.INVISIBLE);
                onOffLineData.operation = OnOffLineData.ON_LINE;
                if(!"".equals(reson)){
                    SuperToastApi.showInitSuperToast(OnOffLineActivity.this, Constant.ON_LINE_NOT_TIP);
                }
                if("".equals(OnOffLineApp.fridgeCode)){
                    SuperToastApi.showInitSuperToast(OnOffLineActivity.this, Constant.FRIDGE_NOT_SCAN);
                    return;
                }
            }else{//下线
                onOffLineData.operation = OnOffLineData.OFF_LINE;
                if("".equals(reson)){
                    SuperToastApi.showInitSuperToast(OnOffLineActivity.this, Constant.OFF_LINE_TIP);
                    return;
                }
                if("".equals(OnOffLineApp.fridgeCode)){
                    SuperToastApi.showInitSuperToast(OnOffLineActivity.this, Constant.FRIDGE_NOT_SCAN);
                    return;
                }
            }

            onOffLineData.post_code = OnOffLineApp.postCode;
            onOffLineData.reason = reasonEdit.getText().toString();
            onOffLineData.serial_number = OnOffLineApp.fridgeCode;
            submitOnOffLine(onOffLineData);
        }
    }

    /**
     * 提交上线/下线数据到服务器。
     * @param onOffLineData
     */
    private void submitOnOffLine(OnOffLineData onOffLineData){
        Map<String,String> dataMap = onOffLineData.setData();
        OnOffLineApp.getApp().getNetService(this).submitOnOffLine(dataMap,OnOffLineApp.sessionId).subscribe(new Action1<String>() {
            @Override
            public void call(String fridgeResponse) {
                //加载数据成功
                if (fridgeResponse.equalsIgnoreCase(OnOffLineData.RESPONSE_SUCCESS)) {
                    EventBus.getDefault().post(new ComEvent("",ComEvent.ACTION_ONOFF_SUCCESS));
                }else if(fridgeResponse.equalsIgnoreCase(OnOffLineData.SERIAL_NUMBER_ERROR)){
                    EventBus.getDefault().post(new ComEvent("",ComEvent.ACTION_ONOFF_ERROR_SERIAL_NUM));
                }else{
                    EventBus.getDefault().post(new ComEvent("",ComEvent.ACTION_ONOFF_FAILUE));
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                EventBus.getDefault().post(new ComEvent("",ComEvent.ACTION_ONOFF_FAILUE));
            }
        });
    }

    /**
     * 获取传入参数。
     */
    private void getIntentExtra(){
        Intent intent = getIntent();
        String sessionId = intent.getStringExtra(Constants.SESSION_ID);
        String postCode = intent.getStringExtra(Constants.SCAN_POST_CODE);
        OnOffLineApp.sessionId = sessionId;
        OnOffLineApp.postCode = postCode;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ComServiceUtils.stopService(this);
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(ComEvent event) {
       if(ComEvent.ACTION_ONOFF_FRIDGE_SCAN == event.getActionType()) {//扫描电冰箱二维码成功
           SuperToastApi.showInitSuperToast(this, Constant.FRIDGE_SCAN_SUCCESS);
           OnOffLineApp.isFridgeScan = true;
           OnOffLineApp.fridgeCode = event.getMessage();
           serialNumberEdit.setText(OnOffLineApp.fridgeCode);//扫描成功设置显示值
       }else if(ComEvent.ACTION_ONOFF_SUCCESS == event.getActionType()){//数据提交成功
           SuperToastApi.showInitSuperToast(OnOffLineActivity.this, Constant.SUBMIT_SUCCESS);
           OnOffLineApp.fridgeCode = "";//清空操作
           serialNumberEdit.setText("");
           reasonEdit.setText("");
       }else if(ComEvent.ACTION_ONOFF_FAILUE == event.getActionType()){//数据提交失败
           SuperToastApi.showInitSuperToast(OnOffLineActivity.this, Constant.SUBMINT_FAILUE);
       }else if(ComEvent.ACTION_ONOFF_ERROR_SERIAL_NUM == event.getActionType()){//生产流水号错误
           SuperToastApi.showInitSuperToast(OnOffLineActivity.this,Constant.ERROR_SERIAL_NUMBER);
       }
    }
}
