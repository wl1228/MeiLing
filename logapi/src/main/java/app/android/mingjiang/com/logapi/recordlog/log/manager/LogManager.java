package app.android.mingjiang.com.logapi.recordlog.log.manager;

import android.content.Context;

/**
 * 作者：wangzs on 2016/1/11 16:02
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class LogManager {
    private static ILogManager sManager;

    public synchronized static ILogManager getManager(Context context) {
        if (context == null) {
            return null;
        }

        if (sManager == null) {
            sManager = new LogManagerImpl(context);
        }

        return sManager;
    }
}

