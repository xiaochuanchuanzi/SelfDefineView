package test.com.selfdefineview;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

import test.com.selfdefineview.webviewtxt.ExceptionHandler;

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
        //增加这句话
        QbSdk.initX5Environment(this,null);
        ExceptionHandler.getInstance().initConfig(this);
    }
}
