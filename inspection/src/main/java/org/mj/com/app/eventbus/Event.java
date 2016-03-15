package org.mj.com.app.eventbus;

//import com.mingjiang.android.kanban.entity.PostOperInstruction;
//import com.mingjiang.android.kanban.entity.SessionObj;

import java.util.List;
import java.util.Map;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class Event {

    public static final int ACTION_POST_SCAN = 0;           //岗位扫描
    public static final int ACTION_USER_SCAN = 1;           //用户扫描
    public static final int ACTION_CHECK_USER = 2;          //冰箱扫描
    public static final int ACTION_RESULT_SCAN = 3;   //检验结果扫描
    public static final int ACTION_LOAD_SERVER = 4;         //加载服务数据（JSON）
    public static final int ACTION_LOAD_SERVER_ERROR = 5;   //加载服务数据出错
    public static final int ACTION_LOAD_IMAGE = 6;          //加载操作步骤图片
    public static final int ACTION_LOAD_IMAGE_ERROR = 7;    //加载操作步骤图片出错

    protected String message;
    protected int actionType;
    private List<String> listMessage;
    private Map<String, String> mapMessage;
    private int count;
//    private PostOperInstruction postOperInstruction;
//    private SessionObj sessionObj;
    public Event(int actionType) {
        this.actionType = actionType;
    }

//    public Event(SessionObj sessionObj ,int actionType){
//        this.sessionObj = sessionObj;
//        this.actionType = actionType;
//    }

    public Event(String message, int actionType) {
        this.message = message;
        this.actionType = actionType;
    }
    public Event(int count,int actionType)
    {
        this.actionType = actionType;
        this.count = count;
    }
    public int getCount(){
        return count;
    }
//    public Event(PostOperInstruction instruction,int actionType){
//        this.postOperInstruction = instruction;
//        this.actionType = actionType;
//    }

    public Event(List<String> message, int actionType) {
        this.listMessage = message;
        this.actionType = actionType;
    }

    public Event(Map<String, String> message, int actionType) {
        this.mapMessage = message;
        this.actionType = actionType;
    }

    public int getActionType() {
        return actionType;
    }

    public String getMessage() {
        return message;
    }
//
//    public PostOperInstruction getPostOperInstruction()
//    {
//        return postOperInstruction;
//    }
//
//    public SessionObj getSessionObj(){return  this.sessionObj;}
    public List<String> getListMessage() {
        return listMessage;
    }

    public Map<String, String> getMapMessage() {
        return mapMessage;
    }
}
