package app.android.mingjiang.com.matrtials.entity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 备注：边线库物料查询结果。
 * 作者：wangzs on 2016/2/20 09:11
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class AroundMaterialResult {

    public String result_size = ""; //返回记录数

    public List<AroundMaterialValue> result = new ArrayList<AroundMaterialValue>();//返回记录

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
