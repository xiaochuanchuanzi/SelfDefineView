package test.com.selfdefineview;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.com.selfdefineview.cardview.CardActivity;
import test.com.selfdefineview.constraint.ConstraintActivity;
import test.com.selfdefineview.coordinate.CoordinateActivity;
import test.com.selfdefineview.coordinate.CoordinatorActivity;
import test.com.selfdefineview.dragtime.DragTimeActivity;
import test.com.selfdefineview.excel.ExcelActivity;
import test.com.selfdefineview.wifi.WifiActivity;
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
    public void enterXiaoKongtu10(View view){
        Intent intent = new Intent(MainActivity.this, CoordinatorActivity.class);
        startActivity(intent);
    }

    /**
     * 点击进入CoordinatorLayout的学习页面
     * @param view
     */
    public void enterXiaoKongtu4(View view){
        Intent intent = new Intent(MainActivity.this, CoordinateActivity.class);
        startActivity(intent);
    }

    /**
     * 点击进入CoordinatorLayout的学习页面
     * @param view
     */
    public void enterXiaoKongtu5(View view){
        Intent intent = new Intent(MainActivity.this, WifiActivity.class);
        startActivity(intent);
    }

    /**
     * 从一个App跳转到另一个App,或者跳转到下载页面
     * @param view
     */
    public void enterXiaoKongtu6(View view){
        openApp();
    }

    /**
     * 点击进入拖动时间选择页面
     * @param view
     */
    public void enterXiaoKongtu7(View view){
        Intent intent = new Intent(MainActivity.this, DragTimeActivity.class);
        startActivity(intent);
    }

    /**
     * 点击进入拖动时间选择页面
     * @param view
     */
    public void enterXiaoKongtu8(View view){
        Intent intent = new Intent(MainActivity.this, CardActivity.class);
        startActivity(intent);
    }
    /**
     * 点击进入EXCEL表格页面
     * @param view
     */
    public void enterXiaoKongtu9(View view){
        Intent intent = new Intent(MainActivity.this, ExcelActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到另一个Activity或者跳转到下载页面
     */
    public void openApp(){
        ApplicationInfo info = null;
        try {
            info = this.getPackageManager().getApplicationInfo("com.taihuahui", PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info == null) {
            //跳转到官网下载链接
            Uri uri = Uri.parse("market://details?id=" + "com.tencent.mm");
            //改处写APP的包名即可
            Intent intentwx = new Intent(Intent.ACTION_VIEW, uri);
            intentwx.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentwx);
        } else {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.taihuahui","com.taihuahui.module.start.AdvertisementActivity"));
            int launchFlags = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED;
            intent.setFlags(launchFlags);
            intent.setAction("android.intent.action.VIEW");
            /*Bundle bundle = new Bundle();
            bundle.putString("uid", "来自测试应用");
            bundle.putString("upwd", "来自测试应用");
            intent.putExtras(bundle);*/
            startActivity(intent);
        }
    }
}
