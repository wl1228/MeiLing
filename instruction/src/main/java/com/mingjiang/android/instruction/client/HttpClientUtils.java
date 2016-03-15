package com.mingjiang.android.instruction.client;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者：wangzs on 2015/12/24 15:18
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class HttpClientUtils {

    private static final String TAG = "";
    public static String  getGsonMsg(String postCode,String sessionId){
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("operation_id", postCode));
        //params.add(new BasicNameValuePair("X-Openerp-Session-Id", sessionId));

        //对参数编码
        String param = URLEncodedUtils.format(params, "UTF-8");

        //baseUrl
        String baseUrl = "http://192.168.0.143:9007/api/interface/operation.instruction/get_info";

        //将URL与参数拼接
        //HttpGet getMethod = new HttpGet(baseUrl + "?" + param);

        HttpGet get = new HttpGet(baseUrl+ "?" + param);
        //添加http头信息
        //get.addHeader("operation_id", postCode);
        get.addHeader("X-Openerp-Session-Id", sessionId);
        //get.addHeader("User-Agent","your agent");

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
