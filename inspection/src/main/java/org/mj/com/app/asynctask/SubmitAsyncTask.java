package org.mj.com.app.asynctask;
import android.os.AsyncTask;

import org.mj.com.app.client.HttpClientUtils;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class SubmitAsyncTask extends AsyncTask {
    OnReasonListener onReasonListener;
    public SubmitAsyncTask(OnReasonListener onReasonListener){
        this.onReasonListener=onReasonListener;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        boolean result=false;
        try {
            result=HttpClientUtils.postData((String)params[0],(String)params[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        onReasonListener.onReasonListener((boolean)o);
    }
   public interface OnReasonListener{
       public void onReasonListener(boolean result);
    }
}

