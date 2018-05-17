package test.com.selfdefineview;

import android.app.Application;

/**
 * Created by zhangsixia on 18/5/17.
 */

public class MyApplication extends Application {

    public static MyApplication app;
    public static MyApplication getInstance() {
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
