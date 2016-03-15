package app.android.mingjiang.com.qrcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.mingjiang.android.base.event.ComEvent;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;

import android_serialport_api.ComBean;
import android_serialport_api.SerialHelper;
import app.android.mingjiang.com.qrcode.R;
import app.android.mingjiang.com.qrcode.utils.FinishCode2QRUtil;
import de.greenrobot.event.EventBus;

/*
* 串口服务类
* 这里可以控制串口的启动和串口数据的分发
* */
public class ComService extends Service {
    private final static String STR_BAUD_RATE1 = "9600";
    private final static String STR_BAUD_RATE2 = "9600";
    private final static String STR_DEV_ONE = "/dev/ttyS2";
    private final static String STR_DEV_TWO = "/dev/ttyS3";
    private final static String STR_DEV_THREE = "/dev/ttyS4";
    private final static String STR_DEV_FOUR = "/dev/ttyS5";
    SerialControl comOne, comTwo, comThree, comFour;
    public static boolean PRINT = true;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("ComService", "onCreate()");
        super.onCreate();
        initData();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventBackgroundThread(ComEvent event) {
        if (event.getActionType() == ComEvent.ACTION_PRINT) {
            print_code(event.getMessage());
        }
    }

    public void initData() {
        comOne = new SerialControl();
        comTwo = new SerialControl();
        comThree = new SerialControl();
        comFour = new SerialControl();
        comOne.setPort(STR_DEV_ONE);
        comTwo.setPort(STR_DEV_TWO);
        comThree.setPort(STR_DEV_THREE);
        comFour.setPort(STR_DEV_FOUR);
        comOne.setBaudRate(STR_BAUD_RATE1);
        comTwo.setBaudRate(STR_BAUD_RATE2);
        comThree.setBaudRate(STR_BAUD_RATE1);
        comFour.setBaudRate(STR_BAUD_RATE2);
        Log.e("STR_BAUD_RATE2", STR_BAUD_RATE2);
        openComPort(comOne);
        openComPort(comTwo);
        openComPort(comThree);
        openComPort(comFour);
        //print_code("asd123");
    }

    private class SerialControl extends SerialHelper {
        public SerialControl() {
        }

        //        从串口收取数据
        @Override
        protected void onDataReceived(final ComBean comRecData) {
            if (comRecData.sComPort.equals(STR_DEV_THREE)) {
                Log.e("onDataReceived bRec", Arrays.toString(comRecData.bRec));
                String code = new String(comRecData.bRec);
                Log.e("onDataReceived   code", code);
                EventBus.getDefault().post(new ComEvent(code, ComEvent.ACTION_GET_CODE));
                code = FinishCode2QRUtil.toQECodeAuto31(code);
                print_code(code);
            }
        }

        @Override
        public void stopSend() {
            super.stopSend();
        }
    }


    private void openComPort(SerialHelper ComPort) {
        try {
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

    private void showMessage(int sMsg) {
        Toast.makeText(this.getApplicationContext(),
                getString(sMsg), Toast.LENGTH_LONG).show();
    }


    private void print_code(String value) {
        Log.e("print_code", value);
        Log.e("ComService.PRINT", String.valueOf(ComService.PRINT));
        if (ComService.PRINT) {
//            value = PrintLabel.QECode(value);
            value = "ABC123";
            comOne.sendTxt(value);
            comTwo.sendTxt(value);
            comThree.sendTxt(value);
            comFour.sendTxt(value);
        }
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }
}
