package app.android.mingjiang.com.progresswheel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 备注：制作显示ProgressWheel的对话框。
 * 作者：wangzs on 2016/1/15 13:37
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class LoadingDialog {
    private static Dialog mDialog = null;
    private static ProgressWheel mProgressWheel = null;

    public static void showDialog(Context context, String text,boolean cancelable) {
        createObject(context,text,cancelable);
        mProgressWheel.startSpinning();
        mDialog.show();
    }

    private static void createObject(Context context,String text,boolean cancelable){
        closeCommentDialog(context);

        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用

        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setCancelable(cancelable);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_progress_wheel, null);
        mDialog.setContentView(layout);

        mProgressWheel = (ProgressWheel) layout.findViewById(R.id.pw_spinner);
        mProgressWheel.setText(text);

        Window dialogWindow = mDialog.getWindow();//
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.gravity = Gravity.CENTER;

        lp.width = d.getWidth() - 20; // 宽度

        dialogWindow.setAttributes(lp);
    }

    public static void closeCommentDialog(Context context) {
        if (mProgressWheel != null) {
            mProgressWheel.stopSpinning();
            mProgressWheel = null;
        }

        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
