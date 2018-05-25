package test.com.selfdefineview.springappbarlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.com.selfdefineview.R;

public class SpringAppBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spling_main);
    }

    public void onNormalAppBarLayoutClick(View view) {
        startActivity(new Intent(this, NormalAppBarLayoutActivity.class));
    }

    public void onSpringAppBarLayoutClick(View view) {
        startActivity(new Intent(this, SpringAppBarLayoutActivity.class));
    }

    public void onSpringTabAppBarLayoutClick(View view) {
        startActivity(new Intent(this, SpringAppBarLayoutWithTabActivity.class));
    }
}
