package app.android.mingjiang.com.logapi.recordlog.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：wangzs on 2016/1/11 16:07
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class LogUtils {
        public static boolean DEBUG = true;
        public static boolean CRASH_SAVE_2_FILE = true;
        public static boolean CRASH_UPLOAD_2_NETWORK = false;

        public static final int LOG_TYPE_2_LOGCAT = 0x01;
        public static final int LOG_TYPE_2_FILE = 0x02;
        public static final int LOG_TYPE_2_FILE_AND_LOGCAT = 0x03;
        public static final int LOG_TYPE_2_NETWORK = 0x04;

        public final static String LOG_CACHE_DIRECTORY_NAME = "log";
        public final static String CRASH_CACHE_DIRECTORY_NAME = "crash";
        private static String sLogFileName = null;
        private static String sCrashFileName = null;

        public static File getLogDir(Context context) {
            return Utils.getAppCacheDir(context, LOG_CACHE_DIRECTORY_NAME);
        }

        public static File getCrashDir(Context context) {
            return Utils.getAppCacheDir(context, CRASH_CACHE_DIRECTORY_NAME);
        }

    /**
     * 记录操作日志信息。
     * @param context
     * @return
     */
    public static String getLogFileName(Context context) {
        Date date = new Date();
        if (TextUtils.isEmpty(sLogFileName)) {
            StringBuffer fileName = new StringBuffer();
            fileName.append("log_")
                    .append(new SimpleDateFormat("yyyy-MM-dd").format(date))
                    .append(".txt");
            File sub = new File(getLogDir(context), fileName.toString());
            sLogFileName = sub.getAbsolutePath();
        }

        return sLogFileName;
    }

    /**
     * 记录崩溃日志信息。
     * @param context
     * @return
     */
    public static String getCrashFileName(Context context) {
        Date date = new Date();
        if (TextUtils.isEmpty(sCrashFileName)) {
            StringBuffer fileName = new StringBuffer();
            fileName.append("crash_")
                    .append(new SimpleDateFormat("yyyy-MM-dd").format(date))
                    .append(".txt");
            File sub = new File(getCrashDir(context),fileName.toString());
            sCrashFileName = sub.getAbsolutePath();
        }

        return sCrashFileName;
    }
}

