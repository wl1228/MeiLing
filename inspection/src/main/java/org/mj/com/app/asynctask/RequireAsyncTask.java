package org.mj.com.app.asynctask;
import android.net.Uri;
import android.os.AsyncTask;

import org.mj.com.app.client.HttpClientUtils;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class RequireAsyncTask extends AsyncTask {
    OnResultListener onResultListener;
    public RequireAsyncTask(OnResultListener onResultListener){
        this.onResultListener=onResultListener;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        String data=null;
        byte[] resultByte=HttpClientUtils.getData((String)params[0]);
        if (resultByte!=null){
           data= new String(resultByte);
        }
        System.out.println("--------------data=" +data);
        return data;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        onResultListener.onResultListener((String)o);
    }
   public interface OnResultListener{
        public void onResultListener(String result);
    }
}

