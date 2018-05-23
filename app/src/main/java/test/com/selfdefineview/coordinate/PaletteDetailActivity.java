package test.com.selfdefineview.coordinate;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;

import test.com.selfdefineview.R;

/**
 * Created by yifeng on 16/8/10.
 *
 */
public class PaletteDetailActivity extends AppCompatActivity {

    public static final String EXTRA_INDEX = "index";

    private CollapsingToolbarLayout mToolbarCtl;
    private ImageView mHeaderIv;
    private ScrollView mContentSv;
    public Toolbar mToolbarTb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //如果手机版本在4.4以上，就使用通知栏透明效果
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_palette_detail);

        mToolbarCtl = (CollapsingToolbarLayout) findViewById(R.id.ctl_toolbar);
        mHeaderIv = (ImageView) findViewById(R.id.iv_header);
        mContentSv = (ScrollView) findViewById(R.id.sv_content);

        ViewCompat.setNestedScrollingEnabled(mContentSv, true);

        int index = getIntent().getIntExtra(EXTRA_INDEX, 0);
        mHeaderIv.setImageResource(getResources().getIdentifier("ic_palette_0"+index%4, "mipmap", getPackageName()));
        palette(getResources().getIdentifier("ic_palette_0"+index%4, "mipmap", getPackageName()));

        mToolbarTb = (Toolbar) findViewById(R.id.tb_toolbar);
        if (mToolbarTb!=null) {
            setSupportActionBar(mToolbarTb);

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void palette(int res){
        Palette.from(BitmapFactory.decodeResource(getResources(), res))
                .generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        int color = palette.getDominantColor(ContextCompat.getColor(PaletteDetailActivity.this, R.color.blue));
                        int colorDark = palette.getDarkMutedColor(color);
                        int titleTextColor = palette.getDominantSwatch().getTitleTextColor();
                        mToolbarCtl.setContentScrimColor(color);
                        mToolbarCtl.setStatusBarScrimColor(colorDark);
                        mToolbarCtl.setCollapsedTitleTextColor(titleTextColor);
                        mToolbarCtl.setExpandedTitleColor(titleTextColor);
                        ToolbarColorizeHelper.colorizeToolbar(mToolbarTb, titleTextColor, PaletteDetailActivity.this);
                    }
                });
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mToolbarTb != null) {
            mToolbarTb.setTitle(title);
        }
    }

}
