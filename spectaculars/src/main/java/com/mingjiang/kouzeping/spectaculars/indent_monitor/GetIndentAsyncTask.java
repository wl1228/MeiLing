package com.mingjiang.kouzeping.spectaculars.indent_monitor;


import android.os.AsyncTask;

import com.mingjiang.kouzeping.spectaculars.utils.HttpClientUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class GetIndentAsyncTask extends AsyncTask<String,String,List<IndentItem>>{
    OnIndentResultListener onIndentResultListener;

    public GetIndentAsyncTask(OnIndentResultListener onIndentResultListener) {
        this.onIndentResultListener = onIndentResultListener;
    }

    @Override
    protected List<IndentItem> doInBackground(String... params) {
        List<IndentItem> result=new ArrayList<>();
        String data=null;
        data= HttpClientUtils.postData(params[0],params[1]);
//
        JSONArray jsonArray=null;
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(data);
            jsonArray=jsonObject.getJSONArray("result");
            for(int i=0; i< jsonArray.length(); i++) {
                IndentItem deviceDetail=new IndentItem();
                JSONObject mark = (JSONObject) jsonArray.get(i);
                deviceDetail.setOrder_id(mark.getString("order_id"));
                deviceDetail.setProduct_line(mark.getString("product_line"));
                deviceDetail.setExport_id(mark.getString("export_id"));
                deviceDetail.setExport_country(mark.getString("export_country"));
                deviceDetail.setProduct_id(mark.getString("product_id"));
                deviceDetail.setOrder_quantity(mark.getString("order_quantity"));
                deviceDetail.setExpire_date(mark.getString("expire_date"));
                deviceDetail.setIs_customize(mark.getString("is_customize"));
                deviceDetail.setNotes(mark.getString("notes"));
                deviceDetail.setFinish_number(mark.getString("finish_number"));
                deviceDetail.setBatch_id(mark.getString("batch_id"));
                result.add(deviceDetail);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    protected void onPostExecute(List<IndentItem> result) {
        super.onPostExecute(result);
        onIndentResultListener.onIndentResultListener(result);
    }
    public interface OnIndentResultListener{
        public void onIndentResultListener(List<IndentItem> result);
    }
}
