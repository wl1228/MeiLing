package app.android.mingjiang.com.logapi.recordlog.log.task;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.android.mingjiang.com.logapi.recordlog.utils.FileUtils;
import app.android.mingjiang.com.logapi.recordlog.utils.LogUtils;
import app.android.mingjiang.com.logapi.recordlog.utils.Utils;

/**
 * 作者：wangzs on 2016/1/11 16:06
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class LogTask implements Runnable {
    private Context mContext;
    private String mTag;
    private String mMsg;
    private int mLogType;

    public LogTask(Context context, String tag, String msg, int logType) {
        mContext = context;
        mMsg = msg;
        mTag = tag;
        mLogType = logType;
    }

    @Override
    public void run() {
        if (mContext == null || TextUtils.isEmpty(mMsg)
                || TextUtils.isEmpty(mTag)) {
            return;
        }

        if ((mLogType & LogUtils.LOG_TYPE_2_LOGCAT) > 0) {
            Log.d(mTag, mMsg);
        }

        if ((mLogType & LogUtils.LOG_TYPE_2_FILE) > 0) {
            log2File("ms:" + System.currentTimeMillis() + " [" + mTag + "]"
                    + mMsg);
        }

        if ((mLogType & LogUtils.LOG_TYPE_2_NETWORK) > 0) {
            log2Network(mTag, mMsg);
        }
    }

    /**
     * 保存日志信息到本地文件。
     * @param msg
     */
    private void log2File(String msg) {
        if (Utils.sdAvailible()) {
            try {
                String systemInfo = "\n";
                File file = new File(LogUtils.getLogFileName(mContext));
                if (!file.exists() || file.isDirectory()) {//如果文件不存在或文件为文件夹
                    FileUtils.delete(file);
                    file.createNewFile();
                    systemInfo = Utils.buildSystemInfo(mContext);
                }

                String lineSeparator = System.getProperty("line.separator");
                if (lineSeparator == null) {
                    lineSeparator = "\n";
                }

                // Encode and encrypt the message.
                FileOutputStream trace = new FileOutputStream(file, true);
                OutputStreamWriter writer = new OutputStreamWriter(trace,
                        "utf-8");
                writer.write(Utils.encrypt(buildLog(msg, systemInfo)));
                writer.flush();

                trace.flush();
                trace.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String buildLog(String msg, String systemInfo) {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        sb.append(systemInfo);
        sb.append("\n");
        sb.append(new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss").format(date));
        sb.append(msg);
        sb.append("\n");

        return sb.toString();
    }

    private void log2Network(String tag, String msg) {
        //将数据上传到服务器端

    }
}

