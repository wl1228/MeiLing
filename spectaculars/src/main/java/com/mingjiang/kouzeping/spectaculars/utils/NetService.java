package com.mingjiang.kouzeping.spectaculars.utils;

import com.mingjiang.kouzeping.spectaculars.badness_monitor.BadnessItem;
import com.mingjiang.kouzeping.spectaculars.indent_monitor.IndentInfo;
import com.mingjiang.kouzeping.spectaculars.material_monitor.MidMaterialResult;
import com.mingjiang.kouzeping.spectaculars.product_monitor.LineLogInfoItem;
import com.mingjiang.kouzeping.spectaculars.product_monitor.LineLogItem;
import com.mingjiang.kouzeping.spectaculars.product_monitor.PlanItem;
import com.mingjiang.kouzeping.spectaculars.product_monitor.YieldItem;

import java.util.List;
import java.util.Map;

import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public interface NetService {

    //中间库物料数据查询
    @POST("/api/interface/public/material_manage.material_stock/repertory_for_android")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
//    Observable<SubmitResult> sendProductInfo(@Body HashMap<String,String> params, @Header(SESSIONID) String sessionId);
    Observable<MidMaterialResult> queryMidMaterial(@QueryMap Map<String, String> options);

    //请求停线记录
    @POST("/api/interface/public/craftwork.production.line/queryProdLineinfo")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<LineLogItem>> queryLineLog();

    //停线记录详情查询
    @POST("/api/interface/public/craftwork.production.line.log/queryProductLineLog")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<LineLogInfoItem>> queryLinLogInfo(@QueryMap Map<String, String> options);

    //请求五大不良数据集
    @POST("/api/interface/public/craftwork.production.line/queryProdLineinfo")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<BadnessItem>> queryBadness();

    //请求日计划
    @POST("/api/interface/public/plan_manage.daily_plan/monitor_progress")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<List<PlanItem>> queryPlanData();

    //获取产量数据
    @POST("/api/interface/public/plan_manage.daily_plan/statistic_production")
    @Headers({"Content-Type:application/x-www-form-urlencoded","Accept-Language:zh-CN"})
    Observable<YieldItem> queryYieldData(@QueryMap Map<String, String> options);

    //订单查询
    @POST("/api/interface/public/plan_manage.product_order/product_order_query")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<IndentInfo> queryIndentInfo(@QueryMap Map<String, String> options);


}
