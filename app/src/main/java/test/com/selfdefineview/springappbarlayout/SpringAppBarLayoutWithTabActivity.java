package test.com.selfdefineview.springappbarlayout;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayoutSpringBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabScrimHelper;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.mmin18.widget.RealtimeBlurView;

import test.com.selfdefineview.R;

public class SpringAppBarLayoutWithTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_app_bar_tab_layout);
        //初始化Toolbar控件
        Toolbar toolbar = findViewById(R.id.toolbar);
        //设置支持toolbar功能
        setSupportActionBar(toolbar);
        //为标题的左上角添加一个图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //获取CollapsingToolbarLayout布局
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        //设置图片模糊效果的布局View
        final RealtimeBlurView realtimeBlurView = findViewById(R.id.real_time_blur_view);
        //初始化获取AppBarLayout布局view
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        //获取滚动行为   Behavior
        AppBarLayoutSpringBehavior springBehavior = (AppBarLayoutSpringBehavior) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
        //在滚动的Behavior事件的回调中,设置模糊效果的动态变化
        springBehavior.setSpringOffsetCallback(new AppBarLayoutSpringBehavior.SpringOffsetCallback() {
            @Override
            public void springCallback(int offset) {
                int radius = 20 * (240 - offset > 0 ? 240 - offset : 0) / 240;
                //根据滑动,动态设置图片的模糊效果
                realtimeBlurView.setBlurRadius(radius);
            }
        });
        //初始化ViewPager和TabLayout布局控件
        ViewPager viewPager = findViewById(R.id.tabs_viewpager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        //将ViewPager添加到TabLayout中去
        tabLayout.setupWithViewPager(viewPager);
        //为ViewPager设置适配器
        viewPager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager()));

        TabScrimHelper tabScrimHelper = new TabScrimHelper(tabLayout, collapsingToolbarLayout);
        appBarLayout.addOnOffsetChangedListener(tabScrimHelper);
    }
}
