package test.com.selfdefineview.coordinate;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import test.com.selfdefineview.R;

/**
 * Created by 立涛 on 2018/5/21.
 */

public class CoordinatorActivity extends AppCompatActivity {

    private TabLayout mIndicatorTl;
    private ViewPager mContentVp;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    private ActionBarDrawerToggle drawerToggle;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_layout);

        mContentVp = (ViewPager) findViewById(R.id.vp_content);

        initContent();
        initTab();
    }

    /**
     * 获取屏幕的宽高的方法
     * @return
     */
    public int[] getScreenSize(){
        int screenSize[] = new int[2];
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenSize[0] = displayMetrics.widthPixels;
        screenSize[1] = displayMetrics.heightPixels;
        return screenSize;
    }

    public void onClickFab(View v){
        Snackbar.make(findViewById(R.id.fab_add), "Show The Snackbar", Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 顶部tab导航条
     */
    private void initTab(){
        mIndicatorTl = (TabLayout) findViewById(R.id.tl_indicator);
        mIndicatorTl.setTabMode(TabLayout.MODE_FIXED);
        mIndicatorTl.setTabTextColors(ContextCompat.getColor(this, R.color.gray), ContextCompat.getColor(this, R.color.white));
        mIndicatorTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white));
        //API4以后引入的实现兼容的一个帮助类ViewCompat，下面的方法是兼容到10的版本
        ViewCompat.setElevation(mIndicatorTl, 10);
        mIndicatorTl.setupWithViewPager(mContentVp);
    }

    private void initContent(){
        tabIndicators = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tabIndicators.add("Tab " + i);
        }
        tabFragments = new ArrayList<>();
        for (String s : tabIndicators) {
            tabFragments.add(TabListFragment.newInstance(s));
        }
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }

    }
}
