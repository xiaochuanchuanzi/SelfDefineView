
package test.com.selfdefineview.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

/**
 * app版本管理
 *
 * @author anjihang
 */
public class AppInfo {

    /**
     * app版本号
     */
    public static int VERSION_CODE;

    /**
     * 手机imei
     */
    public static String IMEI = "";

    /**
     * app版本名称
     */
    public static String VERSION_NAME = "";

    /**
     * 手机屏幕宽度
     */
    public static int SCREEN_WIDTH = 0;

    /**
     * 手机屏幕高度
     */
    public static int SCREEN_HEIGHT = 0;

    /**
     * 手机屏幕像素密度
     */
    public static float SCREEN_DENSITY = 0;

    /**
     * 获取app版本信息
     */
    public static void init(Context app) {
        PackageInfo pinfo = null;
        try {
            pinfo = app.getPackageManager().getPackageInfo(app.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            VERSION_NAME = pinfo.versionName;
            VERSION_CODE = pinfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        SCREEN_WIDTH = app.getResources().getDisplayMetrics().widthPixels;
        SCREEN_HEIGHT = app.getResources().getDisplayMetrics().heightPixels;
        SCREEN_DENSITY = app.getResources().getDisplayMetrics().density;
    }

    public static void getImei(Context app) {
        TelephonyManager tm = (TelephonyManager) app.getSystemService(Activity.TELEPHONY_SERVICE);
        IMEI = tm.getDeviceId();
    }
}
