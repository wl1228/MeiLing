package com.mingjiang.android.onoffline.client;

import retrofit.http.Header;
import retrofit.http.QueryMap;
import rx.Observable;

import com.mingjiang.android.onoffline.entity.OnOffLineData;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * 备注：上线/下线网络通信。
 * 作者：wangzs on 2016/2/19 14:03
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public interface NetService {
    public static final String SESSIONID = "X-Openerp-Session-Id";

    @POST("/api/interface/quality_manage.up_down_record/onoff")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<String> submitOnOffLine(@QueryMap Map<String,String> params,@Header(SESSIONID) String sessionId);

}
