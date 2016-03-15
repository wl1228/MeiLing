package org.mj.com.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjiang.android.base.util.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.mj.com.app.asynctask.RequireAsyncTask;
import org.mj.com.app.asynctask.SubmitAsyncTask;
import org.mj.com.app.eventbus.Event;
import org.mj.com.app.utils.Constant;
import org.mj.com.app.utils.ServiceUtils;
import org.mj.com.app.utils.SharedPrefsUtil;
import org.mj.com.inspection.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import de.greenrobot.event.EventBus;
/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class CheckActivity extends Activity {

    List<TextView> mList;//用来存放人员检测结果
    List<TextView> mDeviceList;//用来存放设备检测结果
    List<TextView> mIndexList;//用来存放指示当前岗位的textview
    TextView textView11,textView12,textView13,textView46,myPostTextView,mDeviceFiveTest,mTextviewLocation,mTextViewrReason;
    String mProduct_id;
    Spinner spinner14,spinner43;
    String mUserName="null";
    int mClasses=1;
    String mResult_code="";
    String mItemsRessonCode="";//记录不合格原因
    String mItemsPositionCode="";//记录不合格的位置
//    {"statics_result": false, "bad_position": "\u53d8\u6e29\u80c6", "bad_reason": "\u7bb1\u4f53\u6b63\u9762\uff0824\u9762\u3001\u6881\uff09\u78d5\u78b0\u3001\u5212\u75d5\u3001\u6298\u3001\u5916\u7ffb\u3001\u6389\u6f06",
//            "pre_result": "2", "wash_result1": false, "product_id": "ML9000001", "wash_result3": false, "wash_result2": false, "leak_result4": false,
//            "perfusion_result": false, "leak_result1": false, "general_result": false, "leak_result3": false, "leak_result2": false, "state": "1",
//            "shock_result": false, "wifi_result": false, "screen_result": false, "infrared_result": false, "property3_result": false, "dynamicd_result": false}
    String[] mCheckNameKey=new String[]{ "pre_result","leak_result1","leak_result2","general_result", "screen_result",  "leak_result3", "leak_result4","wash_result1", "wash_result2", "wash_result3"};
    //1,箱发检验 2. 检漏1 3.检漏2  4.总装  5.试屏 6. 检漏3 7.检漏4  8.清洗背面 9.清洗正面  10 清洗终检
    String[] mCheckName=new String[]{"箱发检验","检漏检验一","检漏检验二","总装检验","试屏检验","检漏检验三","检漏检验四","清洗检验（背面）","清洗检验（正面）","清洗检验（终检）"};
    String[] mPostNames=new String[]{"箱发检验1-1","箱发检验1-2","检漏检验1-1","检漏检验1-2","检漏检验2","总装检验1-1","总装检验1-2","试屏检验","检漏检验3","检漏检验4","清洗检验（背面）","清洗检验（正面）","清洗检验（终检）"};
    String[] mClassesName=new String[]{"甲班","乙班"};
    String[] mDeviceResutlKey=new String[]{"perfusion_result","property3_result","shock_result","infrared_result","dynamicd_result","dynamicd_result","wifi_result"};
    int mPost;
    int mCheckPost;
    int[] mResult=new int[10];
    int[] mDeviceResult=new int[7];
    AlertDialog mQualifiedDialog,mDisqualifiedDialog,mChoseResonDialog,mPositionDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        mUserName=getIntent().getStringExtra("username");
        //注册eventbus
        EventBus.getDefault().register(this);
        //启动产品扫描服务
        ServiceUtils.startService(CheckActivity.this, Constant.USER_CHECK_ACTIVITY);
        initViewForFind();
        ininViewForSpinner();
        initViewForData();
        initViewForEvent();
    }
    private void initViewForMyPost() {
        switch (mPost){
            case 0:
            case 1:
                mCheckPost=0;
                break;
            case 2:
            case 3:
                mCheckPost=1;
                break;
            case 4:
                mCheckPost=2;
                break;
            case 5:
            case 6:
                mCheckPost=3;
                break;
            case 7:
                mCheckPost=4;
                break;
            case 8:
                mCheckPost=5;
                break;
            case 9:
                mCheckPost=6;
                break;
            case 10:
                mCheckPost=7;
                break;
            case 11:
                mCheckPost=8;
                break;
            case 12:
                mCheckPost=9;
                break;
        }
        myPostTextView=mList.get(mCheckPost);
        initViewForIndex();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPost= SharedPrefsUtil.getInt(CheckActivity.this, SharedPrefsUtil.CHECK_POST_CODE);
        mClasses=SharedPrefsUtil.getInt(CheckActivity.this, SharedPrefsUtil.CHECK_CLASSES_CODE);
        if(mPost==0){//本地文件不存在
            mPost=0;
            spinner14.setSelection(mPost);
        }else {
            spinner14.setSelection(mPost);
        }
        if(mClasses==0){//本地文件不存在
            mClasses=0;
            spinner43.setSelection(mClasses);
        }else {
            spinner43.setSelection(mClasses);
        }
        initViewForMyPost();
    }

    private void ininViewForSpinner() {
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,R.layout.simple_spinner_item_mycreat, mPostNames);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,R.layout.simple_spinner_item_mycreat, mClassesName);
        spinner14.setAdapter(arrayAdapter1);
        spinner43.setAdapter(arrayAdapter2);
        //注册事件
        spinner14.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent;
                mPost = position;
                SharedPrefsUtil.setValue(CheckActivity.this, SharedPrefsUtil.CHECK_POST_CODE, mPost);
                initViewForMyPost();
                Toast.makeText(getApplicationContext(), "当前岗位为：" + spinner.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "没有改变的处理", Toast.LENGTH_LONG).show();
            }

        });
        //注册事件
        spinner43.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent;
                mClasses = position;
                SharedPrefsUtil.setValue(CheckActivity.this, SharedPrefsUtil.CHECK_CLASSES_CODE, mClasses);
                //  initViewForMyPost();
                Toast.makeText(getApplicationContext(), "当前班次为：" + spinner.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "没有改变的处理", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void initViewForEvent() {
        textView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void initViewForData() {
        for (int i=0;i<10;i++){
            TextView textView=mList.get(i);
            textView.setText(mCheckName[i]);
        }

    }
    private void initViewForResultData() {
        for (int i=0;i<10;i++){
            TextView textView=mList.get(i);
            if (mResult[i]==1){
                textView.setBackgroundResource(R.color.green);
            }else if(mResult[i]==-1){
                textView.setBackgroundResource(R.color.red);
            }else{
                textView.setBackgroundResource(R.color.gray);
            }
        }
    }
    private void initViewForDeviceData() {
        for (int i=0;i<7;i++){
            TextView textView=mDeviceList.get(i);
            if (mDeviceResult[i]==1){
                textView.setBackgroundResource(R.color.green);
            }else if(mDeviceResult[i]==-1){
                textView.setBackgroundResource(R.color.red);
            }else{
                textView.setBackgroundResource(R.color.gray);
            }
        }
    }
    private void initViewForIndex() {
        for (int i=0;i<10;i++){
            TextView textView=mIndexList.get(i);
            if (i==mCheckPost){
                textView.setVisibility(View.VISIBLE);
            }else{
                textView.setVisibility(View.INVISIBLE);
            }
        }

    }


    public void onEventMainThread(Event event) {

        if (event.getActionType() == Event.ACTION_CHECK_USER) {
            String postCode = event.getMessage();
            Log.d("show post code message:", postCode);//显示岗位编码数据
            SharedPrefsUtil.setValue(this, SharedPrefsUtil.CHECK_POST_CODE, mPost);//将PostCode保存本地
            startDataDownLoad(postCode);
        }
    }
    /**
     * 启动冰箱扫描。
     * @param postCode
     */
    public void startDataDownLoad(String postCode){

        if (postCode.startsWith("result")){
            startResultAffirm(postCode.split(":")[1]);
        }else if (postCode.equals("submit")){
            commitCheckData();
        }else if (postCode.startsWith("reason1")){
            startPositionScan(postCode.split(":")[1]);
        }else if (postCode.startsWith("reason")){
            startReasonAffirm(postCode.split(":")[1]);
        } else if (postCode.startsWith("position")){
            if (!"".equals(mItemsRessonCode)){
                mItemsPositionCode=postCode.split(":")[1];
                startReasonAffirm(mItemsRessonCode);
            }else {
                Toast.makeText(CheckActivity.this, "请扫描出错原因", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(CheckActivity.this, "正在获取产品信息", Toast.LENGTH_SHORT).show();
            mProduct_id=postCode;
            //获取冰箱信息的时候需要将岗位上传，获取错误信息
            String url = Config.getBaseUrl(CheckActivity.this)+Constant.PRODUCT_URL + postCode+"/"+(mPost+1);
            new RequireAsyncTask(new RequireAsyncTask.OnResultListener() {
                @Override
                public void onResultListener(String result) {
                    //在此处判断网路结果，如结果正确，则跳转，否则提示重新输入
                    if (result != null) {
                        try {
                            //在得到冰箱数据后，先将状态设为未检
                            textView46.setText("当前状态：未检");
                            mTextviewLocation.setText("");
                            mTextViewrReason.setText("");
                            JSONObject jsonObject = new JSONObject(result);
                            textView11.setText("当前产品：" + jsonObject.getString("product_id"));
                            if ("1".equals(jsonObject.getString("state"))) {
                                textView12.setText("产品状态:在线生产");
                            } else if ("2".equals(jsonObject.getString("state"))) {
                                textView12.setText("产品状态:下线维修");
                            } else {
                                textView12.setText("产品状态:报废");
                            }
//                            "bad_position": "变温胆",
//                             "bad_reason": "箱体正面（24面、梁）磕碰、划痕、折、外翻、掉漆",
                            if(jsonObject.has("bad_position")){
                                mTextviewLocation.setText("位置："+jsonObject.getString("bad_position"));
                            }
                            if (jsonObject.has("bad_reason")){
                                mTextViewrReason.setText("原因："+jsonObject.getString("bad_reason"));
                            }
                            for (int i = 0; i < 10; i++) {
                                if ("1".equals(jsonObject.getString(mCheckNameKey[i]))) {
                                    mResult[i] = 1;
                                } else if ("2".equals(jsonObject.getString(mCheckNameKey[i]))) {
                                    mResult[i] = -1;
                                } else {
                                    mResult[i] = 0;
                                }
                            }
                            initViewForResultData();
                            boolean flag=true;
                            for (int i=0;i<7;i++){
                                if ("1".equals(jsonObject.getString(mDeviceResutlKey[i]))) {
                                    mDeviceResult[i] = 1;
                                } else if ("2".equals(jsonObject.getString(mDeviceResutlKey[i]))) {
                                    mDeviceResult[i] = -1;
                                    flag=false;
                                } else {
                                    mDeviceResult[i] = 0;
                                    flag=false;
                                }
                            }
                            initViewForDeviceData();
                            if (flag){mDeviceFiveTest.setBackgroundResource(R.color.green);}else {mDeviceFiveTest.setBackgroundResource(R.color.red);}
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CheckActivity.this, "获取产品信息失败，请重新扫码", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(CheckActivity.this, "获取产品信息失败，请重新扫码", Toast.LENGTH_SHORT).show();
                    }
                }
            }).execute(url);
        }
    }

    public void startResultAffirm(String resultCode) {
        if ("1".equals(resultCode)){
            showQualifiedDialog();
        }else {
            showChoseReasonDialog();
        }

    }
    public void startReasonAffirm(String resultCode) {
        mResult_code="2";
        mItemsRessonCode=resultCode;
        showDisqualifiedDialog();
    }
    public void startPositionScan(String resultCode) {
        mResult_code="2";
        mItemsRessonCode=resultCode;
        showPositionDialog();
    }
    /**
     * 显示合格对话框。
     */
    private void showQualifiedDialog() {
        mResult_code="1";
        mItemsRessonCode="";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.submit);
        builder.setTitle("检测结果合格");
        builder.setMessage("产品检测合格，可扫码提交或做其他选择");
        builder.setPositiveButton("提交",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        commitCheckData();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("重检",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        initForCheckData();
                        dialog.dismiss();
                    }
                });
        mQualifiedDialog=builder.create();
        mQualifiedDialog.show();
    }
    /**
     * 显示位置选择对话框。
     */
    private void showPositionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择位置");
        builder.setMessage("产品检测不合格（" + mItemsRessonCode + "），请选择出错位置");
        builder.setNegativeButton("重检",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        initForCheckData();
                        dialog.dismiss();
                    }
                });
        mPositionDialog=builder.create();
        mPositionDialog.show();
    }
    /**
     * 显示不合格对话框。
     */
    private void showDisqualifiedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.submit);
        builder.setTitle("检测结果不合格");
        builder.setMessage("产品检测不合格，问题原因：" + mItemsPositionCode + "    位置：" + mItemsPositionCode);
        builder.setPositiveButton("提交",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        commitCheckData();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("重检",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        initForCheckData();
                        dialog.dismiss();
                    }
                });
        mDisqualifiedDialog=builder.create();
        mDisqualifiedDialog.show();
    }
    /**
     * 重新初始化原因，位置
     */
    private void initForCheckData() {
        mItemsRessonCode="";
        mItemsPositionCode="";
    }
    /**
     * 显示选择原因对话框。
     */
    private void showChoseReasonDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("检测结果不合格");
        builder.setMessage("产品检测不合格，请扫码选择原因");
        builder.setNegativeButton("重检",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //做提交操作
                        dialog.dismiss();
                    }
                });
        mChoseResonDialog=builder.create();
        mChoseResonDialog.show();
    }
    /**
     * 提交数据。
     */
    private void commitCheckData() {
        //做提交操作
        Toast.makeText(CheckActivity.this, "正在提交", Toast.LENGTH_SHORT).show();
        try {
            mQualifiedDialog.dismiss();
        }catch (Exception e){
        }
        try {
            mDisqualifiedDialog.dismiss();
        }catch (Exception e){
        }
        try {
            mChoseResonDialog.dismiss();
        }catch (Exception e){
        }
        try {
            mPositionDialog.dismiss();
        }catch (Exception e){
        }
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        String submitData="{'product_id':'"+mProduct_id+"','post':'"+(mPost+1)+"','person':'"+mUserName+"','classes':'"+(mClasses+1)+
                "','time':'"+date+"','result':'"+mResult_code+"','items_codes':'"+mItemsRessonCode+"','position_id':'"+mItemsPositionCode+"'}";
        System.out.println("------submitdata:" + submitData);
        //submitdata:{'product_id':'cp685321','post':'4','person':'ML12345678','classes':'ML12345678','time':'2015-12-14 01:14:18','result':'2','items_codes':'XF-M001'}
        new SubmitAsyncTask(new SubmitAsyncTask.OnReasonListener(){
            @Override
            public void onReasonListener(boolean result) {
                if (result==true){
                    Toast.makeText(CheckActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    if ("1".equals(mResult_code)){
                        myPostTextView.setBackgroundResource(R.color.green);
                        textView46.setText("当前状态：检测合格");
                    }else{
                        myPostTextView.setBackgroundResource(R.color.red);
                        textView46.setText("当前状态：检测不合格");
                    }
                }else{
                    Toast.makeText(CheckActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                    textView46.setText("当前状态：提交失败");
                }
                initForCheckData();
            }
        }).execute(Config.getBaseUrl(CheckActivity.this)+Constant.RESULT_URL,submitData);
    }
    private void initViewForFind() {
        textView11=(TextView)findViewById(R.id.activity_check_text11);
        textView12=(TextView)findViewById(R.id.activity_check_text12);
        textView13=(TextView)findViewById(R.id.activity_check_text13);
        textView46=(TextView)findViewById(R.id.activity_check_text45);
        spinner14=(Spinner)findViewById(R.id.activity_check_spinner14);
        spinner43=(Spinner)findViewById(R.id.activity_check_spinner43);

        mList=new ArrayList<>();
        mList.add((TextView)findViewById(R.id.activity_check_text21));
        mList.add((TextView)findViewById(R.id.activity_check_text22));
        mList.add((TextView)findViewById(R.id.activity_check_text23));
        mList.add((TextView)findViewById(R.id.activity_check_text24));
        mList.add((TextView)findViewById(R.id.activity_check_text25));
        mList.add((TextView)findViewById(R.id.activity_check_text26));
        mList.add((TextView)findViewById(R.id.activity_check_text27));
        mList.add((TextView)findViewById(R.id.activity_check_text28));
        mList.add((TextView)findViewById(R.id.activity_check_text29));
        mList.add((TextView)findViewById(R.id.activity_check_text2a));


        mIndexList=new ArrayList<>();
        mIndexList.add((TextView)findViewById(R.id.activity_check_text01));
        mIndexList.add((TextView)findViewById(R.id.activity_check_text02));
        mIndexList.add((TextView)findViewById(R.id.activity_check_text03));
        mIndexList.add((TextView)findViewById(R.id.activity_check_text04));
        mIndexList.add((TextView)findViewById(R.id.activity_check_text05));
        mIndexList.add((TextView)findViewById(R.id.activity_check_text06));
        mIndexList.add((TextView)findViewById(R.id.activity_check_text07));
        mIndexList.add((TextView)findViewById(R.id.activity_check_text08));
        mIndexList.add((TextView)findViewById(R.id.activity_check_text09));
        mIndexList.add((TextView)findViewById(R.id.activity_check_text0a));

        mDeviceList=new ArrayList<>();
        mDeviceList.add((TextView)findViewById(R.id.activity_check_device21));
        mDeviceList.add((TextView)findViewById(R.id.activity_check_device22));
        mDeviceList.add((TextView)findViewById(R.id.activity_check_device31));
        mDeviceList.add((TextView)findViewById(R.id.activity_check_device32));
        mDeviceList.add((TextView)findViewById(R.id.activity_check_device33));
        mDeviceList.add((TextView)findViewById(R.id.activity_check_device34));
        mDeviceList.add((TextView)findViewById(R.id.activity_check_device35));

        mDeviceFiveTest=(TextView)findViewById(R.id.activity_check_device23);
        mTextviewLocation=(TextView)findViewById(R.id.activity_check_location);
        mTextViewrReason=(TextView)findViewById(R.id.activity_check_reason);
        TextView textView41=(TextView)findViewById(R.id.activity_check_text41);
        textView41.setText("工号:"+mUserName);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ServiceUtils.stopService(CheckActivity.this);
    }
}
