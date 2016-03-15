package com.mingjiang.kouzeping.spectaculars.utils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class HttpClientUtils {
    private static final String TAG = "";
    //通过地址获得byte数据
    public static String getData(String path) {
        String result = "";
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(3*1000);
            conn.setRequestMethod("GET");
            InputStream inStream = conn.getInputStream();
            byte[] bf=new byte[1024];
            int len=0;
            while ((len=inStream.read(bf))!=-1){
                result=result+new String(bf,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String postData(String path, String submitData) {
        byte[] updata=null;
        byte[] data=null;
        int len=0;
        if (submitData!=null){
//            submitData = URLEncoder.encode(submitData);// 中文数据需要经过URL编码
            updata= submitData.getBytes();
            len=updata.length;
        }
        String result = "";
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            //这是请求方式为POST
            conn.setRequestMethod("POST");
            //设置post请求必要的请求头
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 请求头, 必须设置
            conn.setRequestProperty("Accept-Language", "zh-CN");// 请求头, 必须设置
            conn.setRequestProperty("Content-Length", len + "");// 注意是字节长度, 不是字符长度
            if (updata!=null&&updata.length>0&&len>0){
                conn.setDoOutput(true);// 准备写出
                conn.getOutputStream().write(updata);// 写出数据
            }else {
                conn.setDoOutput(false);// 不写出
            }
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = rd.readLine()) != null) {
                    response.append(line);
                }
                rd.close();
                result= response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}