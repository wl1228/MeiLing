package com.mingjiang.android.base.util;

import app.android.mingjiang.com.androidbootstrap.BootstrapLabel;
import app.android.mingjiang.com.androidbootstrap.api.attributes.BootstrapHeading;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapHeading;

/**
 * 作者：wangzs on 2016/1/19 16:30
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class BootstrapLabelApi {

    private BootstrapLabelApi(){
        throw new UnsupportedOperationException("不能实例化");
    }

    /**
     *设置文字大小：H1、H2、H3、H4、H5、H6。
     * @param bootstrapLabel
     * @param bootstrapHeading
     */
    public static final void setBootstrapHeading(BootstrapLabel bootstrapLabel,BootstrapHeading bootstrapHeading){
        bootstrapLabel.setBootstrapHeading(bootstrapHeading);
    }

    /**
     *设置圆角。
     * @param bootstrapLabel
     * @param rounded
     */
    public static final void setRoundedCorners(BootstrapLabel bootstrapLabel,boolean rounded){
        bootstrapLabel.setRounded(rounded);
    }

    /**
     * 初始化设置。
     * @param bootstrapLabel
     */
    public static final void initConfig(BootstrapLabel bootstrapLabel){
        bootstrapLabel.setRounded(true);
        bootstrapLabel.setBootstrapHeading(DefaultBootstrapHeading.H2);
    }

    /**
     * 初始化设置，增加文字大小设置。
     * @param bootstrapLabel
     * @param textSize
     */
    public static final void initConfig(BootstrapLabel bootstrapLabel,float textSize){
        initConfig(bootstrapLabel);
        bootstrapLabel.setTextSize(textSize);
    }
    /**
     * 同样可以在BootstrapLabel里面添加下面属性来进行设置.
     * <declare-styleable name="BootstrapLabel">
     <attr name="bootstrapHeading"/>
     <attr name="roundedCorners"/>
     </declare-styleable>
     */
}
