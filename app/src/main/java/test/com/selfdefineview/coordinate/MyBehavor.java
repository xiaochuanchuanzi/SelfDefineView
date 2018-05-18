package test.com.selfdefineview.coordinate;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zhangsixia on 18/5/18.
 */

public class MyBehavor extends CoordinatorLayout.Behavior<TextView> {

    private int screenWidth;
    public MyBehavor(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        screenWidth = display.widthPixels;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        //返回false表示child不依赖dependency，ture表示依赖
        return dependency instanceof Button;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        //根据dependency的位置，设置child的位置
        child.setX(dependency.getX()+200);
        child.setY(dependency.getY()+200);
        return true;
        /* int top = dependency.getTop();
        int left = dependency.getLeft();
        int x = screenWidth - left - child.getWidth();
        int y = top - 800;
        setPosition(child, x, y);
        return true;*/
    }


    private void setPosition(View v, int x, int y) {
        CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) v.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        v.setLayoutParams(layoutParams);
    }

}
