package test.com.selfdefineview;

import android.app.Application;
import android.os.Environment;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.smtt.sdk.QbSdk;

import test.com.selfdefineview.lineanimator.ScreenUtils;
import test.com.selfdefineview.util.AppInfo;
import test.com.selfdefineview.util.LogHelper;
import test.com.selfdefineview.webviewtxt.ExceptionHandler;

/**
 * Created by zhangsixia on 18/5/17.
 */

public class MyApplication extends Application {

    public static MyApplication app;
    public static MyApplication getInstance() {
        return app;
    }

    //更新下载的app的保存地址
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/huarun/";
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        ScreenUtils.init(app);

        //初始化app基本信息
        AppInfo.init(app);

        //初始化TBS
        QbSdk.initX5Environment(this,null);
        ExceptionHandler.getInstance().initConfig(this);

        //设置log信息状态
        LogHelper.setLog(true);

    }
}
