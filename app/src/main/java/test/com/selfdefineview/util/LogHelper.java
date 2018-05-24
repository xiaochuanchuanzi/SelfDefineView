package test.com.selfdefineview.util;

public class LogHelper {

    public static boolean canLog = true;

    public final static int LOGI = 0;
    public final static int LOGE = 1;

    public static void Log(String tag, String msg, int type) {
        if (canLog) {
            switch (type) {
                case LOGI:
                    android.util.Log.i(tag, msg);
                    break;
                case LOGE:
                    android.util.Log.e(tag, msg);
                    break;
                default:
                    android.util.Log.i(tag, msg);
                    break;
            }
        }
    }

    public static void Log(String tag, String msg) {
        if (canLog) {
            android.util.Log.i(tag, msg);
        }
    }
}
