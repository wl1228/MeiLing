package com.mingjiang.kouzeping.spectaculars.product_monitor;

/**
 * Created by kouzeping on 2016/2/25.
 * email：kouzeping@shmingjiang.org.cn
 *
 * prelineState：产线原状态
 * code：产线编码
 * notes：停线日志描述
 * operDate：停线开始时间
 * endDatetime：停线结束时间
 * lineState：产线当前状态
 * id：产线日志记录表ID
 * name：产线名称
 * user_id：操作员名称
 * user_name：操作员编码
 */
public class LineLogInfoItem {
    String prelineState;
    String code;
    String notes;
    String operDate;
    String endDatetime;
    String lineState;
    String id;
    String name;
    String user_id;
    String user_name;

    public String getPrelineState() {
        return prelineState;
    }

    public void setPrelineState(String prelineState) {
        this.prelineState = prelineState;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getLineState() {
        return lineState;
    }

    public void setLineState(String lineState) {
        this.lineState = lineState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
