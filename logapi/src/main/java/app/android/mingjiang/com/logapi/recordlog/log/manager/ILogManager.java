package app.android.mingjiang.com.logapi.recordlog.log.manager;

import android.app.Activity;

/**
 * 作者：wangzs on 2016/1/11 16:02
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public interface ILogManager {

    public boolean log(String tag, String msg, int logType);

    public boolean registerCrashHandler();

    public boolean unregisterCrashHandler();

    public boolean registerActivity(final Activity activity);

    public boolean unregisterActivity(final Activity activity);
}

