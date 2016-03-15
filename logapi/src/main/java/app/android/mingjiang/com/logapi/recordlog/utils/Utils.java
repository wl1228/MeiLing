package app.android.mingjiang.com.logapi.recordlog.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.io.File;

/**
 * 作者：wangzs on 2016/1/11 16:07
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public final class Utils {
    private static String sAppName = "";

    public static File getAppCacheDir(Context context, String subName) {
        if (!sdAvailible()) {
            return null;
        }
        File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        //File sd = Environment.getExternalStorageDirectory();
        File dir = new File(sd, getAppName(context));
        File sub = new File(dir, subName);
        sub.mkdirs();
        return sub;
    }

    public static boolean sdAvailible() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

    public static String encrypt(String str) {
        // TODO: encrypt data.
        return str;
    }

    public static String buildSystemInfo(Context context) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\n");
        buffer.append("#-------system info-------");
        buffer.append("\n");
        buffer.append("version-name:");
        buffer.append(Utils.getVersionName(context));
        buffer.append("\n");
        buffer.append("version-code:");
        buffer.append(Utils.getVersionCode(context));
        buffer.append("\n");
        buffer.append("system-version:");
        buffer.append(Utils.getSystemVersion(context));
        buffer.append("\n");
        buffer.append("model:");
        buffer.append(Utils.getModel(context));
        buffer.append("\n");
        buffer.append("density:");
        buffer.append(Utils.getDensity(context));
        buffer.append("\n");
        buffer.append("imei:");
        buffer.append(Utils.getIMEI(context));
        buffer.append("\n");
        buffer.append("screen-height:");
        buffer.append(Utils.getScreenHeight(context));
        buffer.append("\n");
        buffer.append("screen-width:");
        buffer.append(Utils.getScreenWidth(context));
        buffer.append("\n");
        buffer.append("unique-code:");
        buffer.append(Utils.getUniqueCode(context));
        buffer.append("\n");
        buffer.append("mobile:");
        buffer.append(Utils.getMobile(context));
        buffer.append("\n");
        buffer.append("imsi:");
        buffer.append(Utils.getProvider(context));
        buffer.append("\n");
        buffer.append("isWifi:");
        buffer.append(Utils.isWifi(context));
        buffer.append("\n");
        return buffer.toString();
    }

    /**
     * 获取Pad唯一标示符---设备Id号和设备mac地址。
     * @param context
     * @return
     */
    public static String getUniqueCode(Context context) {
        if (context == null)
            return null;
        String imei = getIMEI(context);
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mUniqueCode = imei + "_" + info.getMacAddress();
        return mUniqueCode;
    }

    /**
     * 获取设备是否连接WiFi设备。
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 获取电话号码。
     * @param context
     * @return
     */
    public static String getMobile(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    /**
     * 获取网络运营商Id。(IMSI for GSM)
     * @param context
     * @return
     */
    public static String getProvider(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }

    /**
     * 获取设备Id号。
     * @param context
     * @return
     */
    public static final String getIMEI(final Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    /**
     * 获取屏幕尺寸---宽。
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕尺寸---高。
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * 获取系统版本。
     * @param context
     * @return
     */
    public static String getSystemVersion(Context context) {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 设备类型。（MediaPad0FHD）
     * @param context
     * @return
     */
    public static String getModel(Context context) {
        return android.os.Build.MODEL != null ? android.os.Build.MODEL.replace(
                " ", "") : "unknown";
    }


    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo pinfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            return pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return "";
    }

    /**
     * 系统日志信息保存文件夹名称。
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        if (TextUtils.isEmpty(sAppName)) {
            sAppName = "com_meiling_log";
            try {
                PackageInfo pinfo = context.getPackageManager().getPackageInfo(
                        context.getPackageName(),
                        PackageManager.GET_CONFIGURATIONS);
                String packageName = pinfo.packageName;
                if (!TextUtils.isEmpty(packageName)) {
                    sAppName = packageName.replaceAll("\\.", "_");
                }
            } catch (PackageManager.NameNotFoundException e) {
            }
        }

        return sAppName;
    }

    /**
     * 软件版本编号。
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pinfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            return pinfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return 1;
    }

}

