package test.com.selfdefineview.util;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static test.com.selfdefineview.MyApplication.app;
/**
 * 本地化存储--SharedPreference
 */
public class SharedPreferencesHelper {
    /**
     * 保存到SharedPreferences的变量名
     */
    public static final String PREFS_NAME = "selfdefineview";
    /**
     * 初始化SharedPreferences
     */
    public static SharedPreferences shared;
    public static SharedPreferences getShared(){
        if(null != shared){
            return shared;
        }
        shared = app.getSharedPreferences(SharedPreferencesHelper.PREFS_NAME, 0);
        return shared;
    }
    /**
     * 初始化编辑器--SharedPreferences.Editor
     */
    public static SharedPreferences.Editor editor;
    public static SharedPreferences.Editor getEditor(){
        if(null != editor){
            return editor;
        }
        editor = getShared().edit();
        return editor;
    }
    /**
     * 设置参数
     */
    public static void setSetting(String name, List<?extends Object> value) {
        getEditor().putString(name,value.toString());
        getEditor().commit();
    }
    public static void setSetting(String name, String value) {
        getEditor().putString(name, value);
        getEditor().commit();
    }
    public static void setSetting(String name, int value) {
        getEditor().putInt(name, value);
        getEditor().commit();
    }
    public static void setSetting(String name, float value) {
        getEditor().putFloat(name, value);
        getEditor().commit();
    }
    public static void setSetting(String name, long value) {
        getEditor().putLong(name, value);
        getEditor().commit();
    }
    public static void setSetting(String name, boolean value) {
        getEditor().putBoolean(name, value);
        getEditor().commit();
    }
    //__________________________________________________________________________________________
    /**
     * 获取参数
     */
    public static List<String> getList(String name) {
        String tagStr = getShared().getString(name, "0");
        String[] array = tagStr.split(",");
        List<String> newStudentNames =java.util.Arrays.asList(array);
        return newStudentNames;
    }
    public static String getString( String name) {
        return getShared().getString(name, "0");
    }
    public static int getInt(String name) {
        return getShared().getInt(name, 0);
    }
    public static int getInt(String name,int defaultSize) {
        return getShared().getInt(name, defaultSize);
    }
    public static float getFloat(String name) {
        return getShared().getFloat(name, 0.0f);
    }
    public static long getLong(String name) {
        return getShared().getLong(name, 0);
    }
    public static boolean getBoolean(String name) {
        return getShared().getBoolean(name, false);
    }
    public static Map<String, ?> getAll(String name) {
        return getShared().getAll();
    }



}
