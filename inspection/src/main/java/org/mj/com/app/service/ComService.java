package org.mj.com.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.mj.com.app.eventbus.Event;
import org.mj.com.app.eventbus.MessageEvent;
import org.mj.com.app.utils.Constant;
import org.mj.com.app.utils.ServiceUtils;
import org.mj.com.inspection.R;

import java.io.IOException;
import java.security.InvalidParameterException;

import android_serialport_api.ComBean;
import android_serialport_api.SerialHelper;
import de.greenrobot.event.EventBus;

/*
* 串口服务类
* 这里可以控制串口的启动和串口数据的分发
* */
/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class ComService extends Service {

    private final static String TAG = "ComService";
    private final static String STR_BAUD_RATE= "115200";
    private final static String STR_DEV_ONE = "/dev/ttyS1";
    private final static String STR_DEV_TWO = "/dev/ttyS2";
    private final static String STR_DEV_THREE = "/dev/ttyS3";
    private final static String STR_DEV_FOUR = "/dev/ttyS4";
    SerialControl comA, comB,comC, comD;
    private static String activityCode = "";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        activityCode = intent.getStringExtra(ServiceUtils.ACTIVITY_CODE);
        initData();
        EventBus.getDefault().register(ComService.this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(ComService.this);
    }

    public void onEventBackgroundThread (Event event) {

    }
    public void initData() {
        comA = new SerialControl();
        comB = new SerialControl();
        comC = new SerialControl();
        comD = new SerialControl();
        comA.setPort(STR_DEV_ONE);
        comB.setPort(STR_DEV_TWO);
        comC.setPort(STR_DEV_THREE);
        comD.setPort(STR_DEV_FOUR);
        comA.setBaudRate(STR_BAUD_RATE);
        comB.setBaudRate(STR_BAUD_RATE);
        comC.setBaudRate(STR_BAUD_RATE);
        comD.setBaudRate(STR_BAUD_RATE);
        openComPort(comA);
        openComPort(comB);
        openComPort(comC);
        openComPort(comD);
    }

    private class SerialControl extends SerialHelper {
        public SerialControl(){
        }
        @Override
        protected void onDataReceived(final ComBean comRecData)
        {

            Log.e(TAG, "received data : " + new String(comRecData.bRec));
            if(Constant.POST_SCAN_ACTIVITY.equals(activityCode)){//岗位扫描
                EventBus.getDefault().post(new Event(new String(comRecData.bRec), Event.ACTION_POST_SCAN));
            }else if(Constant.USER_SCAN_ACTIVITY.equals(activityCode)){//用户扫描
                EventBus.getDefault().post(new MessageEvent(new String(comRecData.bRec), Event.ACTION_USER_SCAN));
            }else if(Constant.USER_CHECK_ACTIVITY.equals(activityCode)){//冰箱扫描
                EventBus.getDefault().post(new MessageEvent(new String(comRecData.bRec), Event.ACTION_CHECK_USER));
            }

        }
    }
    private void openComPort(SerialHelper comPort){
        try
        {
            comPort.open();
//            showMessage(R.string.com_start_success);
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
    private void showMessage(int sMsg)
    {
        Toast.makeText(this.getApplicationContext(),
                getString(sMsg), Toast.LENGTH_SHORT).show();
    }
}
