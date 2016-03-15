package com.mingjiang.android.base.util;

import android.widget.LinearLayout;

import app.android.mingjiang.com.androidbootstrap.BootstrapButtonGroup;
import app.android.mingjiang.com.androidbootstrap.api.attributes.BootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.ButtonMode;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapSize;

/**
 * 备注：按钮组Api,控制一组Button的属性。
 * 作者：wangzs on 2016/1/19 16:15
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class BootstrapButtonGroupApi {

    private BootstrapButtonGroupApi(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     *设置Button类型：
     * @param bootstrapButtonGroup
     * @param buttonMode
     */
    public static final void setButtonModel(BootstrapButtonGroup bootstrapButtonGroup,ButtonMode buttonMode){
        bootstrapButtonGroup.setButtonMode(buttonMode);
    }

    /**
     *是否显示外框。
     * @param bootstrapButtonGroup
     * @param showOutline
     */
    public static final void setShowOutline(BootstrapButtonGroup bootstrapButtonGroup,boolean showOutline){
        bootstrapButtonGroup.setShowOutline(showOutline);
    }

    /**
     *是否显示圆角。
     * @param bootstrapButtonGroup
     * @param rounded
     */
    public static final void setRoundedCorners(BootstrapButtonGroup bootstrapButtonGroup,boolean rounded){
        bootstrapButtonGroup.setRounded(rounded);
    }

    /**
     *Bootstrap提供七种不同类型的特定格式，分别是：
     *PRIMARY(最初),
     *SUCCESS(成功),
     *INFO(信息),
     *WARNING(警告),
     *DANGER(危险),
     *SECONDARY(分开设置),
     *REGULAR(默认);
     * @param bootstrapButtonGroup
     * @param bootstrapBrand
     */
    public static final void setBootstrapBrand(BootstrapButtonGroup bootstrapButtonGroup,BootstrapBrand bootstrapBrand){
        bootstrapButtonGroup.setBootstrapBrand(bootstrapBrand);
    }

    /**
     *设置大小。
     * @param bootstrapButtonGroup
     * @param defaultBootstrapSize
     */
    public static final void setBootstrapSize(BootstrapButtonGroup bootstrapButtonGroup,DefaultBootstrapSize defaultBootstrapSize){
        bootstrapButtonGroup.setBootstrapSize(defaultBootstrapSize);
    }

    /**
     *设置大小。
     * @param bootstrapButtonGroup
     * @param bootstrapSize
     */
    public static final void setBootstrapSize(BootstrapButtonGroup bootstrapButtonGroup,float bootstrapSize){
        bootstrapButtonGroup.setBootstrapSize(bootstrapSize);
    }

    /**
     *设置是否选中
     * @param bootstrapButtonGroup
     */
    public static final void setCheckedButton(BootstrapButtonGroup bootstrapButtonGroup){

    }

    /**
     *初始化设置。
     * @param bootstrapButtonGroup
     */
    public static final void initConfig(BootstrapButtonGroup bootstrapButtonGroup){
        bootstrapButtonGroup.setRounded(true);
        bootstrapButtonGroup.setShowOutline(false);
        bootstrapButtonGroup.setBootstrapSize(DefaultBootstrapSize.LG);
        bootstrapButtonGroup.setBootstrapBrand(DefaultBootstrapBrand.INFO);
        bootstrapButtonGroup.setOrientation(LinearLayout.VERTICAL);//0水平布局，1垂直布局
    }

    /**
     * 初始化设置。
     * @param bootstrapButtonGroup
     * @param size：缩放倍数。
     */
    public static final void initConfig(BootstrapButtonGroup bootstrapButtonGroup,float size){
        initConfig(bootstrapButtonGroup);
        bootstrapButtonGroup.setBootstrapSize(size);
    }

    /**
     * 同样可以在BootstrapButtonGroup里面添加下面属性来进行设置。
     * <declare-styleable name="BootstrapButtonGroup">
     <attr name="buttonMode"/>
     <attr name="showOutline"/>
     <attr name="roundedCorners"/>
     <attr name="bootstrapBrand"/>
     <attr name="bootstrapSize"/>
     <attr name="checkedButton"/>
     </declare-styleable>
     */
}
