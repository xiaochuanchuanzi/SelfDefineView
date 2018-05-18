package test.com.selfdefineview.coordinate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import test.com.selfdefineview.R;

/**
 * Created by zhangsixia on 18/5/14.
 * CoordinateLayout是用于协调子View之间的协作关系的
 */

public class CoordinateActivity extends AppCompatActivity {

    Button mBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinate_layout);
        mBtn = findViewById(R.id.button);
        mBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE){
                    v.setX(event.getRawX()-v.getWidth()/2);
                    v.setY(event.getRawY() - getStatusBarHeight() - getTitleBarHeight() - v.getHeight()/2);
                }
                return true;
            }
        });
    }
    /**
     * 获取状态栏的高度
     * @return
     */
    public int getStatusBarHeight(){
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
    /**
     * 获取布局标题头部的高度
     * @return
     */
    public int getTitleBarHeight(){
        int contentViewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentViewTop - getStatusBarHeight();
        return titleBarHeight;
    }
}
