package test.com.selfdefineview.lineanimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;
import test.com.selfdefineview.*;
import test.com.selfdefineview.bottomnavigationview.BottomNavigationActivity;
import test.com.selfdefineview.cardview.CardActivity;
import test.com.selfdefineview.collapse.CollapseActivity;
import test.com.selfdefineview.constraint.ConstraintActivity;
import test.com.selfdefineview.coordinate.CoordinateActivity;
import test.com.selfdefineview.coordinate.CoordinatorActivity;
import test.com.selfdefineview.dragtime.DragTimeActivity;
import test.com.selfdefineview.excel.ExcelActivity;
import test.com.selfdefineview.springappbarlayout.SpringAppBarActivity;
import test.com.selfdefineview.textinputlayout.TextInputActivity;
import test.com.selfdefineview.updateapk.UpdateActivity;
import test.com.selfdefineview.webviewtxt.WebViewDocActivity;
import test.com.selfdefineview.wifi.WifiActivity;
import test.com.selfdefineview.xiaokongtu.activity.XiaoKongTu2Activity;
import test.com.selfdefineview.xiaokongtu.activity.XiaoKongTuActivity;

/**
 * 列表详情页
 */
public class DetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, SimpleAdapter.OnItemClick {
    public final static String VIEW_NAME_HEADER_IMAGE = "header_image";
    public final static String VIEW_NAME_HEADER_TITLE = "header_title";
    public final static String TITLE = "titleTextView";
    public final static String SUBTITLE = "subTitleView";
    public final static String IMAGEURL = "imageUrl";
    AppBarLayout appBar;
    CollapsingToolbarLayout collapsingToolbar;
    SimpleDraweeView bgImageView;
    SubtitleView subtitleView;//自定义view
    TextView titleTextView;
    Toolbar toolbar;
    RecyclerView recyclerView;//列表
    String title;
    String backgroundImage;
    String subTitle;
    Button button;
    List<String> list = new ArrayList<>();//数据集合
    private int subtitleMargin;
    private int minLength;
    private int maxTitleWidth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        bgImageView = (SimpleDraweeView) findViewById(R.id.bgImageView);
        subtitleView = (SubtitleView) findViewById(R.id.subTitle);
        titleTextView = (TextView) findViewById(R.id.title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        title = getIntent().getStringExtra(TITLE);
        subTitle = getIntent().getStringExtra(SUBTITLE);
        backgroundImage = getIntent().getStringExtra(IMAGEURL);
        button = (Button) findViewById(R.id.button);
        setSize();
        onAfterViews();
    }

    /**
     * 获取尺寸
     */
    public void setSize(){
        subtitleMargin = getResources().getDimensionPixelSize(R.dimen.subtitle_margin);
        minLength = getResources().getDimensionPixelSize(R.dimen.min_length);
        maxTitleWidth = ScreenUtils.screenWidth - subtitleMargin * 2 - minLength * 2;
    }

    /**
     * 分装数据
     */
    public void setData(){
        list.add("销控图--自定义View组合");
        list.add("销控图--自定义View组合单个View");
        list.add("Constraint减少嵌套层级,实现布局优化");
        list.add("CoordinatorLayout的学习页面1");
        list.add("CoordinatorLayout的学习页面2");
        list.add("WIFI列表页面");
        list.add("App跳转到另一个App,跳转到下载页面");
        list.add(" 拖动时间选择页面");
        list.add(" CardView页面");
        list.add(" EXCEL表格页面");
        list.add(" Collapse页面");
        list.add(" 登陆页面");
        list.add(" 底部导航栏页面");
        list.add(" webView加载各种文档页面");
        list.add(" App更新页面");
        list.add("SpringAppBarActivity页面");
    }

