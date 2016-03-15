package com.mingjiang.android.base.util;

import app.android.mingjiang.com.androidbootstrap.BootstrapEditText;
import app.android.mingjiang.com.androidbootstrap.api.attributes.BootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapSize;

/**
 * 备注：输入框Api。
 * 作者：wangzs on 2016/1/19 16:16
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class BootstrapEditTextApi {

    private BootstrapEditTextApi(){
        throw new UnsupportedOperationException("不能实例化");
    }

    /**
     *设置圆角。
     * @param bootstrapEditText
     * @param rounded
     */
    public static final void setRoundedConners(BootstrapEditText bootstrapEditText,boolean rounded){
        bootstrapEditText.setRounded(rounded);
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
     * @param bootstrapEditText
     */
    public static final void setBootstrapBrand(BootstrapEditText bootstrapEditText,BootstrapBrand bootstrapBrand){
        bootstrapEditText.setBootstrapBrand(bootstrapBrand);
    }

    /**
     *设置大小。
     * @param bootstrapEditText
     * @param defaultBootstrapSize
     */
    public static final void setBootstrapSize(BootstrapEditText bootstrapEditText,DefaultBootstrapSize defaultBootstrapSize){
        bootstrapEditText.setBootstrapSize(defaultBootstrapSize);
    }

    /**
     *设置大小。
     * @param bootstrapEditText
     * @param bootstrapSize
     */
    public static final void setBootstrapSize(BootstrapEditText bootstrapEditText,float bootstrapSize){
        bootstrapEditText.setBootstrapSize(bootstrapSize);
    }

    /**
     *初始化设置。
     * @param bootstrapEditText
     */
    public static final void initConfig(BootstrapEditText bootstrapEditText){
        bootstrapEditText.setBootstrapSize(DefaultBootstrapSize.LG);
        bootstrapEditText.setBootstrapBrand(DefaultBootstrapBrand.INFO);
        bootstrapEditText.setRounded(true);
    }

    /**
     * 初始化设置，添加大小设置。
     * @param bootstrapEditText
     * @param size：缩放倍数。
     */
    public static final void initConfig(BootstrapEditText bootstrapEditText,float size){
        initConfig(bootstrapEditText);
        bootstrapEditText.setBootstrapSize(size);
    }
    /**
     * 同样可以在BootstrapEditText里面添加下面属性来进行设置。
     * <declare-styleable name="BootstrapEditText">
     <attr name="roundedCorners"/>
     <attr name="bootstrapBrand"/>
     <attr name="bootstrapSize"/>
     </declare-styleable>
     */
}
