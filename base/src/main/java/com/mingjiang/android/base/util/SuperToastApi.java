package com.mingjiang.android.base.util;

import android.content.Context;
import android.graphics.Color;

import com.mingjiang.android.base.R;

import app.android.mingjiang.com.supertoasts.SuperToast;

/**
 * 备注:SuperToast显示API。
 * 作者：wangzs on 2016/1/14 09:11
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class SuperToastApi {

    private SuperToastApi(){
        throw new UnsupportedOperationException("不能实例化");
    }
    /**
     * 自定义显示SuperToast。
     * @param message:显示内容；
     * @param textSize：显示文字大小。建议取值在10-30之间；
     * @param background:参考SuperToast.Background中的值；
     * @param duration:显示时间长短，建议取值1500-4500之间；
     * @param iconId:Toast上的显示图标Id号。
     */
    public static final void showCustomerSuperToast(Context context,
                                            String message,
                                            int textSize,
                                            int background,
                                            int duration,
                                            int iconId
                                            ){
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FADE);
        superToast.setDuration(duration);
        superToast.setText(message);
        superToast.setBackground(background);
        superToast.setTextColor(Color.WHITE);
        superToast.setTextSize(textSize);
        superToast.setIcon(iconId,SuperToast.IconPosition.LEFT);
        superToast.show();
    }

    /**
     * 设置默认Toast。
     */
    public static final void showInitSuperToast(Context context,String message){
        showCustomerSuperToast(context,message,SuperToast.TextSize.LARGE,SuperToast.Background.GRAY,
                SuperToast.Duration.LONG, R.drawable.icon_message);
    }

    /**
     * 长时间显示SuperToast。
     * @param message : 显示内容；
     */
    public static final void showLongSuperToast(Context context,
                                           String message){

        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FADE);
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setText(message);
        superToast.setBackground(SuperToast.Background.GRAY);
        superToast.setTextColor(Color.WHITE);
        superToast.setTextSize(SuperToast.TextSize.LARGE);
        superToast.show();
    }

    /**
     * 长时间显示SuperToast。
     * @param message : 显示内容；
     * @param background:参考SuperToast.Background中的值；
     */
    public static final void showLongSuperToast(Context context,
                                           String message,
                                           int background){
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FADE);
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setText(message);
        superToast.setBackground(background);
        superToast.setTextColor(Color.WHITE);
        superToast.setTextSize(SuperToast.TextSize.LARGE);
        superToast.show();
    }

    /**
     * 长时间显示SuperToast。
     * @param message:显示内容；
     * @param background:参考SuperToast.Background中的值；
     * @param textSize:显示文字大小。建议取值在10-30之间；
     */
    public static final void showLongSuperToast(Context context,
                                           String message,
                                           int background,
                                           int textSize){
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FADE);
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setText(message);
        superToast.setBackground(background);
        superToast.setTextColor(Color.WHITE);
        superToast.setTextSize(textSize);
        superToast.show();
    }

    /**
     * 长时间显示SuperToast。
     * @param message : 显示内容；
     * @param background:参考SuperToast.Background中的值；
     * @param textSize:显示文字大小。建议取值在10-30之间；
     * @param iconId:Toast上的显示图标Id号。
     */
    public static final void showLongSuperToast(Context context,
                                           String message,
                                           int background,
                                           int textSize,
                                           int iconId){
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FADE);
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setText(message);
        superToast.setBackground(background);
        superToast.setTextColor(Color.WHITE);
        superToast.setTextSize(textSize);
        superToast.setIcon(iconId,SuperToast.IconPosition.LEFT);
        superToast.show();
    }


    /**
     * 短时间显示SuperToast。
     * @param message:显示内容；
     */
    public static final void showShortSuperToast(Context context,
                                            String message){
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FADE);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setText(message);
        superToast.setBackground(SuperToast.Background.GRAY);
        superToast.setTextColor(Color.WHITE);
        superToast.setTextSize(SuperToast.TextSize.LARGE);
        superToast.show();
    }

    /**
     * 短时间显示SuperToast。
     * @param message:显示内容；
     * @param background:参考SuperToast.Background中的值；
     */
    public static final void showShortSuperToast(Context context,
                                            String message,
                                            int background){
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FADE);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setText(message);
        superToast.setBackground(background);
        superToast.setTextColor(Color.WHITE);
        superToast.setTextSize(SuperToast.TextSize.LARGE);
        superToast.show();
    }

    /**
     * 短时间显示SuperToast。
     * @param message:显示内容；
     * @param background:参考SuperToast.Background中的值；
     * @param textSize:显示文字大小。建议取值在10-30之间；
     */
    public static final void showShortSuperToast(Context context,
                                            String message,
                                            int background,
                                            int textSize){
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FADE);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setText(message);
        superToast.setBackground(background);
        superToast.setTextColor(Color.WHITE);
        superToast.setTextSize(textSize);
        superToast.show();
    }

    /**
     * 短时间显示SuperToast。
     * @param message:显示内容；
     * @param background:参考SuperToast.Background中的值；
     * @param textSize:显示文字大小。建议取值在10-30之间；
     * @param iconId:Toast上的显示图标Id号。
     */
    public static final void showShortSuperToast(Context context,
                                            String message,
                                            int background,
                                            int textSize,
                                            int iconId){
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FADE);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setText(message);
        superToast.setBackground(background);
        superToast.setTextColor(Color.WHITE);
        superToast.setTextSize(textSize);
        superToast.setIcon(iconId,SuperToast.IconPosition.LEFT);
        superToast.show();
    }
}
