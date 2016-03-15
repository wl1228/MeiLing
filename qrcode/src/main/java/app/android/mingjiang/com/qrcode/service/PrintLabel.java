package app.android.mingjiang.com.qrcode.service;

public class PrintLabel {

    /*
    * 请将所有的打印标签保存在这里
    *
    * */
    public final static String SAMPLE_LABEL = "^XA^FO100,75^BY3^B3N,N,100,Y,N^FD123ABC^XZ";

    public static String QECode(String value) {
        return "^XA" +
                "^FO25,0" +
                "^BQ,2,5" +
                "^FDHA," + value + "^FS" +
                "^XZ";
    }
}
