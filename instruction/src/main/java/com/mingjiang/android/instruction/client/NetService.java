package com.mingjiang.android.instruction.client;


import com.mingjiang.android.instruction.entity.FridgeParamsBean;
import com.mingjiang.android.instruction.entity.FridgeResponse;
import com.mingjiang.android.instruction.entity.OperStep;
import com.mingjiang.android.instruction.entity.PostOperInstruction;
import com.mingjiang.android.instruction.entity.SessionObj;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by wangzs on 2015/12/2.
 */
public interface NetService {

    public static final String SESSIONID = "X-Openerp-Session-Id";

    //获取作业指导书信息(根据岗位编号来获取岗位指导书)
    @POST("/api/interface/operation.instruction/get_info")
    Observable<PostOperInstruction> getOperInstructionByCode(@Query(PostOperInstruction.OPERATION_ID) String postcode,
                                                             @Header(SESSIONID) String sessionId);

    //再根据图片ID来获取图片信息
    @GET("/api/attachments/image")
    @Headers({"Content-Type: image/png"})
    Observable<Response> getPicMessageById(@Query(OperStep.ID) String id);

    //校验用户是否为本岗位用户
    @FormUrlEncoded
    @POST("/api/userlogin/workstation")
    Observable<SessionObj> isRightUser(@Field("login") String userCode,@Field("workstation") String postCode);

    // 发送冰箱与岗位信息
    @POST("/api/interface/json/process_control.process/trace_process")
    @Headers({"Content-Type: application/json"})
    Observable<FridgeResponse> sendFridgePostBean(@Body FridgeParamsBean params,@Header(SESSIONID) String sessionId);
}