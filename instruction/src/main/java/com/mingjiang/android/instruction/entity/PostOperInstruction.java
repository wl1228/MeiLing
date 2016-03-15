package com.mingjiang.android.instruction.entity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位作业指导书。
 * Created by wangzs on 2015/12/9.
 */
public class PostOperInstruction {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PRODUCTION_LINE = "production_line";
    public static final String APPLY_FACTORY = "apply_factory";
    public static final String PROTECTION_REQUIREMENT = "protection_requirement";
    public static final String POINT = "point";
    public static final String PRODUCTION_NOTES = "production_notes";
    public static final String TOOLS_EQUIPMENT = "tools_equipment";
    public static final String OPERATION_ID = "operation_id";
    public static final String MATARIAL_LIST = "matarial_list";

    public PostOperInstruction()
    {

    }

    public PostOperInstruction(JSONObject jsonObject)
    {
        try {
            this.id = jsonObject.getString(ID);
            this.name = jsonObject.getString(NAME);
            this.apply_factory = jsonObject.getString(APPLY_FACTORY);
            this.production_line = jsonObject.getString(PRODUCTION_LINE);
            this.operation_id = jsonObject.getString(OPERATION_ID);

            this.protection_requirement = jsonObject.getString(PROTECTION_REQUIREMENT);
            this.point = jsonObject.getString(POINT);
            this.production_notes = jsonObject.getString(PRODUCTION_NOTES);
            this.tools_equipment = jsonObject.getString(TOOLS_EQUIPMENT);
            this.matarial_list = jsonObject.getString(MATARIAL_LIST);


            JSONArray jArray = jsonObject.getJSONArray(OperStep.JSON_TITLE);
            this.steps.clear();
            for(int i=0;i<jArray.length();i++){
                JSONObject object  = jArray.getJSONObject(i);
                OperStep operStep = new OperStep(object);
                this.steps.add(operStep);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //数据库ID
    public String id = "";

    //指导书名称
    public String name = "";

    //生产线
    public String production_line = "";

    //适用工厂
    public String apply_factory = "";

    //防护要求
    public String protection_requirement = "";

    //工艺要点
    public String point = "";

    //上岗安全须知及注意事项
    public String production_notes = "";

    //工具/工装
    public String tools_equipment = "";

    //岗位名称
    public String operation_id = "";

    //部件清单
    public String matarial_list = "";

    //操作步骤
    public List<OperStep> steps = new ArrayList<OperStep>();

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
