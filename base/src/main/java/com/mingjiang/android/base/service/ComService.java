package com.mingjiang.android.base.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.mingjiang.android.base.R;
import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.util.Constants;
import com.mingjiang.android.base.util.SuperToastApi;

import java.io.IOException;
import java.security.InvalidParameterException;

import android_serialport_api.ComBean;
import android_serialport_api.SerialHelper;
import de.greenrobot.event.EventBus;

/**
 * 备注：读取串口数据服务。
 * 作者：wangzs on 2016/2/24 19:22
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class ComService extends Service{

    private final static String TAG = "ComService";
    private final static String STR_BAUD_RATE = "115200";
    private final static String STR_DEV_ONE = "/dev/ttyS2";
    private final static String STR_DEV_TWO = "/dev/ttyS3";
    SerialControl comA, comB;

    private static int activityCode = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        activityCode = intent.getIntExtra(Constants.ACTIVITY_CODE,0);
        initData();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        closeComPort();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void initData() {
        comA = new SerialControl();
        comB = new SerialControl();
        comA.setPort(STR_DEV_ONE);
        comB.setPort(STR_DEV_TWO);
        comA.setBaudRate(STR_BAUD_RATE);
        comB.setBaudRate(STR_BAUD_RATE);
        openComPort(comA);
        openComPort(comB);
    }

    private class SerialControl extends SerialHelper {
        public SerialControl(){
        }

        @Override
        protected void onDataReceived(final ComBean comRecData)
        {
            Log.e(TAG, "received data : " + new String(comRecData.bRec));
            if(ComEvent.ACTION_POST_SCAN == activityCode){//岗位扫描
                EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec), ComEvent.ACTION_POST_SCAN));
            }else if(ComEvent.ACTION_USER_SCAN == activityCode){//用户扫描
                EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec), ComEvent.ACTION_USER_SCAN));
            }else if(ComEvent.ACTION_ONOFF_FRIDGE_SCAN == activityCode){//上下线-冰箱扫描
                EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec), ComEvent.ACTION_ONOFF_FRIDGE_SCAN));
            }else if(ComEvent.ACTION_MATERIAL_USER_SCAN == activityCode){//物料管理-用户登录
                EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec), ComEvent.ACTION_MATERIAL_USER_SCAN));
            }else if(ComEvent.ACTION_INSTRUCTION_FRIDGE_SCAN == activityCode){//岗位指导书-冰箱扫描
                EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec), ComEvent.ACTION_INSTRUCTION_FRIDGE_SCAN));
            }else if(ComEvent.ACTION_ADDLIB_SCAN_CODE == activityCode){//一键入库-出库单扫描
                EventBus.getDefault().post(new ComEvent(new String(comRecData.bRec),ComEvent.ACTION_ADDLIB_SCAN_CODE));
            }
        }
    }

    /**
     * 打开串口。
     */
    private void openComPort(SerialHelper ComPort){
        try
        {
            ComPort.open();
            showMessage(R.string.com_start_success);
        } catch (SecurityException e) {
            showMessage(R.string.com_start_fail_security);
        } catch (IOException e) {
            showMessage(R.string.com_start_fail_io);
        } catch (InvalidParameterException e) {
            showMessage(R.string.com_start_fail_param);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 关闭串口。
     */
    public void closeComPort(){
        comA.close();
        comB.close();
    }

    private void showMessage(int sMsg)
    {
        //SuperToastApi.showInitSuperToast(this, getString(sMsg));
        Toast.makeText(getApplicationContext(),sMsg,Toast.LENGTH_LONG).show();
    }
}
