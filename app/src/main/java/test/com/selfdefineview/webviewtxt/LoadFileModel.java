package test.com.selfdefineview.webviewtxt;

import android.text.TextUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 12457 on 2017/8/21.
 */

public class LoadFileModel {


    public static void loadPdfFile(String url, Callback<ResponseBody> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoadFileApi mLoadFileApi = retrofit.create(LoadFileApi.class);
        if (!TextUtils.isEmpty(url)) {
            Call<ResponseBody> call = mLoadFileApi.loadPdfFile(url);
            call.enqueue(callback);
        }

    }
    //https://ysoa.qiban.com/img/2018/05/21_13/%E5%AF%BC%E5%85%A5%E6%A8%A1%E6%9D%BF%E7%9A%84%E5%89%AF%E6%9C%AC.xlsx#-3#1
}
