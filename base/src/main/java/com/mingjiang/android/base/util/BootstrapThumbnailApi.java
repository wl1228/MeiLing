package com.mingjiang.android.base.util;

import app.android.mingjiang.com.androidbootstrap.BootstrapThumbnail;
import app.android.mingjiang.com.androidbootstrap.api.attributes.BootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapSize;

/**
 * 备注：缩略图处理Api。
 * 作者：wangzs on 2016/1/19 16:16
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class BootstrapThumbnailApi {

    private BootstrapThumbnailApi(){
        throw new UnsupportedOperationException("不能实例化");
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
     * @param bootstrapThumbnail
     */
    public static final void setBootstrapBrand(BootstrapThumbnail bootstrapThumbnail,BootstrapBrand bootstrapBrand){
        bootstrapThumbnail.setBootstrapBrand(bootstrapBrand);
    }

    /**
     *设置是否为圆角。
     * @param bootstrapThumbnail
     * @param rounded
     */
    public static final void setRoundedCorners(BootstrapThumbnail bootstrapThumbnail,boolean rounded){
        bootstrapThumbnail.setRounded(rounded);
    }

    /**
     *设置是否有边框。
     * @param bootstrapThumbnail
     * @param borderDisplayed
     */
    public static final void setHasBorder(BootstrapThumbnail bootstrapThumbnail,boolean borderDisplayed){
        bootstrapThumbnail.setBorderDisplayed(borderDisplayed);
    }

    /**
     *设置大小。
     * @param bootstrapThumbnail
     * @param defaultBootstrapSize
     */
    public static final void setBootstrapSize(BootstrapThumbnail bootstrapThumbnail,DefaultBootstrapSize defaultBootstrapSize){
        bootstrapThumbnail.setBootstrapSize(defaultBootstrapSize);
    }

    /**
     *初始化设置。
     * @param bootstrapThumbnail
     */
    public static final void initConfig(BootstrapThumbnail bootstrapThumbnail){
        bootstrapThumbnail.setRounded(false);
        bootstrapThumbnail.setBootstrapSize(DefaultBootstrapSize.LG);
        bootstrapThumbnail.setBorderDisplayed(true);
        bootstrapThumbnail.setBootstrapBrand(DefaultBootstrapBrand.INFO);
    }

    /**
     * 初始化设置，增加大小设置。
     * @param bootstrapThumbnail
     * @param size:缩放倍数。
     */
    public static final void initConfig(BootstrapThumbnail bootstrapThumbnail,float size){
        initConfig(bootstrapThumbnail);
        bootstrapThumbnail.setBootstrapSize(size);
    }

    /**
     * 同样可以在BootstrapThumbnail里面添加下面属性来进行设置。
     * <declare-styleable name="BootstrapThumbnail">
     <attr name="bootstrapBrand"/>
     <attr name="roundedCorners"/>
     <attr name="hasBorder"/>
     <attr name="bootstrapSize"/>
     </declare-styleable>
     */
}
