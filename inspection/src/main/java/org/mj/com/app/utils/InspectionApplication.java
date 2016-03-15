package org.mj.com.app.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by kouzeping on 2016/2/24.
 * email：kouzeping@shmingjiang.org.cn
 */
public class InspectionApplication extends Application {
    private static InspectionApplication application;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        application = InspectionApplication.this;
    }
    public static InspectionApplication getApplication() {
        return application;
    }

    public Context getContext() {
        context=application.getApplicationContext();
        return context;
    }
}
