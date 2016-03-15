package com.mingjiang.android.equipmonitor.client;

import com.mingjiang.android.equipmonitor.entity.EquipmonitorMesssage;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by wangzs on 2015/12/21.
 */
public interface NetService {

    //获取设备监控管理信息
    @GET("/api/operation.instruction/get_info")
    Observable<EquipmonitorMesssage> getEquipmonitorMessageByCode();
}
