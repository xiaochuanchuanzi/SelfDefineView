package test.com.selfdefineview.http;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import test.com.selfdefineview.MainActivity;
import test.com.selfdefineview.R;
import test.com.selfdefineview.util.LogHelper;
import test.com.selfdefineview.util.SharedPreferencesHelper;

/**
 * Created by 立涛 on 2018/5/27.
 * 网络访问工具类
 */
public class HttpUtil {
    private volatile static OkHttpClient instance; //声明成 volatile

    public static OkHttpClient getInstance() {
        if (instance == null) {//Single Checked
            synchronized (OkHttpClient.class) {
                if (instance == null) {//Double Checked
                    instance = new OkHttpClient
                            .Builder()
                            .connectTimeout(40, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .certificatePinner(new CertificatePinner
                                    .Builder()
                                    .add("publicobject.com", "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=")
                                    .build())
                            .build();
                }
            }
        }
        return instance;
    }

    /**
     * 取消某个请求
     */
    public static void cancelTag(Object tag) {
        for (Call call : getInstance().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getInstance().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /**
     * 取消所有请求
     */
    public static void cancelAllTag() {
        getInstance().dispatcher().cancelAll();
    }

    /**
     * JSON格式
     */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 于解决列表的刷新和loading同时存在的问题！
     */
    public static <T> void doPost(final Context context, final int flag, final String url, Map<String, Object> map,
                                  final boolean isShowLoading, final Class<T> clazz, final HttpCallBack httpCallBack) {
        //alertDialog(context);
        JSONObject Jsonobject = new JSONObject();
        List<KeyValue> paramList = new ArrayList<KeyValue>();
        for (String key : map.keySet()) {
            paramList.add(new KeyValue(key, map.get(key)));
        }
        try {
            params2Json(Jsonobject, paramList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String json = String.valueOf(Jsonobject);

        final LoadingDialog loadingDialog = new LoadingDialog(context, R.style.task_progress_dialog);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(true);
        Observable.just("0", url).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                if (s.equals("0")) {
                    return s;
                } else {
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder().url(s)
                            .post(body).tag(flag)
                            .build();
                    try {
                        Response response = getInstance().newCall(request).execute();
                        if (response.isSuccessful()) {
                            return response.body().string();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return "";
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            if (result.equals("0")) {
                                try {
                                    if (isShowLoading) {
                                        loadingDialog.show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    loadingDialog.dismiss();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                LogHelper.Log("POST请求", url);
                                LogHelper.Log("POST数据", json);
                                LogHelper.Log("POST返回", result);
                                Gson gson = new Gson();
                                try {
                                    JSONObject object = new JSONObject(result);
                                    if (object.getInt("ErrorCode") == 2 || object.getInt("ErrorCode") == 7) {
                                        logout(context,object);
                                    } else if (object.getInt("ErrorCode") == 4) {
                                        Toast.makeText(context, object.getString("ErrorMessage"), Toast.LENGTH_SHORT).show();//API内部错误
                                    } else if (object.getInt("ErrorCode") == 8) {
                                        Toast.makeText(context, object.getString("ErrorMessage"), Toast.LENGTH_SHORT).show();
                                    } else if (object.getInt("ErrorCode") == 9) {//处理升级中登录app异常的问题
                                        // alertDialog.show();
                                    } else {
                                        httpCallBack.onSuccess(flag, result, gson.fromJson(result, clazz));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            try {
                                if (loadingDialog.isShowing()) {
                                    loadingDialog.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            httpCallBack.onfail(flag, "无法连接服务器，请检查网络！");
                            LogHelper.Log("POST请求", url);
                            LogHelper.Log("POST数据", json);
                            LogHelper.Log("POST返回", "");
                        }
                    }
                });
    }

    /**
     *
     */
    private static void logout(Context context,JSONObject object) throws JSONException {
        SharedPreferencesHelper.setSetting("AccessToken", "");
        SharedPreferencesHelper.setSetting("UID", "");
        SharedPreferencesHelper.setSetting("ExpiresIn", "");
        SharedPreferencesHelper.setSetting("UserId", "");
        SharedPreferencesHelper.setSetting("WorkerCode", "");
        SharedPreferencesHelper.setSetting("WorkerName", "");
        SharedPreferencesHelper.setSetting("HospitalCode", "");
        SharedPreferencesHelper.setSetting("HospitalName", "");
        SharedPreferencesHelper.setSetting("HeadImageUrl", "");
        SharedPreferencesHelper.setSetting("IsFirstLogin", "");
        Intent intent = new Intent(context, MainActivity.class);
        if (!SharedPreferencesHelper.getBoolean("loginActivityRuning")) {
            Toast.makeText(context, object.getString("ErrorMessage"), Toast.LENGTH_SHORT).show();
            SharedPreferencesHelper.setSetting("loginActivityRuning", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public final static class ArrayItem extends KeyValue {
        public ArrayItem(String key, Object value) {
            super(key, value);
        }
    }

    //生成JSONObject 对象
    private static void params2Json(final JSONObject jsonObject, final List<KeyValue> paramList) throws JSONException {
        HashSet<String> arraySet = new HashSet<String>(paramList.size());
        LinkedHashMap<String, JSONArray> tempData = new LinkedHashMap<String, JSONArray>(paramList.size());
        for (int i = 0; i < paramList.size(); i++) {
            KeyValue kv = paramList.get(i);
            final String key = kv.key;
            if (TextUtils.isEmpty(key)) continue;
            JSONArray ja = null;
            if (tempData.containsKey(key)) {
                ja = tempData.get(key);
            } else {
                ja = new JSONArray();
                tempData.put(key, ja);
            }
            ja.put(RequestParamsHelper.parseJSONObject(kv.value));
            if (kv instanceof ArrayItem) {
                arraySet.add(key);
            }
        }
        for (Map.Entry<String, JSONArray> entry : tempData.entrySet()) {
            String key = entry.getKey();
            JSONArray ja = entry.getValue();
            if (ja.length() > 1 || arraySet.contains(key)) {
                jsonObject.put(key, ja);
            } else {
                Object value = ja.get(0);
                jsonObject.put(key, value);
            }
        }
    }

}
