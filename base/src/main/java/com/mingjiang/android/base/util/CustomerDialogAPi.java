package com.mingjiang.android.base.util;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.EditText;

import app.android.mingjiang.com.herilyalertdialog.HerilyAlertDialog;
import app.android.mingjiang.com.herilyalertdialog.HerilyProgressDialog;
import com.mingjiang.android.base.R;

/**
 * 常见显示对话框Api。
 * 作者：wangzs on 2016/1/15 16:00
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class CustomerDialogAPi {

    private CustomerDialogAPi(){
        throw new UnsupportedOperationException("不能实例化");
    }
    /**
     *获取自定义的AlertDialog.Builder。
     */
    public static final HerilyAlertDialog.Builder createAlertDlgBuilder(Context context) {
        return new HerilyAlertDialog.Builder(context);
    }

    /**
     *获取自定义AlertDialog。
     */
    public static final HerilyProgressDialog createProgressDialog(Context context) {
        return new HerilyProgressDialog(context);
    }

    /**
     *显示标题、信息及操作对话框。
     */
    public static final void showTitleDialog(Context context,String title,
                                       String message, String neutralStr,
                                       boolean cancelable,DialogInterface.OnClickListener listener){
        HerilyAlertDialog.Builder builder = createAlertDlgBuilder(context);
        builder.setCancelable(cancelable);
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_dialog_alert);
        builder.setMessage(message);
        builder.setPositiveButton("确定", listener);//左侧按钮
        builder.setNegativeButton("取消", listener);//右侧按钮
        if(null != neutralStr && !"".equalsIgnoreCase(neutralStr)){
            builder.setNeutralButton(neutralStr, listener);//中间按钮
        }
        builder.show();
    }

    /**
     *显示标题、信息及操作对话框。
     */
    public static final void showTitleDialog(Context context,String title,boolean cancelable,
                                       String message,String positiveStr,String negativeStr,
                                       String neutralStr,DialogInterface.OnClickListener listener){
        HerilyAlertDialog.Builder builder = createAlertDlgBuilder(context);
        builder.setTitle(title);
        builder.setCancelable(cancelable);
        builder.setIcon(R.drawable.ic_dialog_alert);
        builder.setMessage(message);
        builder.setPositiveButton(positiveStr, listener);//左侧按钮
        builder.setNegativeButton(negativeStr, listener);//右侧按钮
        if(null != neutralStr && !"".equalsIgnoreCase(neutralStr)){
            builder.setNeutralButton(neutralStr, listener);//中间按钮
        }
        builder.show();
    }

    /**
     *显示标题、信息及操作对话框。
     */
    public static final void showTitleDialog(Context context,String title,boolean cancelable,
                                       String message,String positiveStr,String negativeStr,
                                       DialogInterface.OnClickListener listener){
        HerilyAlertDialog.Builder builder = createAlertDlgBuilder(context);
        builder.setTitle(title);
        builder.setCancelable(cancelable);
        builder.setIcon(R.drawable.ic_dialog_alert);
        builder.setMessage(message);
        builder.setPositiveButton(positiveStr, listener);//左侧按钮
        builder.setNegativeButton(negativeStr, listener);//右侧按钮
        builder.show();
    }

    /**
     * 输入文本信息对话框。
     */
    public static final void showInputDialog(Context context,String title,boolean cancelable,
                                             DialogInterface.OnClickListener listener){
        HerilyAlertDialog.Builder builder = createAlertDlgBuilder(context);
        builder.setTitle(title);
        builder.setCancelable(cancelable);
        final EditText edittext_Msg = new EditText(context);
        edittext_Msg.setGravity(Gravity.TOP);
        edittext_Msg.setLines(8);
        edittext_Msg.setTextColor(context.getResources().getColor(R.color.alertex_dlg_edit_text_color));
        edittext_Msg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.herily_alertex_dlg_textinput_drawable));
        builder.setView(edittext_Msg);
        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("取消", listener);
        builder.show();
    }

    
    /**
     * 显示仅有标题对话框。
     */
    public static final void showOnlyTiltleDialog(Context context,String title,boolean cancelable){
        HerilyAlertDialog.Builder builder = createAlertDlgBuilder(context);
        builder.setTitle(title);
        builder.setCancelable(cancelable);
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    /**
     * 显示单选数据列表。
     */
    public static final void showSingleListDialog(Context context,String title,boolean cancelable,
                                                  String[] contentList,
                                                  DialogInterface.OnClickListener listener){
        HerilyAlertDialog.Builder builder = createAlertDlgBuilder(context);
        builder.setTitle(title);
        builder.setCancelable(cancelable);
        builder.setSingleChoiceItems(contentList, 0, listener);
        builder.setPositiveButton("确定", listener);
        builder.setNegativeButton("取消", listener);
        builder.show();
    }

    /**
     * 显示多选数据列表。
     */
    public static final void showMultiListDialog(final Context context,String title,boolean cancelable,
                                                 final String[] contentList,
                                                 DialogInterface.OnMultiChoiceClickListener multiListener,
                                                 DialogInterface.OnClickListener listener){
        HerilyAlertDialog.Builder builder = createAlertDlgBuilder(context);
        builder.setIcon(R.drawable.ic_dialog_alert);
        builder.setTitle(title);
        builder.setCancelable(cancelable);
        boolean[] cks = new boolean[contentList.length];
        cks[0] = true;
        builder.setMultiChoiceItems(contentList, cks, multiListener);
        builder.setPositiveButton("确定", listener);
        builder.setNeutralButton("取消", listener);
        builder.show();
    }

    /**
     * 仅显示数据列表。
     */
    public static final void showListDialog(Context context,String title,boolean cancelable,String[] contentList){
        HerilyAlertDialog.Builder builder = createAlertDlgBuilder(context);
        builder.setTitle(title);
        builder.setCancelable(cancelable);
        builder.setIcon(R.drawable.ic_dialog_info);
        builder.setItems(contentList, null);
        builder.setPositiveButton("确定", null);
        builder.show();
    }
}