package app.android.mingjiang.com.logapi.recordlog.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 作者：wangzs on 2016/1/22 11:07
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class LaunchUtils {

    public static final String PACKAGE_NAME = "app.android.mingjiang.com.logshow";

    /**
     * 校验是否安装。
     * @param context
     * @return
     */
    public static boolean isAppInstalled(Context context)
    {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
        }catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }finally {
            if(packageInfo ==null){
                return false;
            }else{
                return true;
            }
        }
    }

    /**
     * 启动其他应用来显示出错信息。
     * @param msg
     */
    public static void startOtherActivity(Context context,String msg){
        Intent intent = context.getPackageManager().
                getLaunchIntentForPackage("app.android.mingjiang.com.logshow");
        if (intent != null) {
            String appName = getAppPackage(context);
            intent.putExtra("package",appName);
            intent.putExtra("error", msg);
            context.startActivity(intent);
        } else {
            Toast.makeText(context.getApplicationContext(),
                    "您没有安装显示出错信息应用，请本地查看出错信息,联系开发人员!",
                    Toast.LENGTH_LONG).show();
            slientInstall(context);             //先安装
            startOtherActivity(context,msg);    //再启动
        }

        /*Intent intent = new Intent();
        ComponentName cn = new ComponentName("app.android.mingjiang.com.logshow","app.android.mingjiang.com.logshow.ShowLogActivity");
        intent.setComponent(cn);
        intent.putExtra("error", msg);
        //intent.setAction("android.intent.action.MAIN");
        try {
            mContext.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(mContext, "没有该子APP，请下载安装", Toast.LENGTH_SHORT).show();
        }*/
    }

    /**
     * 安装apk文件。
     * @param context
     * @return
     */
    public static boolean slientInstall(Context context) {
        FileUtils.createFile(context); // 进行资源的转移 将assets下的文件转移到可读写文件目录下
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath() + "/logshow_temp.apk");

        boolean result = false;
        Process process = null;
        OutputStream out = null;
        System.out.println(file.getPath());
        if (file.exists()) {
            System.out.println(file.getPath() + "==");
            try {
                process = Runtime.getRuntime().exec("su");
                out = process.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(out);
                dataOutputStream.writeBytes("chmod 777 " + file.getPath()
                        + "\n"); // 获取文件所有权限
                dataOutputStream
                        .writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r "
                                + file.getPath()); // 进行静默安装命令
                // 提交命令
                dataOutputStream.flush();
                // 关闭流操作
                dataOutputStream.close();
                out.close();
                int value = process.waitFor();

                // 代表成功
                if (value == 0) {
                    Log.e("显示错误信息APK：", "安装成功！");
                    result = true;
                } else if (value == 1) { // 失败
                    Log.e("显示错误信息APK：", "安装失败！");
                    result = false;
                } else { // 未知情况
                    Log.e("显示错误信息APK：", "未知情况！");
                    result = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!result) {
                Log.e("hao", "root权限获取失败，将进行普通安装");
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
                context.startActivity(intent);
                result = true;
            }
        }

        return result;
    }

    /**
     * 获取app的包名。
     * @param context
     * @return
     */
    public static String getAppPackage(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
           return packageInfo.applicationInfo.packageName;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return "";
        }
    }

}
