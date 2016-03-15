package app.android.mingjiang.com.matrtials.entity;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 备注：中间库物料查询。
 * 作者：wangzs on 2016/2/20 09:09
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class MidMaterialQuery {

    public static final String DEFAULT_PAGE_SIZE = "10";

    public static final String MATERIA_ID = "material_id";
    public static final String MATERIAL_NAME = "material_name";
    public static final String SEAT_ID = "seat_id";
    public static final String RESERVOIR_AREA = "reservoir_area";
    public static final String PAGE_SIZE = "page_size";
    public static final String PAGE_NUMBER = "page_number";

    public String material_id;      //物料编码
    public String material_name;    //物料名称
    public String seat_id;          //库位编号
    public String reservoir_area;   //库区编号
    public String page_size;        //返回记录
    public String page_number;      //页码


    //将实体类数据转换为Map数据。
    public Map<String,String> setData(){

        Map<String,String> retMap = new HashMap<String,String>();

        retMap.put(MATERIA_ID,this.material_id);
        retMap.put(MATERIAL_NAME,this.material_name);
        retMap.put(SEAT_ID,this.seat_id);
        retMap.put(RESERVOIR_AREA,this.reservoir_area);
        if("".equals(this.page_size)){
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
