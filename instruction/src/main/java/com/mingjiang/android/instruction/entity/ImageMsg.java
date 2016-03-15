package com.mingjiang.android.instruction.entity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 图片信息。（带时间戳及文件大小，id是用来请求图片的）
 * Created by wangzs on 2015/12/11.
 */
public class ImageMsg {

    public static final String JSON_TITLE = "pic_path";
    public static final String LAST_UPDATE = "__last_update";
    public static final String ID = "id";
    public static final String FILE_SIZE = "file_size";
    public static final String FILE_PATH = "file_path";

    //图片时间戳：校验图片是否更新
    public String __last_update;

    //图片ID，如果更新可用它来进行请求图片下载
    public String id;

    //图片大小
    public String file_size;

    //图片保存路径
    public String file_path;

    public ImageMsg()
    {

    }

    public ImageMsg(JSONObject jsonObject){

        try {

            this.__last_update = jsonObject.getString(LAST_UPDATE);
            this.id = jsonObject.getString(ID);
            this.file_size = jsonObject.getString(FILE_SIZE);
            //this.file_path = jsonObject.getString(FILE_PATH);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
