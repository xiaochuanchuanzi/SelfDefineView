package test.com.selfdefineview.http;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
import test.com.selfdefineview.R;
import test.com.selfdefineview.util.SharedPreferencesHelper;
import test.com.selfdefineview.util.ToastHelper;

/**
 * Created by 立涛 on 2018/5/27.
 */

public class HttpActiviy extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_layout);
        httpRequest();
    }

    /**
     * 执行网络 请求
     */
    private void httpRequest() {
        String url = "";
        AppConstant.initPostParams().put("OrderId","OrderId");//患者ID
        HttpUtil.doPost(HttpActiviy.this, 1716, url, true,Object.class, new HttpCallBack() {
                    @Override
                    public <E> void onSuccess(int requestCode, String json, E e) {
                        Object applyCaseList = (Object) e;
                        /*if (applyCaseList.ErrorCode == 0) {

                        }*/
                    }
                    @Override
                    public void onfail(int requestCode, String errorType) {
                        ToastHelper.showToastCenterNow("请求数据失败");
                    }
                }
        );
    }
}
