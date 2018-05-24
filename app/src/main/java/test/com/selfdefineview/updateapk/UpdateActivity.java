package test.com.selfdefineview.updateapk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.com.selfdefineview.R;
import test.com.selfdefineview.util.PopWindowHelper;

/**
 * Created by zhangsixia on 18/5/24.
 */

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_layout);

    }

    public void click(View view){
        updateApk();
    }

    /**
     * 检查版本
     */
    public void updateApk(){
        //double oldVersion = Double.parseDouble(AppInfo.VERSION_NAME);//本地获取
        //double newVersion = Double.parseDouble(data.data.versionNo);//网络获取
        double oldVersion = 1.0;
        double newVersion = 1.2;
        if (newVersion > oldVersion) {
            UpdateAppView updateAppView = new UpdateAppView(UpdateActivity.this, new UpdateVersionSM(), true);
            PopWindowHelper.showCenter(UpdateActivity.this, updateAppView, getWindow().getDecorView().getRootView());
        } else {
            //更新指令表
        }
    }
}
