package test.com.selfdefineview.lineanimator;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import test.com.selfdefineview.util.LogHelper;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_IN;
import static android.util.TypedValue.COMPLEX_UNIT_MM;
import static android.util.TypedValue.COMPLEX_UNIT_PT;
import static android.util.TypedValue.COMPLEX_UNIT_PX;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by hwt on 2017/8/28.
 */
public class ScreenUtils {
    // 当前屏幕的densityDpi
    public static float density = 0.0f;
    //密度等级
    public static float scale = 0.0f;
    public static int screenWidth, screenHeight;

    static DisplayMetrics displayMetrics;
    public static void init(Context context) {
        if (context == null) return;
        Resources resources = context.getResources();
        if (resources == null) return;
        displayMetrics = resources.getDisplayMetrics();
        if (displayMetrics == null) return;
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        scale = displayMetrics.density;//3.0
        // 密度因子
        //scale = density / 160;
        LogHelper.Log("DisplayMetrics","   "+displayMetrics.toString());
    }

    /**
     * 密度转换像素
     */
    public static int dip2px(float dipValue) {
        // int px = (int) (dipValue * scale + 0.5f);
        int px = (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, displayMetrics);
        return px;
    }

    /**
     * 像素转换密度
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / scale + 0.5f);
    }

    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            //px：pixel
            case COMPLEX_UNIT_PX://px
                return value;
            case COMPLEX_UNIT_DIP://dp
                return value * metrics.density;
            case COMPLEX_UNIT_SP://sp
                return value * metrics.scaledDensity;
            case COMPLEX_UNIT_PT://pt ： 1/72英寸
                return value * metrics.xdpi * (1.0f / 72);
            case COMPLEX_UNIT_IN://in： inch 英寸
                return value * metrics.xdpi;
            case COMPLEX_UNIT_MM://mm ： 毫米 1英寸=25.4毫米
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

}
