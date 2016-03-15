package app.android.mingjiang.com.qrcode.utils;

/**
 * Created by SunYi on 2015/12/15/0015.
 */
public class FinishCode2QRUtil {
    public static String toQECode(String code) {
        return code + "0000000";
    }
    public static String toQECodeAuto31(String code) {
       while (code.length() < 31){
           code += "0";
       }
        return code;
    }
}
