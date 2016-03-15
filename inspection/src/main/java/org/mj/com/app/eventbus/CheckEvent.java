package org.mj.com.app.eventbus;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class CheckEvent extends Event {
    public CheckEvent(String message, int actionType) {
        super(message,actionType);
    }
}
