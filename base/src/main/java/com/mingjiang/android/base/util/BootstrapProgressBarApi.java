package com.mingjiang.android.base.util;

import app.android.mingjiang.com.androidbootstrap.BootstrapProgressBar;
import app.android.mingjiang.com.androidbootstrap.api.attributes.BootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapSize;

/**
 * 备注：进度框Api,不建议使用，建议使用SpotsProgressApi。
 * 作者：wangzs on 2016/1/19 16:16
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class BootstrapProgressBarApi {

    private BootstrapProgressBarApi(){
        throw new UnsupportedOperationException("不能实例化");
    }

    /**
     *设置条纹。
     * @param bootstrapProgressBar
     * @param striped
     */
    public static final void setStriped(BootstrapProgressBar bootstrapProgressBar,boolean striped){
        bootstrapProgressBar.setStriped(striped);
    }

    /**
     *设置动画。
     * @param bootstrapProgressBar
     * @param animated
     */
    public static final void setAnimated(BootstrapProgressBar bootstrapProgressBar,boolean animated){
        bootstrapProgressBar.setAnimated(animated);
    }

    /**
     *设置进度数据。（1-100）
     * @param bootstrapProgressBar
     * @param progress
     */
    public static final void setProgress(BootstrapProgressBar bootstrapProgressBar,int progress){
        bootstrapProgressBar.setProgress(progress);
    }

    /**
     *设置圆角。
     * @param bootstrapProgressBar
     * @param rounded
     */
    public static final void setRoundedCorners(BootstrapProgressBar bootstrapProgressBar,boolean rounded){
        bootstrapProgressBar.setRounded(rounded);
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
     * @param bootstrapProgressBar
     */
    public static final void setBootstrapBrand(BootstrapProgressBar bootstrapProgressBar,BootstrapBrand bootstrapBrand){
        bootstrapProgressBar.setBootstrapBrand(bootstrapBrand);
    }

    /**
     *设置大小。
     * @param bootstrapProgressBar
     * @param defaultBootstrapSize
     */
    public static final void setBootstrapSize(BootstrapProgressBar bootstrapProgressBar,DefaultBootstrapSize defaultBootstrapSize){
        bootstrapProgressBar.setBootstrapSize(defaultBootstrapSize);
    }

    /**
     *
     * 初始化设置。
     * @param bootstrapProgressBar
     */
    public static final void initConfig(BootstrapProgressBar bootstrapProgressBar){
        bootstrapProgressBar.setBootstrapSize(DefaultBootstrapSize.LG);
        bootstrapProgressBar.setRounded(false);
        bootstrapProgressBar.setAnimated(true);
        bootstrapProgressBar.setStriped(true);
        bootstrapProgressBar.setBootstrapBrand(DefaultBootstrapBrand.INFO);
        //bootstrapProgressBar.setProgress();
    }

    /**
     * 初始化设置，增加大小设置。
     * @param bootstrapProgressBar
     * @param size：缩放倍数。
     */
    public static final void initConfig(BootstrapProgressBar bootstrapProgressBar,float size){
        initConfig(bootstrapProgressBar);
        bootstrapProgressBar.setBootstrapSize(size);
    }
    /**
     * 同样可以在BootstrapProgressBar里面添加下面属性来进行设置。
     * <declare-styleable name="BootstrapProgressBar">
     <attr name="striped" format="boolean"/>
     <attr name="animated" format="boolean"/>
     <attr name="progress" format="integer"/>
     <attr name="roundedCorners"/>
     <attr name="bootstrapBrand"/>
     <attr name="bootstrapSize"/>
     </declare-styleable>
     */
}
