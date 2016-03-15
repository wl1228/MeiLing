package com.mingjiang.android.instruction.entity;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * 操作步骤。
 * Created by wangzs on 2015/12/9.
 */
public class OperStep {

    public static final String STEM_ID = "step_id";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String JSON_TITLE = "steps";
    public static final String DESCRIPTION = "description";

    //检验ID
    public String step_id = "";

    //id号
    public String id = "";

    //检验名
    public String name = "";

    //检验图片
    public ImageMsg pic_path = null;

    public String description = "";

    public OperStep()
    {

    }

    public OperStep(JSONObject jsonObject)
    {
        try {
            this.step_id = jsonObject.getString(STEM_ID);
            this.id = jsonObject.getString(ID);
            this.name = jsonObject.getString(NAME);
            this.description = jsonObject.getString(DESCRIPTION);
            this.pic_path = new ImageMsg(jsonObject.getJSONObject(ImageMsg.JSON_TITLE));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
