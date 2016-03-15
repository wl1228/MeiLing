package com.mingjiang.android.scan.client;

import com.mingjiang.android.scan.entity.SessionObj;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * 备注：
 * 作者：wangzs on 2016/2/19 09:56
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public interface NetService {

    //校验用户是否为本岗位用户
    @FormUrlEncoded
    @POST("/api/userlogin/workstation")
    Observable<SessionObj> isRightUser(@Field("login") String userCode,@Field("workstation") String postCode);

}
