package com.mingjiang.android.base.util;

import app.android.mingjiang.com.androidbootstrap.BootstrapCircleThumbnail;
import app.android.mingjiang.com.androidbootstrap.api.attributes.BootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapSize;

/**
 * 备注：环形缩略图。
 * 作者：wangzs on 2016/1/19 16:15
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class BootstrapCircleThumbnailApi {

    private BootstrapCircleThumbnailApi(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     **Bootstrap提供七种不同类型的特定格式，分别是：
     *PRIMARY(最初),
     *SUCCESS(成功),
     *INFO(信息),
     *WARNING(警告),
     *DANGER(危险),
     *SECONDARY(分开设置),
     *REGULAR(默认);
     * @param bootstrapCircleThumbnail
     * @param bootstrapBrand
     */
    public static final void setBootstrapBrand(BootstrapCircleThumbnail bootstrapCircleThumbnail,BootstrapBrand bootstrapBrand){
        bootstrapCircleThumbnail.setBootstrapBrand(bootstrapBrand);
    }

    /**
     *设置是否有间隔框。
     * @param bootstrapCircleThumbnail
     * @param boorderDisplayed
     */
    public static final void setHasBorder(BootstrapCircleThumbnail bootstrapCircleThumbnail,boolean boorderDisplayed){
        bootstrapCircleThumbnail.setBorderDisplayed(boorderDisplayed);
    }

    /**
     *设置大小。
     * @param bootstrapCircleThumbnail
     * @param defaultBootstrapSize
     */
    public static final void setBootstrapSize(BootstrapCircleThumbnail bootstrapCircleThumbnail,DefaultBootstrapSize defaultBootstrapSize){
        bootstrapCircleThumbnail.setBootstrapSize(defaultBootstrapSize);
    }

    /**
     *设置大小。
     * @param bootstrapCircleThumbnail
     * @param bootstrapSize
     */
    public static final void setBootstrapSize(BootstrapCircleThumbnail bootstrapCircleThumbnail,float bootstrapSize){
        bootstrapCircleThumbnail.setBootstrapSize(bootstrapSize);
    }

    /**
     *初始化设置。
     * @param bootstrapCircleThumbnail
     */
    public static final void initConfig(BootstrapCircleThumbnail bootstrapCircleThumbnail){
        bootstrapCircleThumbnail.setBootstrapSize(DefaultBootstrapSize.LG);
        bootstrapCircleThumbnail.setBorderDisplayed(true);
        bootstrapCircleThumbnail.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
    }

    /**
     * 初始化设置，添加设置大小。
     * @param bootstrapCircleThumbnail
     * @param size:缩放倍数。
     */
    public static final void initConfig(BootstrapCircleThumbnail bootstrapCircleThumbnail,float size){
        initConfig(bootstrapCircleThumbnail);
        bootstrapCircleThumbnail.setBootstrapSize(size);
    }
    /**
     * 同样可以在BootstrapCircleThumbnail里面添加下面属性来进行设置。
     * <declare-styleable name="BootstrapCircleThumbnail">
     <attr name="bootstrapBrand"/>
     <attr name="hasBorder"/>
     <attr name="bootstrapSize"/>
     </declare-styleable>
     */
}
