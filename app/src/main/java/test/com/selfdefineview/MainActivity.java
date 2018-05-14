package test.com.selfdefineview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.com.selfdefineview.constraint.ConstraintActivity;
import test.com.selfdefineview.xiaokongtu.activity.XiaoKongTu2Activity;
import test.com.selfdefineview.xiaokongtu.activity.XiaoKongTuActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 点击进入销控图--自定义View组合
     * @param view
     */
    public void enterXiaoKongtu(View view){
        Intent intent = new Intent(MainActivity.this, XiaoKongTuActivity.class);
        startActivity(intent);
    }

    /**
     * 点击进入销控图--自定义View组合---在一个View内实现
     * @param view
     */
    public void enterXiaoKongtu2(View view){
        Intent intent = new Intent(MainActivity.this, XiaoKongTu2Activity.class);
        startActivity(intent);
    }

    /**
     * 点击进入ConstraintLayout的学习,减少嵌套层级,实现布局优化
     * @param view
     */
    public void enterXiaoKongtu3(View view){
        Intent intent = new Intent(MainActivity.this, ConstraintActivity.class);
        startActivity(intent);
    }

    /**
     * 点击进入CoordinatorLayout的学习页面
     * @param view
     */
    public void enterXiaoKongtu4(View view){
        Intent intent = new Intent(MainActivity.this, ConstraintActivity.class);
        startActivity(intent);
    }
}
