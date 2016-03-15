package com.mingjiang.android.base.util;

import app.android.mingjiang.com.androidbootstrap.BootstrapButton;
import app.android.mingjiang.com.androidbootstrap.api.attributes.BootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.ButtonMode;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import app.android.mingjiang.com.androidbootstrap.api.defaults.DefaultBootstrapSize;
import app.android.mingjiang.com.androidbootstrap.font.FontAwesome;

/**
 * 备注：按钮Api。
 * 作者：wangzs on 2016/1/19 16:13
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class BootstrapButtonApi {

    private BootstrapButtonApi(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /******************************************属性设置*****************************************************/
    /**
     * 设置Button类型：
     * @param bootstrapButton
     * @param model
     */
    public static final void setButtonModel(BootstrapButton bootstrapButton,ButtonMode model){
        bootstrapButton.setButtonMode(model);
    }

    /**
     * 是否显示外框。
     * @param bootstrapButton
     * @param showOutline
     */
    public static final void setOutline(BootstrapButton bootstrapButton,boolean showOutline){
        bootstrapButton.setShowOutline(showOutline);
    }

    /**
     *是否显示圆角。
     * @param bootstrapButton
     */
    public static final void setRoundedCorners(BootstrapButton bootstrapButton,boolean roundedCorners){
        bootstrapButton.setRounded(roundedCorners);
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
     * @param bootstrapButton
     */
    public static final void setBootstrapBrand(BootstrapButton bootstrapButton,BootstrapBrand bootstrapBrand){
        bootstrapButton.setBootstrapBrand(bootstrapBrand);
    }

    /**
     *设置大小。
     * @param bootstrapButton
     * @param defaultBootstrapSize
     */
    public static final void setBootstrapSize(BootstrapButton bootstrapButton,DefaultBootstrapSize defaultBootstrapSize){
        bootstrapButton.setBootstrapSize(defaultBootstrapSize);
    }

    /**
     *设置大小。
     * @param bootstrapButton
     * @param bootstrapSize
     */
    public static final void setBootstrapSize(BootstrapButton bootstrapButton,float bootstrapSize){
        bootstrapButton.setBootstrapSize(bootstrapSize);
    }

    /**
     * 如果是单选或复选框按钮，则可设置是否选中。
     * @param bootstrapButton
     * @param checked
     */
    public static final void setChecked(BootstrapButton bootstrapButton,boolean checked){
        bootstrapButton.setChecked(checked);
    }

    /**
     * 在按钮上添加小图标，可以与文字结合使用。
     * @param bootstrapButton
     * @param iconCode
     */
    public static final void setFontAwesomeIcon(BootstrapButton bootstrapButton,@FontAwesome.Icon CharSequence iconCode){
        bootstrapButton.setFontAwesomeIcon(iconCode);
    }

    /******************************************属性设置*****************************************************/


    /*****************************************特殊Button初始化配置******************************************/
    public static final void initConfig(BootstrapButton bootstrapButton){
        bootstrapButton.setRounded(true);
        bootstrapButton.setShowOutline(false);
        bootstrapButton.setBootstrapBrand(DefaultBootstrapBrand.INFO);
        bootstrapButton.setBootstrapSize(DefaultBootstrapSize.LG);
        //bootstrapButton.setFontAwesomeIcon(FontAwesome.FA_FILE_CODE_O);//设置小图标
        bootstrapButton.setButtonMode(ButtonMode.REGULAR);
        //bootstrapButton.setText("sfsdfas");//设置文字
    }

    /**
     * Button初始化，添加设置大小。
     * @param bootstrapButton
     * @param size:缩放倍数，不要太大。
     */
    public static final void initConfig(BootstrapButton bootstrapButton,float size){
        initConfig(bootstrapButton);
        bootstrapButton.setBootstrapSize(size);
    }
    /**
     * 设置初始化“成功”按钮。
     * @param bootstrapButton
     */
    public static final void setInitSuccessButton(BootstrapButton bootstrapButton){
        bootstrapButton.setRounded(true);
        bootstrapButton.setShowOutline(true);
        bootstrapButton.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        bootstrapButton.setBootstrapSize(DefaultBootstrapSize.LG);
    }

    /**
     * 设置初始化“警告”按钮。
     * @param bootstrapButton
     */
    public static final void setInitWaringButton(BootstrapButton bootstrapButton){
        bootstrapButton.setRounded(true);
        bootstrapButton.setShowOutline(true);
        bootstrapButton.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        bootstrapButton.setBootstrapSize(DefaultBootstrapSize.LG);
    }

    /**
     * 设置初始化“失败”按钮。
     * @param bootstrapButton
     */
    public static final void setInitDangerButton(BootstrapButton bootstrapButton){
        bootstrapButton.setRounded(true);
        bootstrapButton.setShowOutline(true);
        bootstrapButton.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
        bootstrapButton.setBootstrapSize(DefaultBootstrapSize.LG);
    }

    /**
     * 设置初始化“原始”按钮。
     * @param bootstrapButton
     */
    public static final void setInitPrimaryButton(BootstrapButton bootstrapButton){
        bootstrapButton.setRounded(true);
        bootstrapButton.setShowOutline(true);
        bootstrapButton.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        bootstrapButton.setBootstrapSize(DefaultBootstrapSize.LG);
    }

    /*****************************************特殊Button初始化配置******************************************/

    /**
     *同样可以在BootstrapButton里面添加下面属性来进行设置。
     * <declare-styleable name="BootstrapButton">
     <attr name="buttonMode"/>
     <attr name="showOutline"/>
     <attr name="roundedCorners"/>
     <attr name="bootstrapBrand"/>
     <attr name="bootstrapSize"/>
     <attr name="checked"/>
     </declare-styleable>
     */
}
