package test.com.selfdefineview.http;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import test.com.selfdefineview.util.SharedPreferencesHelper;

import static test.com.selfdefineview.MyApplication.app;


public class AppConstant {
    public static boolean getIsTest() {
        ApplicationInfo appInfo = null;
        try {
            appInfo = app.getPackageManager().getApplicationInfo(app.getPackageName(), PackageManager.GET_META_DATA);
            int ENVIRONMENT = appInfo.metaData.getInt("DADA_ENVIRONMENT");
            if (ENVIRONMENT == 0) {
                return true;
            } else {
                return false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static Map<String, Object> hashMapobj = new HashMap<>();
    public static Map<String, Object> initPostParams() {
        hashMapobj.clear();
        hashMapobj.put("WorkerName", SharedPreferencesHelper.getString("WorkerName"));
        hashMapobj.put("WorkerCode", SharedPreferencesHelper.getString("WorkerCode"));
        return hashMapobj;
    }

    public static JSONObject getBaseJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("WorkerName", SharedPreferencesHelper.getString("WorkerName"));
            jsonObject.put("WorkerCode", SharedPreferencesHelper.getString("WorkerCode"));
            jsonObject.put("HospitalCode", SharedPreferencesHelper.getString("HospitalCode"));
            jsonObject.put("UID", SharedPreferencesHelper.getString("UID"));
            jsonObject.put("AccessToken", SharedPreferencesHelper.getString("AccessToken"));
            jsonObject.put("SourseType", "3");
            jsonObject.put("PlatformId", "1000"); /// 1000 云诊所，2000爱牙分期，3000 B2B商城
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
