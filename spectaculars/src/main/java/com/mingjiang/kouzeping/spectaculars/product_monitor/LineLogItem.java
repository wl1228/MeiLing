package com.mingjiang.kouzeping.spectaculars.product_monitor;

/**
 * Created by kouzeping on 2016/2/24.
 * email：kouzeping@shmingjiang.org.cn
 */
public class LineLogItem {
//    runtime：当天产线运行的时间（时间戳）
//    code：产线编码
//    lineState：产线当前状态
//    name：产线名称
//    id：产线日志记录表ID
    public String id;
    public String code;
    public String name;
    public int runtime;
    public String lineState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getLineState() {
        return lineState;
    }

    public void setLineState(String lineState) {
        this.lineState = lineState;
    }
}
