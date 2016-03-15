package com.mingjiang.android.base.util;

import app.android.mingjiang.com.androidbootstrap.AwesomeTextView;
import app.android.mingjiang.com.androidbootstrap.api.attributes.BootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapBrand;

/**
 * 备注：TextViewApi。
 * 作者：wangzs on 2016/1/19 16:14
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class BootstrapTextViewApi {

    private BootstrapTextViewApi(){
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
     * @param bootstrapTextView
     */
    public static final void setBootstrapBrand(AwesomeTextView bootstrapTextView,BootstrapBrand bootstrapBrand){
        bootstrapTextView.setBootstrapBrand(bootstrapBrand);
    }

    /**
     *设置小图标，来自fontawesome-webfont-v450.ttf
     * @param bootstrapTextView
     * @param charSequence
     */
    public static final void setFontAwesomeIcon(AwesomeTextView bootstrapTextView,CharSequence charSequence){
        bootstrapTextView.setFontAwesomeIcon(charSequence);
    }

    /**
     *设置小图标，来自typicon-v207.ttf
     * @param bootstrapTextView
     * @param charSequence
     */
    public static final void setTypicon(AwesomeTextView bootstrapTextView,CharSequence charSequence){
        bootstrapTextView.setTypicon(charSequence);
    }

    /**
     *初始化设置。
     * @param bootstrapTextView
     */
    public static final void initConfig(AwesomeTextView bootstrapTextView){
        bootstrapTextView.setBootstrapBrand(DefaultBootstrapBrand.INFO);
    }

    /**
     * 同样可以在AwesomeTextView里面添加下面属性来进行设置。
     *  <attr name="bootstrapText" format="string"/>
     <attr name="bootstrapBrand"/>
     <attr name="fontAwesomeIcon"/>
     <attr name="typicon"/>
     */
}
