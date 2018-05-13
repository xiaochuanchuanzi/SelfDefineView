package test.com.selfdefineview.constraint;

import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import test.com.selfdefineview.R;

/**
 * Created by 立涛 on 2018/5/13.
 */

public class ConstraintActivity extends AppCompatActivity {
    private TextView tab1;
    private TextView tab2;
    private TextView tab3;
    private ConstraintLayout activity_main;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_layout);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        activity_main = findViewById(R.id.activity_main);
    }

    public void floatBtton(final View view){
        view.setBackgroundColor(getResources().getColor(R.color.red_oa));
        view.setOnTouchListener(new View.OnTouchListener() {
            float dx;
            float dy;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dx = event.getRawX();
                        dy = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.setX(event.getRawX() - dx);
                        view.setY(event.getRawY()- dy - getStatusBarHeight() - getTitleBarHeight());
                        break;
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
