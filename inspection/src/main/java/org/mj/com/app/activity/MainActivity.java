package org.mj.com.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mingjiang.android.base.util.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.mj.com.app.asynctask.RequireAsyncTask;
import org.mj.com.app.eventbus.Event;
import org.mj.com.app.utils.Constant;
import org.mj.com.app.utils.NetCheckUtils;
import org.mj.com.app.utils.ServiceUtils;
import org.mj.com.app.utils.SharedPrefsUtil;
import org.mj.com.inspection.R;

import de.greenrobot.event.EventBus;
/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MainActivity extends Activity {


    EditText mEdittext1,mEdittext2;
    Button mButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册eventbus
        EventBus.getDefault().register(this);
        initForIsNetWork();
        initViewForFind();
        initViewForEvent();
    }
    private void initForIsNetWork() {
        if(!NetCheckUtils.isNetworkAvaiable(MainActivity.this)&&!NetCheckUtils.isWifiActive(MainActivity.this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
//          builder.setIcon(R.drawable.notification);
            builder.setTitle("网络异常");
            String message = "您的网络连接异常，请检查网络后重试";
            builder.setMessage(message);
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    private void initViewForFind() {
        mEdittext1=(EditText)findViewById(R.id.activity_main_edittext1);
        mEdittext2=(EditText)findViewById(R.id.activity_main_edittext2);
        mButton=(Button)findViewById(R.id.activity_main_button1);
    }
    private void initViewForEvent() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "正在登陆", Toast.LENGTH_SHORT).show();
                if("123".equals(mEdittext1.getText().toString())&&"123".equals(mEdittext2.getText().toString())){
                    Intent intent = new Intent(MainActivity.this,CheckActivity.class);
                    intent.putExtra("username", mEdittext1.getText().toString());
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this,"登录失败，请重新扫码或输入工号",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 显示选择对话框。
     * @param postCode
     */
    private void showSelectDialog(final Context context ,final String postCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.meiling);
        builder.setTitle("选择对话框");
        builder.setMessage("该岗位编码已存在，是否重新扫描？");
        builder.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //启动扫描服务
                        ServiceUtils.startService(context, Constant.POST_SCAN_ACTIVITY);
                    }
                });
        builder.setNegativeButton("否",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        startUserScanActivity(postCode);
                    }
                });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String postCode = SharedPrefsUtil.getString(this, SharedPrefsUtil.POST_CODE);
        if("".equals(postCode) || postCode == null){//本地文件不存在，则启动扫描服务
            //启动扫描服务
            ServiceUtils.startService(this,Constant.POST_SCAN_ACTIVITY);
        }else { //本地文件存在，则提示是否重新扫描
            showSelectDialog(this,postCode);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ServiceUtils.stopService(this);
    }

    public void onEventMainThread(Event event) {

        if (event.getActionType() == Event.ACTION_POST_SCAN) {
            String postCode = event.getMessage();
            Log.d("show post code message:", postCode);//显示岗位编码数据
            SharedPrefsUtil.setValue(this, SharedPrefsUtil.POST_CODE, postCode);//将PostCode保存本地
            startUserScanActivity(postCode);
        }
    }

    /**
     * 启动产品扫描界面。
     * @param postCode
     */
    public void startUserScanActivity(String postCode){
        Toast.makeText(MainActivity.this, "正在登陆", Toast.LENGTH_SHORT).show();
        postCode=postCode.replace("+","%2b");//将+进行转义
        String url= Config.getBaseUrl(MainActivity.this)+Constant.LOGIN_URL+postCode+"&context=%7b%22verify%22:%200%7d";
                new RequireAsyncTask(new RequireAsyncTask.OnResultListener() {
                    @Override
                    public void onResultListener(String result) {
                        //在此处判断网路结果，如结果正确，则跳转，否则提示重新输入
                        if (result!=null) {
                            String userCode="";
                            try {
                                JSONObject jsonObject=new JSONObject(result);
                                userCode=jsonObject.getString("login");
                                Intent intent = new Intent(MainActivity.this,CheckActivity.class);
                                intent.putExtra("username", userCode);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this,"登录失败，请重新扫码或输入工号",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(MainActivity.this,"登录失败，请重新扫码或输入工号",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute(url);
    }
    @Override
    public void onBackPressed() {
        ServiceUtils.stopService(this);
        super.onBackPressed();
    }
}

