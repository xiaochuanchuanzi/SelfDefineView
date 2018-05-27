package test.com.selfdefineview.http;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import test.com.selfdefineview.R;

/**
 * 正在加载时的弹框
 */
public class LoadingDialog extends Dialog{


    private ImageView laoding;
    private AnimationDrawable rocketAnimation;

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        laoding = (ImageView) findViewById(R.id.image_loading);

    }

    @Override
    protected void onStart() {
        super.onStart();
        rocketAnimation = (AnimationDrawable) laoding.getBackground();
        rocketAnimation.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        rocketAnimation.stop();
        rocketAnimation=null;
    }
}
