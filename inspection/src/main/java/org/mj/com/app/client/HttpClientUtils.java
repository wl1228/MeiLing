package org.mj.com.app.client;

import android.net.Uri;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class HttpClientUtils {

    private static final String TAG = "";
    //通过地址获得byte数据
    public static byte[] getData(String path) {

        byte[] result = null;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(path);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toByteArray(httpEntity);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;

    }
    public static boolean postData(String path,String submitData){
        submitData = URLEncoder.encode(submitData);// 中文数据需要经过URL编码
        String params = "test_detail=" + submitData;
        byte[] data = params.getBytes();
        boolean result = false;
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            //这是请求方式为POST
            conn.setRequestMethod("POST");
            //设置post请求必要的请求头
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 请求头, 必须设置
            conn.setRequestProperty("Content-Length", data.length + "");// 注意是字节长度, 不是字符长度

            conn.setDoOutput(true);// 准备写出
            conn.getOutputStream().write(data);// 写出数据
            int code=conn.getResponseCode();
            if ( code== 200){
                result=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String  getGsonMsg(String postCode,String sessionId){
//        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
//        params.add(new BasicNameValuePair("operation_id", postCode));
//        //params.add(new BasicNameValuePair("X-Openerp-Session-Id", sessionId));
//
//        //对参数编码
//        String param = URLEncodedUtils.format(params, "UTF-8");

        //baseUrl
        String baseUrl = "http://192.168.0.106:8072/api/userlogin/login?login=";

        //将URL与参数拼接
        HttpGet get = new HttpGet(baseUrl+postCode);
//        //添加http头信息
//        get.addHeader("X-Openerp-Session-Id", sessionId);
//        //get.addHeader("User-Agent","your agent");
//
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpResponse response = httpClient.execute(get); //发起GET请求
            int code =  response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            Log.i(TAG, "resCode = " +code); //获取响应码
            Log.i(TAG, "result = " + result);//获取服务器响应内容
            if (code == 200) {
                //返回json格式： {"id": "27JpL~j4vsL0LX00E00005","version": "abc"}
                String rev = EntityUtils.toString(response.getEntity());
                return rev;
            }
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String getTest(String code,String sessionId){

        //ArrayList<NameValuePair> headerList = new ArrayList<NameValuePair>();
       // headerList.add(new BasicNameValuePair("Content-Type",
        //        "text/json; charset=unicode"));
        String targetUrl = "http://10.18.89.77:9007/api/operation.instruction/get_info";
        ArrayList<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("operation_id", code));
        paramList.add(new BasicNameValuePair("X-Openerp-Session-Id",sessionId));

        for (int i = 0; i < paramList.size(); i++) {
            NameValuePair nowPair = paramList.get(i);
            String value = nowPair.getValue();
            try {
                value = URLEncoder.encode(value, "unicode");
            } catch (Exception e) {

            }
            if (i == 0) {
                targetUrl += ("?" + nowPair.getName() + "=" + value);
            } else {
                targetUrl += ("&" + nowPair.getName() + "=" + value);
            }
        }

        HttpGet httpRequest = new HttpGet(targetUrl);
        try {
           // for (int i = 0; i < headerList.size(); i++) {
           //     httpRequest.addHeader(headerList.get(i).getName(),
           //             headerList.get(i).getValue());
          //  }

            HttpClient httpClient = new DefaultHttpClient();

            HttpResponse httpResponse = httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity());
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
return "";
    }
}
