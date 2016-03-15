package app.android.mingjiang.com.matrtials.client;

import java.util.List;
import java.util.Map;

import app.android.mingjiang.com.matrtials.entity.AroundMaterialResult;
import app.android.mingjiang.com.matrtials.entity.MaterialOneKeyAddResult;
import app.android.mingjiang.com.matrtials.entity.MidMaterialResult;
import app.android.mingjiang.com.matrtials.entity.RetValue;
import app.android.mingjiang.com.matrtials.entity.SessionObj;
import retrofit.http.Field;
import retrofit.http.Header;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * 备注：
 * 作者：wangzs on 2016/2/20 10:40
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public interface NetService {

    public static final String SESSIONID = "X-Openerp-Session-Id";
    public static final String CONTEXT  = "context";
    public static final String CONTEXT_VALUE = "{\"verify\": 0}";
    //中间库物料数据查询
    @POST("/api/interface/public/material_manage.material_stock/repertory_for_android")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<MidMaterialResult> queryMidMaterial(@QueryMap Map<String,String>  params);

    //线边物料数据查询
    @POST("/api/interface/public/material_manage.line_stock/for_android")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<AroundMaterialResult> queryAroundMaterial(@QueryMap Map<String,String> params);


    //登录接口
    @POST("/api/userlogin/login/")
    Observable<SessionObj> login(@Query("login") String userCode,@Query(CONTEXT) String context);

    //获取出库物料信息
    @POST("/api/interface/public/material_manage.incoming_count/stock_android")
    Observable<List<MaterialOneKeyAddResult>> queryMaterial(@Query(MaterialOneKeyAddResult.OUT_CODE) String out_code);

    //提交一键入库操作
    @POST("/api/interface/public/material_manage.incoming_count/android_confirm")
    Observable<String> submitMaterial(@Query(MaterialOneKeyAddResult.CODE) String materialCode,@Query(MaterialOneKeyAddResult.NUMBER) String materialNumber);

    //中间库一键入库
    @POST("/api/interface/material_manage.incoming_count/stock_android")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Observable<RetValue> addLibMaterial(@QueryMap Map<String, String> params,@Header(SESSIONID) String sessionId);

}
