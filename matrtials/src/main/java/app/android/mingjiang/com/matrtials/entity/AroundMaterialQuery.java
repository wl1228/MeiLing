package app.android.mingjiang.com.matrtials.entity;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 备注：线边库物料查询。
 * 作者：wangzs on 2016/2/20 09:09
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class AroundMaterialQuery {

    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String MATERIAL_ID = "material_id";
    public static final String MATERIAL_NAME = "material_name";
    public static final String AREA_ID = "area_id";
    public static final String PAGE_SIZE = "page_size";
    public static final String PAGE_NUMBER = "page_number";

    public String material_id;          //物料编码
    public String material_name;        //物料名称
    public String area_id;               //下料点编号
    public String page_size;             //返回记录
    public String page_number;           //页码

    public AroundMaterialQuery(){

    }

    //将实体类数据转换为Map数据。
   public Map<String,String> setData(){

        Map<String,String> retMap = new HashMap<String,String>();
        retMap.put(MATERIAL_ID,this.material_id);
        retMap.put(MATERIAL_NAME,this.material_name);
        retMap.put(AREA_ID,this.area_id);
       if("".equals(page_size)){
           retMap.put(PAGE_SIZE,DEFAULT_PAGE_SIZE);
       }else{
           retMap.put(PAGE_SIZE,this.page_size);
       }
        retMap.put(PAGE_NUMBER,this.page_number);

        return retMap;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