    /**
     * 初始化控件
     */
    public void initWidget(){
        //设置支持TitleBar
        setSupportActionBar(toolbar);
        //设置显示返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置颜色--为白色
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        //点击--执行返回操作
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ViewCompat.setTransitionName(bgImageView, VIEW_NAME_HEADER_IMAGE);
        ViewCompat.setTransitionName(titleTextView, VIEW_NAME_HEADER_TITLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//解决SimpleDraweeView不能加载图片问题
            getWindow().setSharedElementEnterTransition(
                    DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                            ScalingUtils.ScaleType.CENTER_CROP)); // 进入
            getWindow().setSharedElementReturnTransition(
                    DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                            ScalingUtils.ScaleType.CENTER_CROP)); // 返回
        }
    }

    void onAfterViews() {
        setData();
        initWidget();
        collapsingToolbar.setTitle(" ");
        titleTextView.setText(title);
        if (!TextUtils.isEmpty(subTitle)) {
            ((CollapsingToolbarLayout.LayoutParams) titleTextView.getLayoutParams()).setParallaxMultiplier(0.65f);
            subtitleView.setVisibility(View.VISIBLE);
            subtitleView.setText(subTitle);
            subtitleView.setTextColor(0x00ffffff);
            subtitleView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    subtitleView.animate(1);
                    appBar.addOnOffsetChangedListener(DetailActivity.this);
                }
            }, 500);
            titleTextView.post(new Runnable() {
                @Override
                public void run() {
                    calculateSubTitle();
                }
            });
        } else {
            titleTextView.setY(ScreenUtils.dip2px(16));
            ((CollapsingToolbarLayout.LayoutParams) titleTextView.getLayoutParams()).setParallaxMultiplier(0.5f);
            subtitleView.setVisibility(View.GONE);
        }
        bgImageView.setImageURI(backgroundImage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SimpleAdapter simpleAdapter = new SimpleAdapter(list);
        recyclerView.setAdapter(simpleAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subtitleView.setPercent(0);
                subtitleView.animate(1);
            }
        });
        simpleAdapter.setInterface(this);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (!TextUtils.isEmpty(subTitle)) {
            float value = appBar.getHeight() - toolbar.getHeight() * 2;
            float p = (value + verticalOffset) / value;
            if (p < 0) {
                p = 0;
            }
            subtitleView.setPercent(p);
        }
    }

    /**
     * 计算subtitleView
     */
    public void calculateSubTitle() {
        int titleWidth = titleTextView.getWidth();
        if (titleWidth > maxTitleWidth) {
            titleTextView.getLayoutParams().width = maxTitleWidth;
            titleTextView.requestLayout();
            subtitleView.getLayoutParams().width = maxTitleWidth + minLength * 2;
            subtitleView.setLength(minLength * 2);
            subtitleView.requestLayout();
        } else if (subtitleView.getWidth() > ScreenUtils.screenWidth - subtitleMargin * 2) {
            subtitleView.getLayoutParams().width = ScreenUtils.screenWidth - subtitleMargin * 2;
            subtitleView.setLength(subtitleView.getLayoutParams().width - titleWidth);
            subtitleView.requestLayout();
        } else if (subtitleView.getWidth() < titleWidth + minLength * 2) {
            subtitleView.getLayoutParams().width = titleWidth + minLength * 2;
            subtitleView.setLength(subtitleView.getLayoutParams().width - titleWidth);
            subtitleView.requestLayout();
        } else {
            subtitleView.setLength(subtitleView.getLayoutParams().width - titleWidth);
            subtitleView.requestLayout();
        }
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}
    /**
     * 按下返回按钮时,subtitleView隐藏
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        subtitleView.setVisibility(View.GONE);
    }
    /**
     * 跳转到另一个Activity或者跳转到下载页面
     */
    public void openApp() {
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
            intent.setComponent(new ComponentName("com.taihuahui", "com.taihuahui.module.start.AdvertisementActivity"));
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
    /**
     * 点击条目进入的Acitivyt
     */
    @Override
    public void setOnItemClickListener(int position) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(DetailActivity.this, XiaoKongTuActivity.class);
                break;
            case 1:
                intent.setClass(DetailActivity.this, XiaoKongTu2Activity.class);
                break;
            case 2:
                intent.setClass(DetailActivity.this, ConstraintActivity.class);
                break;
            case 3:
                intent.setClass(DetailActivity.this, CoordinatorActivity.class);
                break;
            case 4:
                intent.setClass(DetailActivity.this, CoordinateActivity.class);
                break;
            case 5:
                intent.setClass(DetailActivity.this, WifiActivity.class);
                break;
            case 6:
                openApp();
                break;
            case 7:
                intent.setClass(DetailActivity.this, DragTimeActivity.class);
                break;
            case 8:
                intent.setClass(DetailActivity.this, CardActivity.class);
                break;
            case 9:
                intent.setClass(DetailActivity.this, ExcelActivity.class);
                break;
            case 10:
                intent.setClass(DetailActivity.this, CollapseActivity.class);
                break;
            case 11:
                intent.setClass(DetailActivity.this, TextInputActivity.class);
                break;
            case 12:
                intent.setClass(DetailActivity.this, BottomNavigationActivity.class);
                break;
            case 13:
                intent.setClass(DetailActivity.this, WebViewDocActivity.class);
                break;
            case 14:
                intent.setClass(DetailActivity.this, UpdateActivity.class);
                break;
            case 15:
                intent.setClass(DetailActivity.this, SpringAppBarActivity.class);
                break;
            case 16:
                intent.setClass(DetailActivity.this, UpdateActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
